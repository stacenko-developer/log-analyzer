package backend.academy.logAnalyzer.enums;

import backend.academy.logAnalyzer.exception.HttpMethodNotSupportedException;
import java.util.Arrays;
import lombok.Getter;

@Getter
public enum HttpMethod {
    GET("get"),
    POST("post"),
    PUT("put"),
    DELETE("delete"),
    PATCH("patch"),
    HEAD("head"),
    CONNECT("connect"),
    OPTIONS("options"),
    TRACE("trace");

    private final String value;

    HttpMethod(String value) {
        this.value = value;
    }

    public static HttpMethod getHttpMethodByValue(String value) {
        return Arrays.stream(HttpMethod.values())
            .filter(httpMethod -> httpMethod.value.equalsIgnoreCase(value))
            .findFirst()
            .orElseThrow(HttpMethodNotSupportedException::new);
    }
}
