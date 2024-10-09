package umg.progra2.formulario;

import umg.progra2.baseDatos.Model.Producto;
import umg.progra2.baseDatos.Service.ProductoService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class frmProducto {
    private JPanel JframePdroducto;
    private JLabel lblTitulo;
    private JLabel lblCodigo;
    private JTextField textFieldIdProducto;
    private JLabel lblDescripcion;
    private JTextField textFieldDescripcion;
    private JLabel lblOrigen;
    private JComboBox comboBoxOrigen;
    private JButton buttonGrabar;
    private JButton buttonBuscar;
    private JButton buttonActualizar;
    private JButton buttonEliminar;
    private JLabel lbExistencia;
    private JTextField textFieldExistencia;
    private JLabel lblPrecio;
    private JTextField textFieldPrecio;

    public frmProducto() {
        comboBoxOrigen.addItem("México");
        comboBoxOrigen.addItem("EE.UU.");
        comboBoxOrigen.addItem("China");
        comboBoxOrigen.addItem("Suiza");
        comboBoxOrigen.addItem("Japón");
        comboBoxOrigen.addItem("Italia");
        comboBoxOrigen.addItem("Alemania");
        comboBoxOrigen.addItem("Polonia");
        comboBoxOrigen.addItem("Corea del Sur");
        comboBoxOrigen.addItem("Taiwán");
        comboBoxOrigen.addItem("India");
        comboBoxOrigen.addItem("Francia");
        comboBoxOrigen.addItem("Canadá");
        comboBoxOrigen.addItem("Brasil");
        comboBoxOrigen.addItem("Colombia");
        comboBoxOrigen.addItem("España");
        comboBoxOrigen.addItem("Guatemala");
        comboBoxOrigen.addItem("Estados Unidos");


        buttonGrabar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               // JOptionPane.showMessageDialog(null, "Descrip" + textFieldDescripcion.getText());
                //textFieldDescripcion.setText("b");
                Producto producto = new Producto();

                producto.setDescripcion(textFieldDescripcion.getText());
                producto.setOrigen(comboBoxOrigen.getSelectedItem().toString());
                producto.setPrecio(Double.parseDouble(textFieldPrecio.getText()));
                producto.setExistencia(Integer.parseInt(textFieldExistencia.getText()));


                try{
                    new ProductoService().addProducto(producto);
                    JOptionPane.showMessageDialog(null, "Producto creado exitosamente");
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(null, "Ups hay clavo con la db:" +ex.getMessage());
                }
            }
        });
        buttonBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //buscar
                int id = textFieldIdProducto.getText().isEmpty() ? 0 : Integer.parseInt(textFieldIdProducto.getText());  // ? = ternario
                try{
                    Producto productoEncontrado = new ProductoService().getProducto(id);
                    if(productoEncontrado != null){
                        textFieldIdProducto.setText(String.valueOf(productoEncontrado.getIdProducto()));
                        textFieldDescripcion.setText(productoEncontrado.getDescripcion());
                        comboBoxOrigen.setSelectedItem(productoEncontrado.getOrigen());
                        textFieldPrecio.setText(String.valueOf(productoEncontrado.getPrecio()));
                        textFieldExistencia.setText(String.valueOf(productoEncontrado.getExistencia()));
                    }else{
                        JOptionPane.showMessageDialog(null, "No se encontro el producto");
                        return;
                    }

                }catch(Exception ex){
                    JOptionPane.showMessageDialog(null, "Hay erro en db:" +ex.getMessage());
                }
            }
        });
        buttonActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //boton de actualizar informacion
                Producto producto = new Producto();

                producto.setIdProducto(Integer.parseInt(textFieldIdProducto.getText()));
                producto.setDescripcion(textFieldDescripcion.getText());
                producto.setOrigen(comboBoxOrigen.getSelectedItem().toString());
                producto.setPrecio(Double.parseDouble(textFieldPrecio.getText()));
                producto.setExistencia(Integer.parseInt(textFieldExistencia.getText()));


                try{
                    new ProductoService().updateProducto(producto);
                    JOptionPane.showMessageDialog(null, "Producto Actualizado exitosamente");
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(null, "Ups hay clavo con la db:" +ex.getMessage());
                }
            }
        });
        buttonEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = textFieldIdProducto.getText().isEmpty() ? 0 : Integer.parseInt(textFieldIdProducto.getText());
                try {
                    // Llamar al servicio para eliminar los datos
                    boolean eliminado = new ProductoService().deleteProducto(id);

                    if (eliminado) {
                        JOptionPane.showMessageDialog(null, "Producto eliminado exitosamente");

                        textFieldIdProducto.setText("");
                        textFieldDescripcion.setText("");
                        comboBoxOrigen.removeAllItems();
                        textFieldExistencia.setText("");
                        textFieldPrecio.setText("");

                    } else {
                        JOptionPane.showMessageDialog(null, "No se encontró el producto para eliminar");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al eliminar el producto: " + ex.getMessage());
                }
            }
        });
    }



    public static void main(String[] args) {
        JFrame frame = new JFrame("frmProducto");
        frame.setContentPane(new frmProducto().JframePdroducto);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
