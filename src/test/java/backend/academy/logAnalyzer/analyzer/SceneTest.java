package backend.academy.logAnalyzer.analyzer;

import backend.academy.logAnalyzer.CommonTest;
import backend.academy.logAnalyzer.dto.GeneralInformation;
import backend.academy.logAnalyzer.enums.HttpMethod;
import backend.academy.logAnalyzer.enums.HttpStatus;
import backend.academy.logAnalyzer.enums.OutputFormat;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import static backend.academy.logAnalyzer.constants.ConstValues.ADDRESSES_TEXT;
import static backend.academy.logAnalyzer.constants.ConstValues.DEFAULT_FORMAT;
import static backend.academy.logAnalyzer.constants.ConstValues.END_DATE_OUTPUT_TEXT;
import static backend.academy.logAnalyzer.constants.ConstValues.FILES_OUTPUT_TEXT;
import static backend.academy.logAnalyzer.constants.ConstValues.GENERAL_INFORMATION_TEXT;
import static backend.academy.logAnalyzer.constants.ConstValues.HTTP_METHODS_TEXT;
import static backend.academy.logAnalyzer.constants.ConstValues.NO_VALUE_IN_STATISTICS;
import static backend.academy.logAnalyzer.constants.ConstValues.PERCENTILE_OUTPUT_TEXT;
import static backend.academy.logAnalyzer.constants.ConstValues.REQUESTED_RESOURCES_TEXT;
import static backend.academy.logAnalyzer.constants.ConstValues.REQUESTS_COUNT_OUTPUT_TEXT;
import static backend.academy.logAnalyzer.constants.ConstValues.RESPONSE_AVERAGE_SIZE_OUTPUT_TEXT;
import static backend.academy.logAnalyzer.constants.ConstValues.RESPONSE_CODES_TEXT;
import static backend.academy.logAnalyzer.constants.ConstValues.START_DATE_OUTPUT_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NULL_ELEMENTS_STATISTICS_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NULL_GENERAL_INFORMATION_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NULL_STATISTICS_EXCEPTION_TEXT;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SceneTest extends CommonTest {

    private final GeneralInformation DEFAULT_GENERAL_INFORMATION = new GeneralInformation(
        new HashSet<>(getRandomStringsList()),
        null,
        null,
        getRandomNumber(),
        BigDecimal.valueOf(getRandomNumber()),
        BigInteger.valueOf(getRandomNumber())
    );

    @Test
    public void renderGeneralInformationReportWithNullGeneralInformation_ShouldThrowNullPointerException() {
        assertThatThrownBy(() -> {
            Scene.renderGeneralInformation(null, null);
        }).isInstanceOf(NullPointerException.class)
            .hasMessageContaining(NULL_GENERAL_INFORMATION_EXCEPTION_TEXT);
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForRenderGeneralInformation")
    public void renderGeneralInformation_ShouldRenderGeneralInformation(
        GeneralInformation generalInformation, OutputFormat outputFormat
    ) {
        final String[] result = Scene.renderGeneralInformation(generalInformation, outputFormat).split("\n");
        final String header = result[0];
        final String files = result[3];
        final String startDate = result[4];
        final String endDate = result[5];
        final String requestsCount = result[6];
        final String averageResponseSize = result[7];
        final String percentile = result[8];

        assertThat(header)
            .contains(String.valueOf(outputFormat.headerSymbol()), GENERAL_INFORMATION_TEXT);

        assertThat(files)
            .contains(FILES_OUTPUT_TEXT, generalInformation.filePaths().toString());

        assertThat(startDate)
            .contains(START_DATE_OUTPUT_TEXT, generalInformation.from().toString());

        assertThat(endDate)
            .contains(END_DATE_OUTPUT_TEXT, generalInformation.to().toString());

        assertThat(requestsCount)
            .contains(REQUESTS_COUNT_OUTPUT_TEXT, String.valueOf(generalInformation.requestsCount()));

        assertThat(averageResponseSize)
            .contains(RESPONSE_AVERAGE_SIZE_OUTPUT_TEXT, String.valueOf(generalInformation.responseAverageSize()));

        assertThat(percentile)
            .contains(PERCENTILE_OUTPUT_TEXT, String.valueOf(generalInformation.percentile()));
    }

    @Test
    public void renderGeneralInformationWithNoDates_ShouldRenderGeneralInformationWithNoDates() {
        final String[] result = Scene
            .renderGeneralInformation(DEFAULT_GENERAL_INFORMATION, null).split("\n");
        final String startDate = result[4];
        final String endDate = result[5];

        assertThat(startDate)
            .contains(START_DATE_OUTPUT_TEXT, NO_VALUE_IN_STATISTICS);

        assertThat(endDate)
            .contains(END_DATE_OUTPUT_TEXT, NO_VALUE_IN_STATISTICS);
    }

    @Test
    public void renderGeneralInformationWithNullFormat_ShouldRenderByDefaultFormat() {
        final String[] result = Scene
            .renderGeneralInformation(DEFAULT_GENERAL_INFORMATION, null).split("\n");
        final String header = result[0];

        assertThat(header)
            .contains(String.valueOf(DEFAULT_FORMAT.headerSymbol()), GENERAL_INFORMATION_TEXT);
    }

    @Test
    public void renderResourceInformationWithNullMap_ShouldThrowNullPointerException() {
        assertThatThrownBy(() -> {
            Scene.renderResourcesInformation(null, null);
        }).isInstanceOf(NullPointerException.class)
            .hasMessageContaining(NULL_STATISTICS_EXCEPTION_TEXT);
    }

    @Test
    public void renderResourceInformationWithNullKeyInMap_ShouldThrowNullPointerException() {
        final Map<String, Integer> incorrectStatistics = new HashMap<>();
        incorrectStatistics.put(null, getRandomNumber());

        assertThatThrownBy(() -> {
            Scene.renderResourcesInformation(incorrectStatistics, null);
        }).isInstanceOf(NullPointerException.class)
            .hasMessageContaining(NULL_ELEMENTS_STATISTICS_EXCEPTION_TEXT);
    }

    @Test
    public void renderResourceInformationWithNullValueInMap_ShouldThrowNullPointerException() {
        final Map<String, Integer> incorrectStatistics = new HashMap<>();
        incorrectStatistics.put(getRandomString(), null);

        assertThatThrownBy(() -> {
            Scene.renderResourcesInformation(incorrectStatistics, null);
        }).isInstanceOf(NullPointerException.class)
            .hasMessageContaining(NULL_ELEMENTS_STATISTICS_EXCEPTION_TEXT);
    }

    @ParameterizedTest
    @EnumSource(OutputFormat.class)
    public void renderResourcesInformation_ShouldRenderResourcesInformation(OutputFormat outputFormat) {
        final Map<String, Integer> resources = getRandomStatistics();
        final String[] result = Scene
            .renderResourcesInformation(resources, outputFormat).split("\n");

        checkStatistics(resources, result, outputFormat.headerSymbol(), REQUESTED_RESOURCES_TEXT);
    }

    @Test
    public void renderResourcesInformationWithNullFormat_ShouldRenderResourcesInformationWithDefaultFormat() {
        final Map<String, Integer> resources = getRandomStatistics();
        final String[] result = Scene
            .renderResourcesInformation(resources, null).split("\n");

        checkStatistics(resources, result, DEFAULT_FORMAT.headerSymbol(), REQUESTED_RESOURCES_TEXT);
    }

    @Test
    public void renderResponseCodesInformationWithNullMap_ShouldThrowNullPointerException() {
        assertThatThrownBy(() -> {
            Scene.renderResponseCodesInformation(null, null);
        }).isInstanceOf(NullPointerException.class)
            .hasMessageContaining(NULL_STATISTICS_EXCEPTION_TEXT);
    }

    @Test
    public void renderResponseCodesInformationWithNullKeyInMap_ShouldThrowNullPointerException() {
        final Map<HttpStatus, Integer> incorrectStatistics = new HashMap<>();
        incorrectStatistics.put(null, getRandomNumber());

        assertThatThrownBy(() -> {
            Scene.renderResponseCodesInformation(incorrectStatistics, null);
        }).isInstanceOf(NullPointerException.class)
            .hasMessageContaining(NULL_ELEMENTS_STATISTICS_EXCEPTION_TEXT);
    }

    @Test
    public void renderResponseCodesInformationWithNullValueInMap_ShouldThrowNullPointerException() {
        final Map<HttpStatus, Integer> incorrectStatistics = new HashMap<>();
        incorrectStatistics.put(HttpStatus.OK, null);

        assertThatThrownBy(() -> {
            Scene.renderResponseCodesInformation(incorrectStatistics, null);
        }).isInstanceOf(NullPointerException.class)
            .hasMessageContaining(NULL_ELEMENTS_STATISTICS_EXCEPTION_TEXT);
    }

    @ParameterizedTest
    @EnumSource(OutputFormat.class)
    public void renderResponseCodesInformation_ShouldRenderResponseCodesInformation(OutputFormat outputFormat) {
        final Map<HttpStatus, Integer> httpStatuses = getRandomHttpStatusesStatistics();
        final String[] result = Scene
            .renderResponseCodesInformation(httpStatuses, outputFormat).split("\n");

        checkStatistics(httpStatuses, result, outputFormat.headerSymbol(), RESPONSE_CODES_TEXT);
    }

    @Test
    public void renderResponseCodesInformationWithNullFormat_ShouldRenderResponseCodesInformationWithDefaultFormat() {
        final Map<HttpStatus, Integer> httpStatuses = getRandomHttpStatusesStatistics();
        final String[] result = Scene
            .renderResponseCodesInformation(httpStatuses, null).split("\n");

        checkStatistics(httpStatuses, result, DEFAULT_FORMAT.headerSymbol(), RESPONSE_CODES_TEXT);
    }

    @Test
    public void renderAddressesInformationWithNullMap_ShouldThrowNullPointerException() {
        assertThatThrownBy(() -> {
            Scene.renderAddressesInformation(null, null);
        }).isInstanceOf(NullPointerException.class)
            .hasMessageContaining(NULL_STATISTICS_EXCEPTION_TEXT);
    }

    @Test
    public void renderAddressesInformationWithNullKeyInMap_ShouldThrowNullPointerException() {
        final Map<String, Integer> incorrectStatistics = new HashMap<>();
        incorrectStatistics.put(null, getRandomNumber());

        assertThatThrownBy(() -> {
            Scene.renderAddressesInformation(incorrectStatistics, null);
        }).isInstanceOf(NullPointerException.class)
            .hasMessageContaining(NULL_ELEMENTS_STATISTICS_EXCEPTION_TEXT);
    }

    @Test
    public void renderAddressesInformationWithNullValueInMap_ShouldThrowNullPointerException() {
        final Map<String, Integer> incorrectStatistics = new HashMap<>();
        incorrectStatistics.put(getRandomString(), null);

        assertThatThrownBy(() -> {
            Scene.renderAddressesInformation(incorrectStatistics, null);
        }).isInstanceOf(NullPointerException.class)
            .hasMessageContaining(NULL_ELEMENTS_STATISTICS_EXCEPTION_TEXT);
    }

    @ParameterizedTest
    @EnumSource(OutputFormat.class)
    public void renderAddressesInformation_ShouldRenderAddressesInformation(OutputFormat outputFormat) {
        final Map<String, Integer> addresses = getRandomStatistics();
        final String[] result = Scene
            .renderAddressesInformation(addresses, outputFormat).split("\n");

        checkStatistics(addresses, result, outputFormat.headerSymbol(), ADDRESSES_TEXT);
    }

    @Test
    public void renderAddressesInformationWithNullFormat_ShouldRenderAddressesInformationWithDefaultFormat() {
        final Map<String, Integer> addresses = getRandomStatistics();
        final String[] result = Scene
            .renderAddressesInformation(addresses, null).split("\n");

        checkStatistics(addresses, result, DEFAULT_FORMAT.headerSymbol(), ADDRESSES_TEXT);
    }

    @Test
    public void renderMethodsInformationWithNullMap_ShouldThrowNullPointerException() {
        assertThatThrownBy(() -> {
            Scene.renderMethodsInformation(null, null);
        }).isInstanceOf(NullPointerException.class)
            .hasMessageContaining(NULL_STATISTICS_EXCEPTION_TEXT);
    }

    @Test
    public void renderMethodsInformationWithNullKeyInMap_ShouldThrowNullPointerException() {
        final Map<HttpMethod, Integer> incorrectStatistics = new HashMap<>();
        incorrectStatistics.put(null, getRandomNumber());

        assertThatThrownBy(() -> {
            Scene.renderMethodsInformation(incorrectStatistics, null);
        }).isInstanceOf(NullPointerException.class)
            .hasMessageContaining(NULL_ELEMENTS_STATISTICS_EXCEPTION_TEXT);
    }

    @Test
    public void renderMethodsInformationWithNullValueInMap_ShouldThrowNullPointerException() {
        final Map<HttpMethod, Integer> incorrectStatistics = new HashMap<>();
        incorrectStatistics.put(HttpMethod.GET, null);

        assertThatThrownBy(() -> {
            Scene.renderMethodsInformation(incorrectStatistics, null);
        }).isInstanceOf(NullPointerException.class)
            .hasMessageContaining(NULL_ELEMENTS_STATISTICS_EXCEPTION_TEXT);
    }

    @ParameterizedTest
    @EnumSource(OutputFormat.class)
    public void renderMethodsInformation_ShouldRenderMethodsInformation(OutputFormat outputFormat) {
        final Map<HttpMethod, Integer> methods = getRandomHttpMethodsStatistics();
        final String[] result = Scene
            .renderMethodsInformation(methods, outputFormat).split("\n");

        checkStatistics(methods, result, outputFormat.headerSymbol(), HTTP_METHODS_TEXT);
    }

    @Test
    public void renderMethodsInformationWithNullFormat_ShouldRenderMethodsInformationWithDefaultFormat() {
        final Map<HttpMethod, Integer> methods = getRandomHttpMethodsStatistics();
        final String[] result = Scene
            .renderMethodsInformation(methods, null).split("\n");

        checkStatistics(methods, result, DEFAULT_FORMAT.headerSymbol(), HTTP_METHODS_TEXT);
    }

    private static void checkStatistics(Map<?, ?> statistics, String[] result, char headerSymbol, String correctHeaderText) {
        final String header = result[0];
        int statisticsIndex = 3;

        assertThat(header)
            .contains(String.valueOf(headerSymbol), correctHeaderText);

        for (Map.Entry<?, ?> entry : statistics.entrySet()) {
            final String entryStr = result[statisticsIndex];

            assertThat(entryStr)
                .contains(entry.getKey().toString(), String.valueOf(entry.getValue()));

            statisticsIndex++;
        }
    }

    private Map<String, Integer> getRandomStatistics() {
        final Map<String, Integer> statistics = new LinkedHashMap<>();
        final int elementsCount = 5;

        for (int index = 0; index < elementsCount; index++) {
            statistics.put(getRandomString(), getRandomNumber());
        }

        return statistics;
    }


    private static List<Object> getArgumentsForRenderGeneralInformation() {
        final List<Object> result = new ArrayList<>();
        final int count = 20;
        final int daysCount = 1;

        for (OutputFormat outputFormat : OutputFormat.values()) {
            for (int i = 0; i < count; i++) {
                result.add(new Object[] {
                    new GeneralInformation(
                        new HashSet<>(getRandomStringsList()),
                        LocalDate.now(),
                        LocalDate.now().plusDays(daysCount),
                        i,
                        BigDecimal.valueOf(getRandomNumber()),
                        BigInteger.valueOf(getRandomNumber())
                    ),
                    outputFormat
                });
            }
        }

        return result;
    }
}
