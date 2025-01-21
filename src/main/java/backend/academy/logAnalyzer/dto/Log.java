package backend.academy.logAnalyzer.dto;

import backend.academy.logAnalyzer.enums.HttpStatus;
import java.math.BigInteger;
import java.time.ZonedDateTime;
import org.apache.commons.lang3.StringUtils;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NOT_POSITIVE_BODY_BYTES_SEND_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NULL_ADDRESSES_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NULL_HTTP_REFERER_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NULL_HTTP_REQUEST_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NULL_HTTP_STATUS_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NULL_REMOTE_USER_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NULL_SEND_BYTES_COUNT_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NULL_TIME_LOCAL_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NULL_USER_AGENT_EXCEPTION_TEXT;

public record Log(String remoteAddress, String remoteUser, ZonedDateTime timeLocal,
                  Request request, HttpStatus httpStatus, BigInteger bodyBytesSend,
                  String httpReferer, String httpUserAgent) {
    public Log {
        validateRemoteAddress(remoteAddress);
        validateRemoteUser(remoteUser);
        validateTimeLocal(timeLocal);

        validateHttpRequest(request);
        validateHttpStatus(httpStatus);
        validateBodyBytesSend(bodyBytesSend);

        validateHttpReferer(httpReferer);
        validateHttpUserAgent(httpUserAgent);
    }

    private void validateRemoteAddress(String remoteAddress) {
        if (StringUtils.isBlank(remoteAddress)) {
            throw new NullPointerException(NULL_ADDRESSES_EXCEPTION_TEXT);
        }
    }

    private void validateRemoteUser(String remoteUser) {
        if (StringUtils.isBlank(remoteUser)) {
            throw new NullPointerException(NULL_REMOTE_USER_EXCEPTION_TEXT);
        }
    }

    private void validateTimeLocal(ZonedDateTime timeLocal) {
        if (timeLocal == null) {
            throw new NullPointerException(NULL_TIME_LOCAL_EXCEPTION_TEXT);
        }
    }

    private void validateHttpRequest(Request request) {
        if (request == null) {
            throw new NullPointerException(NULL_HTTP_REQUEST_EXCEPTION_TEXT);
        }
    }

    private void validateHttpStatus(HttpStatus httpStatus) {
        if (httpStatus == null) {
            throw new NullPointerException(NULL_HTTP_STATUS_EXCEPTION_TEXT);
        }
    }

    private void validateBodyBytesSend(BigInteger bodyBytesSend) {
        if (bodyBytesSend == null) {
            throw new NullPointerException(NULL_SEND_BYTES_COUNT_EXCEPTION_TEXT);
        }

        if (bodyBytesSend.compareTo(BigInteger.ZERO) < 0) {
            throw new IllegalArgumentException(NOT_POSITIVE_BODY_BYTES_SEND_EXCEPTION_TEXT);
        }
    }

    private void validateHttpReferer(String httpReferer) {
        if (StringUtils.isBlank(httpReferer)) {
            throw new NullPointerException(NULL_HTTP_REFERER_EXCEPTION_TEXT);
        }
    }

    private void validateHttpUserAgent(String httpUserAgent) {
        if (StringUtils.isBlank(httpUserAgent)) {
            throw new NullPointerException(NULL_USER_AGENT_EXCEPTION_TEXT);
        }
    }
}
