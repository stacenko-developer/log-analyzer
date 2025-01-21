package backend.academy.logAnalyzer.analyzer;

import backend.academy.logAnalyzer.dto.Command;
import backend.academy.logAnalyzer.enums.FilterField;
import backend.academy.logAnalyzer.enums.OutputFormat;
import backend.academy.logAnalyzer.exception.IncorrectCommandException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import static backend.academy.logAnalyzer.constants.ConstValues.BEGIN_COMMAND_VALUE;
import static backend.academy.logAnalyzer.constants.ConstValues.COMMAND_ARGUMENTS_DESCRIPTION;
import static backend.academy.logAnalyzer.constants.ConstValues.FILE_PATH_ARGUMENT;
import static backend.academy.logAnalyzer.constants.ConstValues.FILTER_FIELD_ARGUMENT;
import static backend.academy.logAnalyzer.constants.ConstValues.FILTER_VALUE_ARGUMENT;
import static backend.academy.logAnalyzer.constants.ConstValues.FORMAT_ARGUMENT;
import static backend.academy.logAnalyzer.constants.ConstValues.FROM_ARGUMENT;
import static backend.academy.logAnalyzer.constants.ConstValues.NO_LOG_ANALYZE_ARGUMENTS;
import static backend.academy.logAnalyzer.constants.ConstValues.TO_ARGUMENT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.DUPLICATE_ARGUMENTS_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.INCORRECT_BEGIN_COMMAND_VALUE_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.INCORRECT_DATE_FORMAT_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NOT_VALUE_FOR_FILE_PATH_ARGUMENT_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NOT_VALUE_FOR_FILTER_FIELD_ARGUMENT_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NOT_VALUE_FOR_FILTER_VALUE_ARGUMENT_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NOT_VALUE_FOR_FORMAT_ARGUMENT_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NOT_VALUE_FOR_FROM_ARGUMENT_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NOT_VALUE_FOR_TO_ARGUMENT_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NO_LOG_ANALYZE_ARGUMENT_WITH_OTHERS_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NULL_COMMAND_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.SOME_VALUES_IN_FILE_PATH_ARGUMENT_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.SOME_VALUES_IN_FILTER_FIELD_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.SOME_VALUES_IN_FILTER_VALUE_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.SOME_VALUES_IN_FORMAT_ARGUMENT_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.SOME_VALUES_IN_FROM_ARGUMENT_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.SOME_VALUES_IN_TO_ARGUMENT_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.VALUE_WITHOUT_ARGUMENT_EXCEPTION_TEXT;

@UtilityClass
public class CommandParser {

    private static final String SPACES_REGEX = "\\s+";

    private static String filePath;
    private static String format;
    private static String filterField;
    private static String filterValue;
    private static LocalDate from;
    private static LocalDate to;
    private static String currentArgument;

    public static Command parse(String input) {
        validateInput(input);

        final String[] command = processInput(input);
        final Map<String, Boolean> availableCommands = getAvailableCommands();

        validateCommand(command);
        initializeArguments();

        for (int i = 1; i < command.length; i++) {
            if (availableCommands.containsKey(command[i])) {
                currentArgument = command[i];

                if (NO_LOG_ANALYZE_ARGUMENTS.contains(currentArgument)) {
                    validateNoLogAnalyzeArgument(command, i);

                    return null;
                }

                handleCommand(command[i], availableCommands);
            } else {
                setValueForArgument(command[i]);
            }
        }

        validateValueContains(availableCommands);

        final OutputFormat resultOutputFormat = getOutputFormat(format);
        final FilterField resultFilterField = getFilterField(filterField);

        return new Command(
            filePath, from, to, resultOutputFormat, resultFilterField, filterValue
        );
    }

    private void setValueForArgument(String value) {
        if (StringUtils.isBlank(currentArgument)) {
            throw new IncorrectCommandException(VALUE_WITHOUT_ARGUMENT_EXCEPTION_TEXT);
        }

        switch (currentArgument) {
            case FILE_PATH_ARGUMENT:
                if (filePath != null) {
                    throw new IncorrectCommandException(SOME_VALUES_IN_FILE_PATH_ARGUMENT_EXCEPTION_TEXT);
                }

                filePath = value;
                break;
            case FROM_ARGUMENT:
                if (from != null) {
                    throw new IncorrectCommandException(SOME_VALUES_IN_FROM_ARGUMENT_EXCEPTION_TEXT);
                }

                from = getLocalDate(value);
                break;
            case TO_ARGUMENT:
                if (to != null) {
                    throw new IncorrectCommandException(SOME_VALUES_IN_TO_ARGUMENT_EXCEPTION_TEXT);
                }

                to = getLocalDate(value);
                break;
            case FORMAT_ARGUMENT:
                if (format != null) {
                    throw new IncorrectCommandException(SOME_VALUES_IN_FORMAT_ARGUMENT_EXCEPTION_TEXT);
                }

                format = value;
                break;
            case FILTER_FIELD_ARGUMENT:
                if (filterField != null) {
                    throw new IncorrectCommandException(SOME_VALUES_IN_FILTER_FIELD_EXCEPTION_TEXT);
                }

                filterField = value;
                break;
            case FILTER_VALUE_ARGUMENT:
                if (StringUtils.isNotBlank(filterValue)) {
                    throw new IncorrectCommandException(SOME_VALUES_IN_FILTER_VALUE_EXCEPTION_TEXT);
                }

                filterValue = value;
                break;
            default:
                break;
        }
    }

