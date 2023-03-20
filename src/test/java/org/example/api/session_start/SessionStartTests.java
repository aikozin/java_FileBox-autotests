package org.example.api.session_start;

import org.example.filebox.api.helpers.ApiHelper;
import org.example.filebox.api.retrofit.models.session_start.SessionStartRequest;
import org.example.filebox.api.retrofit.models.session_start.SessionStartResponse;
import org.example.filebox.db.helpers.DataBaseHelper;
import org.example.filebox.db.jdbc.entities.SessionDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.example.filebox.api.helpers.ErrorsEnum.ERROR_REQUEST_PARAMETERS;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SessionStartTests {

    private static ApiHelper apiHelper;
    private static DataBaseHelper dataBaseHelper;

    @BeforeAll
    public static void init() {
        apiHelper = new ApiHelper();
        dataBaseHelper = new DataBaseHelper();
    }

    @Test
    @DisplayName("Создание новой сессии")
    void startSession() throws IOException {
        String webIp = "192.168.1.1";
        String webAgent = "Chrome";

        SessionStartRequest sessionStartRequest = new SessionStartRequest(webIp, webAgent);
        SessionStartResponse sessionStartResponse = apiHelper.sessionStart(sessionStartRequest);
        String idSessionJson = sessionStartResponse.getIdSession();

        SessionDao sessionDao = dataBaseHelper.findSession(idSessionJson);

        Assertions.assertAll(
                () -> assertEquals(idSessionJson, sessionDao.getIdSession()),
                () -> assertEquals(webAgent, sessionDao.getWebAgent()),
                () -> assertEquals(webIp, sessionDao.getWebIp())
        );
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
        Assertions.assertTrue(apiHelper.checkErrorResponse(sessionStartRequest, ERROR_REQUEST_PARAMETERS.getValue()));

        int sessionCountAfter = dataBaseHelper.getSessionsCount();

        Assertions.assertEquals(sessionCountAfter, sessionCountBefore);
    }
}
