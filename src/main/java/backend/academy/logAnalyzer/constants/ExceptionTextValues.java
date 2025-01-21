package backend.academy.logAnalyzer.constants;

import lombok.experimental.UtilityClass;
import static backend.academy.logAnalyzer.constants.ConstValues.BEGIN_COMMAND_VALUE;
import static backend.academy.logAnalyzer.constants.ConstValues.FILE_PATH_ARGUMENT;
import static backend.academy.logAnalyzer.constants.ConstValues.FILTER_FIELD_ARGUMENT;
import static backend.academy.logAnalyzer.constants.ConstValues.FILTER_VALUE_ARGUMENT;
import static backend.academy.logAnalyzer.constants.ConstValues.FORMAT_ARGUMENT;
import static backend.academy.logAnalyzer.constants.ConstValues.FROM_ARGUMENT;
import static backend.academy.logAnalyzer.constants.ConstValues.LOG_FORMAT;
import static backend.academy.logAnalyzer.constants.ConstValues.TO_ARGUMENT;

@UtilityClass
public class ExceptionTextValues {
    public static final String NO_PREVIOUS_COMMAND_EXCEPTION_TEXT = "Предыдущая команда отсутствует";

    public static final String LOG_FILES_NOT_FOUND_EXCEPTION_TEXT = "По указанному пути лог-файлы не найдены";
    public static final String URL_DATA_NOT_READ_EXCEPTION_TEXT = "Не удалось считать данные по указанному url";

    public static final String EXIT_FROM_CURRENT_DIRECTORY_EXCEPTION_TEXT = "Выход из текущей директории";

    public static final String ONLY_ONE_VALUE_TEXT = "принимает только одно значение";
    public static final String NO_VALUE_FOR_ARGUMENT_TEXT = "Отсутствует значение для аргумента";

    public static final String INCORRECT_BEGIN_COMMAND_VALUE_EXCEPTION_TEXT = "Команда должна начинаться с "
        + BEGIN_COMMAND_VALUE;
    public static final String INCORRECT_DATE_FORMAT_EXCEPTION_TEXT = "Дата указана в некорректном формате";
    public static final String INCORRECT_LOG_FORMAT_EXCEPTION_TEXT = "Данные должны быть в формате: "
        + LOG_FORMAT;
    public static final String INCORRECT_DATE_RANGE_EXCEPTION_TEXT = "Конечная дата должна быть больше начальной";
    public static final String FIND_LOCAL_FILES_EXCEPTION_TEXT = "Ошибка при поиске файлов по указанному пути";
    public static final String FILE_DATA_READ_EXCEPTION_TEXT = "Ошибка при чтении данных с файла";

    public static final String NOT_POSITIVE_REQUESTS_EXCEPTION_TEXT
        = "Количество запросов не должно быть отрицательным";
    public static final String NOT_POSITIVE_RESPONSE_AVERAGE_SIZE_EXCEPTION_TEXT =
        "Средний размер ответа не должен быть отрицательным";
    public static final String NOT_POSITIVE_PERCENTILE_EXCEPTION_TEXT = "Перцентиль не должен быть отрицательным";
    public static final String NOT_POSITIVE_BODY_BYTES_SEND_EXCEPTION_TEXT =
        "Количество отправленных байт не должно быть отрицательным числом";

    public static final String DUPLICATE_ARGUMENTS_EXCEPTION_TEXT
        = "В команде обнаружено дублирование аргументов";
    public static final String NO_LOG_ANALYZE_ARGUMENT_WITH_OTHERS_EXCEPTION_TEXT
        = "Аргументы для выхода, истории команд и запуска предыдущей команды могут встречаться только один раз"
        + " и без других аргументов";

    public static final String VALUE_WITHOUT_ARGUMENT_EXCEPTION_TEXT = "Значения не должны быть указаны "
        + "без самих аргументов";
    public static final String SOME_VALUES_IN_FILE_PATH_ARGUMENT_EXCEPTION_TEXT = FILE_PATH_ARGUMENT + " "
        + ONLY_ONE_VALUE_TEXT;
    public static final String SOME_VALUES_IN_FROM_ARGUMENT_EXCEPTION_TEXT = FROM_ARGUMENT + " "
        + ONLY_ONE_VALUE_TEXT;
    public static final String SOME_VALUES_IN_TO_ARGUMENT_EXCEPTION_TEXT = TO_ARGUMENT + " "
        + ONLY_ONE_VALUE_TEXT;

    public static final String NOT_VALUE_FOR_FILE_PATH_ARGUMENT_EXCEPTION_TEXT = NO_VALUE_FOR_ARGUMENT_TEXT + " "
        + FILE_PATH_ARGUMENT;
    public static final String NOT_VALUE_FOR_FROM_ARGUMENT_EXCEPTION_TEXT
        = NO_VALUE_FOR_ARGUMENT_TEXT + " " + FROM_ARGUMENT;
    public static final String NOT_VALUE_FOR_TO_ARGUMENT_EXCEPTION_TEXT
        = NO_VALUE_FOR_ARGUMENT_TEXT + " " + TO_ARGUMENT;
    public static final String NOT_VALUE_FOR_FORMAT_ARGUMENT_EXCEPTION_TEXT = NO_VALUE_FOR_ARGUMENT_TEXT + " "
        + FORMAT_ARGUMENT;
    public static final String NOT_VALUE_FOR_FILTER_FIELD_ARGUMENT_EXCEPTION_TEXT
        = NO_VALUE_FOR_ARGUMENT_TEXT + " " + FILTER_FIELD_ARGUMENT;
    public static final String NOT_VALUE_FOR_FILTER_VALUE_ARGUMENT_EXCEPTION_TEXT
        = NO_VALUE_FOR_ARGUMENT_TEXT + " " + FILTER_VALUE_ARGUMENT;

