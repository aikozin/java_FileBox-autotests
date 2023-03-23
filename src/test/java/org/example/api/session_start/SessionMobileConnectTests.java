package org.example.api.session_start;

import org.example.filebox.api.helpers.ApiHelper;
import org.example.filebox.api.retrofit.models.session_mobile_connect.SessionMobileConnectRequest;
import org.example.filebox.api.retrofit.models.session_mobile_connect.SessionMobileConnectResponse;
import org.example.filebox.api.retrofit.models.session_start.SessionStartRequest;
import org.example.filebox.api.retrofit.models.session_start.SessionStartResponse;
import org.example.filebox.db.helpers.DataBaseHelper;
import org.example.filebox.db.jdbc.entities.SessionDao;
import org.example.filebox.helpers.LoggerHelper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static org.example.filebox.api.helpers.ErrorsEnum.*;
import static org.example.filebox.helpers.DataHepler.*;
import static org.example.filebox.helpers.LoggerHelper.isEquals;
import static org.example.filebox.helpers.LoggerHelper.isTrue;

public class SessionMobileConnectTests {

    private static ApiHelper apiHelper;
    private static DataBaseHelper dataBaseHelper;
    private final static String WEB_IP = "192.168.1.1";
    private final static String WEB_AGENT = "Chrome";
    private String idSession;


    @BeforeAll
    public static void init() {
        Logger logger = LoggerHelper.create(SessionStartTests.class.getName());
        apiHelper = new ApiHelper(logger);
        dataBaseHelper = new DataBaseHelper(logger);
    }

    @BeforeEach
    public void beforeTests() throws IOException {
        SessionStartRequest sessionStartRequest = new SessionStartRequest(WEB_IP, WEB_AGENT);
        SessionStartResponse sessionStartResponse = apiHelper.sessionStart(sessionStartRequest);
        idSession = sessionStartResponse.getIdSession();
    }

    @Test
    @DisplayName("Подключение MOBILE к сессии с life_time по умолчанию")
    void sessionMobileConnect() throws IOException {
        String mobileIp = "192.168.1.2";
        String mobileAgent = "MobileApp";

        SessionMobileConnectRequest sessionMobileConnectRequest = new SessionMobileConnectRequest(idSession,
                mobileIp, mobileAgent);
        SessionMobileConnectResponse sessionMobileConnectResponse =
                apiHelper.sessionMobileConnect(sessionMobileConnectRequest);
        long timeEndInSecondExpected = calculateEndTimeInSec(calculateStartTimeInSec(), 5);

        isEquals("api_timeEndInSecond",
                timeEndInSecondExpected, convertStringTimeStampToSec(sessionMobileConnectResponse.getTimeEnd()));
        isEquals("api_WEB_IP", WEB_IP, sessionMobileConnectResponse.getWebIp());
        isEquals("api_WEB_AGENT", WEB_AGENT, sessionMobileConnectResponse.getWebAgent());

        SessionDao sessionDao = dataBaseHelper.findSession(idSession);

        isEquals("db_idSession", idSession, sessionDao.getIdSession());
        isEquals("db_timeEndInSecond", timeEndInSecondExpected, convertTimeStampToSec(sessionDao.getTimeEnd()));
        isEquals("db_WEB_IP", WEB_IP, sessionDao.getWebIp());
        isEquals("db_WEB_AGENT", WEB_AGENT, sessionDao.getWebAgent());
        isEquals("db_mobileIp", mobileIp, sessionDao.getMobileIp());
        isEquals("db_mobileAgent", mobileAgent, sessionDao.getMobileAgent());
    }

