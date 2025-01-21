package backend.academy.logAnalyzer.dto;

import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NULL_GENERAL_INFORMATION_EXCEPTION_TEXT;
import static backend.academy.logAnalyzer.constants.ExceptionTextValues.NULL_REQUEST_STATISTICS_EXCEPTION_TEXT;

public record ProcessingResponse(GeneralInformation generalInformation, RequestStatistics requestStatistics) {
    public ProcessingResponse {
        validateGeneralInformation(generalInformation);
        validateRequestStatistics(requestStatistics);
    }

    private void validateGeneralInformation(GeneralInformation generalInformation) {
        if (generalInformation == null) {
            throw new NullPointerException(NULL_GENERAL_INFORMATION_EXCEPTION_TEXT);
        }
    }

    private void validateRequestStatistics(RequestStatistics requestStatistics) {
        if (requestStatistics == null) {
            throw new NullPointerException(NULL_REQUEST_STATISTICS_EXCEPTION_TEXT);
        }
    }
}
