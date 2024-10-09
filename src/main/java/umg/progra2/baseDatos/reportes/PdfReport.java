package umg.progra2.baseDatos.reportes;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import umg.progra2.baseDatos.Model.Producto;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

public class PdfReport {
    //Fuentes del documento
    private static final Font TITLE_FONT = new Font(Font.FontFamily.UNDEFINED, 20, Font.ITALIC);
    private static final Font HEADER_FONT = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
    private static final Font NORMAL_FONT = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);

    public void generateProductReport(List<Producto> productos, String outputPath) throws DocumentException, IOException {
        Document document = new Document(PageSize.A3); //tamaño del documento
        PdfWriter.getInstance(document, new FileOutputStream(outputPath)); //Crea el archivo
        document.open();

        addTitle(document);
        addProductTable(document, productos); //agrega todos los productos a la tabla

        document.close();
    }

    private void addTitle(Document document) throws DocumentException {
        Paragraph title = new Paragraph("Reportes Laboratorio 3", TITLE_FONT); //Parrafo
        title.setAlignment(Element.ALIGN_JUSTIFIED);
        document.add(title);
        document.add(Chunk.NEWLINE);//Salto de linea
    }

    //Agregar mas columnas
    private void addProductTable(Document document, List<Producto> productos) throws DocumentException {
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100); //Procentaje de ancho
        addTableHeader(table); //encabezado
        addRows(table, productos);
        document.add(table);
    }

    //Se utiliza cuando se agrega columna
    private void addTableHeader(PdfPTable table) {
        // Este código utiliza la clase Stream de Java para crear un flujo de datos con los títulos de las columnas de una tabla PDF. Luego,
        // para cada título de columna, se crea una celda de encabezado en la tabla con ciertas propiedades (color de fondo, ancho del borde, y texto).



        Stream.of("ID", "Descripción", "Origen","Precio","Existencia", "Total")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.CYAN);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle, HEADER_FONT));
                    table.addCell(header); //Agrega celda al encabezado
                });

        //Stream.of("ID", "Descripción", "Origen", "Precio"):
        //Crea un flujo (Stream) de datos con los elementos "ID", "Descripción", "Origen", y "Precio".


//        Este código utiliza la clase Stream de Java para crear un flujo de datos con los títulos de las columnas de una tabla PDF. Luego, para cada título de columna, se crea una celda de encabezado en la tabla con ciertas propiedades (color de fondo, ancho del borde, y texto).
//                Explicación del Código
//        Stream.of("ID", "Descripción", "Origen", "Precio"):
//        Crea un flujo (Stream) de datos con los elementos "ID", "Descripción", "Origen", y "Precio".
//.forEach(columnTitle -> { ... }):
//        Para cada elemento en el flujo (en este caso, cada título de columna), ejecuta el bloque de código dentro de las llaves { ... }.
//        Dentro del bloque forEach:
//        PdfPCell header = new PdfPCell();: Crea una nueva celda para la tabla PDF.
//        header.setBackgroundColor(BaseColor.LIGHT_GRAY);: Establece el color de fondo de la celda a gris claro.
//                header.setBorderWidth(2);: Establece el ancho del borde de la celda a 2 puntos.
//                header.setPhrase(new Phrase(columnTitle, HEADER_FONT));: Establece el texto de la celda con el título de la columna y la fuente de encabezado.
//                table.addCell(header);: Añade la celda a la tabla.
//¿Qué es Stream.of?
//                Stream.of es un método estático de la clase Stream en Java que se utiliza para crear un flujo (Stream) a partir de una secuencia de elementos. En este caso, se está utilizando para crear un flujo de cadenas de texto ("ID", "Descripción", "Origen", "Precio").
//        Código

    } //Fin de addTableHeader

    private void addRows(PdfPTable table, List<Producto> productos) {
        double totalPrecio = 0.0; // Suma total de precios
        int totalExistencia = 0;// Suma total de existencias
        double precioTotal = 0.0;

        for (Producto producto : productos) {
            table.addCell(new Phrase(String.valueOf(producto.getIdProducto()), NORMAL_FONT));
            table.addCell(new Phrase(producto.getDescripcion(), NORMAL_FONT));
            table.addCell(new Phrase(String.valueOf(producto.getOrigen()), NORMAL_FONT));
            table.addCell(new Phrase(String.valueOf(producto.getPrecio()), NORMAL_FONT));
            table.addCell(new Phrase(String.valueOf(producto.getExistencia()), NORMAL_FONT));
            table.addCell(new Phrase(String.valueOf(producto.getTotal()), NORMAL_FONT));

            // Acumula los totales
            totalPrecio += producto.getPrecio();
            totalExistencia += producto.getExistencia();
            precioTotal += producto.getTotal();
        }

        // Después de agregar todos los productos, agrega una fila para los totales
        table.addCell(new Phrase("Total:", NORMAL_FONT)); // Celda para el total
        table.addCell(new Phrase("", NORMAL_FONT)); // Celda vacía para la descripción
        table.addCell(new Phrase("", NORMAL_FONT)); // Celda vacía para el origen
        table.addCell(new Phrase(String.valueOf(totalPrecio), NORMAL_FONT)); // Total de precios
        table.addCell(new Phrase(String.valueOf(totalExistencia), NORMAL_FONT)); // Total de existencias
        table.addCell(new Phrase(String.valueOf(precioTotal), NORMAL_FONT));
    }
}
