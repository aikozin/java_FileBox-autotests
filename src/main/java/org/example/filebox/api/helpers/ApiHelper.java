package org.example.filebox.api.helpers;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.example.filebox.api.retrofit.ApiMethods;
import org.example.filebox.api.retrofit.RetrofitClient;
import org.example.filebox.api.retrofit.models.session_mobile_connect.SessionMobileConnectRequest;
import org.example.filebox.api.retrofit.models.session_mobile_connect.SessionMobileConnectResponse;
import org.example.filebox.api.retrofit.models.session_start.SessionStartRequest;
import org.example.filebox.api.retrofit.models.session_start.SessionStartResponse;
import retrofit2.Call;
import retrofit2.Response;

import java.io.File;
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
     * Методы ручки /session/start
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

    public ResponseBody sessionStartError(SessionStartRequest sessionStartRequest) throws IOException {
        return apiMethods.restSessionStart(sessionStartRequest).execute().errorBody();
    }

    /**
     * Методы ручки /session/mobile/connect
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

    public ResponseBody sessionMobileConnectError(SessionMobileConnectRequest sessionMobileConnectRequest)
            throws IOException {
        return apiMethods.restSessionMobileConnect(sessionMobileConnectRequest).execute().errorBody();
    }

    /**
     * Метод отправки файла на сервер
     */
    public Response<Void> dataSend(File file, String idSession, String type, String source) throws IOException {
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(),
                RequestBody.create(MediaType.parse("multipart/form-data"), file));
        Call<Void> call = apiMethods.restDataSend(filePart, idSession, type, source);
        return call.execute();
    }

    public ResponseBody dataSendErrorWithoutFile(String idSession, String type, String source)
            throws IOException {
        return apiMethods.restDataSendWithoutFile(idSession, type, source).execute().errorBody();
    }

    public ResponseBody dataSendError(File file, String idSession, String type, String source) throws IOException {
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(),
                RequestBody.create(MediaType.parse("multipart/form-data"), file));
        return apiMethods.restDataSend(filePart, idSession, type, source).execute().errorBody();
    }

    /**
     * Проверка текста ошибки в ответе на запрос
     */
    public boolean checkErrorResponse(ResponseBody responseBody, String errorMessage) throws IOException {
        assert responseBody != null;
        String errorMessageActual = responseBody.string();
        logger.info(errorMessageActual);
        return errorMessageActual.contains(errorMessage);
    }
}
