package backend.academy.logAnalyzer.dto;

import backend.academy.logAnalyzer.CommonTest;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.INCORRECT_DATE_RANGE_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NOT_POSITIVE_PERCENTILE_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NOT_POSITIVE_REQUESTS_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NOT_POSITIVE_RESPONSE_AVERAGE_SIZE_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NULL_FILE_PATH_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NULL_PERCENTILE_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NULL_RESPONSE_AVERAGE_SIZE_EXCEPTION_TEXT;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GeneralInformationTest extends CommonTest {

    @Test
    public void createGeneralInformationWithPaths_ShouldCreateGeneralInformation() {
        createGeneralInformationProcess(getRandomStringsList(), null, null,
            BigDecimal.ZERO.intValue(), BigDecimal.ZERO, BigInteger.ZERO
        );
    }

    @Test
    public void createGeneralInformationWithPathsAndStatisticsAndFrom_ShouldCreateGeneralInformation() {
        createGeneralInformationProcess(getRandomStringsList(), LocalDate.now(), null,
            BigDecimal.ZERO.intValue(), BigDecimal.ZERO, BigInteger.ZERO
        );
    }

    @Test
    public void createGeneralInformationWithPathsAndTo_ShouldCreateGeneralInformation() {
        createGeneralInformationProcess(getRandomStringsList(), null, LocalDate.now(),
            BigDecimal.ZERO.intValue(), BigDecimal.ZERO, BigInteger.ZERO
        );
    }

    @Test
    public void createGeneralInformationWithPathsAndStatisticsAndTimeRange_ShouldCreateGeneralInformation() {
        createGeneralInformationProcess(getRandomStringsList(),
            LocalDate.now().minusDays(DEFAULT_DAYS_COUNT), LocalDate.now(),
            BigDecimal.ZERO.intValue(), BigDecimal.ZERO, BigInteger.ZERO
        );
    }

    @Test
    public void createGeneralInformationWithNullFilePathsList_ShouldThrowNullPointerException() {
        assertThatThrownBy(() -> {
            new GeneralInformation(null, null, null,
                BigDecimal.ZERO.intValue(), BigDecimal.ZERO, BigInteger.ZERO);
        }).isInstanceOf(NullPointerException.class)
            .hasMessageContaining(NULL_FILE_PATH_EXCEPTION_TEXT);
    }

    @Test
    public void createGeneralInformationWithEmptyFilePathsList_ShouldThrowNullPointerException() {
        assertThatThrownBy(() -> {
            new GeneralInformation(new HashSet<>(), null, null,
                BigDecimal.ZERO.intValue(), BigDecimal.ZERO, BigInteger.ZERO);
        }).isInstanceOf(NullPointerException.class)
            .hasMessageContaining(NULL_FILE_PATH_EXCEPTION_TEXT);
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForCreateGeneralInformationWithNullInList")
    public void createGeneralInformationWithNullFilePathInList_ShouldThrowNullPointerException(List<String> filePaths) {
        assertThatThrownBy(() -> {
            new GeneralInformation(new HashSet<>(filePaths), null, null,
                BigDecimal.ZERO.intValue(), BigDecimal.ZERO, BigInteger.ZERO);
        }).isInstanceOf(NullPointerException.class)
            .hasMessageContaining(NULL_FILE_PATH_EXCEPTION_TEXT);
    }

    @Test
    public void createGeneralInformationWithEqualTimeRange_ShouldThrowIllegalArgumentException() {
        assertThatThrownBy(() -> {
            final LocalDate date = LocalDate.now();

            new GeneralInformation(new HashSet<>(getRandomStringsList()), date, date,
                BigDecimal.ZERO.intValue(), BigDecimal.ZERO, BigInteger.ZERO);
        }).isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining(INCORRECT_DATE_RANGE_EXCEPTION_TEXT);
    }

    @Test
    public void createGeneralInformationWithFromMoreThanTo_ShouldThrowIllegalArgumentException() {
        assertThatThrownBy(() -> {
            new GeneralInformation(new HashSet<>(getRandomStringsList()), LocalDate.now().plusDays(DEFAULT_DAYS_COUNT),
                LocalDate.now(), BigDecimal.ZERO.intValue(), BigDecimal.ZERO, BigInteger.ZERO);
        }).isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining(INCORRECT_DATE_RANGE_EXCEPTION_TEXT);
    }

    @ParameterizedTest
    @MethodSource("getNotPositiveNumbers")
    public void createGeneralInformationWithNotPositiveRequestsCount_ShouldThrowIllegalArgumentException(
        int notPositiveRequestsCount
    ) {
        assertThatThrownBy(() -> {
            new GeneralInformation(new HashSet<>(getRandomStringsList()), null, null,
                notPositiveRequestsCount, BigDecimal.ZERO, BigInteger.ZERO);
        }).isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining(NOT_POSITIVE_REQUESTS_EXCEPTION_TEXT);
    }

    @Test
    public void createGeneralInformationWithNullResponseAverageSize_ShouldThrowNullPointerException() {
        assertThatThrownBy(() -> {
            new GeneralInformation(new HashSet<>(getRandomStringsList()), null,
                null, BigInteger.ZERO.intValue(), null, BigInteger.ZERO);
        }).isInstanceOf(NullPointerException.class)
            .hasMessageContaining(NULL_RESPONSE_AVERAGE_SIZE_EXCEPTION_TEXT);
    }

    @ParameterizedTest
    @MethodSource("getNotPositiveBigRealNumbers")
    public void createGeneralInformationWithNotPositiveResponseAverageSize_ShouldThrowIllegalArgumentException(
        BigDecimal notPositiveResponseAverageSize
    ) {
        assertThatThrownBy(() -> {
            new GeneralInformation(new HashSet<>(getRandomStringsList()), null,
                null, BigInteger.ZERO.intValue(), notPositiveResponseAverageSize, BigInteger.ZERO);
        }).isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining(NOT_POSITIVE_RESPONSE_AVERAGE_SIZE_EXCEPTION_TEXT);
    }

    @Test
    public void createGeneralInformationWithNullPercentile_ShouldThrowNullPointerException() {
        assertThatThrownBy(() -> {
            new GeneralInformation(new HashSet<>(getRandomStringsList()), null,
                null, BigInteger.ZERO.intValue(), BigDecimal.ZERO, null);
        }).isInstanceOf(NullPointerException.class)
            .hasMessageContaining(NULL_PERCENTILE_EXCEPTION_TEXT);
    }

    @ParameterizedTest
    @MethodSource("getNotPositiveBigNumbers")
    public void createGeneralInformationWithNotPositivePercentile_ShouldThrowIllegalArgumentException(
        BigInteger notPositivePercentile
    ) {
        assertThatThrownBy(() -> {
            new GeneralInformation(new HashSet<>(getRandomStringsList()), null,
                null, BigInteger.ZERO.intValue(), BigDecimal.ZERO, notPositivePercentile);
        }).isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining(NOT_POSITIVE_PERCENTILE_EXCEPTION_TEXT);
    }

    private void createGeneralInformationProcess(
        List<String> filePaths, LocalDate from, LocalDate to,
        int requestsCount, BigDecimal responseAverageSize, BigInteger percentile
    ) {

        final GeneralInformation generalInformation = new GeneralInformation(
            new HashSet<>(filePaths), from, to, requestsCount, responseAverageSize, percentile
        );

        assertEquals(generalInformation.filePaths(), new HashSet<>(filePaths));
        assertEquals(generalInformation.from(), from);
        assertEquals(generalInformation.to(), to);
        assertEquals(generalInformation.requestsCount(), requestsCount);
        assertEquals(generalInformation.responseAverageSize(), responseAverageSize);
        assertEquals(generalInformation.percentile(), percentile);
    }

    private static List<List<String>> getArgumentsForCreateGeneralInformationWithNullInList() {
        final List<List<String>> result = new ArrayList<>();
        final int count = 100;
        final List<String> randomStringsList = getRandomStringsList(count);

        for (int i = 0; i < count; i++) {
            final List<String> randomList = new ArrayList<>(randomStringsList);
            randomList.set(i, null);
            result.add(randomList);
        }

        return result;
    }
}
