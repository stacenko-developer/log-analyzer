package backend.academy.logAnalyzer.analyzer;

import backend.academy.logAnalyzer.exception.NoPreviousCommandException;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NULL_COMMAND_EXCEPTION_TEXT;

@Getter
public class CommandHistoryManager {

    private final List<String> commands = new ArrayList<>();

    public void addCommand(String command) {
        validateCommand(command);
        saveCommand(command);
    }

    public String getLastCommand() {
        if (commands.isEmpty()) {
            throw new NoPreviousCommandException();
        }

        return commands.getLast();
    }

    private void validateCommand(String command) {
        if (command == null) {
            throw new NullPointerException(NULL_COMMAND_EXCEPTION_TEXT);
        }
    }

    private void saveCommand(String command) {
        commands.add(command);
    }
}
