package backend.academy.logAnalyzer.analyzer;

import backend.academy.logAnalyzer.dto.Command;
import backend.academy.logAnalyzer.dto.GeneralInformation;
import backend.academy.logAnalyzer.dto.Log;
import backend.academy.logAnalyzer.dto.ProcessingResponse;
import backend.academy.logAnalyzer.dto.Request;
import backend.academy.logAnalyzer.dto.RequestStatistics;
import backend.academy.logAnalyzer.enums.FilterField;
import backend.academy.logAnalyzer.enums.HttpMethod;
import backend.academy.logAnalyzer.enums.HttpStatus;
import backend.academy.logAnalyzer.exception.ExitFromCurrentDirectoryException;
import backend.academy.logAnalyzer.exception.FileDataReadException;
import backend.academy.logAnalyzer.exception.FindLocalFilesException;
import backend.academy.logAnalyzer.exception.LogFilesNotFoundException;
import backend.academy.logAnalyzer.exception.UrlDataReadException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Duration;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static backend.academy.logAnalyzer.constants.ConstValues.BASE_PATH;
import static backend.academy.logAnalyzer.constants.ConstValues.STATISTICS_LIMIT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NULL_COMMAND_EXCEPTION_TEXT;

public class CommandHandler {

    private Map<String, Integer> resources;
    private Map<HttpStatus, Integer> httpStatuses;
    private Map<String, Integer> addresses;
    private Map<HttpMethod, Integer> methods;
    private List<BigInteger> requestsBytes;

    public ProcessingResponse process(Command command) {
        if (command == null) {
            throw new NullPointerException(NULL_COMMAND_EXCEPTION_TEXT);
        }

        initializeStatistics();

        final String urlBeginning = "http";
        final Set<String> filePaths;

        if (command.filePath().startsWith(urlBeginning)) {
            filePaths = Set.of(command.filePath());
            processLogsFromUrl(command.filePath(), command);
        } else {
            filePaths = findLocalFiles(command.filePath());

            if (filePaths.isEmpty()) {
                throw new LogFilesNotFoundException(command.filePath());
            }
            for (String filePath : filePaths) {
                processLogsFromLocalFile(filePath, command);
            }
        }

        final int requestsCount = requestsBytes.size();
        final BigInteger bytesCount = new BigInteger(String.valueOf(getSum(requestsBytes)));

        final Map<String, Integer> sortedLimitedResources = getLimitedSortedMap(resources);
        final Map<HttpStatus, Integer> sortedLimitedHttpStatuses = getLimitedSortedMap(httpStatuses);
        final Map<String, Integer> sortedLimitedAddresses = getLimitedSortedMap(addresses);
        final Map<HttpMethod, Integer> sortedLimitedMethods = getLimitedSortedMap(methods);

        final RequestStatistics requestStatistics = new RequestStatistics(
            sortedLimitedResources, sortedLimitedHttpStatuses, sortedLimitedAddresses, sortedLimitedMethods
        );
        final GeneralInformation generalInformation = new GeneralInformation(
            filePaths, command.from(), command.to(), requestsCount,
            getResponseAverageSize(bytesCount, requestsCount), getPercentile(requestsBytes)
        );

        return new ProcessingResponse(generalInformation, requestStatistics);
    }

