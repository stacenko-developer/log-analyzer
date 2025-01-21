package backend.academy.logAnalyzer.exception;

import static backend.academy.logAnalyzer.constants.ExceptionTextValues.INCORRECT_LOG_FORMAT_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ProblemSolving.LOG_FORMAT_ERROR_SOLVING;

public class IncorrectLogFormatException extends LogAnalyzerException {
    public IncorrectLogFormatException() {
        super(INCORRECT_LOG_FORMAT_EXCEPTION_TEXT);
    }

    public IncorrectLogFormatException(Exception ex) {
        super(INCORRECT_LOG_FORMAT_EXCEPTION_TEXT, ex);
    }

    @Override
    public String getSolution() {
        return LOG_FORMAT_ERROR_SOLVING;
    }
}
