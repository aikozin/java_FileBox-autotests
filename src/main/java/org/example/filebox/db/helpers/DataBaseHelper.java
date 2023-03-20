package org.example.filebox.db.helpers;

import org.example.filebox.db.jdbc.DBConnector;
import org.example.filebox.db.jdbc.entities.SessionDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.example.filebox.db.jdbc.entities.SessionDao.*;

public class DataBaseHelper {

    private static Statement db;

    public DataBaseHelper() {
        db = DBConnector.createConnection();
    }

    /**
     * Получение сессии по id_session
     *
     * @param idSession id искомой сессии
     * @return запись с искомой сессией
     */
    public SessionDao findSession(String idSession) {
        String query = String.format("select * from session where id_session = '%s'", idSession);
        try {
            ResultSet result = db.executeQuery(query);
            if (result.next()) {
                return new SessionDao(
                        result.getString(ID_SESSION),
                        result.getTimestamp(TIME_START),
                        result.getTimestamp(TIME_END),
                        result.getString(WEB_IP),
                        result.getString(WEB_AGENT),
                        result.getString(MOBILE_IP),
                        result.getString(MOBILE_AGENT)
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось получить записи из таблицы: " + e.getMessage());
        }
        return null;
    }

    /**
     * Получение количества записей в таблице session
     *
     * @return количество записей в таблице session
     */
    public int getSessionsCount() {
        String query = "select count(*) from session";
        try {
            ResultSet result = db.executeQuery(query);
            if (result.next()) {
                return result.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось получить записи из таблицы: " + e.getMessage());
        }
        return 0;
    }
}