package backend.academy.logAnalyzer;

import backend.academy.logAnalyzer.enums.HttpMethod;
import backend.academy.logAnalyzer.enums.HttpProtocol;
import backend.academy.logAnalyzer.enums.HttpStatus;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CommonTest {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    protected static final int DEFAULT_DAYS_COUNT = 1;
    protected static final String DEFAULT_VALUE = "-";

    protected static int getRandomNumber(int minValue, int maxValue) {
        return SECURE_RANDOM.nextInt(minValue, maxValue + 1);
    }

    protected static int getRandomNumber() {
        final int minValue = 0;
        final int maxValue = 1000;

        return getRandomNumber(minValue, maxValue);
    }

    protected static BigInteger[] getNotPositiveBigNumbers() {
        final int minValue = -100;
        final int maxValue = 0;

        return getBigNumbers(minValue, maxValue);
    }

    protected static BigInteger[] getBigNumbers(int minValue, int maxValue) {
        return IntStream
            .range(minValue, maxValue)
            .mapToObj(BigInteger::valueOf)
            .toArray(BigInteger[]::new);
    }

    protected static int[] getNotPositiveNumbers() {
        final int minValue = -100;
        final int maxValue = 0;

        return IntStream
            .range(minValue, maxValue)
            .toArray();
    }

    protected static BigDecimal[] getNotPositiveBigRealNumbers() {
        final int minValue = -100;
        final int maxValue = 0;

        return IntStream
            .range(minValue, maxValue)
            .mapToObj(BigDecimal::valueOf)
            .toArray(BigDecimal[]::new);
    }

    protected static String[] getArgumentsForTestStringForNull() {
        return new String[] {
            null, "", " ", "   ", "       "
        };
    }

    protected static String[] getArgumentsForGetEnumByIncorrectValue() {
        return new String[] {
            null, "", " ", "   ", "Неизвестный ключ"
        };
    }

    protected static List<Object[]> getArgumentsForCreateRequest() {
        return Stream.of(HttpMethod.values())
            .flatMap(httpMethod -> Stream.of(HttpProtocol.values())
                .map(httpProtocol -> new Object[] {
                    httpMethod, getRandomAddress(), httpProtocol
                }))
            .collect(Collectors.toList());
    }

    protected static String getRandomString() {
        final int leftLimit = 'a';
        final int rightLimit = 'z';
        final int targetStringLength = 10;

        return SECURE_RANDOM.ints(leftLimit, rightLimit + 1)
            .limit(targetStringLength)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();
    }

    protected static String getRandomAddress() {
        final int minValue = 100;
        final int maxValue = 300;

        return getRandomNumber(minValue, maxValue) + "." + getRandomNumber(minValue, maxValue) + "." + getRandomNumber(minValue, maxValue)
            + "." + getRandomNumber(minValue, maxValue);
    }

    protected static List<String> getRandomStringsList() {
        final int randomStringsCount = 100;

        return getRandomStringsList(randomStringsCount);
    }

    protected static List<String> getRandomStringsList(int randomStringsCount) {
        final int minRangeValue = 0;

        return IntStream.range(minRangeValue, randomStringsCount)
            .mapToObj(_ -> getRandomString())
            .toList();
    }

    protected static Map<HttpStatus, Integer> getRandomHttpStatusesStatistics() {
        final Map<HttpStatus, Integer> httpStatuses = new HashMap<>();

        for (HttpStatus httpStatus : HttpStatus.values()) {
            httpStatuses.put(httpStatus, getRandomNumber());
        }

        return httpStatuses;
    }

    protected static Map<HttpMethod, Integer> getRandomHttpMethodsStatistics() {
        final Map<HttpMethod, Integer> httpMethods = new HashMap<>();

        for (HttpMethod httpMethod : HttpMethod.values()) {
            httpMethods.put(httpMethod, getRandomNumber());
        }

        return httpMethods;
    }

    protected static List<String> getArgumentsForCreateCommandWithFilterAndIncorrectFilterValue() {
        final int minValue = 0;
        final int maxValue = 1000;
        final List<String> result = new ArrayList<>();

        for (int i = minValue; i <= maxValue; i++) {
            try {
                HttpStatus.getHttpStatusByCode(i);
            } catch (Exception exception) {
                result.add(String.valueOf(i));
            }
        }

        return result;
    }
}
