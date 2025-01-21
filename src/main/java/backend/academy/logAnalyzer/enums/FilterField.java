package backend.academy.logAnalyzer.enums;

import backend.academy.logAnalyzer.exception.FilterFieldNotSupportedException;
import java.util.Arrays;
import lombok.Getter;

@Getter
public enum FilterField {
    ADDRESS("address"),
    USER("user"),
    HTTP_METHOD("method"),
    AGENT("agent"),
    HTTP_STATUS("status");

    private final String value;

    FilterField(String value) {
        this.value = value;
    }

    public static FilterField getFilterFieldByValue(String value) {
        return Arrays.stream(FilterField.values())
            .filter(filterField -> filterField.value.equalsIgnoreCase(value))
            .findFirst()
            .orElseThrow(FilterFieldNotSupportedException::new);
    }
}
