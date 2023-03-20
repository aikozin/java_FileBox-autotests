package org.example.filebox.api.retrofit;

import org.example.filebox.api.retrofit.models.session_start.SessionStartRequest;
import org.example.filebox.api.retrofit.models.session_start.SessionStartResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiMethods {

    String BASE_URL = "http://127.0.0.1:5000";

    @POST("/session/start")
    Call<SessionStartResponse> sessionStart(@Body SessionStartRequest sessionStartRequest);
}
