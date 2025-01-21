package backend.academy.logAnalyzer.analyzer;

import backend.academy.logAnalyzer.dto.GeneralInformation;
import backend.academy.logAnalyzer.enums.HttpMethod;
import backend.academy.logAnalyzer.enums.HttpStatus;
import backend.academy.logAnalyzer.enums.OutputFormat;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.experimental.UtilityClass;
import static backend.academy.logAnalyzer.constants.ConstValues.ADDRESSES_TEXT;
import static backend.academy.logAnalyzer.constants.ConstValues.DEFAULT_FORMAT;
import static backend.academy.logAnalyzer.constants.ConstValues.END_DATE_OUTPUT_TEXT;
import static backend.academy.logAnalyzer.constants.ConstValues.FILES_OUTPUT_TEXT;
import static backend.academy.logAnalyzer.constants.ConstValues.GENERAL_INFORMATION_TEXT;
import static backend.academy.logAnalyzer.constants.ConstValues.HEADER_SYMBOLS_COUNT;
import static backend.academy.logAnalyzer.constants.ConstValues.HTTP_METHODS_TEXT;
import static backend.academy.logAnalyzer.constants.ConstValues.NO_VALUE_IN_STATISTICS;
import static backend.academy.logAnalyzer.constants.ConstValues.PERCENTILE_OUTPUT_TEXT;
import static backend.academy.logAnalyzer.constants.ConstValues.REQUESTED_RESOURCES_TEXT;
import static backend.academy.logAnalyzer.constants.ConstValues.REQUESTS_COUNT_OUTPUT_TEXT;
import static backend.academy.logAnalyzer.constants.ConstValues.RESPONSE_AVERAGE_SIZE_OUTPUT_TEXT;
import static backend.academy.logAnalyzer.constants.ConstValues.RESPONSE_CODES_TEXT;
import static backend.academy.logAnalyzer.constants.ConstValues.START_DATE_OUTPUT_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NULL_ELEMENTS_STATISTICS_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NULL_GENERAL_INFORMATION_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NULL_STATISTICS_EXCEPTION_TEXT;

@UtilityClass
public class Scene {

    private static final int DEFAULT_COLUMN_SIZE = 30;
    private static final String COUNT_NAME = "Количество";

    private static char headerSymbol;
    private static int maxColumnSize;

    public static String renderGeneralInformation(GeneralInformation generalInformation,
        OutputFormat outputFormat) {
        validateGeneralInformation(generalInformation);

        final Map<String, String> generalInformationMap = getGeneralInformationMap(generalInformation);
        final List<String> tableHeaders = List.of("Метрика", "Значение");

        maxColumnSize = Math.max(DEFAULT_COLUMN_SIZE,
            generalInformationMap.values().stream().mapToInt(String::length).max().orElse(0));
        headerSymbol = getHeaderSymbol(outputFormat);

        return getSection(generalInformationMap, GENERAL_INFORMATION_TEXT, tableHeaders);
    }

    public static String renderResourcesInformation(Map<String, Integer> resources,
        OutputFormat outputFormat) {
        validateMap(resources);

        final List<String> tableHeaders = List.of("Ресурс", COUNT_NAME);

        maxColumnSize = getMaxColumnSizeByKeys(resources);
        headerSymbol = getHeaderSymbol(outputFormat);

        return getSection(resources, REQUESTED_RESOURCES_TEXT, tableHeaders);
    }

    public static String renderResponseCodesInformation(Map<HttpStatus, Integer> httpStatuses,
        OutputFormat outputFormat) {
        validateMap(httpStatuses);

        final List<String> tableHeaders = List.of("Код", "Имя", COUNT_NAME);

        maxColumnSize = getMaxColumnSizeByKeys(httpStatuses);
        headerSymbol = getHeaderSymbol(outputFormat);

        return getSection(httpStatuses, RESPONSE_CODES_TEXT, tableHeaders);
    }

    public static String renderAddressesInformation(Map<String, Integer> addresses,
        OutputFormat outputFormat) {
        validateMap(addresses);

        final List<String> tableHeaders = List.of("Адрес", COUNT_NAME);

        maxColumnSize = getMaxColumnSizeByKeys(addresses);
        headerSymbol = getHeaderSymbol(outputFormat);

        return getSection(addresses, ADDRESSES_TEXT, tableHeaders);
    }

    public static String renderMethodsInformation(Map<HttpMethod, Integer> httpMethods,
        OutputFormat outputFormat) {
        validateMap(httpMethods);

        final List<String> tableHeaders = List.of("Метод", COUNT_NAME);

        maxColumnSize = getMaxColumnSizeByKeys(httpMethods);
        headerSymbol = getHeaderSymbol(outputFormat);

        return getSection(httpMethods, HTTP_METHODS_TEXT, tableHeaders);
    }

