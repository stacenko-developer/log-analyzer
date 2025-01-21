package backend.academy.logAnalyzer.enums;

import backend.academy.logAnalyzer.CommonTest;
import backend.academy.logAnalyzer.exception.FilterFieldNotSupportedException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.FILTER_FIELD_NOT_SUPPORTED_EXCEPTION_TEXT;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FilterFieldTest extends CommonTest {

    @ParameterizedTest
    @EnumSource(FilterField.class)
    public void getFilterFieldByValue_ShouldReturnFilterField(FilterField filterField) {
        assertEquals(filterField, FilterField.getFilterFieldByValue(filterField.value()));
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForGetEnumByIncorrectValue")
    public void getFilterFieldByIncorrectValue_ShouldThrowFilterFieldNotSupportedException(String incorrectValue) {
        assertThatThrownBy(() -> {
            FilterField.getFilterFieldByValue(incorrectValue);
        }).isInstanceOf(FilterFieldNotSupportedException.class)
            .hasMessageContaining(FILTER_FIELD_NOT_SUPPORTED_EXCEPTION_TEXT);
    }
}
