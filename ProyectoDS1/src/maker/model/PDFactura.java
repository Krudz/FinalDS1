package maker.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;

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


public class PDFactura {


    // Fonts definitions (Definición de fuentes).
    private static final Font chapterFont = FontFactory.getFont(FontFactory.HELVETICA, 30, Font.BOLDITALIC);
    private static final Font paragraphFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);
    private static final Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.BOLD, BaseColor.BLUE);

    private static final String fondoPantalla = "/Users/alexis/Downloads/FinalDS1/fondopdf.png";


    private FacturaBD bdFactura;
    private ClienteBD bdCliente;
    private ObservableList<ItemFactura> listaItems = FXCollections.observableArrayList();
    private Factura facturaData;

    public PDFactura(Factura facturaD) {
        facturaData = facturaD;
        bdFactura = new FacturaBD();
        bdCliente = new ClienteBD();
        verReporte();
    }


    public void verReporte() {
        try {
            createPDF(new File("/Users/alexis/Downloads/FinalDS1/FacturaDamasco-" + facturaData.getNumeroRef() + ".pdf"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void createPDF(File pdfNewFile) {
        // Creamos el documento e indicamos el nombre del fichero.
        try {
            Document document = new Document();
            try {
                PdfWriter.getInstance(document, new FileOutputStream(pdfNewFile));

            } catch (FileNotFoundException fileNotFoundException) {
                System.out.println("No Encontrado "
                        + "(No se encontró el fichero para generar el pdf)" + fileNotFoundException);
            }
            document.open();
            // Añadimos los metadatos del PDF
            document.addTitle("Reporte de ventas");
            document.addSubject("Shopping");
            document.addKeywords("Java, PDF");
            document.addAuthor("Damasco Records");
            document.addCreator("Christian Rodriguez");

            // Primera página
            Chunk chunk = new Chunk("               DAMASCO RECORDS         ", chapterFont);
            chunk.setBackground(BaseColor.YELLOW);

            //(Creemos el primer capítulo)
            Chapter chapter = new Chapter(new Paragraph(chunk), 1);
            chapter.setNumberDepth(0);


            //(Añadimos una imagen)
            Image image = null;
            try {
                image = Image.getInstance(fondoPantalla);
                image.setAbsolutePosition(50, 120);
                chapter.add(image);
            } catch (BadElementException ex) {
                System.out.println("Image BadElementException" + ex);
            } catch (IOException ex) {
                System.out.println("Image IOException " + ex);
            }


            //organiza los datos encontrados para posteriormente mostrar
            Cliente clienteEncontrado = bdCliente.consultar(facturaData.getDatosCliente());
            listaItems = bdFactura.subconsultaFactura(facturaData.getNumeroRef());

            chapter.add(new Paragraph("->", paragraphFont));
            chapter.add(image);


            Integer numColumnsFact = 1;

            // Creamos la tabla
            PdfPTable tableFact = new PdfPTable(numColumnsFact);
            PdfPCell columnHeaderFact;

            columnHeaderFact = new PdfPCell(new Phrase("FACTURA # " + facturaData.getNumeroRef() +
                    "           " + "VENDEDOR: " + facturaData.getVendedor().toUpperCase() + "           "
                    + "HORA: " + facturaData.getHora(), smallBold));

            columnHeaderFact.setHorizontalAlignment(Element.ALIGN_CENTER);
            tableFact.addCell(columnHeaderFact);

            tableFact.setHeaderRows(1);

            String contenidoTabla = LocalDate.now().toString() + "\n\nDatos cliente: \n" + clienteEncontrado.getNombre() + " " + clienteEncontrado.getApellido()
                    + "\n\nIdentificacion: \n" + clienteEncontrado.getIdentificacion() + "\n\nCelular: \n" + clienteEncontrado.getTelefono() + "\n\n\nDetalle de la compra: \n";

            for (int j = 0; j < listaItems.size(); j++) {
                contenidoTabla += listaItems.get(j).getDescripcion() + "       $ " + listaItems.get(j).getSubtotal() + "\n";
            }

            contenidoTabla += "\n\nTotal Compra: $ " + facturaData.getTotalVenta() + "\n\n" +
                    "\nMedio de pago: " + facturaData.getMedioDePago();

            //(rellenamos las filas de la tabla).
            tableFact.addCell(contenidoTabla);
            chapter.add(tableFact);


            // (Añadimos el elemento con la tabla).
            document.add(chapter);
            document.close();


            System.out.println("La factura PDF se ha generado");
        } catch (DocumentException documentException) {
            System.out.println("Ha ocurrido un error al generar el archivo PDF " + documentException);
        }
    }


}
