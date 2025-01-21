package backend.academy.logAnalyzer.dto;

import backend.academy.logAnalyzer.enums.FilterField;
import backend.academy.logAnalyzer.enums.HttpMethod;
import backend.academy.logAnalyzer.enums.HttpStatus;
import backend.academy.logAnalyzer.enums.OutputFormat;
import backend.academy.logAnalyzer.exception.IncorrectCommandException;
import java.time.LocalDate;
import org.apache.commons.lang3.StringUtils;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.HTTP_METHOD_NOT_SUPPORTED_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.HTTP_STATUS_NOT_SUPPORTED_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.INCORRECT_DATE_RANGE_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.INCORRECT_FILTERS_IN_COMMAND_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NULL_FILE_PATH_EXCEPTION_TEXT;

public record Command(String filePath, LocalDate from, LocalDate to,
                      OutputFormat format, FilterField filterField, String filterValue) {
    public Command {
        validateFilePath(filePath);
        validateDateRanges(from, to);
        validateFilter(filterField, filterValue);
    }

    private void validateFilter(FilterField filterField, String filterValue) {
        if (filterField != null && StringUtils.isBlank(filterValue)) {
            throw new IncorrectCommandException(INCORRECT_FILTERS_IN_COMMAND_EXCEPTION_TEXT);
        }

        if (filterField == null && StringUtils.isNotBlank(filterValue)) {
            throw new IncorrectCommandException(INCORRECT_FILTERS_IN_COMMAND_EXCEPTION_TEXT);
        }

        if (filterField != null) {
            switch (filterField) {
                case HTTP_METHOD:
                    validateHttpMethod(filterValue);
                    break;
                case HTTP_STATUS:
                    validateHttpStatus(filterValue);
                    break;
                default:
                    break;
            }
        }
    }

    private void validateFilePath(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            throw new IncorrectCommandException(NULL_FILE_PATH_EXCEPTION_TEXT);
        }
    }

    private void validateDateRanges(LocalDate from, LocalDate to) {
        if ((from != null && to != null) && (from.isAfter(to) || from.equals(to))) {
            throw new IncorrectCommandException(INCORRECT_DATE_RANGE_EXCEPTION_TEXT);
        }
    }

    private static void validateHttpStatus(String input) {
        try {
            HttpStatus.getHttpStatusByCode(Integer.parseInt(input));
        } catch (Exception ex) {
            throw new IncorrectCommandException(HTTP_STATUS_NOT_SUPPORTED_EXCEPTION_TEXT, ex);
        }
    }

    private static void validateHttpMethod(String input) {
        try {
            HttpMethod.getHttpMethodByValue(input);
        } catch (Exception ex) {
            throw new IncorrectCommandException(HTTP_METHOD_NOT_SUPPORTED_EXCEPTION_TEXT, ex);
        }
    }
}
