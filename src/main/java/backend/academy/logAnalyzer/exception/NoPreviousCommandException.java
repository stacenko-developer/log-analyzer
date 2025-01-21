package backend.academy.logAnalyzer.exception;

import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NO_PREVIOUS_COMMAND_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ProblemSolving.NO_PREVIOUS_COMMAND_PROBLEM_SOLVING;

public class NoPreviousCommandException extends LogAnalyzerException {

    public NoPreviousCommandException() {
        super(NO_PREVIOUS_COMMAND_EXCEPTION_TEXT);
    }

    @Override
    public String getSolution() {
        return NO_PREVIOUS_COMMAND_PROBLEM_SOLVING;
    }
}
