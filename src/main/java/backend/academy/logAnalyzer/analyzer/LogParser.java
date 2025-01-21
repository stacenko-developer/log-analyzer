package backend.academy.logAnalyzer.analyzer;

import backend.academy.logAnalyzer.dto.Log;
import backend.academy.logAnalyzer.dto.Request;
import backend.academy.logAnalyzer.enums.HttpMethod;
import backend.academy.logAnalyzer.enums.HttpProtocol;
import backend.academy.logAnalyzer.enums.HttpStatus;
import backend.academy.logAnalyzer.exception.IncorrectLogFormatException;
import java.math.BigInteger;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import static backend.academy.logAnalyzer.constants.ConstValues.TIME_LOCAL_FORMAT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NULL_LOG_DATA_EXCEPTION_TEXT;

@UtilityClass
public class LogParser {

    private static final Pattern LOG_PATTERN = Pattern.compile(
        "(\\S+) - (\\S+) \\[(.+?)\\] \"(\\S+\\s\\S+\\s\\S+)\" (\\d+) (\\d+) \"(.*?)\" \"(.*?)\""
    );
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
        DateTimeFormatter.ofPattern(TIME_LOCAL_FORMAT, Locale.ENGLISH);

    public static Log parse(String logEntry) {
        if (StringUtils.isBlank(logEntry)) {
            throw new NullPointerException(NULL_LOG_DATA_EXCEPTION_TEXT);
        }

        final Matcher matcher = LOG_PATTERN.matcher(logEntry);

        if (!matcher.matches()) {
            throw new IncorrectLogFormatException();
        }

        final String remoteAddress = matcher.group(1);
        final String remoteUser = matcher.group(2);
        final ZonedDateTime timeLocal = getTimeLocal(matcher.group(3));

        final String request = matcher.group(4);

        final String[] requestData = request.split(" ");

        final String httpMethodStr = requestData[0];
        final String resource = requestData[1];
        final String httpProtocolStr = requestData[2];

        final HttpMethod httpMethod = HttpMethod.getHttpMethodByValue(httpMethodStr);
        final HttpProtocol httpProtocol = HttpProtocol.getHttpProtocolByValue(httpProtocolStr);
        final Request httpRequest = new Request(httpMethod, resource, httpProtocol);

        final int status = Integer.parseInt(matcher.group(5));
        final HttpStatus httpStatus = HttpStatus.getHttpStatusByCode(status);
        final BigInteger bodyBytesSend = new BigInteger(matcher.group(6));
        final String httpReferer = matcher.group(7);
        final String httpUserAgent = matcher.group(8);

        return new Log(remoteAddress, remoteUser, timeLocal, httpRequest,
            httpStatus, bodyBytesSend, httpReferer, httpUserAgent);
    }

    private static ZonedDateTime getTimeLocal(String dateInput) {
        try {
            return ZonedDateTime.parse(dateInput, DATE_TIME_FORMATTER);
        } catch (Exception ex) {
            throw new IncorrectLogFormatException(ex);
        }
    }
}
