package maker.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;


import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import maker.model.*;


public class ControladoraReporte {

    @FXML
    private DatePicker dpSelectorFecha;

    private ControladoraInicio controladoraIni;

    public void setControladoraIni(ControladoraInicio controladoraIni) {
        this.controladoraIni = controladoraIni;
    }

    // Fonts definitions (Definición de fuentes).
    private static final Font chapterFont = FontFactory.getFont(FontFactory.HELVETICA, 30, Font.BOLDITALIC);
    private static final Font paragraphFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);

    private static final Font categoryFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private static final Font subcategoryFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
    private static final Font blueFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);
    private static final Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.BOLD, BaseColor.BLUE);

    private static final String fondoPantalla = "/Users/alexis/Downloads/FinalDS1/fondopdf.png";


    private FacturaBD bdFactura;
    private ClienteBD bdCliente;
    private ItemFacturaBD bdItem;
    private ProductoBD bdProducto;
    private ObservableList<Factura> listaFacturasConsulta = FXCollections.observableArrayList();
    private ObservableList<ItemFactura> listaItems = FXCollections.observableArrayList();
    private double utilidadTotal;

    @FXML
    public void initialize(){
        bdFactura = new FacturaBD();
        bdCliente = new ClienteBD();
        bdItem = new ItemFacturaBD();
        bdProducto = new ProductoBD();
        utilidadTotal = 0;
    }


    public boolean validarFecha(){
        if(!dpSelectorFecha.getEditor().getText().equals("".trim()) && (dpSelectorFecha.getValue().isBefore(LocalDate.now().plusDays(1)))){
            return true;
        }
        return false;
    }



    @FXML
    public void verReporte(){
        if(validarFecha()){
            try{
                listaFacturasConsulta = bdFactura.consultar(dpSelectorFecha.getValue());
                System.out.println("la cantidad de facturas encontradas es: " + listaFacturasConsulta.size() + "\n\n\n");

                if(createPDF(new File("/Users/alexis/Downloads/FinalDS1/ReporteDamasco-" + dpSelectorFecha.getValue().toString() +".pdf"))){
                    controladoraIni.mostrarVentanaExitosa();
                }
                else{
                    controladoraIni.getMainApp().mostrarVentanaError();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        else{
            controladoraIni.getMainApp().mostrarVentanaError();
        }
    }


    public boolean createPDF(File pdfNewFile) {
        // Creamos el documento e indicamos el nombre del fichero.
        try {
            Document document = new Document();
            try {

                PdfWriter.getInstance(document, new FileOutputStream(pdfNewFile));

            } catch (FileNotFoundException fileNotFoundException) {
                System.out.println("No Encontrado "
                        + "(No se encontró el fichero para generar el pdf)" + fileNotFoundException);
                return false;
            }
            document.open();
            // Añadimos los metadatos del PDF
            document.addTitle("Reporte de ventas");
            document.addSubject("Shopping");
            document.addKeywords("Java, PDF");
            document.addAuthor("Damasco Records");
            document.addCreator("Christian Rodriguez");

            // Primera página
            Chunk chunk = new Chunk("Reporte de ventas DAMASCO", chapterFont);
            chunk.setBackground(BaseColor.YELLOW);

            //(Creemos el primer capítulo)
            Chapter chapter = new Chapter(new Paragraph(chunk), 1);
            chapter.setNumberDepth(0);
            chapter.add(new Paragraph("Total ventas realizadas en el dia " + dpSelectorFecha.getValue().toString() + ":", paragraphFont));
            chapter.add(new Paragraph("->", paragraphFont));

            //(Añadimos una imagen)
            Image image = null;
            try {
                image = Image.getInstance(fondoPantalla);
                image.setAbsolutePosition(50, 120);
                chapter.add(image);
            } catch (BadElementException ex) {
                System.out.println("Image BadElementException" +  ex);
                return false;
            } catch (IOException ex) {
                System.out.println("Image IOException " +  ex);
                return false;
            }

            // Utilización de PdfPTable
            ArrayList<String> lista;
            lista = bdItem.listaCompradosFecha(dpSelectorFecha.getValue());

            Integer numColumns = 4;
            Integer numRows = lista.size();

            // Creamos la tabla
            PdfPTable table = new PdfPTable(numColumns);
            table.setWidthPercentage(100);

            // Ahora llenamos la tabla del PDF
            PdfPCell columnHeader;

            // (rellenamos las COLUMNAS de la tabla).
            //columna 1
            columnHeader = new PdfPCell(new Phrase("DESCRIPCION ",smallBold));
            columnHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(columnHeader);

            //columna 2
            columnHeader = new PdfPCell(new Phrase("CANTIDAD DE VENTAS ",smallBold));
            columnHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(columnHeader);

            //columna 3
            columnHeader = new PdfPCell(new Phrase("TOTAL VENTAS ", smallBold));
            columnHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(columnHeader);

            //columna 4
            columnHeader = new PdfPCell(new Phrase("UTILIDAD VENTAS ",smallBold));
            columnHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(columnHeader);

            table.setHeaderRows(1);

            String codigoRef = "";
            int count = 0;

            //(rellenamos las filas de la tabla).
            for (int row = 0; row < numRows; row++) {
                table.addCell(lista.get(row));
                codigoRef = bdItem.codigoRef(lista.get(row));
                count = bdItem.cantidad(dpSelectorFecha.getValue(), lista.get(row));
                Producto productoObj = bdProducto.consultar(codigoRef);
                double totalVentas = count * Double.parseDouble(productoObj.getPrecioVenta());
                double utilidadVentas = count * (Double.parseDouble(productoObj.getPrecioVenta())
                        - Double.parseDouble(productoObj.getCoste()));

                table.addCell(""+count);
                table.addCell(""+totalVentas);
                table.addCell(""+utilidadVentas);
            }


            // (Añadimos la tabla)
            chapter.add(table);

            chapter.add(new Paragraph("->", paragraphFont));



            //organiza los datos encontrados para posteriormente mostrar
            for (int i = 0; i < listaFacturasConsulta.size(); i++) {
                Cliente clienteEncontrado = bdCliente.consultar(listaFacturasConsulta.get(i).getDatosCliente());
                listaItems = bdFactura.subconsultaFactura(listaFacturasConsulta.get(i).getNumeroRef());

                crearFactura(chapter, image, i, clienteEncontrado, listaItems);
            }

            chapter.add(new Paragraph("->", paragraphFont));
            chapter.add(new Paragraph("Utilidad total: $ " + utilidadTotal, paragraphFont));


            // (Añadimos el elemento con la tabla).
            document.add(chapter);
            document.close();

            //se reinicia valor
            utilidadTotal = 0;

            System.out.println("El reporte PDF se ha generado");
            return true;
        } catch (DocumentException documentException) {
            System.out.println("Ha ocurrido un error al generar el archivo PDF " + documentException);
            return false;
        }
    }





    public void crearFactura(Chapter chapter, Image image, int i, Cliente clienteEncontrado, ObservableList<ItemFactura> items){
        chapter.add(new Paragraph("->", paragraphFont));
        chapter.add(image);


        Integer numColumnsFact = 1;
        utilidadTotal += listaFacturasConsulta.get(i).getUtilidad();

        // Creamos la tabla
        PdfPTable tableFact = new PdfPTable(numColumnsFact);
        PdfPCell columnHeaderFact;

        columnHeaderFact = new PdfPCell(new Phrase("FACTURA # " + listaFacturasConsulta.get(i).getNumeroRef() +
                "           " + "VENDEDOR: " + listaFacturasConsulta.get(i).getVendedor().toUpperCase() + "           "
                + "HORA: " + listaFacturasConsulta.get(i).getHora(),smallBold));
        columnHeaderFact.setHorizontalAlignment(Element.ALIGN_CENTER);
        tableFact.addCell(columnHeaderFact);

        tableFact.setHeaderRows(1);

        String contenidoTabla = "Datos cliente: \n" + clienteEncontrado.getNombre() + " " + clienteEncontrado.getApellido()
                + "\n\nIdentificacion: \n" +  clienteEncontrado.getIdentificacion() + "\n\nCelular: \n" +  clienteEncontrado.getTelefono() + "\n\n\nDetalle de la compra: \n";

        for (int j = 0; j < items.size(); j++) {
            contenidoTabla += items.get(j).getDescripcion() + "       $ " + items.get(j).getSubtotal() + "\n";
        }

        contenidoTabla += "\nTotal Compra: $ " + listaFacturasConsulta.get(i).getTotalVenta() + "\n\n" +
        "Utilidad por Compra: $ " +  listaFacturasConsulta.get(i).getUtilidad() + "\n\nMedio de pago: " + listaFacturasConsulta.get(i).getMedioDePago();

        //(rellenamos las filas de la tabla).
        tableFact.addCell(contenidoTabla);
        chapter.add(tableFact);
        listaItems.remove(0,listaItems.size()-1);

    }

}