    private static int getMaxColumnSizeByKeys(Map<?, ?> map) {
        return Math.max(DEFAULT_COLUMN_SIZE,
            map.keySet().stream().mapToInt(element -> element.toString().length()).max().orElse(0));
    }

    private static String getSection(Map<?, ?> statistic, String header, List<String> tableHeaders) {
        return String.format("%s%n%s%n%s",
            getHeader(header, headerSymbol),
            getTableHeader(tableHeaders),
            getStatistics(statistic));
    }

    private static Map<String, String> getGeneralInformationMap(GeneralInformation generalInformation) {
        final Map<String, String> information = new LinkedHashMap<>();
        final String startDate = getDateValue(generalInformation.from());
        final String endDate = getDateValue(generalInformation.to());
        final char byteSymbol = 'b';
        final List<String> filePaths = generalInformation
            .filePaths()
            .stream()
            .map(el -> el.substring(el.lastIndexOf('\\') + 1))
            .toList();

        information.put(FILES_OUTPUT_TEXT, filePaths.toString());
        information.put(START_DATE_OUTPUT_TEXT, startDate);
        information.put(END_DATE_OUTPUT_TEXT, endDate);
        information.put(REQUESTS_COUNT_OUTPUT_TEXT,
            String.valueOf(generalInformation.requestsCount()));
        information.put(RESPONSE_AVERAGE_SIZE_OUTPUT_TEXT,
            String.valueOf(generalInformation.responseAverageSize()) + byteSymbol);
        information.put(PERCENTILE_OUTPUT_TEXT,
            String.valueOf(generalInformation.percentile()) + byteSymbol);

        return information;
    }

    private static String getDateValue(LocalDate from) {
        return from != null
            ? from.toString()
            : NO_VALUE_IN_STATISTICS;
    }

    private static String getRenderResult(Map.Entry<?, ?> element) {
        final char separate = '|';
        final String columnFormat = " %" + maxColumnSize + "s ";

        if (Objects.requireNonNull(element.getKey()) instanceof HttpStatus httpStatus) {
            return String.format(separate + columnFormat + separate + columnFormat + separate + columnFormat + separate,
                centerText(String.valueOf(httpStatus.code())),
                centerText(element.getKey().toString()),
                centerText(element.getValue().toString()));
        }

        return String.format(separate + columnFormat + separate + columnFormat + separate,
            centerText(element.getKey().toString()),
            centerText(element.getValue().toString()));
    }

    private static String getHeader(String headerText, char headerSymbol) {
        final String headerFormat = "%s %s";

        return String.format(headerFormat,
            String.valueOf(headerSymbol).repeat(HEADER_SYMBOLS_COUNT), headerText);
    }

    private static char getHeaderSymbol(OutputFormat outputFormat) {
        return outputFormat != null
            ? outputFormat.headerSymbol()
            : DEFAULT_FORMAT.headerSymbol();
    }

    private static String getStatistics(Map<?, ?> statistics) {
        final StringBuilder stringBuilder = new StringBuilder();
        int count = 0;

        for (Map.Entry<?, ?> element : statistics.entrySet()) {
            stringBuilder.append(getRenderResult(element));
            count++;

            if (count < statistics.size()) {
                stringBuilder.append('\n');
            }
        }

        return stringBuilder.toString();
    }

    private static String getTableHeader(List<String> headers) {
        final StringBuilder header = new StringBuilder("|");
        final StringBuilder separator = new StringBuilder("|");

        for (String column : headers) {
            header.append(' ').append(centerText(column)).append(" |");
            separator.append(':').append("-".repeat(maxColumnSize)).append(":|");
        }

        return header.append('\n').append(separator).toString();
    }

    private static String centerText(String text) {
        final int padding = (maxColumnSize - text.length()) / 2;
        return " ".repeat(padding) + text + " ".repeat(maxColumnSize - padding - text.length());
    }

    private static void validateMap(Map<?, ?> statistics) {
        if (statistics == null) {
            throw new NullPointerException(NULL_STATISTICS_EXCEPTION_TEXT);
        }

        statistics.forEach((key, value) -> {
            if (key == null || value == null) {
                throw new NullPointerException(NULL_ELEMENTS_STATISTICS_EXCEPTION_TEXT);
            }
        });
    }

    private static void validateGeneralInformation(GeneralInformation generalInformation) {
        if (generalInformation == null) {
            throw new NullPointerException(NULL_GENERAL_INFORMATION_EXCEPTION_TEXT);
        }
    }
}
