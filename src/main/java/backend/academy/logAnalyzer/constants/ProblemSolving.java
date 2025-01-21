package backend.academy.logAnalyzer.constants;

import backend.academy.logAnalyzer.enums.FilterField;
import backend.academy.logAnalyzer.enums.HttpMethod;
import backend.academy.logAnalyzer.enums.HttpProtocol;
import backend.academy.logAnalyzer.enums.OutputFormat;
import java.util.Arrays;
import lombok.experimental.UtilityClass;
import static backend.academy.logAnalyzer.constants.ConstValues.LOG_FORMAT;
import static backend.academy.logAnalyzer.constants.ConstValues.REQUEST_FORMAT;
import static backend.academy.logAnalyzer.constants.ConstValues.TIME_LOCAL_FORMAT;

@UtilityClass
public class ProblemSolving {

    private static final String AVAILABLE_VIEWS = "Допустимые виды";
    private static final String READ_MORE_TEXT = "\nОзнакомиться подробнее";
    private static final String RIGHT_WAY_TEXT
        = "1. Убедитесь, что вы указали правильный путь\n";
    private static final String REASONS_TEXT = "Возможные причины: \n";

    public static final String EXIT_FROM_CURRENT_DIRECTORY_SOLVING
        = "Работайте в рамках текущей директории и не "
        + "используйте специальные конструкции для ее изменения";

    public static final String FILE_DATA_READ_WITH_ERROR_SOLVING
        = RIGHT_WAY_TEXT + "2. В файле содержатся только логи nginx";

    public static final String FILTER_FIELD_NOT_SUPPORTED_SOLVING
        = AVAILABLE_VIEWS + " полей, по которым будет проходить фильтрация: "
        + Arrays.toString(Arrays.stream(FilterField.values()).map(FilterField::value).toArray());

    public static final String NOT_SUPPORTED_HTTP_METHOD_SOLVING
        = AVAILABLE_VIEWS + " методов: " + Arrays.toString(HttpMethod.values())
        + READ_MORE_TEXT + ": https://developer.mozilla.org/ru/docs/Web/HTTP/Methods";

    public static final String NOT_SUPPORTED_HTTP_PROTOCOL_SOLVING
        = AVAILABLE_VIEWS + " HTTP протоколов: " + Arrays.toString(HttpProtocol.values())
        + READ_MORE_TEXT + ": https://dev.to/accreditly/http1-vs-http2-vs-http3-2k1c";

    public static final String NOT_SUPPORTED_HTTP_STATUS_SOLVING
        = READ_MORE_TEXT + ": https://developer.mozilla.org/en-US/docs/Web/HTTP/Status";

    public static final String INCORRECT_COMMAND_SOLVING
        = """
        Какая команда считается корректной:
        1. Команда состоит только из допустимого набора аргументов
        2. Начинается со слова analyzer
        3. Для каждого указанного аргумента должно быть значение и только одно
        4. Указаны все обязательные аргументы
        5. Аргумент выхода не должен быть вместе с другими аргументами
        6. При фильтрации поля и значения фильтров должны поддерживаться
        7. Для фильтрации по дате необходимо указывать дату в формате: yyyy-mm-dd
        8. Время начала интервала должно быть строго меньше времени конца""";

    public static final String FIND_LOCAL_FILES_ERROR_SOLVING
        = RIGHT_WAY_TEXT + "2. Проверьте правильность использования glob выражений"
        + READ_MORE_TEXT + ": https://en.wikipedia.org/wiki/Glob_(programming)";

    public static final String LOG_FORMAT_ERROR_SOLVING
        = REASONS_TEXT
        + "1. Несоответствие формату " + LOG_FORMAT
        + "\n2. Дата не соответствует формату " + TIME_LOCAL_FORMAT
        + "\n3. Запрос не соответствует формату: " + REQUEST_FORMAT
        + "\nПример логов nginx: https://raw.githubusercontent.com/elastic/examples/master/"
        + "Common%20Data%20Formats/nginx_logs/nginx_logs";

    public static final String OUTPUT_FORMAT_ERROR_SOLVING
        = "Используйте допустимые форматы для отображения отчета: " + Arrays.toString(OutputFormat.values());

    public static final String URL_DATA_READ_PROBLEM_SOLVING
        = REASONS_TEXT
        + "1. Указан неверный url, возможно, присутствуют опечатки\n"
        + "2. Сайт может быть временно недоступен";

    public static final String NO_PREVIOUS_COMMAND_PROBLEM_SOLVING
        = "Прежде чем получить предыдущую команду, убедитесь, что ранее вы уже вводили команды";

}
