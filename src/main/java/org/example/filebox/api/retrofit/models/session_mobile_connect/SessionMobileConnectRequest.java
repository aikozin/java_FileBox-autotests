package org.example.filebox.api.retrofit.models.session_mobile_connect;

import com.google.gson.annotations.SerializedName;

public class SessionMobileConnectRequest {

    @SerializedName("id_session")
    private String idSession;
    @SerializedName("infinity")
    private boolean isInfinity;
    @SerializedName("mobile_agent")
    private String mobileAgent;
    @SerializedName("mobile_ip")
    private String mobileIp;

    public SessionMobileConnectRequest(String idSession, String mobileIp, String mobileAgent, boolean isInfinity) {
        this.idSession = idSession;
        this.mobileAgent = mobileAgent;
        this.mobileIp = mobileIp;
        this.isInfinity = isInfinity;
    }

    public SessionMobileConnectRequest(String idSession, String mobileIp, String mobileAgent) {
        this.idSession = idSession;
        this.mobileAgent = mobileAgent;
        this.mobileIp = mobileIp;
    }
}
