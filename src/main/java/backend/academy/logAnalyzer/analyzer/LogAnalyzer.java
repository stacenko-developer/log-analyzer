package backend.academy.logAnalyzer.analyzer;

import backend.academy.logAnalyzer.dto.Command;
import backend.academy.logAnalyzer.dto.ProcessingResponse;
import backend.academy.logAnalyzer.exception.LogAnalyzerException;
import java.nio.charset.Charset;
import java.util.Scanner;
import static backend.academy.logAnalyzer.constants.ConstValues.BEGIN_COMMAND_VALUE;
import static backend.academy.logAnalyzer.constants.ConstValues.HISTORY_COMMAND;
import static backend.academy.logAnalyzer.constants.ConstValues.PREVIOUS_COMMAND;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.UNKNOWN_ERROR_EXCEPTION_TEXT;

public class LogAnalyzer {

    private final Scanner scanner = new Scanner(System.in, Charset.defaultCharset());
    private final CommandHistoryManager commandHistoryManager = new CommandHistoryManager();
    private final CommandHandler commandHandler = new CommandHandler();

    public void start(String[] args) {
        final boolean emptyArgs = emptyArgs(args);

        if (emptyArgs) {
            LogAnalyzerPrinter.printMainMenu();
        }

        while (true) {
            try {
                final String input = getInput(args);

                handleInput(input);

                if (PREVIOUS_COMMAND.equals(input) || HISTORY_COMMAND.equals(input)) {
                    if (!emptyArgs) {
                        break;
                    } else {
                        continue;
                    }
                }

                final Command command = CommandParser.parse(input);

                if (command == null) {
                    break;
                }

                final ProcessingResponse processingResponse = commandHandler.process(command);

                LogAnalyzerPrinter.printStatistics(processingResponse, command.format());
            } catch (Exception ex) {
                handleException(ex);
            }

            if (!emptyArgs) {
                break;
            }
        }
    }

    private void handleInput(String input) {
        if (!PREVIOUS_COMMAND.equals(input) && !HISTORY_COMMAND.equals(input)) {
            commandHistoryManager.addCommand(input);
        }

        if (HISTORY_COMMAND.equals(input)) {
            LogAnalyzerPrinter.printCommandHistory(commandHistoryManager.commands());
        }
    }

    private void handleException(Exception ex) {
        if (ex instanceof LogAnalyzerException logAnalyzerException) {
            LogAnalyzerPrinter.printErrorText(logAnalyzerException.getMessage(), logAnalyzerException.getSolution());
        } else {
            LogAnalyzerPrinter.printErrorText(UNKNOWN_ERROR_EXCEPTION_TEXT);
        }
    }

    private String getInput(String[] args) {
        String input;

        if (emptyArgs(args)) {
            input = scanner.nextLine();
        } else {
            final String delimiter = " ";
            input = BEGIN_COMMAND_VALUE + delimiter + String.join(delimiter, args);
        }

        if (PREVIOUS_COMMAND.equals(input)) {
            return commandHistoryManager.getLastCommand();
        }

        return input;
    }

    private boolean emptyArgs(String[] args) {
        return args == null || args.length == 0;
    }
}
