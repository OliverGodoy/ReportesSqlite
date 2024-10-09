package umg.progra2.baseDatos.reportes;

import umg.progra2.baseDatos.Model.Producto;
import umg.progra2.baseDatos.Service.ProductoService;

import javax.swing.*;
import java.util.List;

public class pruebas {

    public static void main(String[] args) {
        try{
            //Mostrar productos
            List<Producto> prod = new ProductoService().getAllProductos();

            //Existencias menores a 20
            //List<Producto> prod = new ProductoService().ObtenerTodos("existencia < 20");

            //Pais especifico
            //List<Producto> prod = new ProductoService().ObtenerTodos("origen = 'Alemania'");

            //Precios mayores a 2000
            //List<Producto> prod = new ProductoService().ObtenerTodos("precio > 2000");

            //Agrupados por pais y ordenados por precio
            //List<Producto> prod = new ProductoService().AgrupadosOrdenados();

            new PdfReport().generateProductReport(prod,"C:\\Lib\\reporteLab3.pdf");
            //mostrar un mensaje de que se genero el reporte
            //con jpanel
            JOptionPane.showMessageDialog(null, "Reporte generado en C:\\Lib\\reporteLab3.pdf");
        }
        catch(Exception e){
            System.out.println("Error: " +e);
        }
    }
}
