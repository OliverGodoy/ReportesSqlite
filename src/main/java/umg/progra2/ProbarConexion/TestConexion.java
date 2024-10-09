package umg.progra2.ProbarConexion;

import umg.progra2.baseDatos.Conexion.Conexion;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TestConexion {
    public static void main(String[] args) {

        //Crear la tabla
//        try (Connection conn = Conexion.getConnection()) {
//            if (conn != null) {
//                System.out.println("Conexión exitosa a SQLite.");
//
//                // Intentar realizar una operación básica
//                String sql = "CREATE TABLE IF NOT EXISTS tb_producto ("
//                        + " id INTEGER PRIMARY KEY AUTOINCREMENT,"
//                        + " descripcion TEXT NOT NULL,"
//                        + " origen TEXT,"
//                        + " precio REAL NOT NULL,"
//                        + " existencia INTEGER NOT NULL"
//                        + ");";
//
//                try (Statement stmt = conn.createStatement()) {
//                    stmt.execute(sql);
//                    System.out.println("Tabla 'tb_producto' creada o ya existe.");
//                }
//            }
//        } catch (SQLException e) {
//            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
//        }

        //Ingresar datos a la tabla
        try (Connection conn = Conexion.getConnection();
             Statement stmt = conn.createStatement()) {

            // Insertar 20 registros en la tabla tb_prueba
            String sql = "INSERT INTO tb_producto (descripcion, origen, precio, existencia) VALUES ('Carro', 'Japón', 15000.99, 2);";

            stmt.executeUpdate(sql);
            System.out.println("20 registros insertados en la tabla 'tb_productos'.");

        } catch (SQLException e) {
            System.out.println("Error al insertar datos: " + e.getMessage());
        }

    }
}
