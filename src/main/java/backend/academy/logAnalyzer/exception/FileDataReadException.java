package backend.academy.logAnalyzer.exception;

import static backend.academy.logAnalyzer.constants.ExceptionTextValues.FILE_DATA_READ_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ProblemSolving.FILE_DATA_READ_WITH_ERROR_SOLVING;

public class FileDataReadException extends LogAnalyzerException {
    public FileDataReadException(Exception ex) {
        super(FILE_DATA_READ_EXCEPTION_TEXT, ex);
    }

    @Override
    public String getSolution() {
        return FILE_DATA_READ_WITH_ERROR_SOLVING;
    }
}
