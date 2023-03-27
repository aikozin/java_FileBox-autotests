package org.example.filebox.db.helpers;

import org.example.filebox.db.jdbc.DBConnector;
import org.example.filebox.db.jdbc.entities.DataDao;
import org.example.filebox.db.jdbc.entities.SessionDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

import static org.example.filebox.db.jdbc.entities.SessionDao.*;
import static org.example.filebox.db.jdbc.entities.DataDao.*;

public class DataBaseHelper {

    private static Statement db;
    private final Logger logger;

    public DataBaseHelper(Logger logger) {
        this.logger = logger;
        db = DBConnector.createConnection();
    }

    /**
     * Получение сессии по id_session
     */
    public SessionDao findSession(String idSession) {
        String query = String.format("select * from session where id_session = '%s'", idSession);
        try {
            ResultSet result = db.executeQuery(query);
            if (result.next()) {
                SessionDao sessionDao = new SessionDao(result.getString(SessionDao.ID_SESSION),
                        result.getTimestamp(TIME_START), result.getTimestamp(TIME_END), result.getString(WEB_IP),
                        result.getString(WEB_AGENT), result.getString(MOBILE_IP), result.getString(MOBILE_AGENT));
                logger.info(sessionDao.toString());
                return sessionDao;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось получить записи из таблицы: " + e.getMessage());
        }
        return null;
    }

    /**
     * Получение количества записей в таблице session
     */
    public int getSessionsCount() {
        String query = "select count(*) from session";
        try {
            ResultSet result = db.executeQuery(query);
            if (result.next()) {
                logger.info("Count rows in session table = " + result.getInt(1));
                return result.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось получить записи из таблицы: " + e.getMessage());
        }
        return 0;
    }

    /**
     * Получение файла по idSession и fileName
     */
    public DataDao findData(String idSession, String fileName) {
        String query = String.format("select * from data where id_session = '%s' and file_name_real = '%s'", idSession,
                fileName);
        try {
            ResultSet result = db.executeQuery(query);
            if (result.next()) {
                DataDao dataDao = new DataDao(result.getLong(ID), result.getString(DataDao.ID_SESSION),
                        result.getString(TYPE), result.getString(FILE_NAME_REAL), result.getString(FILE_NAME_FS),
                        result.getTimestamp(TIME_BIRTH), result.getTimestamp(TIME_DEATH), result.getString(STATUS),
                        result.getString(SOURCE));
                logger.info(dataDao.toString());
                return dataDao;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось получить записи из таблицы: " + e.getMessage());
        }
        return null;
    }

    /**
     * Получение количества записей в таблице data
     */
    public int getDataCount() {
        String query = "select count(*) from data";
        try {
            ResultSet result = db.executeQuery(query);
            if (result.next()) {
                logger.info("Count rows in data table = " + result.getInt(1));
                return result.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось получить записи из таблицы: " + e.getMessage());
        }
        return 0;
    }

}