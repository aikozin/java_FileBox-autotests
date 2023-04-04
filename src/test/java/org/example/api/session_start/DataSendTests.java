package org.example.api.session_start;

import okhttp3.ResponseBody;
import org.example.filebox.api.helpers.ApiHelper;
import org.example.filebox.api.retrofit.models.session_start.SessionStartRequest;
import org.example.filebox.api.retrofit.models.session_start.SessionStartResponse;
import org.example.filebox.db.helpers.DataBaseHelper;
import org.example.filebox.db.jdbc.entities.DataDao;
import org.example.filebox.helpers.FileHelper;
import org.example.filebox.helpers.LoggerHelper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static org.example.filebox.api.helpers.ErrorsEnum.*;
import static org.example.filebox.helpers.DataHepler.*;
import static org.example.filebox.helpers.LoggerHelper.*;

public class DataSendTests {

    private static ApiHelper apiHelper;
    private static DataBaseHelper dataBaseHelper;
    private static FileHelper fileHelper;
    private final static String WEB_IP = "192.168.1.1";
    private final static String WEB_AGENT = "Chrome";
    private String idSession;
    private File file;

    @BeforeAll
    public static void init() {
        Logger logger = LoggerHelper.create(DataSendTests.class.getName());
        apiHelper = new ApiHelper(logger);
        dataBaseHelper = new DataBaseHelper(logger);
        fileHelper = new FileHelper(logger);
    }

    @BeforeEach
    public void beforeTests() throws IOException {
        SessionStartRequest sessionStartRequest = new SessionStartRequest(WEB_IP, WEB_AGENT);
        SessionStartResponse sessionStartResponse = apiHelper.sessionStart(sessionStartRequest);
        idSession = sessionStartResponse.getIdSession();
    }

    @AfterEach
    public void afterTests() {
        if (file != null) {
            isTrue("Delete file", file.delete());
        }
    }

    private static Stream<Arguments> parametersSource1() {
        return Stream.of(
                Arguments.of("file.txt", "file", "MOBILE", 1),
                Arguments.of("image.jpeg", "file", "WEB", 95),
                Arguments.of("info.txt", "text", "WEB", 1),
                Arguments.of("doc.pdf", "file", "WEB", 1)
        );
    }

    @ParameterizedTest(name = "{index} => fileName={0}, type={1}, source={2}, sizeInMb={3}")
    @DisplayName("Загрузка файла")
    @MethodSource("parametersSource1")
    void dataSend(String fileName, String type, String source, int sizeInMb) throws IOException {
        file = fileHelper.createFile(fileName, sizeInMb);

        isTrue("File send successful", apiHelper.dataSend(file, idSession, type, source).isSuccessful());
        long timeStartExpected = calculateStartTimeInSec();
        long timeEndExpected = calculateEndTimeInSec(timeStartExpected, 5);

        DataDao dataDao = dataBaseHelper.findData(idSession, fileName);

        isEquals("db_idSession", idSession, dataDao.getIdSession());
        isEquals("db_type", type, dataDao.getType());
        isEquals("db_fileName", fileName, dataDao.getFileNameReal());
        isEqualsTime("db_timeStartExpected", timeStartExpected, convertTimeStampToSec(dataDao.getTimeBirth()));
        isEqualsTime("db_timeEndExpected", timeEndExpected, convertTimeStampToSec(dataDao.getTimeDeath()));
        isEquals("db_source", source, dataDao.getSource());
    }

    @Test
    @DisplayName("Запрос без файла")
    void dataSendWithoutFile() throws IOException {
        int dataCountBefore = dataBaseHelper.getDataCount();

        ResponseBody responseBody = apiHelper.dataSendErrorWithoutFile(idSession, "file", "MOBILE");

        int dataCountAfter = dataBaseHelper.getDataCount();

        isTrue("Errors equal", apiHelper.checkErrorResponse(responseBody,
                FILE_MISSING_OR_ERROR_GETTING_FILE.getValue()));
        isEquals("dataCount", dataCountBefore, dataCountAfter);
    }

    @Test
    @DisplayName("Запрос с несуществующим id_session")
    void dataSendNegativeIdSession() throws IOException {
        file = fileHelper.createFile("file.txt", 1);
        idSession = UUID.randomUUID().toString();

        int dataCountBefore = dataBaseHelper.getDataCount();

        ResponseBody responseBody = apiHelper.dataSendError(file, idSession, "file", "MOBILE");

        int dataCountAfter = dataBaseHelper.getDataCount();

        isTrue("Errors equal", apiHelper.checkErrorResponse(responseBody,
                SESSION_WITH_ID_DOES_NOT_EXIST.getValue()));
        isEquals("dataCount", dataCountBefore, dataCountAfter);
    }

    private static Stream<Arguments> parametersSource2() {
        return Stream.of(
                Arguments.of("", "file", "MOBILE"),
                Arguments.of("123", "", "MOBILE"),
                Arguments.of("123", "file", "")
        );
    }

    @ParameterizedTest(name = "{index} => idSession={0}, typeFile={1}, source={2}")
    @DisplayName("Подключение к сессии c пустыми параметрами")
    @MethodSource("parametersSource2")
    void dataSendNegative(String idSession, String type, String source) throws IOException {
        file = fileHelper.createFile("file.txt", 1);

        int dataCountBefore = dataBaseHelper.getDataCount();

        ResponseBody responseBody = apiHelper.dataSendError(file, idSession, type, source);

        int dataCountAfter = dataBaseHelper.getDataCount();

        isTrue("Errors equal", apiHelper.checkErrorResponse(responseBody,
                ERROR_REQUEST_PARAMETERS.getValue()));
        isEquals("dataCount", dataCountBefore, dataCountAfter);
    }

    @Test
    @DisplayName("Запрос с несуществующим source")
    void dataSendNegativeSource() throws IOException {
        file = fileHelper.createFile("file.txt", 1);

        int dataCountBefore = dataBaseHelper.getDataCount();

        ResponseBody responseBody = apiHelper.dataSendError(file, idSession, "file", "QWERTY");

        int dataCountAfter = dataBaseHelper.getDataCount();

        isTrue("Errors equal", apiHelper.checkErrorResponse(responseBody,
                INVALID_FILE_SOURCE_TYPE.getValue()));
        isEquals("dataCount", dataCountBefore, dataCountAfter);
    }

    @Test
    @DisplayName("Отправка файла неразрешенного размера")
    void dataSendNegativeFile() throws IOException {
        file = fileHelper.createFile("file.txt", 101);

        int dataCountBefore = dataBaseHelper.getDataCount();

        ResponseBody responseBody = apiHelper.dataSendError(file, idSession, "file", "WEB");

        int dataCountAfter = dataBaseHelper.getDataCount();

        isTrue("Errors equal", apiHelper.checkErrorResponse(responseBody, ""));
        isEquals("dataCount", dataCountBefore, dataCountAfter);
    }
}
