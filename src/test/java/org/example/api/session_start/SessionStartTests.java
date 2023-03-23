package org.example.api.session_start;

import org.example.filebox.api.helpers.ApiHelper;
import org.example.filebox.api.retrofit.models.session_start.SessionStartRequest;
import org.example.filebox.api.retrofit.models.session_start.SessionStartResponse;
import org.example.filebox.db.helpers.DataBaseHelper;
import org.example.filebox.db.jdbc.entities.SessionDao;
import org.example.filebox.helpers.LoggerHelper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static org.example.filebox.api.helpers.ErrorsEnum.ERROR_REQUEST_PARAMETERS;
import static org.example.filebox.helpers.DataHepler.*;
import static org.example.filebox.helpers.LoggerHelper.isEquals;
import static org.example.filebox.helpers.LoggerHelper.isTrue;

public class SessionStartTests {

    private static ApiHelper apiHelper;
    private static DataBaseHelper dataBaseHelper;

    @BeforeAll
    public static void init() {
        Logger logger = LoggerHelper.create(SessionStartTests.class.getName());
        apiHelper = new ApiHelper(logger);
        dataBaseHelper = new DataBaseHelper(logger);
    }

    @Test
    @DisplayName("Создание новой сессии")
    void startSession() throws IOException {
        String webIp = "192.168.1.1";
        String webAgent = "Chrome";

        SessionStartRequest sessionStartRequest = new SessionStartRequest(webIp, webAgent);
        SessionStartResponse sessionStartResponse = apiHelper.sessionStart(sessionStartRequest);
        long timeStartExpected = calculateStartTimeInSec();
        long timeEndExpected = calculateEndTimeInSec(timeStartExpected, 5);

        String idSessionJson = sessionStartResponse.getIdSession();

        SessionDao sessionDao = dataBaseHelper.findSession(idSessionJson);

        isEquals("db_idSessionJson", idSessionJson, sessionDao.getIdSession());
        isEquals("db_webAgent", webAgent, sessionDao.getWebAgent());
        isEquals("db_webIp", webIp, sessionDao.getWebIp());
        isEquals("db_timeStartExpected", timeStartExpected, convertTimeStampToSec(sessionDao.getTimeStart()));
        isEquals("db_timeEndExpected", timeEndExpected, convertTimeStampToSec(sessionDao.getTimeEnd()));
    }

    private static Stream<Arguments> parametersSource() {
        return Stream.of(
                Arguments.of("", "Chrome"),
                Arguments.of("", ""),
                Arguments.of("192.168.1.1", "")
        );
    }

    @ParameterizedTest(name = "{index} => webIp={0}, webAgent={1}")
    @DisplayName("Создание сессии c пустыми параметрами")
    @MethodSource("parametersSource")
    void startSessionNegative1(String webIp, String webAgent) throws IOException {
        int sessionCountBefore = dataBaseHelper.getSessionsCount();

        SessionStartRequest sessionStartRequest = new SessionStartRequest(webIp, webAgent);

        int sessionCountAfter = dataBaseHelper.getSessionsCount();

        isTrue("Errors equal",
                apiHelper.checkErrorResponse(sessionStartRequest, ERROR_REQUEST_PARAMETERS.getValue()));
        isEquals("sessionCount", sessionCountAfter, sessionCountBefore);
    }
}
