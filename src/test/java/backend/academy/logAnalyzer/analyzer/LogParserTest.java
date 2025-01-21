package backend.academy.logAnalyzer.analyzer;

import backend.academy.logAnalyzer.CommonTest;
import backend.academy.logAnalyzer.dto.Log;
import backend.academy.logAnalyzer.dto.Request;
import backend.academy.logAnalyzer.enums.HttpMethod;
import backend.academy.logAnalyzer.enums.HttpProtocol;
import backend.academy.logAnalyzer.enums.HttpStatus;
import backend.academy.logAnalyzer.exception.HttpStatusNotSupportedException;
import backend.academy.logAnalyzer.exception.IncorrectLogFormatException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.math.BigInteger;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import static backend.academy.logAnalyzer.constants.ConstValues.TIME_LOCAL_FORMAT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.HTTP_STATUS_NOT_SUPPORTED_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.INCORRECT_LOG_FORMAT_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NULL_LOG_DATA_EXCEPTION_TEXT;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LogParserTest extends CommonTest {

    @ParameterizedTest
    @MethodSource("getArgumentsForTestStringForNull")
    public void parseLogWithNullLogData_ShouldThrowNullPointerException(String nullLogData) {
        assertThatThrownBy(() -> {
            LogParser.parse(nullLogData);
        }).isInstanceOf(NullPointerException.class)
            .hasMessageContaining(NULL_LOG_DATA_EXCEPTION_TEXT);
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForParseLogWithIncorrectFormat")
    public void parseLogWithIncorrectFormat_ShouldThrowIncorrectLogFormatException(String incorrectFormat) {
        assertThatThrownBy(() -> {
            LogParser.parse(incorrectFormat);
        }).isInstanceOf(IncorrectLogFormatException.class)
            .hasMessageContaining(INCORRECT_LOG_FORMAT_EXCEPTION_TEXT);
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForCreateCommandWithFilterAndIncorrectFilterValue")
    public void parseLogWithIncorrectHttpStatus_ShouldThrowHttpStatusNotSupportedException(int incorrectStatus) {
        final String log =
            "93.180.71.3 - - [17/May/2015:08:05:32 +0000] \"GET /downloads/product_1 HTTP/1.1\" " + incorrectStatus +
                " 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"";

        assertThatThrownBy(() -> {
            LogParser.parse(log);
        }).isInstanceOf(HttpStatusNotSupportedException.class)
            .hasMessageContaining(HTTP_STATUS_NOT_SUPPORTED_EXCEPTION_TEXT);
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForParseLogCorrectly")
    public void parseLogWithCorrectData_ShouldParseLog(String log, Log correctResult) {
        assertEquals(LogParser.parse(log), correctResult);
    }

    private static List<Object[]> getArgumentsForParseLogCorrectly() {
        final List<Object[]> result = new ArrayList<>();

        result.add(new Object[] {
            "93.180.71.3 - - [17/May/2015:08:05:32 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"",
            new Log(
                "93.180.71.3", "-", ZonedDateTime.parse("17/May/2015:08:05:32 +0000",
                DateTimeFormatter.ofPattern(TIME_LOCAL_FORMAT, Locale.ENGLISH)),
                new Request(HttpMethod.GET, "/downloads/product_1", HttpProtocol.LEGACY_HTTP),
                HttpStatus.NOT_MODIFIED, BigInteger.ZERO, "-", "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)"
            )
        });

        result.add(new Object[] {
            "80.70.214.71 - - [17/May/2015:09:05:20 +0000] \"HEAD /downloads/product_1 HTTP/1.1\" 200 0 \"-\" \"Wget/1.13.4 (linux-gnu)\"",
            new Log(
                "80.70.214.71", "-", ZonedDateTime.parse("17/May/2015:09:05:20 +0000",
                DateTimeFormatter.ofPattern(TIME_LOCAL_FORMAT, Locale.ENGLISH)),
                new Request(HttpMethod.HEAD, "/downloads/product_1", HttpProtocol.LEGACY_HTTP),
                HttpStatus.OK, BigInteger.ZERO, "-", "Wget/1.13.4 (linux-gnu)"
            )
        });

        result.add(new Object[] {
            "202.143.95.26 - - [17/May/2015:13:05:54 +0000] \"GET /downloads/product_2 HTTP/1.1\" 404 337 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.16)\"",
            new Log(
                "202.143.95.26", "-", ZonedDateTime.parse("17/May/2015:13:05:54 +0000",
                DateTimeFormatter.ofPattern(TIME_LOCAL_FORMAT, Locale.ENGLISH)),
                new Request(HttpMethod.GET, "/downloads/product_2", HttpProtocol.LEGACY_HTTP),
                HttpStatus.NOT_FOUND, new BigInteger("337"), "-", "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.16)"
            )
        });

        return result;
    }

    private static String[] getArgumentsForParseLogWithIncorrectFormat() {
        return new String[] {
            "- - [17/May/2015:08:05:32 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"",
            "93.180.71.3 - - 08:05:32 +0000 \"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"",
            "93.180.71.3 - - [17/May/2015:08:05:32 +0000] \"GET /downloads/product_1 HTTP/1.1\" XX0 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"",
            "93.180.71.3 - \"GET /downloads/product_1 HTTP/1.1\" [17/May/2015:08:05:32 +0000] 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"",
            "93.180.71.3 - - [17/May/2015:08:05:32 +0000] \"GET /downloads/product_1 HTTP/1.1\"",
            "- - [May/2015:08:05] \"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3",
            "93.180.71.3 - - [17/May/2015:08:05:57 +0000 GET /downloads/product_1\" HTTP/1.1\" 304 0 \"-\" Debian APT-HTTP/1.3\"",
            "93.180.71.3 - - [17/May/2015:08:05:57 +0000] \"GET /downloads/product_1 HTTP/1.1\" 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.10.3)\"",
            "- - [17/May/2015:08:05:32 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"-\"",
            "93.180.71.3 - - [17/ May/2015 :08:05:32 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3\"",
            "93.180.71.3 - - [17/May/2015:8:5:32 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3\"",
            "93.180.71.3 - - [17/May/2015:08:05:32 +0000] - 304 0 \"-\" \"Debian APT-HTTP/1.3\"",
            "93.180.71.3 - - [2015/May/17:08:05:32 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3\"",
            "93.180.71.3 - - [17/May/2015:08:05:32 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 \"-\" \"Debian APT-HTTP/1.3\"",
            "93.180.71.3 - - [17/May/2015:08:05:32 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 abc \"-\" \"Debian APT-HTTP/1.3\"",
            "93.180.71.3 - - [17/May/2015:08:05:32 +0000] 217.168.17.5 \"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3\"",
            "93.180.71.3 - - [17/May/2015:08:05:32 +0000] \" \" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"",
            "93.180.71.3 - - [2015/May/17:08:05:32 +0000] \"GET /downloads/product_1 HTTP/1.1\" a 0 \"-\" \"Debian APT-HTTP/1.3\"",
            "93.180.71.3 - - [17/May/2015:08:05:32 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 -1 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"",
            "93.180.71.3 - - [17/May/2015:08:05:32 +0000] \"GET \" 304 -1 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"",
            "93.180.71.3 - - [17/May/2015:08:05:32 +0000] \"GET HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"",
            "93.180.71.3 - - [17/May/2015:08:05:32 +0000] \"GET HTTP/1.1 HTTP/1.1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"",
            "93.180.71.3 - - [17/May/2015:08:05:32 +00] \"GET HTTP/1.1 HTTP/1.1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\""
        };
    }
}
