package org.example.filebox.api.retrofit.models.session_start;

import com.google.gson.annotations.SerializedName;

public class SessionStartRequest {

    @SerializedName("web_ip")
    private String webIp;
    @SerializedName("web_agent")
    private String webAgent;

    public SessionStartRequest(String webIp, String webAgent) {
        this.webIp = webIp;
        this.webAgent = webAgent;
    }

}
