package ru.etu.components;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcRunner {

    //  Database credentials
    static final String DB_URL = "jdbc:postgresql://localhost/javaBD";
    static final String USER = "postgres";
    static final String PASS = "pass";

    public static Connection getConn() {

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Properties info = new Properties();
        info.setProperty("user", USER);
        info.setProperty("password", PASS);
        info.setProperty("useUnicode", "true");
        info.setProperty("characterEncoding", "utf8");
        //URL к базе состоит из протокола:подпротокола://[хоста]:[порта_СУБД]/[БД] и других_сведений

        Connection connection = null;
        try {
            //Создаём соединение
            connection = DriverManager.getConnection(DB_URL, info);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
