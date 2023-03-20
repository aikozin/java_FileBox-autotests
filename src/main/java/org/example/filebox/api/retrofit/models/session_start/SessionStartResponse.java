package org.example.filebox.api.retrofit.models.session_start;

import com.google.gson.annotations.SerializedName;

public class SessionStartResponse {

    @SerializedName("id_session")
    private String idSession;

    public String getIdSession() {
        return idSession;
    }
}
