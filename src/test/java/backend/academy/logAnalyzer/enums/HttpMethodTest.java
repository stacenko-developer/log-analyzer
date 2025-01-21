package backend.academy.logAnalyzer.enums;

import backend.academy.logAnalyzer.CommonTest;
import backend.academy.logAnalyzer.exception.HttpMethodNotSupportedException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.HTTP_METHOD_NOT_SUPPORTED_EXCEPTION_TEXT;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpMethodTest extends CommonTest {

    @ParameterizedTest
    @EnumSource(HttpMethod.class)
    public void getHttpMethodByValue_ShouldReturnHttpMethod(HttpMethod httpMethod) {
        assertEquals(httpMethod, HttpMethod.getHttpMethodByValue(httpMethod.value()));
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForGetEnumByIncorrectValue")
    public void getHttpMethodByIncorrectValue_ShouldThrowHttpMethodNotSupportedException(String incorrectValue) {
        assertThatThrownBy(() -> {
            HttpMethod.getHttpMethodByValue(incorrectValue);
        }).isInstanceOf(HttpMethodNotSupportedException.class)
            .hasMessageContaining(HTTP_METHOD_NOT_SUPPORTED_EXCEPTION_TEXT);
    }
}
