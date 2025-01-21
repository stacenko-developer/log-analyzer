package backend.academy.logAnalyzer.exception;

import static backend.academy.logAnalyzer.constants.ExceptionTextValues.EXIT_FROM_CURRENT_DIRECTORY_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ProblemSolving.EXIT_FROM_CURRENT_DIRECTORY_SOLVING;

public class ExitFromCurrentDirectoryException extends LogAnalyzerException {
    public ExitFromCurrentDirectoryException() {
        super(EXIT_FROM_CURRENT_DIRECTORY_EXCEPTION_TEXT);
    }

    @Override
    public String getSolution() {
        return EXIT_FROM_CURRENT_DIRECTORY_SOLVING;
    }
}
