package backend.academy.logAnalyzer.dto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.INCORRECT_DATE_RANGE_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NOT_POSITIVE_PERCENTILE_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NOT_POSITIVE_REQUESTS_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NOT_POSITIVE_RESPONSE_AVERAGE_SIZE_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NULL_FILE_PATH_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NULL_PERCENTILE_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NULL_RESPONSE_AVERAGE_SIZE_EXCEPTION_TEXT;

public record GeneralInformation(Set<String> filePaths, LocalDate from, LocalDate to,
                                 int requestsCount, BigDecimal responseAverageSize, BigInteger percentile) {
    public GeneralInformation {
        validateFilePaths(filePaths);
        validateDateRanges(from, to);
        validateRequestsCount(requestsCount);
        validateResponseAverageSize(responseAverageSize);
        validatePercentile(percentile);
    }

    private void validateFilePaths(Set<String> filePaths) {
        if (filePaths == null || filePaths.isEmpty() || filePaths.stream().anyMatch(StringUtils::isBlank)) {
            throw new NullPointerException(NULL_FILE_PATH_EXCEPTION_TEXT);
        }
    }

    private void validateDateRanges(LocalDate from, LocalDate to) {
        if ((from != null && to != null) && (from.isAfter(to) || from.equals(to))) {
            throw new IllegalArgumentException(INCORRECT_DATE_RANGE_EXCEPTION_TEXT);
        }
    }

    private void validateRequestsCount(int requestsCount) {
        if (requestsCount < 0) {
            throw new IllegalArgumentException(NOT_POSITIVE_REQUESTS_EXCEPTION_TEXT);
        }
    }

    private void validateResponseAverageSize(BigDecimal responseAverageSize) {
        if (responseAverageSize == null) {
            throw new NullPointerException(NULL_RESPONSE_AVERAGE_SIZE_EXCEPTION_TEXT);
        }

        if (responseAverageSize.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(NOT_POSITIVE_RESPONSE_AVERAGE_SIZE_EXCEPTION_TEXT);
        }
    }

    private void validatePercentile(BigInteger percentile) {
        if (percentile == null) {
            throw new NullPointerException(NULL_PERCENTILE_EXCEPTION_TEXT);
        }

        if (percentile.compareTo(BigInteger.ZERO) < 0) {
            throw new IllegalArgumentException(NOT_POSITIVE_PERCENTILE_EXCEPTION_TEXT);
        }
    }
}
