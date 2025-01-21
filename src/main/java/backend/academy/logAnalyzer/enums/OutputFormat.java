package backend.academy.logAnalyzer.enums;

import backend.academy.logAnalyzer.exception.OutputFormatNotSupportedException;
import java.util.Arrays;
import lombok.Getter;

@Getter
public enum OutputFormat {
    MARKDOWN("markdown", '#'),
    ADOC("adoc", '=');

    private final String value;
    private final char headerSymbol;

    OutputFormat(String value, char headerSymbol) {
        this.value = value;
        this.headerSymbol = headerSymbol;
    }

    public static OutputFormat getOutputFormatByValue(String value) {
        return Arrays.stream(OutputFormat.values())
            .filter(outputFormat -> outputFormat.value.equalsIgnoreCase(value))
            .findFirst()
            .orElseThrow(OutputFormatNotSupportedException::new);
    }
}