    public static final String INCORRECT_FILTERS_IN_COMMAND_EXCEPTION_TEXT = "Атрибуты " + FILTER_FIELD_ARGUMENT
        + " и " + FILTER_VALUE_ARGUMENT + " должны быть указаны вместе";

    public static final String NULL_LOG_DATA_EXCEPTION_TEXT = "Данные лога не должны быть null";
    public static final String NULL_STATISTICS_EXCEPTION_TEXT = "Статистика и ее не должна быть null";
    public static final String NULL_ELEMENTS_STATISTICS_EXCEPTION_TEXT = "Элементы статистики не должны быть null";

    public static final String NULL_FILE_PATH_EXCEPTION_TEXT = "Путь к файлам не должен быть null";
    public static final String NULL_RESPONSE_AVERAGE_SIZE_EXCEPTION_TEXT =
        "Средний размер ответа не должен быть null";
    public static final String NULL_PERCENTILE_EXCEPTION_TEXT = "Перцентиль не должен быть null";
    public static final String NULL_SEND_BYTES_COUNT_EXCEPTION_TEXT
        = "Количество отправленных байт не должно быть null";
    public static final String NULL_COMMAND_EXCEPTION_TEXT = "Команда не должна быть пустой";
    public static final String NULL_REMOTE_USER_EXCEPTION_TEXT
        = "Пользователь, отправивший запрос, не должен быть null";
    public static final String NULL_TIME_LOCAL_EXCEPTION_TEXT = "Время отправки запроса не должно быть null";
    public static final String NULL_HTTP_REQUEST_EXCEPTION_TEXT = "HTTP запрос не должен быть null";
    public static final String NULL_HTTP_PROTOCOL_EXCEPTION_TEXT = "HTTP протокол не должен быть null";
    public static final String NULL_RESOURCES_EXCEPTION_TEXT = "Ресурсы не должны быть null";
    public static final String NULL_HTTP_STATUSES_EXCEPTION_TEXT = "HTTP статусы не должна быть null";
    public static final String NULL_ADDRESSES_EXCEPTION_TEXT = "Адреса не должна быть null";
    public static final String NULL_HTTP_METHODS_EXCEPTION_TEXT = "HTTP методы не должны быть null";
    public static final String NULL_COUNT_EXCEPTION_TEXT = "Значение количества чего-либо не должно null";
    public static final String NULL_REQUEST_STATISTICS_EXCEPTION_TEXT = "Статистика запросов не должна быть null";
    public static final String NULL_GENERAL_INFORMATION_EXCEPTION_TEXT = "Общая информация не должна быть null";
    public static final String NULL_HTTP_REFERER_EXCEPTION_TEXT
        = "URL-адрес ссылающейся страницы не должен быть null";
    public static final String NULL_HTTP_STATUS_EXCEPTION_TEXT = "HTTP статус не должен быть null";
    public static final String NULL_USER_AGENT_EXCEPTION_TEXT
        = "Информация об устройстве и браузере клиента не должна быть null";

    public static final String NOT_POSITIVE_COUNT_EXCEPTION_TEXT
        = "Значение количества чего-либо не должно быть отрицательным";

    public static final String OUTPUT_FORMAT_NOT_SUPPORTED_EXCEPTION_TEXT = "Данный формат не поддерживается";
    public static final String HTTP_STATUS_NOT_SUPPORTED_EXCEPTION_TEXT = "Данный HTTP статус не поддерживается";
    public static final String HTTP_METHOD_NOT_SUPPORTED_EXCEPTION_TEXT = "Данный HTTP метод не поддерживается";
    public static final String HTTP_PROTOCOL_NOT_SUPPORTED_EXCEPTION_TEXT = "Данный HTPP протокол не поддерживается";

    public static final String FILTER_FIELD_NOT_SUPPORTED_EXCEPTION_TEXT
        = "Данное поле для фильтрации не поддерживается";
    public static final String SOME_VALUES_IN_FORMAT_ARGUMENT_EXCEPTION_TEXT
        = FORMAT_ARGUMENT + " " + ONLY_ONE_VALUE_TEXT;
    public static final String SOME_VALUES_IN_FILTER_VALUE_EXCEPTION_TEXT
        = FILTER_VALUE_ARGUMENT + " " + ONLY_ONE_VALUE_TEXT;
    public static final String SOME_VALUES_IN_FILTER_FIELD_EXCEPTION_TEXT
        = FILTER_FIELD_ARGUMENT + " " + ONLY_ONE_VALUE_TEXT;

    public static final String UNKNOWN_ERROR_EXCEPTION_TEXT = "Неизвестная ошибка";
}
