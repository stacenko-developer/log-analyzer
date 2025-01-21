package backend.academy.logAnalyzer.dto;

import backend.academy.logAnalyzer.CommonTest;
import backend.academy.logAnalyzer.enums.HttpMethod;
import backend.academy.logAnalyzer.enums.HttpProtocol;
import backend.academy.logAnalyzer.enums.HttpStatus;
import java.math.BigInteger;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NOT_POSITIVE_BODY_BYTES_SEND_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NULL_ADDRESSES_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NULL_HTTP_REFERER_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NULL_HTTP_REQUEST_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NULL_HTTP_STATUS_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NULL_REMOTE_USER_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NULL_SEND_BYTES_COUNT_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NULL_TIME_LOCAL_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NULL_USER_AGENT_EXCEPTION_TEXT;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LogTest extends CommonTest {

    private static final Request DEFAULT_REQUEST = new Request(
        HttpMethod.GET, DEFAULT_VALUE, HttpProtocol.FAST_HTTP
    );

    @ParameterizedTest
    @MethodSource("getArgumentsForCreateLog")
    public void createLog_ShouldCreateLog(String remoteAddress, String remoteUser, ZonedDateTime timeLocal,
        Request request, HttpStatus httpStatus, BigInteger bodyBytesSend, String httpReferer, String httpUserAgent) {

        final Log log = new Log(remoteAddress, remoteUser, timeLocal,
            request, httpStatus, bodyBytesSend, httpReferer, httpUserAgent);

        assertEquals(log.remoteAddress(), remoteAddress);
        assertEquals(log.remoteUser(), remoteUser);
        assertEquals(log.timeLocal(), timeLocal);

        assertEquals(log.request(), request);
        assertEquals(log.httpStatus(), httpStatus);
        assertEquals(log.bodyBytesSend(), bodyBytesSend);

        assertEquals(log.httpReferer(), httpReferer);
        assertEquals(log.httpUserAgent(), httpUserAgent);
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForTestStringForNull")
    public void createLogWithNullRemoteAddress_ShouldThrowNullPointerException(String incorrectValue) {
        assertThatThrownBy(() -> {
            new Log(
                incorrectValue, DEFAULT_VALUE, ZonedDateTime.now(),
                DEFAULT_REQUEST, HttpStatus.OK, new BigInteger(String.valueOf(getRandomNumber())),
                DEFAULT_VALUE, DEFAULT_VALUE
            );
        }).isInstanceOf(NullPointerException.class)
            .hasMessageContaining(NULL_ADDRESSES_EXCEPTION_TEXT);
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForTestStringForNull")
    public void createLogWithNullRemoteUser_ShouldThrowNullPointerException(String incorrectValue) {
        assertThatThrownBy(() -> {
            new Log(
                DEFAULT_VALUE, incorrectValue, ZonedDateTime.now(),
                DEFAULT_REQUEST, HttpStatus.OK, new BigInteger(String.valueOf(getRandomNumber())),
                DEFAULT_VALUE, DEFAULT_VALUE
            );
        }).isInstanceOf(NullPointerException.class)
            .hasMessageContaining(NULL_REMOTE_USER_EXCEPTION_TEXT);
    }

    @Test
    public void createLogWithNullTimeLocal_ShouldThrowNullPointerException() {
        assertThatThrownBy(() -> {
            new Log(
                DEFAULT_VALUE, DEFAULT_VALUE, null,
                DEFAULT_REQUEST, HttpStatus.OK, new BigInteger(String.valueOf(getRandomNumber())),
                DEFAULT_VALUE, DEFAULT_VALUE
            );
        }).isInstanceOf(NullPointerException.class)
            .hasMessageContaining(NULL_TIME_LOCAL_EXCEPTION_TEXT);
    }

    @Test
    public void createLogWithNullHttpRequest_ShouldThrowNullPointerException() {
        assertThatThrownBy(() -> {
            new Log(
                DEFAULT_VALUE, DEFAULT_VALUE, ZonedDateTime.now(),
                null, HttpStatus.OK, new BigInteger(String.valueOf(getRandomNumber())),
                DEFAULT_VALUE, DEFAULT_VALUE
            );
        }).isInstanceOf(NullPointerException.class)
            .hasMessageContaining(NULL_HTTP_REQUEST_EXCEPTION_TEXT);
    }

    @Test
    public void createLogWithNullHttpStatus_ShouldThrowNullPointerException() {
        assertThatThrownBy(() -> {
            new Log(
                DEFAULT_VALUE, DEFAULT_VALUE, ZonedDateTime.now(),
                DEFAULT_REQUEST, null, new BigInteger(String.valueOf(getRandomNumber())),
                DEFAULT_VALUE, DEFAULT_VALUE
            );
        }).isInstanceOf(NullPointerException.class)
            .hasMessageContaining(NULL_HTTP_STATUS_EXCEPTION_TEXT);
    }

    @Test
    public void createLogWithNullBodyBytesSend_ShouldThrowNullPointerException() {
        assertThatThrownBy(() -> {
            new Log(
                DEFAULT_VALUE, DEFAULT_VALUE, ZonedDateTime.now(),
                DEFAULT_REQUEST, HttpStatus.OK, null,
                DEFAULT_VALUE, DEFAULT_VALUE
            );
        }).isInstanceOf(NullPointerException.class)
            .hasMessageContaining(NULL_SEND_BYTES_COUNT_EXCEPTION_TEXT);
    }

    @ParameterizedTest
    @MethodSource("getNotPositiveBigNumbers")
    public void createLogWithNotPositiveBodyBytesSend_ShouldThrowIllegalArgumentException(BigInteger notPositiveValue) {
        assertThatThrownBy(() -> {
            new Log(
                DEFAULT_VALUE, DEFAULT_VALUE, ZonedDateTime.now(),
                DEFAULT_REQUEST, HttpStatus.OK, notPositiveValue,
                DEFAULT_VALUE, DEFAULT_VALUE
            );
        }).isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining(NOT_POSITIVE_BODY_BYTES_SEND_EXCEPTION_TEXT);
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForTestStringForNull")
    public void createLogWithNullHttpReferer_ShouldThrowNullPointerException(String incorrectValue) {
        assertThatThrownBy(() -> {
            new Log(
                DEFAULT_VALUE, DEFAULT_VALUE, ZonedDateTime.now(),
                DEFAULT_REQUEST, HttpStatus.OK, new BigInteger(String.valueOf(getRandomNumber())),
                incorrectValue, DEFAULT_VALUE
            );
        }).isInstanceOf(NullPointerException.class)
            .hasMessageContaining(NULL_HTTP_REFERER_EXCEPTION_TEXT);
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForTestStringForNull")
    public void createLogWithNullHttpUserAgent_ShouldThrowNullPointerException(String incorrectValue) {
        assertThatThrownBy(() -> {
            new Log(
                DEFAULT_VALUE, DEFAULT_VALUE, ZonedDateTime.now(),
                DEFAULT_REQUEST, HttpStatus.OK, new BigInteger(String.valueOf(getRandomNumber())),
                DEFAULT_VALUE, incorrectValue
            );
        }).isInstanceOf(NullPointerException.class)
            .hasMessageContaining(NULL_USER_AGENT_EXCEPTION_TEXT);
    }

    public static List<Object[]> getArgumentsForCreateLog() {
        final List<Object[]> result = new ArrayList<>();
        final List<Object[]> requests = getArgumentsForCreateRequest();
        final HttpStatus[] httpStatuses = HttpStatus.values();

        for (HttpStatus httpStatus : httpStatuses) {
            for (Object[] request : requests) {
                result.add(new Object[]{
                    getRandomString(), getRandomString(), ZonedDateTime.now(),
                    new Request((HttpMethod) request[0], (String) request[1], (HttpProtocol) request[2]),
                    httpStatus, new BigInteger(String.valueOf(getRandomNumber())), getRandomString(), getRandomString()
                });
            }
        }

        result.add(new Object[]{
            DEFAULT_VALUE, DEFAULT_VALUE, ZonedDateTime.now(),
            DEFAULT_REQUEST,
            HttpStatus.OK, new BigInteger(String.valueOf(getRandomNumber())), DEFAULT_VALUE, DEFAULT_VALUE
        });

        return result;
    }
}