    @Test
    @DisplayName("Подключение MOBILE к сессии с life_time = infinity")
    void sessionMobileConnectInfinityLife() throws IOException {
        String mobileIp = "192.168.1.2";
        String mobileAgent = "MobileApp";
        String lifeTime = "infinity";

        SessionMobileConnectRequest sessionMobileConnectRequest = new SessionMobileConnectRequest(idSession,
                mobileIp, mobileAgent, lifeTime);
        SessionMobileConnectResponse sessionMobileConnectResponse =
                apiHelper.sessionMobileConnect(sessionMobileConnectRequest);
        long timeEndInSecondExpected = calculateEndTimeInSec(calculateStartTimeInSec(), 365 * 24 * 60);

        isTrue("api_TimeEnd", convertStringTimeStampToSec(sessionMobileConnectResponse.getTimeEnd()) >=
                timeEndInSecondExpected);
        isEquals("api_WEB_IP", WEB_IP, sessionMobileConnectResponse.getWebIp());
        isEquals("api_WEB_AGENT", WEB_AGENT, sessionMobileConnectResponse.getWebAgent());

        SessionDao sessionDao = dataBaseHelper.findSession(idSession);

        isEquals("db_idSession", idSession, sessionDao.getIdSession());
        isTrue("db_TimeEnd", convertTimeStampToSec(sessionDao.getTimeEnd()) >= timeEndInSecondExpected);
        isEquals("db_WEB_IP", WEB_IP, sessionDao.getWebIp());
        isEquals("db_WEB_AGENT", WEB_AGENT, sessionDao.getWebAgent());
        isEquals("db_mobileIp", mobileIp, sessionDao.getMobileIp());
        isEquals("db_mobileAgent", mobileAgent, sessionDao.getMobileAgent());
    }

    @Test
    @DisplayName("Подключение MOBILE к сессии с несуществующим id_session")
    void sessionMobileConnectNegativeIdSession() throws IOException {
        String mobileIp = "192.168.1.2";
        String mobileAgent = "MobileApp";
        idSession = UUID.randomUUID().toString();

        int sessionCountBefore = dataBaseHelper.getSessionsCount();

        SessionMobileConnectRequest sessionMobileConnectRequest = new SessionMobileConnectRequest(idSession,
                mobileIp, mobileAgent);

        int sessionCountAfter = dataBaseHelper.getSessionsCount();

        isTrue("Errors equal", apiHelper.checkErrorResponse(sessionMobileConnectRequest,
                SESSION_WITH_ID_DOES_NOT_EXIST.getValue()));
        isEquals("sessionCount", sessionCountAfter, sessionCountBefore);
    }

    @Test
    @DisplayName("Подключение MOBILE к сессии с несуществующим life_time")
    void sessionMobileConnectNegativeLifeTime() throws IOException {
        String mobileIp = "192.168.1.2";
        String mobileAgent = "MobileApp";
        String lifeTime = "minute";

        int sessionCountBefore = dataBaseHelper.getSessionsCount();

        SessionMobileConnectRequest sessionMobileConnectRequest = new SessionMobileConnectRequest(idSession,
                mobileIp, mobileAgent, lifeTime);

        int sessionCountAfter = dataBaseHelper.getSessionsCount();

        isTrue("Errors equal", apiHelper.checkErrorResponse(sessionMobileConnectRequest,
                LIFE_TIME_DOES_NOT_EXIST.getValue()));
        isEquals("sessionCount", sessionCountAfter, sessionCountBefore);
    }

    private static Stream<Arguments> parametersSource() {
        return Stream.of(
                Arguments.of("", "MobileApp"),
                Arguments.of("", ""),
                Arguments.of("192.168.1.2", "")
        );
    }

    @ParameterizedTest(name = "{index} => mobileIp={0}, mobileAgent={1}")
    @DisplayName("Подключение к сессии c пустыми параметрами")
    @MethodSource("parametersSource")
    void startSessionNegative1(String mobileIp, String mobileAgent) throws IOException {
        int sessionCountBefore = dataBaseHelper.getSessionsCount();

        SessionMobileConnectRequest sessionMobileConnectRequest = new SessionMobileConnectRequest(idSession,
                mobileIp, mobileAgent);

        int sessionCountAfter = dataBaseHelper.getSessionsCount();

        isTrue("Errors equal", apiHelper.checkErrorResponse(sessionMobileConnectRequest,
                ERROR_REQUEST_PARAMETERS.getValue()));
        isEquals("sessionCount", sessionCountAfter, sessionCountBefore);
    }
}
