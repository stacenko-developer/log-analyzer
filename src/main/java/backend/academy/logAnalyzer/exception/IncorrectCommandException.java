package backend.academy.logAnalyzer.exception;

import static backend.academy.logAnalyzer.constants.ProblemSolving.INCORRECT_COMMAND_SOLVING;

public class IncorrectCommandException extends LogAnalyzerException {

    public static final String DEFAULT_MESSAGE = "Введенная команда некорректна. Причина:";
    public static final String FORMAT = "%s %s";

    public IncorrectCommandException(String message) {
        super(String.format(FORMAT, DEFAULT_MESSAGE, message));
    }

    public IncorrectCommandException(String message, Exception ex) {
        super(String.format(FORMAT, DEFAULT_MESSAGE, message), ex);
    }

    @Override
    public String getSolution() {
        return INCORRECT_COMMAND_SOLVING;
    }
}