    private <T> Map<T, Integer> getLimitedSortedMap(Map<T, Integer> map) {
        return map.entrySet().stream().sorted(Map.Entry.<T, Integer>comparingByValue().reversed())
            .limit(STATISTICS_LIMIT)
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (oldValue, newValue) -> oldValue,
                LinkedHashMap::new
            ));
    }

    private void processLogsFromUrl(String filePath, Command command) {
        final int timeoutValueSeconds = 10;
        final HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(filePath))
            .timeout(Duration.ofSeconds(timeoutValueSeconds))
            .GET()
            .build();

        try (HttpClient httpClient = HttpClient
            .newBuilder()
            .connectTimeout(Duration.ofSeconds(timeoutValueSeconds))
            .build()
        ) {
            final HttpResponse<Stream<String>> response
                = httpClient.send(request, HttpResponse.BodyHandlers.ofLines());

            processLogs(response.body(), command);
        } catch (Exception ex) {
            throw new UrlDataReadException(ex);
        }
    }

    private void processLogsFromLocalFile(String filePath, Command command) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath, StandardCharsets.UTF_8))) {
            processLogs(reader.lines(), command);
        } catch (IOException ex) {
            throw new FileDataReadException(ex);
        }
    }

    private void processLogs(Stream<String> logs, Command command) {
        logs
            .map(LogParser::parse)
            .filter(log -> isValidForFilter(command, log))
            .forEach(this::updateStatistics);
    }

    private void initializeStatistics() {
        resources = new HashMap<>();
        httpStatuses = new EnumMap<>(HttpStatus.class);
        addresses = new HashMap<>();
        methods = new EnumMap<>(HttpMethod.class);
        requestsBytes = new ArrayList<>();
    }

    private void updateStatistics(Log log) {
        final Request request = log.request();

        requestsBytes.add(log.bodyBytesSend());
        resources.put(request.resource(), resources.getOrDefault(request.resource(), 0) + 1);
        httpStatuses.put(log.httpStatus(), httpStatuses.getOrDefault(log.httpStatus(), 0) + 1);
        addresses.put(log.remoteAddress(), addresses.getOrDefault(log.remoteAddress(), 0) + 1);
        methods.put(request.httpMethod(), methods.getOrDefault(request.httpMethod(), 0) + 1);
    }

    private boolean isValidForFilter(Command command, Log log) {
        if (command.from() != null
            && log.timeLocal().isBefore(command.from().atStartOfDay(ZoneId.systemDefault()))) {
            return false;
        }

        if (command.to() != null
            && log.timeLocal().isAfter(command.to().atStartOfDay(ZoneId.systemDefault()))) {
            return false;
        }

        return command.filterField() == null
            || isValidForFilterField(command.filterField(), command.filterValue(), log);
    }

    private BigDecimal getResponseAverageSize(BigInteger bytesCount, int requestsCount) {
        if (requestsCount == 0) {
            return BigDecimal.ZERO;
        }

        final int scale = 2;

        return new BigDecimal(bytesCount)
            .divide(BigDecimal.valueOf(requestsCount), scale, RoundingMode.HALF_UP);
    }

    private BigInteger getPercentile(List<BigInteger> list) {
        if (list.isEmpty()) {
            return BigInteger.ZERO;
        }

        final List<BigInteger> sortedList = new ArrayList<>(list.stream().sorted().toList());
        final double percentileRatio = 0.95;
        final int index = (int) Math.ceil(percentileRatio * list.size()) - 1;

        return sortedList.get(index);
    }

    private BigInteger getSum(List<BigInteger> list) {
        return list.stream()
            .reduce(BigInteger.ZERO, BigInteger::add);
    }

    private boolean isValidForFilterField(FilterField filterField, String filterValue, Log log) {
        return switch (filterField) {
            case ADDRESS -> log.remoteAddress().equalsIgnoreCase(filterValue);
            case USER -> log.remoteUser().equalsIgnoreCase(filterValue);
            case HTTP_METHOD -> log.request().httpMethod().toString().equalsIgnoreCase(filterValue);
            case AGENT -> log.httpUserAgent().toLowerCase().contains(filterValue.toLowerCase());
            case HTTP_STATUS -> log.httpStatus().code() == Integer.parseInt(filterValue);
        };
    }

    private Set<String> findLocalFiles(String path) {
        final String movePreviousDirectoryCommand = "../";

        if (path.contains(movePreviousDirectoryCommand)) {
            throw new ExitFromCurrentDirectoryException();
        }

        if (!containGlobSymbols(path)) {
            return path.contains(BASE_PATH)
                ? Set.of(path)
                : Set.of(BASE_PATH + path);
        }

        final Set<String> logFiles = new HashSet<>();
        final String globText = "glob:";
        final Path baseDirectory = Path.of(BASE_PATH);

        String newPath = path;

        if (!newPath.contains(BASE_PATH)) {
            newPath = BASE_PATH + newPath;
        }

        newPath = newPath.replace("\\", "\\\\");

        final PathMatcher matcher = FileSystems.getDefault().getPathMatcher(globText + newPath);

        try {
            Files.walkFileTree(baseDirectory,
                EnumSet.noneOf(FileVisitOption.class), Integer.MAX_VALUE,
                new SimpleFileVisitor<>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                        if (matcher.matches(file)) {
                            logFiles.add(file.toString());
                        }
                        return FileVisitResult.CONTINUE;
                    }
                });
        } catch (IOException ex) {
            throw new FindLocalFilesException(ex);
        }

        return logFiles;
    }

    private boolean containGlobSymbols(String path) {
        final List<Character> globSymbols = List.of('*', '?', '[', ']');

        for (int i = 0; i < path.length(); i++) {
            if (globSymbols.contains(path.charAt(i))) {
                return true;
            }
        }

        return false;
    }
}
