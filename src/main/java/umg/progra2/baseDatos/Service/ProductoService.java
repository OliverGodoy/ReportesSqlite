package umg.progra2.baseDatos.Service;

import umg.progra2.baseDatos.Dao.ProductoDao;
import umg.progra2.baseDatos.Model.Producto;

import java.util.List;

public class ProductoService {
    private final ProductoDao productoDao = new ProductoDao();

    public void addProducto(Producto producto) {
        productoDao.addProducto(producto);
    }

    public Producto getProducto(int id) {
        return productoDao.getProducto(id);
    }

    public List<Producto> getAllProductos() {
        return productoDao.getAllProductos();
    }

    public void updateProducto(Producto producto) {
        productoDao.updateProducto(producto);
    }

    public boolean deleteProducto(int id) {
        return productoDao.deleteProducto(id);
    }

    public List<Producto> ObtenerTodos(String condicion) {
        return productoDao.ObtenerTodos(condicion);
    }

    public List<Producto> AgrupadosOrdenados(){
        return productoDao.AgrupadosOrdenados();
    }

}