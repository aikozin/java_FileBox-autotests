package org.example.filebox.api.helpers;

import okhttp3.ResponseBody;
import org.example.filebox.api.retrofit.ApiMethods;
import org.example.filebox.api.retrofit.RetrofitClient;
import org.example.filebox.api.retrofit.models.session_start.SessionStartRequest;
import org.example.filebox.api.retrofit.models.session_start.SessionStartResponse;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

public class ApiHelper {

    private static ApiMethods apiMethods;

    public ApiHelper() {
        apiMethods = RetrofitClient.getInstance().getMyApi();
    }

    /**
     * Метод ручки /session/start
     *
     * @param sessionStartRequest тело с web_agent и web_ip
     * @return тело с id_session
     */
    public SessionStartResponse sessionStart(SessionStartRequest sessionStartRequest) throws IOException {
        Call<SessionStartResponse> call = apiMethods.sessionStart(sessionStartRequest);
        Response<SessionStartResponse> response = call.execute();
        if (response.isSuccessful() && response.body() != null) {
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
    public boolean checkErrorResponse(SessionStartRequest sessionStartRequest, String errorMessage) throws IOException {
        Call<SessionStartResponse> call = apiMethods.sessionStart(sessionStartRequest);
        ResponseBody responseBody = call.execute().errorBody();
        assert responseBody != null;
        return responseBody.string().contains(errorMessage);
    }
}
