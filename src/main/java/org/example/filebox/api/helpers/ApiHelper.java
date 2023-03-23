package org.example.filebox.api.helpers;

import okhttp3.ResponseBody;
import org.example.filebox.api.retrofit.ApiMethods;
import org.example.filebox.api.retrofit.RetrofitClient;
import org.example.filebox.api.retrofit.models.session_mobile_connect.SessionMobileConnectRequest;
import org.example.filebox.api.retrofit.models.session_mobile_connect.SessionMobileConnectResponse;
import org.example.filebox.api.retrofit.models.session_start.SessionStartRequest;
import org.example.filebox.api.retrofit.models.session_start.SessionStartResponse;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.logging.Logger;

public class ApiHelper {

    private static ApiMethods apiMethods;
    private final Logger logger;

    public ApiHelper(Logger logger) {
        this.logger = logger;
        apiMethods = RetrofitClient.getInstance().getMyApi();
    }

    /**
     * Метод ручки /session/start
     *
     * @param sessionStartRequest тело с web_agent и web_ip
     * @return тело с id_session
     */
    public SessionStartResponse sessionStart(SessionStartRequest sessionStartRequest) throws IOException {
        Call<SessionStartResponse> call = apiMethods.restSessionStart(sessionStartRequest);
        Response<SessionStartResponse> response = call.execute();
        if (response.isSuccessful() && response.body() != null) {
            logger.info(response.body().toString());
            return response.body();
        } else {
            return null;
        }
    }

    /**
     * Метод ручки /session/mobile/connect
     *
     * @param sessionMobileConnectRequest тело с id_session, mobile_agent и mobile_ip
     * @return тело с time_end, time_start, web_agent и web_ip
     */
    public SessionMobileConnectResponse sessionMobileConnect(SessionMobileConnectRequest sessionMobileConnectRequest)
            throws IOException {
        Call<SessionMobileConnectResponse> call = apiMethods.restSessionMobileConnect(sessionMobileConnectRequest);
        Response<SessionMobileConnectResponse> response = call.execute();
        if (response.isSuccessful() && response.body() != null) {
            logger.info(response.body().toString());
            return response.body();
        } else {
            return null;
        }
    }

    /**
     * Проверка текста ошибки в ответе на запрос
     *
     * @param sessionStartRequest тело запроса
     * @param errorMessage        ожидаемый текст ошибки
     * @return true, если текст ошибки совпал
     */
    public boolean checkErrorResponse(SessionStartRequest sessionStartRequest, String errorMessage)
            throws IOException {
        Call<SessionStartResponse> call = apiMethods.restSessionStart(sessionStartRequest);
        ResponseBody responseBody = call.execute().errorBody();
        assert responseBody != null;
        String errorMessageActual = responseBody.string();
        logger.info(errorMessageActual);
        return errorMessageActual.contains(errorMessage);
    }

    /**
     * Проверка текста ошибки в ответе на запрос
     *
     * @param sessionMobileConnectRequest тело запроса
     * @param errorMessage                ожидаемый текст ошибки
     * @return true, если текст ошибки совпал
     */
    public boolean checkErrorResponse(SessionMobileConnectRequest sessionMobileConnectRequest, String errorMessage)
            throws IOException {
        Call<SessionMobileConnectResponse> call = apiMethods.restSessionMobileConnect(sessionMobileConnectRequest);
        ResponseBody responseBody = call.execute().errorBody();
        assert responseBody != null;
        String errorMessageActual = responseBody.string();
        logger.info(errorMessageActual);
        return errorMessageActual.contains(errorMessage);
    }
}
