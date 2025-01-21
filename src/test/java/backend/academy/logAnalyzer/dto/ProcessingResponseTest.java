package backend.academy.logAnalyzer.dto;

import backend.academy.logAnalyzer.CommonTest;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Set;
import org.junit.jupiter.api.Test;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NULL_GENERAL_INFORMATION_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NULL_REQUEST_STATISTICS_EXCEPTION_TEXT;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProcessingResponseTest extends CommonTest {

    private final RequestStatistics defaultRequestStatistics
        = new RequestStatistics(new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>());
    private final GeneralInformation defaultGeneralInformation = new GeneralInformation(
            Set.of(getRandomString()), null, null, BigInteger.ZERO.intValue(), BigDecimal.ZERO, BigInteger.ZERO
    );

    @Test
    public void createProcessingResponse_ShouldCreateProcessingResponse() {
        final ProcessingResponse
            processingResponse = new ProcessingResponse(defaultGeneralInformation, defaultRequestStatistics);

        assertEquals(processingResponse.generalInformation(), defaultGeneralInformation);
        assertEquals(processingResponse.requestStatistics(), defaultRequestStatistics);
    }

    @Test
    public void createProcessingResponseWithNullGeneralInformation_ShouldThrowNullPointerException() {
        assertThatThrownBy(() -> {
            new ProcessingResponse(null, defaultRequestStatistics);
        }).isInstanceOf(NullPointerException.class)
            .hasMessageContaining(NULL_GENERAL_INFORMATION_EXCEPTION_TEXT);
    }

    @Test
    public void createProcessingResponseWithNullRequestStatistics_ShouldThrowNullPointerException() {
        assertThatThrownBy(() -> {
            new ProcessingResponse(defaultGeneralInformation, null);
        }).isInstanceOf(NullPointerException.class)
            .hasMessageContaining(NULL_REQUEST_STATISTICS_EXCEPTION_TEXT);
    }
}
