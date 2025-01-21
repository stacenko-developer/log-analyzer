package backend.academy.logAnalyzer.dto;

import backend.academy.logAnalyzer.enums.HttpMethod;
import backend.academy.logAnalyzer.enums.HttpStatus;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NOT_POSITIVE_COUNT_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NULL_ADDRESSES_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NULL_COUNT_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NULL_HTTP_METHODS_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NULL_HTTP_STATUSES_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NULL_RESOURCES_EXCEPTION_TEXT;

public record RequestStatistics(Map<String, Integer> resources, Map<HttpStatus, Integer> httpStatuses,
                                Map<String, Integer> addresses, Map<HttpMethod, Integer> httpMethods) {
    public RequestStatistics {
        validateResources(resources);
        validateHttpStatuses(httpStatuses);
        validateAddresses(addresses);
        validateHttpMethods(httpMethods);
    }

    private void validateResources(Map<String, Integer> resources) {
        if (resources == null) {
            throw new NullPointerException(NULL_RESOURCES_EXCEPTION_TEXT);
        }

        resources.forEach((key, value) -> {
            if (StringUtils.isBlank(key)) {
                throw new NullPointerException(NULL_RESOURCES_EXCEPTION_TEXT);
            }

            validateCount(value);
        });
    }

    private void validateHttpStatuses(Map<HttpStatus, Integer> httpStatuses) {
        if (httpStatuses == null) {
            throw new NullPointerException(NULL_HTTP_STATUSES_EXCEPTION_TEXT);
        }

        httpStatuses.forEach((key, value) -> {
            if (key == null) {
                throw new NullPointerException(NULL_HTTP_STATUSES_EXCEPTION_TEXT);
            }

            validateCount(value);
        });
    }

    private void validateAddresses(Map<String, Integer> addresses) {
        if (addresses == null) {
            throw new NullPointerException(NULL_ADDRESSES_EXCEPTION_TEXT);
        }

        addresses.forEach((key, value) -> {
            if (StringUtils.isBlank(key)) {
                throw new NullPointerException(NULL_ADDRESSES_EXCEPTION_TEXT);
            }

            validateCount(value);
        });
    }

    private void validateHttpMethods(Map<HttpMethod, Integer> httpMethods) {
        if (httpMethods == null) {
            throw new NullPointerException(NULL_HTTP_METHODS_EXCEPTION_TEXT);
        }

        httpMethods.forEach((key, value) -> {
            if (key == null) {
                throw new NullPointerException(NULL_HTTP_METHODS_EXCEPTION_TEXT);
            }

            validateCount(value);
        });
    }

    private void validateCount(Integer count) {
        if (count == null) {
            throw new NullPointerException(NULL_COUNT_EXCEPTION_TEXT);
        }

        if (count < 0) {
            throw new IllegalArgumentException(NOT_POSITIVE_COUNT_EXCEPTION_TEXT);
        }
    }
}
