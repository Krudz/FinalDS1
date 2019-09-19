package maker.view;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import maker.model.Producto;
import maker.model.ProductoBD;
import maker.model.ProveedorBD;

public class ControladoraInventarioAddProducto {

    @FXML
    private TextField tfCodigo;
    @FXML
    private TextField tfCantidad;
    @FXML
    private TextField tfArtista;
    @FXML
    private TextField tfAlbum;
    @FXML
    private TextField tfCoste;
    @FXML
    private TextField tfPrecioVenta;
    @FXML
    private TextField tfProveedor;
    @FXML
    private TextField tfTipo;
    @FXML
    private TextField tfGenero;



    //************************ se crea obj de tipo controladora inicio
    private ControladoraInicio controladoraIni;

    public ControladoraInicio getControladoraIni() {
        return controladoraIni;
    }

    public void setControladoraIni(ControladoraInicio controladoraIni) {
        this.controladoraIni = controladoraIni;
    }
    //************************

    private ProductoBD bdProducto;
    private ProveedorBD bdProveedor;

    //cuando se envia un producto a la ventana de editar producto
    private boolean isForEditar;

    //si el producto a editar esta inactivo
    private boolean isInactivo;

    @FXML
    public void initialize() {

        bdProducto = new ProductoBD();
        bdProveedor = new ProveedorBD();
        isForEditar = false;
        isInactivo = false;
        tfCodigo.setEditable(false);
        tfCodigo.setText("00"+bdProducto.ultimoUtilizado());
    }

    //cierra la ventana en el momento de dar clic en aceptar.
    @FXML
    public void regresar() {
        controladoraIni.mostrarPanelInventario();
    }

    @FXML
    public void aceptar() {
        if (validarCamposVacios()) {

            String codigo = tfCodigo.getText();
            String nombreProveedor = tfProveedor.getText();
            String tipo = tfTipo.getText();
            String artista = tfArtista.getText();
            String album = tfAlbum.getText();
            String genero = tfGenero.getText();
            String coste = tfCoste.getText();
            String precioVenta = tfPrecioVenta.getText();
            String cantidad = tfCantidad.getText();

            Producto producto = new Producto(codigo, nombreProveedor, tipo, artista, album, genero, coste, precioVenta, cantidad, "Activo");

            if (!isForEditar) {
                try {
                    if (!bdProveedor.busquedaProveedor(nombreProveedor).get(0).getEstado().equals("Inactivo")) {
                        if (!bdProducto.registrar(producto)) {
                            controladoraIni.getMainApp().mostrarVentanaError();
                            return;
                        }
                    } else {
                        controladoraIni.getMainApp().mostrarVentanaError();
                        return;
                    }
                } catch (IndexOutOfBoundsException iex) {
                    controladoraIni.getMainApp().mostrarVentanaError();
                    return;
                }
            } else {
                isForEditar = false;
                try {
                    if (!bdProveedor.busquedaProveedor(nombreProveedor).get(0).getEstado().equals("Inactivo")) {
                        if(isInactivo){producto.setEstado("Inactivo");}
                        if (!bdProducto.actualizar(producto)) {
                            isForEditar = true;
                            controladoraIni.getMainApp().mostrarVentanaError();
                            return;
                        }
                    } else {
                        controladoraIni.getMainApp().mostrarVentanaError();
                        isForEditar = true;
                        return;
                    }
                }catch (IndexOutOfBoundsException iex) {
                    controladoraIni.getMainApp().mostrarVentanaError();
                    isForEditar = true;
                    return;
                }
            }
            controladoraIni.mostrarVentanaExitosa();
            controladoraIni.mostrarPanelInventario();
        } else {
            controladoraIni.getMainApp().mostrarVentanaError();
        }

    }


    public void mostrarDatos(Producto productoToEditar) {
        tfCodigo.setText(productoToEditar.getCodigo());
        tfCantidad.setText(productoToEditar.getCantidad());
        tfArtista.setText(productoToEditar.getArtista());
        tfAlbum.setText(productoToEditar.getAlbum());
        tfCoste.setText(productoToEditar.getCoste());
        tfPrecioVenta.setText(productoToEditar.getPrecioVenta());
        tfProveedor.setText(productoToEditar.getUnProveedor());
        tfTipo.setText(productoToEditar.getTipo());
        tfGenero.setText(productoToEditar.getGenero());
        isForEditar = true;
        tfCodigo.setEditable(false);
        if(productoToEditar.getEstado().equals("Inactivo")){
            isInactivo = true;
        }
    }


    public boolean validarCamposVacios() {
        if (tfCodigo.getText().isEmpty() || tfCantidad.getText().isEmpty() || tfArtista.getText().isEmpty() ||
                tfAlbum.getText().isEmpty() || tfCoste.getText().isEmpty() || tfPrecioVenta.getText().isEmpty() ||
                tfGenero.getText().isEmpty() || tfTipo.getText().isEmpty() ||
                tfProveedor.getText().isEmpty()) {
            return false;
        }
        try {
            int coste = Integer.parseInt(tfCoste.getText());
            int precioVenta = Integer.parseInt(tfPrecioVenta.getText());
            int stock = Integer.parseInt(tfCantidad.getText());
            if(stock <= 0){return false;}
        } catch (NumberFormatException nb) {
            System.out.println("error numerico");
            return false;
        }
        return true;
    }

    @FXML
    public void ayuda(){
        controladoraIni.mostrarVentanaAyudaInventario();
    }
}
