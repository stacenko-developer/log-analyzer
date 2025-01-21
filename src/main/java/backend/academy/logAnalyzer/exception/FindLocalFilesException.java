package backend.academy.logAnalyzer.exception;

import static backend.academy.logAnalyzer.constants.ExceptionTextValues.FIND_LOCAL_FILES_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ProblemSolving.FIND_LOCAL_FILES_ERROR_SOLVING;

public class FindLocalFilesException extends LogAnalyzerException {
    public FindLocalFilesException(Exception ex) {
        super(FIND_LOCAL_FILES_EXCEPTION_TEXT, ex);
    }

    @Override
    public String getSolution() {
        return FIND_LOCAL_FILES_ERROR_SOLVING;
    }
}
