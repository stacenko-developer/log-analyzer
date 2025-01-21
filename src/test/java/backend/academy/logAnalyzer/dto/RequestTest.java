package backend.academy.logAnalyzer.dto;

import backend.academy.logAnalyzer.CommonTest;
import backend.academy.logAnalyzer.enums.HttpMethod;
import backend.academy.logAnalyzer.enums.HttpProtocol;
import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NULL_HTTP_METHODS_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NULL_HTTP_PROTOCOL_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NULL_RESOURCES_EXCEPTION_TEXT;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RequestTest extends CommonTest {

    @ParameterizedTest
    @MethodSource("getArgumentsForCreateRequest")
    public void createRequest_ShouldCreateRequest(HttpMethod httpMethod, String resource, HttpProtocol httpProtocol) {
        final Request request = new Request(httpMethod, resource, httpProtocol);

        assertEquals(request.httpMethod(), httpMethod);
        assertEquals(request.resource(), resource);
        assertEquals(request.httpProtocol(), httpProtocol);
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForCreateRequestWithNullHttpMethod")
    public void createRequestWithNullHttpMethod_ShouldThrowNullPointerException(String resource, HttpProtocol httpProtocol) {
        assertThatThrownBy(() -> {
            new Request(null, resource, httpProtocol);
        }).isInstanceOf(NullPointerException.class)
            .hasMessageContaining(NULL_HTTP_METHODS_EXCEPTION_TEXT);
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForCreateRequestWithNullResource")
    public void createRequestWithNullResource_ShouldThrowNullPointerException(HttpMethod httpMethod, String resource, HttpProtocol httpProtocol) {
        assertThatThrownBy(() -> {
            new Request(httpMethod, resource, httpProtocol);
        }).isInstanceOf(NullPointerException.class)
            .hasMessageContaining(NULL_RESOURCES_EXCEPTION_TEXT);
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForCreateRequestWithNullHttpHttpProtocol")
    public void createRequestWithNullHttpProtocol_ShouldThrowNullPointerException(HttpMethod httpMethod, String resource) {
        assertThatThrownBy(() -> {
            new Request(httpMethod, resource, null);
        }).isInstanceOf(NullPointerException.class)
            .hasMessageContaining(NULL_HTTP_PROTOCOL_EXCEPTION_TEXT);
    }

    private static List<Object[]> getArgumentsForCreateRequestWithNullHttpMethod() {
        return getArgumentsForCreateRequest()
            .stream()
            .map(obj -> new Object[]{obj[1], obj[2]})
            .toList();
    }

    private static List<Object[]> getArgumentsForCreateRequestWithNullHttpHttpProtocol() {
        return getArgumentsForCreateRequest()
            .stream()
            .map(obj -> new Object[]{obj[0], obj[1]})
            .toList();
    }

    private static List<Object[]> getArgumentsForCreateRequestWithNullResource() {
        return getArgumentsForCreateRequest()
            .stream()
            .peek(obj -> {
                String[] arg = getArgumentsForTestStringForNull();
                obj[1] = arg[getRandomNumber(0, arg.length - 1)];
                }
            )
            .toList();
    }
}
