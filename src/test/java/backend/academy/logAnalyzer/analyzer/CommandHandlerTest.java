package backend.academy.logAnalyzer.analyzer;

import backend.academy.logAnalyzer.dto.Command;
import backend.academy.logAnalyzer.dto.GeneralInformation;
import backend.academy.logAnalyzer.dto.ProcessingResponse;
import backend.academy.logAnalyzer.dto.RequestStatistics;
import backend.academy.logAnalyzer.enums.FilterField;
import backend.academy.logAnalyzer.enums.HttpMethod;
import backend.academy.logAnalyzer.enums.HttpStatus;
import backend.academy.logAnalyzer.exception.ExitFromCurrentDirectoryException;
import backend.academy.logAnalyzer.exception.FileDataReadException;
import backend.academy.logAnalyzer.exception.LogFilesNotFoundException;
import backend.academy.logAnalyzer.exception.UrlDataReadException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;
import static backend.academy.logAnalyzer.constants.ConstValues.BASE_PATH;
import static backend.academy.logAnalyzer.constants.ConstValues.RESOURCES_PATH;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.EXIT_FROM_CURRENT_DIRECTORY_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.FILE_DATA_READ_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.LOG_FILES_NOT_FOUND_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NULL_COMMAND_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.URL_DATA_NOT_READ_EXCEPTION_TEXT;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandHandlerTest {

    private final CommandHandler commandHandler = new CommandHandler();

    @Test
    public void processCommandWithNullCommand_ShouldThrowNullPointerException() {
        assertThatThrownBy(() -> {
            commandHandler.process(null);
        }).isInstanceOf(NullPointerException.class)
            .hasMessageContaining(NULL_COMMAND_EXCEPTION_TEXT);
    }

    @Test
    public void processCommandWithNonExistentFilePath_ShouldThrowFileDataReadException() {
        final String incorrectFilePath = RESOURCES_PATH + "log444.txt";

        assertThatThrownBy(() -> {
            commandHandler.process(new Command(
                incorrectFilePath, null, null, null, null, null
                )
            );
        }).isInstanceOf(FileDataReadException.class)
            .hasMessageContaining(FILE_DATA_READ_EXCEPTION_TEXT);
    }

    @Test
    public void processCommandWithIncorrectUrl_ShouldThrowUrlDataReadException() {
        final String incorrectUrl = "https://raw.githubusercontent.com/elastic/example";

        assertThatThrownBy(() -> {
            commandHandler.process(new Command(
                    incorrectUrl, null, null, null, null, null
                )
            );
        }).isInstanceOf(UrlDataReadException.class)
            .hasMessageContaining(URL_DATA_NOT_READ_EXCEPTION_TEXT);
    }

    @Test
    public void processCommandWithNotFoundFileByGlob_ShouldThrowLogFilesNotFoundException() {
        final String incorrectFilePathByGlob = RESOURCES_PATH + "l\\*.txt";

        assertThatThrownBy(() -> {
            commandHandler.process(new Command(
                incorrectFilePathByGlob, null, null, null, null, null
                )
            );
        }).isInstanceOf(LogFilesNotFoundException.class)
            .hasMessageContaining(LOG_FILES_NOT_FOUND_EXCEPTION_TEXT);
    }

    @Test
    public void processCommandWithTryingToExitFromCurrentDirectory_ShouldThrowExitFromCurrentDirectoryException() {
        final String pathWithCurrentDirMoving = "../";

        assertThatThrownBy(() -> {
            commandHandler.process(new Command(
                    pathWithCurrentDirMoving, null, null, null, null, null
                )
            );
        }).isInstanceOf(ExitFromCurrentDirectoryException.class)
            .hasMessageContaining(EXIT_FROM_CURRENT_DIRECTORY_EXCEPTION_TEXT);
    }

    @Test
    public void processCommandWithNoData_ShouldCorrectlyProcess() {
        final String filename = RESOURCES_PATH + "empty-log.txt";
        final Command command = new Command(
            filename, null, null,
            null, null, null
        );

        assertEquals(getProcessingResponseForNoData(filename), commandHandler.process(command));
    }

    @Test
    public void processCommandWithFilePathOnly_ShouldCorrectlyProcess() {
        final String filename = RESOURCES_PATH + "log1.txt";

        processCommandWithFilePathOnly(filename);
    }

    @Test
    public void processCommandWithAbsoluteFilePathOnly_ShouldCorrectlyProcess() {
        final String filename = BASE_PATH + RESOURCES_PATH + "log1.txt";

        processCommandWithFilePathOnly(filename);
    }

    @Test
    public void processCommandWithFilePathAndFrom_ShouldCorrectlyProcess() {
        final String filename = RESOURCES_PATH + "log1.txt";
        final Map<String, Integer> resources = new LinkedHashMap<>();
        resources.put("/downloads/product_1", 5);
        resources.put("/downloads/product_2", 3);

        final Map<HttpStatus, Integer> httpStatuses = new LinkedHashMap<>();
        httpStatuses.put(HttpStatus.NOT_MODIFIED, 5);
        httpStatuses.put(HttpStatus.NOT_FOUND, 2);
        httpStatuses.put(HttpStatus.OK, 1);

        final Map<String, Integer> addresses = new LinkedHashMap<>();
        addresses.put("91.234.194.89", 2);
        addresses.put("80.91.33.133", 2);
        addresses.put("93.180.71.3", 1);
        addresses.put("5.83.131.103", 1);
        addresses.put("217.168.17.5", 1);

        final Map<HttpMethod, Integer> httpMethods = new LinkedHashMap<>();
        httpMethods.put(HttpMethod.GET, 8);

        final LocalDate date = LocalDate.parse("2015-06-17", DateTimeFormatter.ISO_LOCAL_DATE);

        final Command command = new Command(
            filename, date,
            null, null, null, null
        );
        final ProcessingResponse correctResult = new ProcessingResponse(
            new GeneralInformation(
                Set.of(BASE_PATH + filename), date,
                null, 8, new BigDecimal("145.25"), new BigInteger("490")
            ),
            new RequestStatistics(
                resources, httpStatuses, addresses, httpMethods
            )
        );

        assertEquals(correctResult, commandHandler.process(command));
    }

    @Test
    public void processCommandWithFilePathAndTo_ShouldCorrectlyProcess() {
        final String filename = RESOURCES_PATH + "log1.txt";
        final Map<String, Integer> resources = new LinkedHashMap<>();
        resources.put("/downloads/product_1", 4);
        resources.put("/downloads/product_2", 3);

        final Map<HttpStatus, Integer> httpStatuses = new LinkedHashMap<>();
        httpStatuses.put(HttpStatus.NOT_MODIFIED, 4);
        httpStatuses.put(HttpStatus.NOT_FOUND, 2);
        httpStatuses.put(HttpStatus.OK, 1);

        final Map<String, Integer> addresses = new LinkedHashMap<>();
        addresses.put("91.234.194.89", 2);
        addresses.put("93.180.71.3", 1);
        addresses.put("80.91.33.133", 1);
        addresses.put("5.83.131.103", 1);
        addresses.put("217.168.17.5", 1);

        final Map<HttpMethod, Integer> httpMethods = new LinkedHashMap<>();
        httpMethods.put(HttpMethod.GET, 7);

        final LocalDate from = LocalDate.parse("2015-06-17", DateTimeFormatter.ISO_LOCAL_DATE);
        final LocalDate to = LocalDate.parse("2015-06-18", DateTimeFormatter.ISO_LOCAL_DATE);

        final Command command = new Command(
            filename, from,
            to, null, null, null
        );
        final ProcessingResponse correctResult = new ProcessingResponse(
            new GeneralInformation(
                Set.of(BASE_PATH + filename), from,
                to, 7, new BigDecimal("166.00"), new BigInteger("490")
            ),
            new RequestStatistics(
                resources, httpStatuses, addresses, httpMethods
            )
        );

        assertEquals(correctResult, commandHandler.process(command));
    }

    @Test
    public void processCommandWithFilePathAndAddressFilter_ShouldCorrectlyProcess() {
        final String filename = RESOURCES_PATH + "log1.txt";
        final Map<String, Integer> resources = new LinkedHashMap<>();
        resources.put("/downloads/product_1", 10);

        final Map<HttpStatus, Integer> httpStatuses = new LinkedHashMap<>();
        httpStatuses.put(HttpStatus.NOT_MODIFIED, 9);
        httpStatuses.put(HttpStatus.NOT_FOUND, 1);

        final Map<String, Integer> addresses = new LinkedHashMap<>();
        addresses.put("80.91.33.133", 10);

        final Map<HttpMethod, Integer> httpMethods = new LinkedHashMap<>();
        httpMethods.put(HttpMethod.GET, 10);

        final Command command = new Command(
            filename, null,
            null, null, FilterField.ADDRESS, "80.91.33.133"
        );
        final ProcessingResponse correctResult = new ProcessingResponse(
            new GeneralInformation(
                Set.of(BASE_PATH + filename), null,
                null, 10, new BigDecimal("32.40"), new BigInteger("324")
            ),
            new RequestStatistics(
                resources, httpStatuses, addresses, httpMethods
            )
        );

        assertEquals(correctResult, commandHandler.process(command));
    }

    @Test
    public void processCommandWithFilePathAndUserFilter_ShouldCorrectlyProcess() {
        final String filename = RESOURCES_PATH + "log1.txt";
        final Map<String, Integer> resources = new LinkedHashMap<>();
        resources.put("/downloads/product_2", 1);

        final Map<HttpStatus, Integer> httpStatuses = new LinkedHashMap<>();
        httpStatuses.put(HttpStatus.OK, 1);

        final Map<String, Integer> addresses = new LinkedHashMap<>();
        addresses.put("217.168.17.5", 1);

        final Map<HttpMethod, Integer> httpMethods = new LinkedHashMap<>();
        httpMethods.put(HttpMethod.GET, 1);

        final ProcessingResponse correctResult = new ProcessingResponse(
            new GeneralInformation(
                Set.of(BASE_PATH + filename), null, null,
                1, new BigDecimal("490.00"), new BigInteger("490")
            ),
            new RequestStatistics(
                resources, httpStatuses, addresses, httpMethods
            )
        );

        final Command command = new Command(
            filename, null,
            null, null, FilterField.USER, "artem"
        );

        assertEquals(correctResult, commandHandler.process(command));
    }

    @Test
    public void processCommandWithFilePathAndMethodFilter_ShouldCorrectlyProcess() {
        final String filename = RESOURCES_PATH + "log1.txt";
        final Map<String, Integer> resources = new LinkedHashMap<>();
        resources.put("/downloads/product_2", 1);

        final Map<HttpStatus, Integer> httpStatuses = new LinkedHashMap<>();
        httpStatuses.put(HttpStatus.OK, 1);

        final Map<String, Integer> addresses = new LinkedHashMap<>();
        addresses.put("54.187.216.43", 1);

        final Map<HttpMethod, Integer> httpMethods = new LinkedHashMap<>();
        httpMethods.put(HttpMethod.HEAD, 1);

        final ProcessingResponse correctResult = new ProcessingResponse(
            new GeneralInformation(
                Set.of(BASE_PATH + filename), null, null,
                1, new BigDecimal("951.00"), new BigInteger("951")
            ),
            new RequestStatistics(
                resources, httpStatuses, addresses, httpMethods
            )
        );

        final Command command = new Command(
            filename, null,
            null, null, FilterField.HTTP_METHOD, "head"
        );

        assertEquals(correctResult, commandHandler.process(command));
    }

    @Test
    public void processCommandWithFilePathAndAgentFilter_ShouldCorrectlyProcess() {
        final String filename = RESOURCES_PATH + "log1.txt";
        final Map<String, Integer> resources = new LinkedHashMap<>();
        resources.put("/downloads/product_1", 1);
        resources.put("/downloads/product_2", 1);

        final Map<HttpStatus, Integer> httpStatuses = new LinkedHashMap<>();
        httpStatuses.put(HttpStatus.OK, 1);
        httpStatuses.put(HttpStatus.NOT_FOUND, 1);

        final Map<String, Integer> addresses = new LinkedHashMap<>();
        addresses.put("54.187.216.43", 1);
        addresses.put("173.203.139.108", 1);

        final Map<HttpMethod, Integer> httpMethods = new LinkedHashMap<>();
        httpMethods.put(HttpMethod.GET, 1);
        httpMethods.put(HttpMethod.HEAD, 1);

        final ProcessingResponse correctResult = new ProcessingResponse(
            new GeneralInformation(
                Set.of(BASE_PATH + filename), null, null,
                2, new BigDecimal("643.00"), new BigInteger("951")
            ),
            new RequestStatistics(
                resources, httpStatuses, addresses, httpMethods
            )
        );

        final Command command = new Command(
            filename, null,
            null, null, FilterField.AGENT, "urlgrabber/3.9.1 yum/3.4.3"
        );

        assertEquals(correctResult, commandHandler.process(command));
    }

    @Test
    public void processCommandWithFilePathAndStatusFilter_ShouldCorrectlyProcess() {
        final String filename = RESOURCES_PATH + "log1.txt";
        final Map<String, Integer> resources = new LinkedHashMap<>();
        resources.put("/downloads/product_2", 4);
        resources.put("/downloads/product_1", 3);

        final Map<HttpStatus, Integer> httpStatuses = new LinkedHashMap<>();
        httpStatuses.put(HttpStatus.OK, 7);

        final Map<String, Integer> addresses = new LinkedHashMap<>();
        addresses.put("217.168.17.5", 4);
        addresses.put("62.75.198.179", 1);
        addresses.put("54.187.216.43", 1);
        addresses.put("5.83.131.103", 1);

        final Map<HttpMethod, Integer> httpMethods = new LinkedHashMap<>();
        httpMethods.put(HttpMethod.GET, 6);
        httpMethods.put(HttpMethod.HEAD, 1);

        final ProcessingResponse correctResult = new ProcessingResponse(
            new GeneralInformation(
                Set.of(BASE_PATH + filename), null, null,
                7, new BigDecimal("1361.14"), new BigInteger("3316")
            ),
            new RequestStatistics(
                resources, httpStatuses, addresses, httpMethods
            )
        );

        final Command command = new Command(
            filename, null,
            null, null, FilterField.HTTP_STATUS, "200"
        );

        assertEquals(correctResult, commandHandler.process(command));
    }

    @Test
    public void processCommandWithFilePathByGlob_ShouldCorrectlyProcess() {
        final String filename = RESOURCES_PATH + "*.txt";

        processCommandWithFileByGlob(filename);
    }

    @Test
    public void processCommandWithAbsoluteFilePathByGlob_ShouldCorrectlyProcess() {
        final String filename = BASE_PATH + RESOURCES_PATH + "*.txt";

        processCommandWithFileByGlob(filename);
    }

    @Test
    public void processCommandWithUrl_ShouldCorrectlyProcess() {
        final String filename = "https://raw.githubusercontent.com/elastic/"
            + "examples/master/Common%20Data%20Formats/nginx_logs/nginx_logs";

        final Map<String, Integer> resources = new LinkedHashMap<>();
        resources.put("/downloads/product_1", 30285);
        resources.put("/downloads/product_2", 21104);
        resources.put("/downloads/product_3", 73);

        final Map<HttpStatus, Integer> httpStatuses = new LinkedHashMap<>();
        httpStatuses.put(HttpStatus.NOT_FOUND, 33876);
        httpStatuses.put(HttpStatus.NOT_MODIFIED, 13330);
        httpStatuses.put(HttpStatus.OK, 4028);
        httpStatuses.put(HttpStatus.PARTIAL_CONTENT, 186);
        httpStatuses.put(HttpStatus.FORBIDDEN, 38);

        final Map<String, Integer> addresses = new LinkedHashMap<>();
        addresses.put("216.46.173.126", 2350);
        addresses.put("180.179.174.219", 1720);
        addresses.put("204.77.168.241", 1439);
        addresses.put("65.39.197.164", 1365);
        addresses.put("80.91.33.133", 1202);

        final Map<HttpMethod, Integer> httpMethods = new LinkedHashMap<>();
        httpMethods.put(HttpMethod.GET, 51379);
        httpMethods.put(HttpMethod.HEAD, 83);

        final ProcessingResponse correctResult = new ProcessingResponse(
            new GeneralInformation(
                Set.of(filename), null, null,
                51462, new BigDecimal("659509.51"), new BigInteger("1768")
            ),
            new RequestStatistics(
                resources, httpStatuses, addresses, httpMethods
            )
        );

        final Command command = new Command(
            filename, null,
            null, null, null, null
        );

        assertEquals(correctResult, commandHandler.process(command));
    }

    private static ProcessingResponse getProcessingResponseForNoData(String filename) {
        return new ProcessingResponse(
            new GeneralInformation(
                Set.of(BASE_PATH + filename), null, null, 0, BigDecimal.ZERO, BigInteger.ZERO
            ),
            new RequestStatistics(
                new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>()
            )
        );
    }

    private void processCommandWithFilePathOnly(String filename) {
        final Map<String, Integer> resources = new LinkedHashMap<>();
        resources.put("/downloads/product_1", 32);
        resources.put("/downloads/product_2", 18);

        final Map<HttpStatus, Integer> httpStatuses = new LinkedHashMap<>();
        httpStatuses.put(HttpStatus.NOT_MODIFIED, 34);
        httpStatuses.put(HttpStatus.NOT_FOUND, 9);
        httpStatuses.put(HttpStatus.OK, 7);

        final Map<String, Integer> addresses = new LinkedHashMap<>();
        addresses.put("80.91.33.133", 10);
        addresses.put("93.180.71.3", 7);
        addresses.put("217.168.17.5", 6);
        addresses.put("173.203.139.108", 4);
        addresses.put("91.234.194.89", 3);

        final Map<HttpMethod, Integer> httpMethods = new LinkedHashMap<>();
        httpMethods.put(HttpMethod.GET, 49);
        httpMethods.put(HttpMethod.HEAD, 1);

        String path = filename;

        if (!filename.contains(BASE_PATH)) {
            path = BASE_PATH + filename;
        }

        final ProcessingResponse correctResult = new ProcessingResponse(
            new GeneralInformation(
                Set.of(path), null, null, 50,
                new BigDecimal("249.88"), new BigInteger("951")
            ),
            new RequestStatistics(
                resources, httpStatuses, addresses, httpMethods
            )
        );

        final Command command = new Command(
            filename, null,
            null, null, null, null
        );

        assertEquals(correctResult, commandHandler.process(command));
    }

    private void processCommandWithFileByGlob(String filename) {
        final Map<String, Integer> resources = new LinkedHashMap<>();
        resources.put("/downloads/product_1", 37);
        resources.put("/downloads/product_2", 18);

        final Map<HttpStatus, Integer> httpStatuses = new LinkedHashMap<>();
        httpStatuses.put(HttpStatus.NOT_MODIFIED, 39);
        httpStatuses.put(HttpStatus.NOT_FOUND, 9);
        httpStatuses.put(HttpStatus.OK, 7);

        final Map<String, Integer> addresses = new LinkedHashMap<>();
        addresses.put("93.180.71.3", 12);
        addresses.put("80.91.33.133", 10);
        addresses.put("217.168.17.5", 6);
        addresses.put("173.203.139.108", 4);
        addresses.put("91.234.194.89", 3);

        final Map<HttpMethod, Integer> httpMethods = new LinkedHashMap<>();
        httpMethods.put(HttpMethod.GET, 54);
        httpMethods.put(HttpMethod.HEAD, 1);

        final ProcessingResponse correctResult = new ProcessingResponse(
            new GeneralInformation(
                Set.of(
                    BASE_PATH + RESOURCES_PATH + "empty-log.txt",
                    BASE_PATH + RESOURCES_PATH + "log1.txt",
                    BASE_PATH + RESOURCES_PATH + "log2.txt"
                ), null, null,
                55, new BigDecimal("227.16"), new BigInteger("951")
            ),
            new RequestStatistics(
                resources, httpStatuses, addresses, httpMethods
            )
        );

        final Command command = new Command(
            filename, null,
            null, null, null, null
        );

        assertEquals(correctResult, commandHandler.process(command));
    }
}
