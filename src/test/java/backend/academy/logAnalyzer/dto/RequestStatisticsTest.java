package backend.academy.logAnalyzer.dto;

import backend.academy.logAnalyzer.CommonTest;
import backend.academy.logAnalyzer.enums.HttpMethod;
import backend.academy.logAnalyzer.enums.HttpStatus;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NOT_POSITIVE_COUNT_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NULL_ADDRESSES_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NULL_COUNT_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NULL_HTTP_METHODS_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NULL_HTTP_STATUSES_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NULL_RESOURCES_EXCEPTION_TEXT;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RequestStatisticsTest extends CommonTest {

    private static final int DEFAULT_COUNT_VALUE = 0;

    @ParameterizedTest
    @MethodSource("getArgumentsForCreateRequestStatistics")
    public void createRequestStatistics_ShouldCreateRequestStatistics(
        Map<String, Integer> resources, Map<HttpStatus, Integer> httpStatuses,
        Map<String, Integer> addresses, Map<HttpMethod, Integer> httpMethods
    ) {
        final RequestStatistics requestStatistics = new RequestStatistics(
            resources, httpStatuses, addresses, httpMethods
        );

        assertEquals(requestStatistics.resources(), resources);
        assertEquals(requestStatistics.httpStatuses(), httpStatuses);
        assertEquals(requestStatistics.addresses(), addresses);
        assertEquals(requestStatistics.httpMethods(), httpMethods);
    }

    @Test
    public void createRequestStatisticsWithNullResources_ShouldThrowNullPointerException() {
        assertThatThrownBy(() -> {
            new RequestStatistics(null, new HashMap<>(), new HashMap<>(), new HashMap<>());
        }).isInstanceOf(NullPointerException.class)
            .hasMessageContaining(NULL_RESOURCES_EXCEPTION_TEXT);
    }

    @Test
    public void createRequestsStatisticsWithNullKeyInResource_ShouldThrowNullPointerException() {
        final Map<String, Integer> incorrectStatistics = new HashMap<>();
        incorrectStatistics.put(null, DEFAULT_COUNT_VALUE);

        assertThatThrownBy(() -> {
            new RequestStatistics(incorrectStatistics, new HashMap<>(), new HashMap<>(), new HashMap<>());
        }).isInstanceOf(NullPointerException.class)
            .hasMessageContaining(NULL_RESOURCES_EXCEPTION_TEXT);
    }

    @Test
    public void createRequestsStatisticsWithNullValueInResource_ShouldThrowNullPointerException() {
        final Map<String, Integer> incorrectStatistics = new HashMap<>();
        incorrectStatistics.put(getRandomString(), null);

        assertThatThrownBy(() -> {
            new RequestStatistics(incorrectStatistics, new HashMap<>(), new HashMap<>(), new HashMap<>());
        }).isInstanceOf(NullPointerException.class)
            .hasMessageContaining(NULL_COUNT_EXCEPTION_TEXT);
    }

    @ParameterizedTest
    @MethodSource("getNotPositiveNumbers")
    public void createRequestsStatisticsWithNotPositiveValueInResource_ShouldThrowIllegalArgumentException(int notPositiveNumber) {
        final Map<String, Integer> incorrectStatistics = new HashMap<>();
        incorrectStatistics.put(getRandomString(), notPositiveNumber);

        assertThatThrownBy(() -> {
            new RequestStatistics(incorrectStatistics, new HashMap<>(), new HashMap<>(), new HashMap<>());
        }).isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining(NOT_POSITIVE_COUNT_EXCEPTION_TEXT);
    }

    @Test
    public void createRequestStatisticsWithNullHttpStatuses_ShouldThrowNullPointerException() {
        assertThatThrownBy(() -> {
            new RequestStatistics(new HashMap<>(), null, new HashMap<>(), new HashMap<>());
        }).isInstanceOf(NullPointerException.class)
            .hasMessageContaining(NULL_HTTP_STATUSES_EXCEPTION_TEXT);
    }

    @Test
    public void createRequestsStatisticsWithNullKeyInHttpStatuses_ShouldThrowNullPointerException() {
        final Map<HttpStatus, Integer> incorrectStatistics = new HashMap<>();
        incorrectStatistics.put(null, DEFAULT_COUNT_VALUE);

        assertThatThrownBy(() -> {
            new RequestStatistics( new HashMap<>(), incorrectStatistics, new HashMap<>(), new HashMap<>());
        }).isInstanceOf(NullPointerException.class)
            .hasMessageContaining(NULL_HTTP_STATUSES_EXCEPTION_TEXT);
    }

    @ParameterizedTest
    @EnumSource(HttpStatus.class)
    public void createRequestsStatisticsWithNullValueInHttpStatuses_ShouldThrowNullPointerException(HttpStatus httpStatus) {
        final Map<HttpStatus, Integer> incorrectStatistics = new HashMap<>();
        incorrectStatistics.put(httpStatus, null);

        assertThatThrownBy(() -> {
            new RequestStatistics(new HashMap<>(), incorrectStatistics, new HashMap<>(), new HashMap<>());
        }).isInstanceOf(NullPointerException.class)
            .hasMessageContaining(NULL_COUNT_EXCEPTION_TEXT);
    }

    @ParameterizedTest
    @EnumSource(HttpStatus.class)
    public void createRequestsStatisticsWithNotPositiveValueInHttpStatuses_ShouldThrowIllegalArgumentException(HttpStatus httpStatus) {
        final Map<HttpStatus, Integer> incorrectStatistics = new HashMap<>();
        final int minValue = -1000;
        final int maxValue = -1;

        incorrectStatistics.put(httpStatus, getRandomNumber(minValue, maxValue));

        assertThatThrownBy(() -> {
            new RequestStatistics(new HashMap<>(), incorrectStatistics, new HashMap<>(), new HashMap<>());
        }).isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining(NOT_POSITIVE_COUNT_EXCEPTION_TEXT);
    }

    @Test
    public void createRequestStatisticsWithNullAddresses_ShouldThrowNullPointerException() {
        assertThatThrownBy(() -> {
            new RequestStatistics(new HashMap<>(), new HashMap<>(), null, new HashMap<>());
        }).isInstanceOf(NullPointerException.class)
            .hasMessageContaining(NULL_ADDRESSES_EXCEPTION_TEXT);
    }

    @Test
    public void createRequestsStatisticsWithNullKeyInAddresses_ShouldThrowNullPointerException() {
        final Map<String, Integer> incorrectStatistics = new HashMap<>();
        incorrectStatistics.put(null, DEFAULT_COUNT_VALUE);

        assertThatThrownBy(() -> {
            new RequestStatistics(new HashMap<>(), new HashMap<>(), incorrectStatistics, new HashMap<>());
        }).isInstanceOf(NullPointerException.class)
            .hasMessageContaining(NULL_ADDRESSES_EXCEPTION_TEXT);
    }

    @Test
    public void createRequestsStatisticsWithNullValueInAddresses_ShouldThrowNullPointerException() {
        final Map<String, Integer> incorrectStatistics = new HashMap<>();
        incorrectStatistics.put(getRandomString(), null);

        assertThatThrownBy(() -> {
            new RequestStatistics(new HashMap<>(), new HashMap<>(), incorrectStatistics, new HashMap<>());
        }).isInstanceOf(NullPointerException.class)
            .hasMessageContaining(NULL_COUNT_EXCEPTION_TEXT);
    }

    @ParameterizedTest
    @MethodSource("getNotPositiveNumbers")
    public void createRequestsStatisticsWithNotPositiveValueInAddresses_ShouldThrowIllegalArgumentException(int notPositiveNumber) {
        final Map<String, Integer> incorrectStatistics = new HashMap<>();
        incorrectStatistics.put(getRandomString(), notPositiveNumber);

        assertThatThrownBy(() -> {
            new RequestStatistics(new HashMap<>(), new HashMap<>(), incorrectStatistics, new HashMap<>());
        }).isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining(NOT_POSITIVE_COUNT_EXCEPTION_TEXT);
    }

    @Test
    public void createRequestStatisticsWithNullHttpMethods_ShouldThrowNullPointerException() {
        assertThatThrownBy(() -> {
            new RequestStatistics(new HashMap<>(), new HashMap<>(), new HashMap<>(), null);
        }).isInstanceOf(NullPointerException.class)
            .hasMessageContaining(NULL_HTTP_METHODS_EXCEPTION_TEXT);
    }

    @Test
    public void createRequestsStatisticsWithNullKeyInHttpMethods_ShouldThrowNullPointerException() {
        final Map<HttpMethod, Integer> incorrectStatistics = new HashMap<>();
        incorrectStatistics.put(null, DEFAULT_COUNT_VALUE);

        assertThatThrownBy(() -> {
            new RequestStatistics(new HashMap<>(), new HashMap<>(), new HashMap<>(), incorrectStatistics);
        }).isInstanceOf(NullPointerException.class)
            .hasMessageContaining(NULL_HTTP_METHODS_EXCEPTION_TEXT);
    }

    @ParameterizedTest
    @EnumSource(HttpMethod.class)
    public void createRequestsStatisticsWithNullValueInHttpStatuses_ShouldThrowNullPointerException(HttpMethod httpMethod) {
        final Map<HttpMethod, Integer> incorrectStatistics = new HashMap<>();
        incorrectStatistics.put(httpMethod, null);

        assertThatThrownBy(() -> {
            new RequestStatistics(new HashMap<>(), new HashMap<>(), new HashMap<>(), incorrectStatistics);
        }).isInstanceOf(NullPointerException.class)
            .hasMessageContaining(NULL_COUNT_EXCEPTION_TEXT);
    }

    @ParameterizedTest
    @EnumSource(HttpMethod.class)
    public void createRequestsStatisticsWithNotPositiveValueInHttpMethods_ShouldThrowIllegalArgumentException(HttpMethod httpMethod) {
        final Map<HttpMethod, Integer> incorrectStatistics = new HashMap<>();
        final int minValue = -1000;
        final int maxValue = -1;

        incorrectStatistics.put(httpMethod, getRandomNumber(minValue, maxValue));

        assertThatThrownBy(() -> {
            new RequestStatistics(new HashMap<>(), new HashMap<>(), new HashMap<>(), incorrectStatistics);
        }).isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining(NOT_POSITIVE_COUNT_EXCEPTION_TEXT);
    }

    private static List<Object[]> getArgumentsForCreateRequestStatistics() {
        final List<Object[]> result = new ArrayList<>();
        final int count = 100;

        for (int i = 0; i < count; i++) {
            result.add(new Object[]{
                Map.of(getRandomString(), getRandomNumber()),
                getRandomHttpStatusesStatistics(),
                Map.of(getRandomString(), getRandomNumber()),
                getRandomHttpMethodsStatistics()
            });
        }

        return result;
    }
}
