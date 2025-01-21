package backend.academy.logAnalyzer.exception;

import static backend.academy.logAnalyzer.constants.ExceptionTextValues.LOG_FILES_NOT_FOUND_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ProblemSolving.FIND_LOCAL_FILES_ERROR_SOLVING;

public class LogFilesNotFoundException extends LogAnalyzerException {
    public LogFilesNotFoundException(String path) {
        super(String.format("%s Путь: %s", LOG_FILES_NOT_FOUND_EXCEPTION_TEXT, path));
    }

    @Override
    public String getSolution() {
        return FIND_LOCAL_FILES_ERROR_SOLVING;
    }
}
