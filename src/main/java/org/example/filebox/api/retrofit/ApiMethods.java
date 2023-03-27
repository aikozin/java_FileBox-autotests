package org.example.filebox.api.retrofit;

import okhttp3.MultipartBody;
import org.example.filebox.api.retrofit.models.session_mobile_connect.SessionMobileConnectRequest;
import org.example.filebox.api.retrofit.models.session_mobile_connect.SessionMobileConnectResponse;
import org.example.filebox.api.retrofit.models.session_start.SessionStartRequest;
import org.example.filebox.api.retrofit.models.session_start.SessionStartResponse;
import retrofit2.Call;
import retrofit2.http.*;

public interface ApiMethods {

    String BASE_URL = "http://127.0.0.1:5000";

    @POST("/session/start")
    Call<SessionStartResponse> restSessionStart(@Body SessionStartRequest sessionStartRequest);

    @POST("/session/mobile/connect")
    Call<SessionMobileConnectResponse> restSessionMobileConnect(
            @Body SessionMobileConnectRequest sessionMobileConnectRequest);

    @Multipart
    @POST("/data/send")
    Call<Void> restDataSend(@Part MultipartBody.Part file, @Query("id_session") String idSession,
                            @Query("type") String type, @Query("source") String source);

    @POST("/data/send")
    Call<Void> restDataSendWithoutFile(@Query("id_session") String idSession,
                                       @Query("type") String type, @Query("source") String source);
}
