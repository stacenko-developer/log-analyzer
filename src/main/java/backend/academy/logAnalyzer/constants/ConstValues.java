package backend.academy.logAnalyzer.constants;

import backend.academy.logAnalyzer.enums.FilterField;
import backend.academy.logAnalyzer.enums.OutputFormat;
import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

@UtilityClass
public class ConstValues {

    public static final String BEGIN_COMMAND_VALUE = "analyzer";

    public static final OutputFormat DEFAULT_FORMAT = OutputFormat.MARKDOWN;

    public static final String REQUIRED_ARGUMENT_TEXT = "(обязательный параметр)";
    public static final String NOT_REQUIRED_ARGUMENT_TEXT = "(опциональный параметр)";

    public static final String REQUEST_FORMAT = "method url http-protocol";
    public static final String LOG_FORMAT = "$remote_addr - $remote_user [$time_local] \"$request\" "
        + "(" + REQUEST_FORMAT + ") "
        + "$status $body_bytes_sent \"$http_referer\" \"$http_user_agent\"";
    public static final String TIME_LOCAL_FORMAT = "dd/MMM/yyyy:HH:mm:ss Z";

    public static final String FILE_PATH_ARGUMENT = "--path";
    public static final String FROM_ARGUMENT = "--from";
    public static final String TO_ARGUMENT = "--to";
    public static final String FORMAT_ARGUMENT = "--format";
    public static final String FILTER_FIELD_ARGUMENT = "--filter-field";
    public static final String FILTER_VALUE_ARGUMENT = "--filter-value";
    public static final String EXIT_ARGUMENT = "--exit";
    public static final String PREVIOUS_ARGUMENT = "--previous";
    public static final String HISTORY_ARGUMENT = "--history";

    public static final String FILES_OUTPUT_TEXT = "Файлы";
    public static final String START_DATE_OUTPUT_TEXT = "Начальная дата";
    public static final String END_DATE_OUTPUT_TEXT = "Конечная дата";
    public static final String REQUESTS_COUNT_OUTPUT_TEXT = "Количество запросов";
    public static final String RESPONSE_AVERAGE_SIZE_OUTPUT_TEXT = "Средний размер ответа";
    public static final String PERCENTILE_OUTPUT_TEXT = "95p размера ответа";

    public static final int HEADER_SYMBOLS_COUNT = 4;

    public static final String GENERAL_INFORMATION_TEXT = "Общая информация";
    public static final String REQUESTED_RESOURCES_TEXT = "Запрашиваемые ресурсы";
    public static final String RESPONSE_CODES_TEXT = "Коды ответа";
    public static final String ADDRESSES_TEXT = "IP адреса";
    public static final String HTTP_METHODS_TEXT = "HTTP методы";

    public static final int STATISTICS_LIMIT = 5;
    public static final String NO_VALUE_IN_STATISTICS = "-";
    public static final String BASE_PATH = Paths.get(StringUtils.EMPTY).toAbsolutePath() + File.separator;

    public static final String PREVIOUS_COMMAND = BEGIN_COMMAND_VALUE + " " + PREVIOUS_ARGUMENT;
    public static final String HISTORY_COMMAND = BEGIN_COMMAND_VALUE + " " + HISTORY_ARGUMENT;

    public static final List<String> NO_LOG_ANALYZE_ARGUMENTS = List.of(
        PREVIOUS_ARGUMENT,
        HISTORY_ARGUMENT,
        EXIT_ARGUMENT
    );

    public static final String RESOURCES_PATH = "src"
        + File.separator + "main"
        + File.separator + "resources" + File.separator;

    public static final Map<String, String> COMMAND_ARGUMENTS_DESCRIPTION = new LinkedHashMap<>();

    static {
        COMMAND_ARGUMENTS_DESCRIPTION.put(FILE_PATH_ARGUMENT,
            "Путь к одному или нескольким NGINX лог-файлам в виде локального шаблона или URL "
                + REQUIRED_ARGUMENT_TEXT);
        COMMAND_ARGUMENTS_DESCRIPTION.put(FROM_ARGUMENT, "Начальная дата логов в формате ISO8601 "
            + NOT_REQUIRED_ARGUMENT_TEXT);
        COMMAND_ARGUMENTS_DESCRIPTION.put(TO_ARGUMENT, "Конечная дата логов в формате ISO8601 "
            + NOT_REQUIRED_ARGUMENT_TEXT);
        COMMAND_ARGUMENTS_DESCRIPTION.put(FORMAT_ARGUMENT, "Формат вывода результата "
            + Arrays.toString(Arrays.stream(OutputFormat.values()).map(OutputFormat::value).toArray())
            + " " + NOT_REQUIRED_ARGUMENT_TEXT);
        COMMAND_ARGUMENTS_DESCRIPTION.put(FILTER_FIELD_ARGUMENT, "Поле, по которому происходит фильтрация "
            + Arrays.toString(Arrays.stream(FilterField.values()).map(FilterField::value).toArray())
            + " " + NOT_REQUIRED_ARGUMENT_TEXT);
        COMMAND_ARGUMENTS_DESCRIPTION.put(FILTER_VALUE_ARGUMENT, "Допустимое значение поля");
        COMMAND_ARGUMENTS_DESCRIPTION.put(PREVIOUS_ARGUMENT, "Выполнить предыдущую команду");
        COMMAND_ARGUMENTS_DESCRIPTION.put(HISTORY_ARGUMENT, "Посмотреть историю комманд");
        COMMAND_ARGUMENTS_DESCRIPTION.put(EXIT_ARGUMENT, "Выйти");
    }
}
