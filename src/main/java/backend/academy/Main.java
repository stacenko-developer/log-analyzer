package backend.academy;

import backend.academy.logAnalyzer.analyzer.LogAnalyzer;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {
    public static void main(String[] args) {
        new LogAnalyzer().start(args);
    }
}
