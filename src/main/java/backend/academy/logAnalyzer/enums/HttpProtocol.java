package backend.academy.logAnalyzer.enums;

import backend.academy.logAnalyzer.exception.HttpProtocolNotSupportedException;
import java.util.Arrays;
import lombok.Getter;

@Getter
public enum HttpProtocol {

    LEGACY_HTTP("http/1.1"),
    MODERN_HTTP("http/2"),
    FAST_HTTP("http/3");

    private final String value;

    HttpProtocol(String value) {
        this.value = value;
    }

    public static HttpProtocol getHttpProtocolByValue(String value) {
        return Arrays.stream(HttpProtocol.values())
            .filter(httpProtocol -> httpProtocol.value.equalsIgnoreCase(value))
            .findFirst()
            .orElseThrow(HttpProtocolNotSupportedException::new);
    }
}
