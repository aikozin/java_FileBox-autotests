package org.example.filebox.db.jdbc.entities;

import java.sql.Timestamp;

public class SessionDao {

    public final static String ID_SESSION = "id_session";
    public final static String TIME_START = "time_start";
    public final static String TIME_END = "time_end";
    public final static String WEB_IP = "web_ip";
    public final static String WEB_AGENT = "web_agent";
    public final static String MOBILE_IP = "mobile_ip";
    public final static String MOBILE_AGENT = "mobile_agent";
    private String idSession;
    private Timestamp timeStart;
    private Timestamp timeEnd;
    private String webIp;
    private String webAgent;
    private String mobileIp;
    private String mobileAgent;

    public SessionDao(String idSession, Timestamp timeStart, Timestamp timeEnd, String webIp, String webAgent,
                      String mobileIp, String mobileAgent) {
        this.idSession = idSession;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.webIp = webIp;
        this.webAgent = webAgent;
        this.mobileIp = mobileIp;
        this.mobileAgent = mobileAgent;
    }

    @Override
    public String toString() {
        return "SessionDao{" +
                "idSession='" + idSession + '\'' +
                ", timeStart=" + timeStart +
                ", timeEnd=" + timeEnd +
                ", webIp='" + webIp + '\'' +
                ", webAgent='" + webAgent + '\'' +
                ", mobileIp='" + mobileIp + '\'' +
                ", mobileAgent='" + mobileAgent + '\'' +
                '}';
    }

    public String getIdSession() {
        return idSession;
    }

    public void setIdSession(String idSession) {
        this.idSession = idSession;
    }

    public Timestamp getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Timestamp timeStart) {
        this.timeStart = timeStart;
    }

    public Timestamp getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Timestamp timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getWebIp() {
        return webIp;
    }

    public void setWebIp(String webIp) {
        this.webIp = webIp;
    }

    public String getWebAgent() {
        return webAgent;
    }

    public void setWebAgent(String webAgent) {
        this.webAgent = webAgent;
    }

    public String getMobileIp() {
        return mobileIp;
    }

    public void setMobileIp(String mobileIp) {
        this.mobileIp = mobileIp;
    }

    public String getMobileAgent() {
        return mobileAgent;
    }

    public void setMobileAgent(String mobileAgent) {
        this.mobileAgent = mobileAgent;
    }
}
