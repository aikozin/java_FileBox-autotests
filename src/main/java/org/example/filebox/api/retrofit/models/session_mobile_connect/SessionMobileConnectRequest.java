package org.example.filebox.api.retrofit.models.session_mobile_connect;

import com.google.gson.annotations.SerializedName;

public class SessionMobileConnectRequest {

    @SerializedName("id_session")
    private String idSession;
    @SerializedName("life_time")
    private String lifeTime;
    @SerializedName("mobile_agent")
    private String mobileAgent;
    @SerializedName("mobile_ip")
    private String mobileIp;

    public SessionMobileConnectRequest(String idSession, String mobileIp, String mobileAgent, String lifeTime) {
        this.idSession = idSession;
        this.mobileAgent = mobileAgent;
        this.mobileIp = mobileIp;
        this.lifeTime = lifeTime;
    }

    public SessionMobileConnectRequest(String idSession,  String mobileIp, String mobileAgent) {
        this.idSession = idSession;
        this.mobileAgent = mobileAgent;
        this.mobileIp = mobileIp;
    }
}
