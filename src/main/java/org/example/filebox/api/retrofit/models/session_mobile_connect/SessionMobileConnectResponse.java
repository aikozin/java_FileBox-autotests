package org.example.filebox.api.retrofit.models.session_mobile_connect;

import com.google.gson.annotations.SerializedName;

public class SessionMobileConnectResponse {

    @SerializedName("time_end")
    private String timeEnd;
    @SerializedName("time_start")
    private String timeStart;
    @SerializedName("web_agent")
    private String webAgent;
    @SerializedName("web_ip")
    private String webIp;

    public String getTimeEnd() {
        return timeEnd;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public String getWebAgent() {
        return webAgent;
    }

    public String getWebIp() {
        return webIp;
    }

    @Override
    public String toString() {
        return "SessionMobileConnectResponse{" +
                "timeEnd='" + timeEnd + '\'' +
                ", timeStart='" + timeStart + '\'' +
                ", webAgent='" + webAgent + '\'' +
                ", webIp='" + webIp + '\'' +
                '}';
    }
}
