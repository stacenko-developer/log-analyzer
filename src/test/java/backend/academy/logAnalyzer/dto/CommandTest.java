package backend.academy.logAnalyzer.dto;

import backend.academy.logAnalyzer.CommonTest;
import backend.academy.logAnalyzer.enums.FilterField;
import backend.academy.logAnalyzer.enums.HttpMethod;
import backend.academy.logAnalyzer.enums.HttpStatus;
import backend.academy.logAnalyzer.enums.OutputFormat;
import backend.academy.logAnalyzer.exception.IncorrectCommandException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.HTTP_METHOD_NOT_SUPPORTED_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.HTTP_STATUS_NOT_SUPPORTED_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.INCORRECT_DATE_RANGE_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.INCORRECT_FILTERS_IN_COMMAND_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NULL_FILE_PATH_EXCEPTION_TEXT;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandTest extends CommonTest {

    @ParameterizedTest
    @MethodSource("getRandomStringsList")
    public void createCommandWithOnlyPath_ShouldCreateCommand(String path) {
        createCommandProcess(path, null, null,
            null, null, null);
    }

    @ParameterizedTest
    @MethodSource("getRandomStringsList")
    public void createCommandWithPathAndFrom_ShouldCreateCommand(String path) {
        createCommandProcess(path, LocalDate.now(), null,
            null, null, null);
    }

    @ParameterizedTest
    @MethodSource("getRandomStringsList")
    public void createCommandWithPathAndTo_ShouldCreateCommand(String path) {
        createCommandProcess(path, null, LocalDate.now(),
            null, null, null);
    }

    @ParameterizedTest
    @MethodSource("getRandomStringsList")
    public void createCommandWithPathAndTimeRange_ShouldCreateCommand(String path) {
        createCommandProcess(path, LocalDate.now().minusDays(DEFAULT_DAYS_COUNT), LocalDate.now(),
            null, null, null);
    }

    @ParameterizedTest
    @EnumSource(OutputFormat.class)
    public void createCommandWithPathAndFormat_ShouldCreateCommand(OutputFormat outputFormat) {
        createCommandProcess(DEFAULT_VALUE, null, null,
            outputFormat, null, null);
    }

    @ParameterizedTest
    @EnumSource(OutputFormat.class)
    public void createCommandWithPathAndFormatAndTimeRange_ShouldCreateCommand(OutputFormat outputFormat) {
        createCommandProcess(DEFAULT_VALUE, LocalDate.now().minusDays(DEFAULT_DAYS_COUNT), LocalDate.now(),
            outputFormat, null, null);
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForCreateCommandWithAllArguments")
    public void createCommandWithAllArguments_ShouldCreateCommand(OutputFormat outputFormat,
        FilterField filterField, String filterValue) {

        createCommandProcess(DEFAULT_VALUE, LocalDate.now().minusDays(DEFAULT_DAYS_COUNT), LocalDate.now(),
            outputFormat, filterField, filterValue);
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForTestStringForNull")
    public void createCommandWithNullPath_ShouldThrowIncorrectCommandException(String incorrectValue) {
        assertThatThrownBy(() -> {
            new Command(incorrectValue, null, null, null, null, null);
        }).isInstanceOf(IncorrectCommandException.class)
            .hasMessageContaining(NULL_FILE_PATH_EXCEPTION_TEXT);
    }

    @Test
    public void createCommandWithEqualTimeRange_ShouldThrowIncorrectCommandException() {
        assertThatThrownBy(() -> {
            final LocalDate date = LocalDate.now();

            new Command(DEFAULT_VALUE, date, date, null, null, null);
        }).isInstanceOf(IncorrectCommandException.class)
            .hasMessageContaining(INCORRECT_DATE_RANGE_EXCEPTION_TEXT);
    }

    @Test
    public void createCommandWithFromMoreThanTo_ShouldThrowIncorrectCommandException() {
        assertThatThrownBy(() -> {
            new Command(DEFAULT_VALUE, LocalDate.now().plusDays(DEFAULT_DAYS_COUNT),
                LocalDate.now(), null, null, null);
        }).isInstanceOf(IncorrectCommandException.class)
            .hasMessageContaining(INCORRECT_DATE_RANGE_EXCEPTION_TEXT);
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForCreateCommandWithFilterFieldWithoutValue")
    public void createCommandWithFilterFieldWithoutValue_ShouldThrowIncorrectCommandException(
        FilterField filterField, String incorrectFieldValue
    ) {
        assertThatThrownBy(() -> {
            new Command(DEFAULT_VALUE, null,
                null, null, filterField, incorrectFieldValue);
        }).isInstanceOf(IncorrectCommandException.class)
            .hasMessageContaining(INCORRECT_FILTERS_IN_COMMAND_EXCEPTION_TEXT);
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForCreateCommandWithFilterValueWithoutField")
    public void createCommandWithFilterValueWithoutField_ShouldThrowIncorrectCommandException(String fieldValue) {
        assertThatThrownBy(() -> {
            new Command(DEFAULT_VALUE, null,
                null, null, null, fieldValue);
        }).isInstanceOf(IncorrectCommandException.class)
            .hasMessageContaining(INCORRECT_FILTERS_IN_COMMAND_EXCEPTION_TEXT);
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForCreateCommandWithFilterAndIncorrectFilterValue")
    public void createCommandWithHttpStatusFilterAndIncorrectFilterValue_ShouldThrowIncorrectCommandException(
        String incorrectHttpCode
    ) {
        createCommandWithNotSupportedFieldValue(FilterField.HTTP_STATUS, incorrectHttpCode,
            HTTP_STATUS_NOT_SUPPORTED_EXCEPTION_TEXT
        );
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForCreateCommandWithFilterAndIncorrectFilterValue")
    public void createCommandWithHttpMethodFilterAndIncorrectFilterValue_ShouldThrowIncorrectCommandException(
        String incorrectHttpMethod
    ) {
        createCommandWithNotSupportedFieldValue(FilterField.HTTP_METHOD, incorrectHttpMethod,
            HTTP_METHOD_NOT_SUPPORTED_EXCEPTION_TEXT
        );
    }

    private void createCommandWithNotSupportedFieldValue(
        FilterField filterField, String incorrectFieldValue, String exceptionText
    ) {
        assertThatThrownBy(() -> {
            new Command(DEFAULT_VALUE, null,
                null, null, filterField, incorrectFieldValue);
        }).isInstanceOf(IncorrectCommandException.class)
            .hasMessageContaining(exceptionText);
    }

    private void createCommandProcess(String filePath, LocalDate from, LocalDate to,
        OutputFormat format, FilterField filterField, String filterValue) {

        final Command command = new Command(
            filePath, from, to, format, filterField, filterValue
        );

        assertEquals(command.filePath(), filePath);
        assertEquals(command.from(), from);
        assertEquals(command.to(), to);
        assertEquals(command.format(), format);
        assertEquals(command.filterField(), filterField);
        assertEquals(command.filterValue(), filterValue);
    }

    private static List<Object[]> getArgumentsForCreateCommandWithAllArguments() {
        final List<Object[]> result = new ArrayList<>();

        for (OutputFormat outputFormat : OutputFormat.values()) {
            for (FilterField filterField : FilterField.values()) {

                if (filterField == FilterField.HTTP_STATUS) {
                    for (HttpStatus httpStatus : HttpStatus.values()) {
                        result.add(new Object[]{
                            outputFormat, filterField, String.valueOf(httpStatus.code())
                        });
                    }
                } else if (filterField == FilterField.HTTP_METHOD) {
                    for (HttpMethod httpMethod : HttpMethod.values()) {
                        result.add(new Object[]{
                            outputFormat, filterField, String.valueOf(httpMethod.value())
                        });
                    }
                } else {
                    result.add(new Object[]{
                        outputFormat, filterField, getRandomString()
                    });
                }
            }
        }

        return result;
    }

    private static List<Object[]> getArgumentsForCreateCommandWithFilterFieldWithoutValue() {
        return Stream.of(FilterField.values())
            .flatMap(filterField -> Stream.of(getArgumentsForTestStringForNull())
                .map(nullString -> new Object[] {
                    filterField, nullString
                }))
            .collect(Collectors.toList());
    }

    private static List<String> getArgumentsForCreateCommandWithFilterValueWithoutField() {
        final List<String> result = new ArrayList<>(Arrays.stream(HttpStatus.values()).map(String::valueOf).toList());

        result.addAll(
            new ArrayList<>(
                Arrays.stream(HttpStatus.values()).map(httpStatus -> String.valueOf(httpStatus.code())).toList()
            )
        );
        result.addAll(new ArrayList<>(Arrays.stream(HttpMethod.values()).map(String::valueOf).toList()));
        result.addAll(getRandomStringsList());

        return result;
    }
}
