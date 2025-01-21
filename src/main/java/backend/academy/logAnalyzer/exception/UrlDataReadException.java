package backend.academy.logAnalyzer.exception;

import static backend.academy.logAnalyzer.constants.ExceptionTextValues.URL_DATA_NOT_READ_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ProblemSolving.URL_DATA_READ_PROBLEM_SOLVING;

public class UrlDataReadException extends LogAnalyzerException {

    public UrlDataReadException(Exception ex) {
        super(URL_DATA_NOT_READ_EXCEPTION_TEXT, ex);
    }

    @Override
    public String getSolution() {
        return URL_DATA_READ_PROBLEM_SOLVING;
    }
}
