package backend.academy.logAnalyzer.exception;

import static backend.academy.logAnalyzer.constants.ExceptionTextValues.FILTER_FIELD_NOT_SUPPORTED_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ProblemSolving.FILTER_FIELD_NOT_SUPPORTED_SOLVING;

public class FilterFieldNotSupportedException extends LogAnalyzerException {
    public FilterFieldNotSupportedException() {
        super(FILTER_FIELD_NOT_SUPPORTED_EXCEPTION_TEXT);
    }

    @Override
    public String getSolution() {
        return FILTER_FIELD_NOT_SUPPORTED_SOLVING;
    }
}
