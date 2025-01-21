package backend.academy.logAnalyzer.exception;

import static backend.academy.logAnalyzer.constants.ExceptionTextValues.HTTP_STATUS_NOT_SUPPORTED_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ProblemSolving.NOT_SUPPORTED_HTTP_STATUS_SOLVING;

public class HttpStatusNotSupportedException extends LogAnalyzerException {
    public HttpStatusNotSupportedException() {
        super(HTTP_STATUS_NOT_SUPPORTED_EXCEPTION_TEXT);
    }

    @Override
    public String getSolution() {
        return NOT_SUPPORTED_HTTP_STATUS_SOLVING;
    }
}
