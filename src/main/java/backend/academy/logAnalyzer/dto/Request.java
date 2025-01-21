package backend.academy.logAnalyzer.dto;

import backend.academy.logAnalyzer.enums.HttpMethod;
import backend.academy.logAnalyzer.enums.HttpProtocol;
import org.apache.commons.lang3.StringUtils;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NULL_HTTP_METHODS_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NULL_HTTP_PROTOCOL_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NULL_RESOURCES_EXCEPTION_TEXT;

public record Request(HttpMethod httpMethod, String resource, HttpProtocol httpProtocol) {

    public Request {
        if (httpMethod == null) {
            throw new NullPointerException(NULL_HTTP_METHODS_EXCEPTION_TEXT);
        }

        if (StringUtils.isBlank(resource)) {
            throw new NullPointerException(NULL_RESOURCES_EXCEPTION_TEXT);
        }

        if (httpProtocol == null) {
            throw new NullPointerException(NULL_HTTP_PROTOCOL_EXCEPTION_TEXT);
        }
    }
}
