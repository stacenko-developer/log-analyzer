package backend.academy.logAnalyzer.exception;

public abstract class LogAnalyzerException extends RuntimeException {

    public LogAnalyzerException(String message) {
        super(message);
    }

    public LogAnalyzerException(String message, Exception ex) {
        super(message, ex);
    }

    public abstract String getSolution();
}
