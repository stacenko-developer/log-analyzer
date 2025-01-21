package backend.academy.logAnalyzer.enums;

import backend.academy.logAnalyzer.CommonTest;
import backend.academy.logAnalyzer.exception.OutputFormatNotSupportedException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.OUTPUT_FORMAT_NOT_SUPPORTED_EXCEPTION_TEXT;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OutputFormatTest extends CommonTest {

    @ParameterizedTest
    @EnumSource(OutputFormat.class)
    public void getOutputFormatByValue_ShouldReturnOutputFormat(OutputFormat outputFormat) {
        assertEquals(outputFormat, OutputFormat.getOutputFormatByValue(outputFormat.value()));
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForGetEnumByIncorrectValue")
    public void getOutputFormatByIncorrectValue_ShouldThrowOutputFormatNotSupportedException(String incorrectValue) {
        assertThatThrownBy(() -> {
            OutputFormat.getOutputFormatByValue(incorrectValue);
        }).isInstanceOf(OutputFormatNotSupportedException.class)
            .hasMessageContaining(OUTPUT_FORMAT_NOT_SUPPORTED_EXCEPTION_TEXT);
    }
}
