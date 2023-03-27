package org.example.filebox.db.jdbc.entities;

import java.sql.Timestamp;

public class DataDao {

    public final static String ID = "id";
    public final static String ID_SESSION = "id_session";
    public final static String TYPE = "type";
    public final static String FILE_NAME_REAL = "file_name_real";
    public final static String FILE_NAME_FS = "file_name_fs";
    public final static String TIME_BIRTH = "time_birth";
    public final static String TIME_DEATH = "time_death";
    public final static String STATUS = "status";
    public final static String SOURCE = "source";
    private long id;
    private String idSession;
    private String type;
    private String file_name_real;
    private String file_name_fs;
    private Timestamp time_birth;
    private Timestamp time_death;
    private String status;
    private String source;

    public DataDao(long id, String idSession, String type, String file_name_real, String file_name_fs,
                   Timestamp time_birth, Timestamp time_death, String status, String source) {
        this.id = id;
        this.idSession = idSession;
        this.type = type;
        this.file_name_real = file_name_real;
        this.file_name_fs = file_name_fs;
        this.time_birth = time_birth;
        this.time_death = time_death;
        this.status = status;
        this.source = source;
    }

    @Override
    public String toString() {
        return "DataDao{" +
                "id=" + id +
                ", idSession='" + idSession + '\'' +
                ", type='" + type + '\'' +
                ", file_name_real='" + file_name_real + '\'' +
                ", file_name_fs='" + file_name_fs + '\'' +
                ", time_birth=" + time_birth +
                ", time_death=" + time_death +
                ", status='" + status + '\'' +
                ", source='" + source + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIdSession() {
        return idSession;
    }

    public void setIdSession(String idSession) {
        this.idSession = idSession;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFileNameReal() {
        return file_name_real;
    }

    public void setFileNameReal(String file_name_real) {
        this.file_name_real = file_name_real;
    }

    public String getFileNameFs() {
        return file_name_fs;
    }

    public void setFileNameFs(String file_name_fs) {
        this.file_name_fs = file_name_fs;
    }

    public Timestamp getTimeBirth() {
        return time_birth;
    }

    public void setTimeBirth(Timestamp time_birth) {
        this.time_birth = time_birth;
    }

    public Timestamp getTimeDeath() {
        return time_death;
    }

    public void setTimeDeath(Timestamp time_death) {
        this.time_death = time_death;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
