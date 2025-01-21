package backend.academy.logAnalyzer.enums;

import backend.academy.logAnalyzer.CommonTest;
import backend.academy.logAnalyzer.exception.HttpProtocolNotSupportedException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.HTTP_PROTOCOL_NOT_SUPPORTED_EXCEPTION_TEXT;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpProtocolTest extends CommonTest {

    @ParameterizedTest
    @EnumSource(HttpProtocol.class)
    public void getHttpProtocolByValue_ShouldReturnHttpProtocol(HttpProtocol httpProtocol) {
        assertEquals(httpProtocol, HttpProtocol.getHttpProtocolByValue(httpProtocol.value()));
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForGetEnumByIncorrectValue")
    public void getHttpProtocolByIncorrectValue_ShouldThrowHttpProtocolNotSupportedException(String incorrectValue) {
        assertThatThrownBy(() -> {
            HttpProtocol.getHttpProtocolByValue(incorrectValue);
        }).isInstanceOf(HttpProtocolNotSupportedException.class)
            .hasMessageContaining(HTTP_PROTOCOL_NOT_SUPPORTED_EXCEPTION_TEXT);
    }
}
