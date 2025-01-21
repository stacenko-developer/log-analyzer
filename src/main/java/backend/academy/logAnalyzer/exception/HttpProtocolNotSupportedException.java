package backend.academy.logAnalyzer.exception;

import static backend.academy.logAnalyzer.constants.ExceptionTextValues.HTTP_PROTOCOL_NOT_SUPPORTED_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ProblemSolving.NOT_SUPPORTED_HTTP_PROTOCOL_SOLVING;

public class HttpProtocolNotSupportedException extends LogAnalyzerException {
    public HttpProtocolNotSupportedException() {
        super(HTTP_PROTOCOL_NOT_SUPPORTED_EXCEPTION_TEXT);
    }

    @Override
    public String getSolution() {
        return NOT_SUPPORTED_HTTP_PROTOCOL_SOLVING;
    }
}
