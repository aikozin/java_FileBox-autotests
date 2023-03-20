package org.example.filebox.db.jdbc;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnector {

    public static final String HOST = "jdbc:mysql://veshpedcoll.ml:3306/filebox";
    public static final String USER = "user";
    public static final String PASSWORD = "database1604";

    public static Statement createConnection() {
        try {
            return DriverManager.getConnection(HOST, USER, PASSWORD).createStatement();
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось подключиться к базе данных: " + e.getMessage());
        }
    }
}