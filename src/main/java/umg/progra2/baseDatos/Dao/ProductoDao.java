package umg.progra2.baseDatos.Dao;

import umg.progra2.baseDatos.Conexion.Conexion;
import umg.progra2.baseDatos.Model.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDao {

    public void addProducto(Producto producto) {
        String query = "INSERT INTO tb_producto (descripcion, origen, precio, existencia) VALUES (?, ? , ?, ?)";

        try (Connection connection = Conexion.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, producto.getDescripcion());
            statement.setString(2, producto.getOrigen());
            statement.setDouble(3, producto.getPrecio());
            statement.setInt(4, producto.getExistencia());


            connection.setAutoCommit(false); // Desactivar autocommit

            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                connection.commit(); // Hacer commit de los cambios
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        producto.setIdProducto(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Producto getProducto(int id) {
        String query = "SELECT * FROM tb_producto WHERE id = ?";
        Producto producto = null;

        try (Connection connection = Conexion.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            System.out.println("Buscando producto con ID: " + id); // Mensaje de depuración
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    producto = new Producto(
                            resultSet.getInt("id"),
                            resultSet.getString("descripcion"),
                            resultSet.getString("origen"),
                            resultSet.getDouble("precio"),
                            resultSet.getInt("existencia")
                    );
                }else {
                    System.out.println("No se encontró ningún producto con el ID: " + id);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return producto;
    }

    public List<Producto> getAllProductos() {
        List<Producto> productos = new ArrayList<>();
        String query = "SELECT * FROM tb_producto order by id";

        try (Connection connection = Conexion.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                productos.add(new Producto(
                        resultSet.getInt("id"),
                        resultSet.getString("descripcion"),
                        resultSet.getString("origen"),
                        resultSet.getDouble("precio"),
                        resultSet.getInt("existencia")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productos;
    }

    public List<Producto> AgrupadosOrdenados(){
        List<Producto> productos = new ArrayList<>();
        String query = "SELECT origen, AVG(precio) AS promedio_precio, SUM(existencia) AS total_existencia" + " FROM tb_producto GROUP BY origen ORDER BY promedio_precio DESC";

        try (Connection connection = Conexion.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                productos.add(new Producto(
                        0, // Suponiendo que id no es aplicable aquí
                        "", // Descripción no disponible en la consulta
                        resultSet.getString("origen"),
                        resultSet.getDouble("promedio_precio"),
                        resultSet.getInt("total_existencia") // Esto debería ser una suma de existencias
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productos;
    }

    public List<Producto> ObtenerTodos(String condicion) {
        List<Producto> productos = new ArrayList<>();
        String query = "select * from tb_producto where "+condicion;

        try (Connection connection = Conexion.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                productos.add(new Producto(
                        resultSet.getInt("id"),
                        resultSet.getString("descripcion"),
                        resultSet.getString("origen"),
                        resultSet.getDouble("precio"),
                        resultSet.getInt("existencia")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productos;
    }



    public void updateProducto(Producto producto) {
        String query = "UPDATE tb_producto SET descripcion = ?, origen = ?, precio = ?, existencia = ? WHERE id = ?";

        try (Connection connection = Conexion.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Asigna los parámetros en el orden correcto:
            statement.setString(1, producto.getDescripcion());  // descripción
            statement.setString(2, producto.getOrigen());       // origen
            statement.setDouble(3, producto.getPrecio());       // precio
            statement.setInt(4, producto.getExistencia());      // existencia
            statement.setInt(5, producto.getIdProducto());      // id_producto (en el WHERE)

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean deleteProducto(int id) {
        String query = "DELETE FROM tb_producto WHERE id = ?";

        try (Connection connection = Conexion.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarCondicional(int id) throws SQLException {
        String sqlCheck = "SELECT precio FROM tb_producto WHERE id_producto = ?";
        String sqlDelete = "DELETE FROM tb_producto WHERE id_producto = ? AND precio = 0.00";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement pstmtCheck = conn.prepareStatement(sqlCheck);
             PreparedStatement pstmtDelete = conn.prepareStatement(sqlDelete)) {

            // Primero, verificamos el precio del producto
            pstmtCheck.setInt(1, id);
            try (ResultSet rs = pstmtCheck.executeQuery()) {
                if (rs.next()) {
                    double precio = rs.getDouble("precio");
                    if (precio != 0.00) {
                        // El precio no es 0.00, no se puede eliminar
                        return false;
                    }
                } else {
                    // El producto no existe
                    return false;
                }
            }

            // Si llegamos aquí, el precio es 0.00, procedemos con la eliminación
            pstmtDelete.setInt(1, id);
            int rowsAffected = pstmtDelete.executeUpdate();

            // Si se eliminó al menos una fila, consideramos que fue exitoso
            return rowsAffected > 0;
        }
    } // fin de eliminarCondicional

}