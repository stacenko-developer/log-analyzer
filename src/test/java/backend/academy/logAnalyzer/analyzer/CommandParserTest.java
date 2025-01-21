package backend.academy.logAnalyzer.analyzer;

import backend.academy.logAnalyzer.CommonTest;
import backend.academy.logAnalyzer.dto.Command;
import backend.academy.logAnalyzer.enums.FilterField;
import backend.academy.logAnalyzer.enums.OutputFormat;
import backend.academy.logAnalyzer.exception.IncorrectCommandException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.DUPLICATE_ARGUMENTS_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NO_LOG_ANALYZE_ARGUMENT_WITH_OTHERS_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.INCORRECT_BEGIN_COMMAND_VALUE_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.INCORRECT_DATE_FORMAT_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NOT_VALUE_FOR_FILE_PATH_ARGUMENT_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NOT_VALUE_FOR_FILTER_FIELD_ARGUMENT_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NOT_VALUE_FOR_FILTER_VALUE_ARGUMENT_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NOT_VALUE_FOR_FORMAT_ARGUMENT_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NOT_VALUE_FOR_FROM_ARGUMENT_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NOT_VALUE_FOR_TO_ARGUMENT_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NULL_COMMAND_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.SOME_VALUES_IN_FILE_PATH_ARGUMENT_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.SOME_VALUES_IN_FILTER_FIELD_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.SOME_VALUES_IN_FILTER_VALUE_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.SOME_VALUES_IN_FORMAT_ARGUMENT_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.SOME_VALUES_IN_FROM_ARGUMENT_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.SOME_VALUES_IN_TO_ARGUMENT_EXCEPTION_TEXT;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandParserTest extends CommonTest {

    @ParameterizedTest
    @MethodSource("getArgumentsForParseCommand")
    public void parseCommand_ShouldCorrectlyParse(String command, Command result) {
        assertEquals(CommandParser.parse(command), result);
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForTestStringForNull")
    public void parseCommandWithNullCommand_ShouldThrowIncorrectCommandException(String nullCommand) {
        assertThatThrownBy(() -> {
            CommandParser.parse(nullCommand);
        }).isInstanceOf(IncorrectCommandException.class)
            .hasMessageContaining(NULL_COMMAND_EXCEPTION_TEXT);
    }

    @ParameterizedTest
    @MethodSource("getRandomStringsList")
    public void parseCommandWithBeginWord_ShouldThrowIncorrectCommandException(String incorrectCommand) {
        assertThatThrownBy(() -> {
            CommandParser.parse(incorrectCommand);
        }).isInstanceOf(IncorrectCommandException.class)
            .hasMessageContaining(INCORRECT_BEGIN_COMMAND_VALUE_EXCEPTION_TEXT);
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForParseCommandWithExitArgumentAndOthers")
    public void parseCommandWithExitArgumentAndOthers_ShouldThrowIncorrectCommandException(String incorrectCommand) {
        assertThatThrownBy(() -> {
            CommandParser.parse(incorrectCommand);
        }).isInstanceOf(IncorrectCommandException.class)
            .hasMessageContaining(NO_LOG_ANALYZE_ARGUMENT_WITH_OTHERS_EXCEPTION_TEXT);
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForParseCommandWithDuplicateArguments")
    public void parseCommandWithDuplicateArguments_ShouldThrowIncorrectCommandException(String incorrectCommand) {
        assertThatThrownBy(() -> {
            CommandParser.parse(incorrectCommand);
        }).isInstanceOf(IncorrectCommandException.class)
            .hasMessageContaining(DUPLICATE_ARGUMENTS_EXCEPTION_TEXT);
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForParseCommandWithSomeValuesInFilePath")
    public void parseCommandWithSomeValuesInFilePathArgument_ShouldThrowIncorrectCommandException(
        String incorrectCommand
    ) {
        assertThatThrownBy(() -> {
            CommandParser.parse(incorrectCommand);
        }).isInstanceOf(IncorrectCommandException.class)
            .hasMessageContaining(SOME_VALUES_IN_FILE_PATH_ARGUMENT_EXCEPTION_TEXT);
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForParseCommandWithSomeValuesInFrom")
    public void parseCommandWithSomeValuesInFromArgument_ShouldThrowIncorrectCommandException(
        String incorrectCommand
    ) {
        assertThatThrownBy(() -> {
            CommandParser.parse(incorrectCommand);
        }).isInstanceOf(IncorrectCommandException.class)
            .hasMessageContaining(SOME_VALUES_IN_FROM_ARGUMENT_EXCEPTION_TEXT);
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForParseCommandWithSomeValuesInTo")
    public void parseCommandWithSomeValuesInToArgument_ShouldThrowIncorrectCommandException(
        String incorrectCommand
    ) {
        assertThatThrownBy(() -> {
            CommandParser.parse(incorrectCommand);
        }).isInstanceOf(IncorrectCommandException.class)
            .hasMessageContaining(SOME_VALUES_IN_TO_ARGUMENT_EXCEPTION_TEXT);
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForParseCommandWithSomeValuesInFormat")
    public void parseCommandWithSomeValuesInFormatArgument_ShouldThrowIncorrectCommandException(
        String incorrectCommand
    ) {
        assertThatThrownBy(() -> {
            CommandParser.parse(incorrectCommand);
        }).isInstanceOf(IncorrectCommandException.class)
            .hasMessageContaining(SOME_VALUES_IN_FORMAT_ARGUMENT_EXCEPTION_TEXT);
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForParseCommandWithSomeValuesInFilterField")
    public void parseCommandWithSomeValuesInFilterFieldArgument_ShouldThrowIncorrectCommandException(
        String incorrectCommand
    ) {
        assertThatThrownBy(() -> {
            CommandParser.parse(incorrectCommand);
        }).isInstanceOf(IncorrectCommandException.class)
            .hasMessageContaining(SOME_VALUES_IN_FILTER_FIELD_EXCEPTION_TEXT);
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForParseCommandWithSomeValuesInFilterValue")
    public void parseCommandWithSomeValuesInFilterValueArgument_ShouldThrowIncorrectCommandException(
        String incorrectCommand
    ) {
        assertThatThrownBy(() -> {
            CommandParser.parse(incorrectCommand);
        }).isInstanceOf(IncorrectCommandException.class)
            .hasMessageContaining(SOME_VALUES_IN_FILTER_VALUE_EXCEPTION_TEXT);
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForParseCommandWithIncorrectDate")
    public void parseCommandWithIncorrectFromDate_ShouldThrowIncorrectCommandException(String incorrectDate) {
        final String incorrectCommand = "analyzer --path file.txt --from " + incorrectDate;

        assertThatThrownBy(() -> {
            CommandParser.parse(incorrectCommand);
        }).isInstanceOf(IncorrectCommandException.class)
            .hasMessageContaining(INCORRECT_DATE_FORMAT_EXCEPTION_TEXT);
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForParseCommandWithIncorrectDate")
    public void parseCommandWithIncorrectToDate_ShouldThrowIncorrectCommandException(String incorrectDate) {
        final String incorrectCommand = "analyzer --path file.txt --to " + incorrectDate;

        assertThatThrownBy(() -> {
            CommandParser.parse(incorrectCommand);
        }).isInstanceOf(IncorrectCommandException.class)
            .hasMessageContaining(INCORRECT_DATE_FORMAT_EXCEPTION_TEXT);
    }

    @ParameterizedTest
    @CsvSource(value = {"analyzer --path", "analyzer"})
    public void parseCommandWithNoValueForFilePathArgument_ShouldThrowIncorrectCommandException(
        String incorrectCommand
    ) {
        assertThatThrownBy(() -> {
            CommandParser.parse(incorrectCommand);
        }).isInstanceOf(IncorrectCommandException.class)
            .hasMessageContaining(NOT_VALUE_FOR_FILE_PATH_ARGUMENT_EXCEPTION_TEXT);
    }

    @Test
    public void parseCommandWithNoValueForFromArgument_ShouldThrowIncorrectCommandException() {
        final String incorrectCommand = "analyzer --path file.txt --from";

        assertThatThrownBy(() -> {
            CommandParser.parse(incorrectCommand);
        }).isInstanceOf(IncorrectCommandException.class)
            .hasMessageContaining(NOT_VALUE_FOR_FROM_ARGUMENT_EXCEPTION_TEXT);
    }

    @Test
    public void parseCommandWithNoValueForToArgument_ShouldThrowIncorrectCommandException() {
        final String incorrectCommand = "analyzer --path file.txt --to";

        assertThatThrownBy(() -> {
            CommandParser.parse(incorrectCommand);
        }).isInstanceOf(IncorrectCommandException.class)
            .hasMessageContaining(NOT_VALUE_FOR_TO_ARGUMENT_EXCEPTION_TEXT);
    }

    @Test
    public void parseCommandWithNoValueForFormatArgument_ShouldThrowIncorrectCommandException() {
        final String incorrectCommand = "analyzer --path file.txt --format";

        assertThatThrownBy(() -> {
            CommandParser.parse(incorrectCommand);
        }).isInstanceOf(IncorrectCommandException.class)
            .hasMessageContaining(NOT_VALUE_FOR_FORMAT_ARGUMENT_EXCEPTION_TEXT);
    }

    @Test
    public void parseCommandWithNoValueForFilterFieldArgument_ShouldThrowIncorrectCommandException() {
        final String incorrectCommand = "analyzer --path file.txt --filter-field";

        assertThatThrownBy(() -> {
            CommandParser.parse(incorrectCommand);
        }).isInstanceOf(IncorrectCommandException.class)
            .hasMessageContaining(NOT_VALUE_FOR_FILTER_FIELD_ARGUMENT_EXCEPTION_TEXT);
    }

    @Test
    public void parseCommandWithNoValueForFilterValueArgument_ShouldThrowIncorrectCommandException() {
        final String incorrectCommand = "analyzer --path file.txt --filter-field method --filter-value";

        assertThatThrownBy(() -> {
            CommandParser.parse(incorrectCommand);
        }).isInstanceOf(IncorrectCommandException.class)
            .hasMessageContaining(NOT_VALUE_FOR_FILTER_VALUE_ARGUMENT_EXCEPTION_TEXT);
    }

    private static String[] getArgumentsForParseCommandWithIncorrectDate() {
        return new String[] {
            "31-08-2024",
            "2024/08/31",
            "2024.08.31",
            "31/08/24",
            "08-31-2024",
            "2024-8-31",
            "2024-08-5",
            "2024-13-01",
            "2024-00-01",
            "2024-01-32",
            "2024-02-30",
            "2024-04-31",
            "2024-08-00",
            "2024-08",
            "2024",
            "08-31",
            "20240831",
            "2024-08-31-01",
            "2024- 08-31",
        };
    }

    private static String[] getArgumentsForParseCommandWithSomeValuesInFilterField() {
        return new String[] {
            "analyzer --filter-field address address",
            "analyzer --filter-field address address address",
            "analyzer --filter-field address address address address",
            "analyzer --filter-field address address address address address",

            "analyzer --filter-field user user",
            "analyzer --filter-field user user user",
            "analyzer --filter-field user user user user",
            "analyzer --filter-field user user user user user",

            "analyzer --filter-field method method",
            "analyzer --filter-field method method method",
            "analyzer --filter-field method method method method",
            "analyzer --filter-field method method method method method",

            "analyzer --filter-field agent agent",
            "analyzer --filter-field agent agent agent",
            "analyzer --filter-field agent agent agent agent",
            "analyzer --filter-field agent agent agent agent agent",

            "analyzer --filter-field status status",
            "analyzer --filter-field status status status",
            "analyzer --filter-field status status status status",
            "analyzer --filter-field status status status status status",
        };
    }

    private static String[] getArgumentsForParseCommandWithSomeValuesInFilterValue() {
        return new String[] {
            "analyzer --filter-field address --filter-value 93.180.71.3 93.180.71.3",
            "analyzer --filter-field user --filter-value - -",
            "analyzer --filter-field method --filter-value get get",
            "analyzer --filter-field agent --filter-value agent agent",
            "analyzer --filter-field status --filter-value 200 200",
        };
    }

    private static String[] getArgumentsForParseCommandWithSomeValuesInFormat() {
        return new String[] {
            "analyzer --format markdown markdown",
            "analyzer --format markdown markdown markdown",
            "analyzer --format markdown markdown markdown markdown",
            "analyzer --format markdown markdown markdown markdown markdown",
            "analyzer --format markdown adoc",
            "analyzer --format markdown adoc adoc",
            "analyzer --format markdown adoc adoc adoc",
            "analyzer --format markdown adoc adoc adoc adoc",
        };
    }

    private static String[] getArgumentsForParseCommandWithSomeValuesInTo() {
        return new String[] {
            "analyzer --to 2024-08-31 2024-08-31",
            "analyzer --to 2024-08-31 2024-08-31 2024-08-31",
            "analyzer --to 2024-08-31 2024-08-31 2024-08-31 2024-08-31",
            "analyzer --to 2024-08-31 2024-08-31 2024-08-31 2024-08-31 2024-08-31",
        };
    }

    private static String[] getArgumentsForParseCommandWithSomeValuesInFrom() {
        return new String[] {
            "analyzer --from 2024-08-31 2024-08-31",
            "analyzer --from 2024-08-31 2024-08-31 2024-08-31",
            "analyzer --from 2024-08-31 2024-08-31 2024-08-31 2024-08-31",
            "analyzer --from 2024-08-31 2024-08-31 2024-08-31 2024-08-31 2024-08-31",
        };
    }

    private static String[] getArgumentsForParseCommandWithSomeValuesInFilePath() {
        return new String[] {
            "analyzer --path file.txt file.txt",
            "analyzer --path file.txt file.txt file.txt",
            "analyzer --path file.txt file.txt file.txt file.txt",
            "analyzer --path file.txt file.txt file.txt file.txt file.txt",
        };
    }

    private static String[] getArgumentsForParseCommandWithDuplicateArguments() {
        return new String[] {
            "analyzer --path file.txt --path",
            "analyzer --path file.txt --path file.txt",
            "analyzer --path --path",
            "analyzer --path file.txt --from 2024-08-31 --from",
            "analyzer --path file.txt --from 2024-08-31 --from 2024-08-31",
            "analyzer --path file.txt --to 2024-08-31 --to",
            "analyzer --path file.txt --to 2024-08-31 --to 2024-08-31",
            "analyzer --path --path --format --format",
            "analyzer --path --path --format adoc --format",
            "analyzer --path --path --format markdown --format",
            "analyzer --path --path --format adoc --format adoc",
            "analyzer --path --path --format markdown --format markdown",
            "analyzer --path --path --format markdown --format --path",
            "analyzer --path --path --format markdown --format --from",
            "analyzer --path --path --format markdown --format --to",
        };
    }

    private static String[] getArgumentsForParseCommandWithExitArgumentAndOthers() {
        return new String[] {
            "analyzer --exit --path file.txt --exit",
            "analyzer --path file.txt --exit",
            "analyzer --path file.txt --exit --from 2024-08-31",
            "analyzer --path file.txt --from 2024-08-31 --exit --to 2025-08-01",
            "analyzer --path file.txt --exit --to 2024-08-31",
            "analyzer --path file.txt --to 2024-08-31 --format markdown --exit",
            "analyzer --path file.txt --to 2024-08-31 --exit --format adoc",
            "analyzer --path file.txt --exit --from 2024-08-31 --to 2025-08-31 --format adoc",
            "     analyzer       --to 2025-08-31  --exit     --from 2024-08-31        --format       adoc       --path    file.txt    "
        };
    }

    private static List<Object[]> getArgumentsForParseCommand() {
        final List<Object[]> result = new ArrayList<>();

        result.add(new Object[]{
            "analyzer --path file.txt",
            new Command("file.txt", null, null, null, null, null)
        });

        result.add(new Object[]{
            "analyzer --path file.txt --from 2024-08-31",
            new Command("file.txt", LocalDate.parse("2024-08-31"), null, null, null, null)
        });

        result.add(new Object[]{
            "analyzer --path file.txt --from 2024-08-31 --to 2025-08-01",
            new Command("file.txt", LocalDate.parse("2024-08-31"), LocalDate.parse("2025-08-01"), null, null, null)
        });

        result.add(new Object[]{
            "analyzer --path file.txt --to 2024-08-31",
            new Command("file.txt", null, LocalDate.parse("2024-08-31"), null, null, null)
        });

        result.add(new Object[]{
            "analyzer --path file.txt --to 2024-08-31 --format markdown",
            new Command("file.txt", null, LocalDate.parse("2024-08-31"), OutputFormat.MARKDOWN, null, null)
        });

        result.add(new Object[]{
            "analyzer --path file.txt --to 2024-08-31 --format adoc",
            new Command("file.txt", null, LocalDate.parse("2024-08-31"), OutputFormat.ADOC, null, null)
        });

        result.add(new Object[]{
            "analyzer --path file.txt --from 2024-08-31 --to 2025-08-31 --format adoc",
            new Command("file.txt", LocalDate.parse("2024-08-31"), LocalDate.parse("2025-08-31"), OutputFormat.ADOC, null, null)
        });

        result.add(new Object[]{
            "analyzer --path file.txt --format adoc",
            new Command("file.txt", null, null, OutputFormat.ADOC, null, null)
        });

        result.add(new Object[]{
            "analyzer --path file.txt --format markdown",
            new Command("file.txt", null, null, OutputFormat.MARKDOWN, null, null)
        });

        result.add(new Object[]{
            "analyzer --to 2025-08-31 --from 2024-08-31 --format adoc --path file.txt ",
            new Command("file.txt", LocalDate.parse("2024-08-31"),
                LocalDate.parse("2025-08-31"), OutputFormat.ADOC, null, null
            )
        });

        result.add(new Object[]{
            "     analyzer       --to 2025-08-31       --from 2024-08-31        --format       adoc       --path    file.txt    ",
            new Command("file.txt", LocalDate.parse("2024-08-31"),
                LocalDate.parse("2025-08-31"), OutputFormat.ADOC, null, null
            )
        });

        result.add(new Object[]{
            "     analyzer       --to 2025-08-31       --from 2024-08-31      " +
                "  --format       adoc       --path    file.txt  --filter-field address  --filter-value 199.38.183.217   ",
            new Command(
                "file.txt", LocalDate.parse("2024-08-31"),
                LocalDate.parse("2025-08-31"), OutputFormat.ADOC, FilterField.ADDRESS, "199.38.183.217")
        });

        result.add(new Object[]{
            "     analyzer       --to 2025-08-31       --from 2024-08-31      " +
                "  --format       adoc       --path    Академия.txt  --filter-field address  --filter-value 199.38.183.217   ",
            new Command(
                "Академия.txt", LocalDate.parse("2024-08-31"),
                LocalDate.parse("2025-08-31"), OutputFormat.ADOC, FilterField.ADDRESS, "199.38.183.217")
        });

        result.add(new Object[]{
            "     analyzer       --to 2025-08-31       --from 2024-08-31      " +
                "  --format       adoc       --path    Академия.txt  --filter-field user  --filter-value -   ",
            new Command(
                "Академия.txt", LocalDate.parse("2024-08-31"),
                LocalDate.parse("2025-08-31"), OutputFormat.ADOC, FilterField.USER, "-")
        });

        result.add(new Object[]{
            "     analyzer       --to 2025-08-31       --from 2024-08-31      " +
                "  --format       adoc       --path    Академия.txt  --filter-field method  --filter-value get   ",
            new Command(
                "Академия.txt", LocalDate.parse("2024-08-31"),
                LocalDate.parse("2025-08-31"), OutputFormat.ADOC, FilterField.HTTP_METHOD, "get")
        });

        result.add(new Object[]{
            "     analyzer       --to 2025-08-31       --from 2024-08-31      " +
                "  --format       adoc       --path    Академия.txt  --filter-field agent  --filter-value -   ",
            new Command(
                "Академия.txt", LocalDate.parse("2024-08-31"),
                LocalDate.parse("2025-08-31"), OutputFormat.ADOC, FilterField.AGENT, "-")
        });

        result.add(new Object[]{
            "     analyzer       --to 2025-08-31       --from 2024-08-31      " +
                "  --format       adoc       --path    Академия.txt  --filter-field status  --filter-value 200   ",
            new Command(
                "Академия.txt", LocalDate.parse("2024-08-31"),
                LocalDate.parse("2025-08-31"), OutputFormat.ADOC, FilterField.HTTP_STATUS, "200")
        });

        result.add(new Object[]{
            "analyzer --exit",
            null
        });

        return result;
    }
}
