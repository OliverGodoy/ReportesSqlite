package umg.progra2.baseDatos.Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
//    private static final String URL = "jdbc:mysql://localhost:3306/db_telebot";
//    private static final String USER = "root";
//    private static final String PASSWORD = "Lg_(098765)";

    private static final String URL = "jdbc:sqlite:db_telebot.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}