    private void validateValueContains(Map<String, Boolean> availableCommands) {
        if (StringUtils.isBlank(filePath)) {
            throw new IncorrectCommandException(NOT_VALUE_FOR_FILE_PATH_ARGUMENT_EXCEPTION_TEXT);
        }

        if (from == null && availableCommands.get(FROM_ARGUMENT)) {
            throw new IncorrectCommandException(NOT_VALUE_FOR_FROM_ARGUMENT_EXCEPTION_TEXT);
        }

        if (to == null && availableCommands.get(TO_ARGUMENT)) {
            throw new IncorrectCommandException(NOT_VALUE_FOR_TO_ARGUMENT_EXCEPTION_TEXT);
        }

        if (StringUtils.isBlank(format) && availableCommands.get(FORMAT_ARGUMENT)) {
            throw new IncorrectCommandException(NOT_VALUE_FOR_FORMAT_ARGUMENT_EXCEPTION_TEXT);
        }

        if (StringUtils.isBlank(filterField) && availableCommands.get(FILTER_FIELD_ARGUMENT)) {
            throw new IncorrectCommandException(NOT_VALUE_FOR_FILTER_FIELD_ARGUMENT_EXCEPTION_TEXT);
        }

        if (StringUtils.isBlank(filterValue) && availableCommands.get(FILTER_VALUE_ARGUMENT)) {
            throw new IncorrectCommandException(NOT_VALUE_FOR_FILTER_VALUE_ARGUMENT_EXCEPTION_TEXT);
        }
    }

    private static void validateNoLogAnalyzeArgument(String[] command, int index) {
        final int correctNoLogAnalyzeArgumentIndex = 1;

        if (command.length - 1 > index || index != correctNoLogAnalyzeArgumentIndex) {
            throw new IncorrectCommandException(NO_LOG_ANALYZE_ARGUMENT_WITH_OTHERS_EXCEPTION_TEXT);
        }
    }

    private static void initializeArguments() {
        filePath = null;
        format = null;
        filterField = null;
        filterValue = null;
        from = null;
        to = null;
        currentArgument = null;
    }

    private static FilterField getFilterField(String filterField) {
        return StringUtils.isNotBlank(filterField)
            ? FilterField.getFilterFieldByValue(filterField)
            : null;
    }

    private static OutputFormat getOutputFormat(String format) {
        return StringUtils.isNotBlank(format)
            ? OutputFormat.getOutputFormatByValue(format)
            : null;
    }

    private static void handleCommand(String command, Map<String, Boolean> availableCommands) {
        if (availableCommands.get(command)) {
            throw new IncorrectCommandException(DUPLICATE_ARGUMENTS_EXCEPTION_TEXT);
        }

        availableCommands.put(command, Boolean.TRUE);
    }

    private static void validateInput(String input) {
        if (StringUtils.isBlank(input)) {
            throw new IncorrectCommandException(NULL_COMMAND_EXCEPTION_TEXT);
        }
    }

    private static void validateCommand(String[] command) {
        final int index = 0;

        if (!BEGIN_COMMAND_VALUE.equals(command[index])) {
            throw new IncorrectCommandException(INCORRECT_BEGIN_COMMAND_VALUE_EXCEPTION_TEXT);
        }
    }

    private static String[] processInput(String input) {
        return input
            .trim()
            .replaceAll(SPACES_REGEX, " ")
            .split(" ");
    }

    private static Map<String, Boolean> getAvailableCommands() {
        final int availableCommandsCount = COMMAND_ARGUMENTS_DESCRIPTION.size();
        final double loadRatio = 0.75;

        final int size = (int) (availableCommandsCount / loadRatio) + 1;
        final Map<String, Boolean> availableCommands = new HashMap<>(size);

        for (String argument : COMMAND_ARGUMENTS_DESCRIPTION.keySet()) {
            availableCommands.put(argument, Boolean.FALSE);
        }

        return availableCommands;
    }

    private static LocalDate getLocalDate(String date) {
        try {
            return LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (Exception ex) {
            throw new IncorrectCommandException(INCORRECT_DATE_FORMAT_EXCEPTION_TEXT, ex);
        }
    }
}
