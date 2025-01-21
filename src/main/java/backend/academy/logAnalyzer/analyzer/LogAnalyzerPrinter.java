package backend.academy.logAnalyzer.analyzer;

import backend.academy.logAnalyzer.dto.ProcessingResponse;
import backend.academy.logAnalyzer.enums.OutputFormat;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import lombok.experimental.UtilityClass;
import static backend.academy.logAnalyzer.constants.ConstValues.BASE_PATH;
import static backend.academy.logAnalyzer.constants.ConstValues.COMMAND_ARGUMENTS_DESCRIPTION;

@UtilityClass
public class LogAnalyzerPrinter {

    private static final PrintStream OUTPUT = System.out;

    public static void printMainMenu() {
        final String header = "Анализатор логов NGINX. Инструкция по использованию: ";
        final String descriptionFormat = "Команда должна начинаться с analyzer. "
            + "В пути к файлам не должно быть пробелов."
            + " Текущая директория: %s%n";

        OUTPUT.println(header);
        printCommandArguments(COMMAND_ARGUMENTS_DESCRIPTION);
        OUTPUT.format(descriptionFormat, BASE_PATH);
        printInputCommand();
    }

    public static void printStatistics(ProcessingResponse processingResponse, OutputFormat outputFormat) {
        if (processingResponse == null) {
            return;
        }

        final String format = "%s%n%n%s%n%n%s%n%n%s%n%n%s%n";

        OUTPUT.format(
            format,
            Scene.renderGeneralInformation(processingResponse.generalInformation(), outputFormat),
            Scene.renderResourcesInformation(processingResponse.requestStatistics().resources(), outputFormat),
            Scene.renderResponseCodesInformation(processingResponse.requestStatistics().httpStatuses(), outputFormat),
            Scene.renderAddressesInformation(processingResponse.requestStatistics().addresses(), outputFormat),
            Scene.renderMethodsInformation(processingResponse.requestStatistics().httpMethods(), outputFormat)
        );
    }

    public static void printErrorText(String errorText, String decisionText) {
        final String errorTextFormat = "Обнаружена ошибка: %s%n Возможное решение: %n%s%n";

        OUTPUT.format(errorTextFormat, errorText, decisionText);
    }

    public static void printErrorText(String text) {
        final String errorTextFormat = "Обнаружена ошибка: %s%n";

        OUTPUT.format(errorTextFormat, text);
    }

    public static void printCommandHistory(List<String> commands) {
        final String commandsHistoryText = "История введенных команд: ";

        OUTPUT.println(commandsHistoryText);
        printList(commands);
    }

    private static void printInputCommand() {
        OUTPUT.println("Введите команду: ");
    }

    private static void printCommandArguments(Map<String, String> commandArguments) {
        if (commandArguments == null) {
            return;
        }

        final String format = "%s - %s%n";

        commandArguments.forEach((key, value) -> OUTPUT.format(format, key, value));
    }

    private static void printList(List<?> list) {
        if (list == null) {
            return;
        }

        OUTPUT.println(String.join("\n", list.stream().map(String::valueOf).toList()));
    }
}
