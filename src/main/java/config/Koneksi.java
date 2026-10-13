package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Koneksi {

    private static final String URL = "jdbc:mysql://localhost:3306/db_rq_alhafizh";
    private static final String USER = "root";
    private static final String PASS = "";

    private Koneksi() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
