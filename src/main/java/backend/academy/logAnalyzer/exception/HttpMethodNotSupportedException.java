package backend.academy.logAnalyzer.exception;

import static backend.academy.logAnalyzer.constants.ExceptionTextValues.HTTP_METHOD_NOT_SUPPORTED_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ProblemSolving.NOT_SUPPORTED_HTTP_METHOD_SOLVING;

public class HttpMethodNotSupportedException extends LogAnalyzerException {
    public HttpMethodNotSupportedException() {
        super(HTTP_METHOD_NOT_SUPPORTED_EXCEPTION_TEXT);
    }

    @Override
    public String getSolution() {
        return NOT_SUPPORTED_HTTP_METHOD_SOLVING;
    }
}
