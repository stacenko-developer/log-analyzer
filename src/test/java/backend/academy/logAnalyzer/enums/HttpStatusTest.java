package backend.academy.logAnalyzer.enums;

import backend.academy.logAnalyzer.CommonTest;
import backend.academy.logAnalyzer.exception.HttpStatusNotSupportedException;
import java.util.stream.IntStream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.HTTP_STATUS_NOT_SUPPORTED_EXCEPTION_TEXT;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpStatusTest extends CommonTest {

    @ParameterizedTest
    @EnumSource(HttpStatus.class)
    public void getHttpStatusByValue_ShouldReturnHttpStatus(HttpStatus httpStatus) {
        assertEquals(httpStatus, HttpStatus.getHttpStatusByCode(httpStatus.code()));
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForGetHttpStatusByIncorrectCode")
    public void getHttpStatusByIncorrectValue_ShouldThrowHttpStatusNotSupportedException(int incorrectCode) {
        assertThatThrownBy(() -> {
            HttpStatus.getHttpStatusByCode(incorrectCode);
        }).isInstanceOf(HttpStatusNotSupportedException.class)
            .hasMessageContaining(HTTP_STATUS_NOT_SUPPORTED_EXCEPTION_TEXT);
    }

    private static int[] getArgumentsForGetHttpStatusByIncorrectCode() {
        final int minIncorrectValue = -100;
        final int maxIncorrectValue = 99;

        return IntStream.range(minIncorrectValue, maxIncorrectValue + 1).toArray();
    }
}

