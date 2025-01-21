package backend.academy.logAnalyzer.exception;

import static backend.academy.logAnalyzer.constants.ExceptionTextValues.OUTPUT_FORMAT_NOT_SUPPORTED_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ProblemSolving.OUTPUT_FORMAT_ERROR_SOLVING;

public class OutputFormatNotSupportedException extends LogAnalyzerException {
    public OutputFormatNotSupportedException() {
        super(OUTPUT_FORMAT_NOT_SUPPORTED_EXCEPTION_TEXT);
    }

    @Override
    public String getSolution() {
        return OUTPUT_FORMAT_ERROR_SOLVING;
    }
}
