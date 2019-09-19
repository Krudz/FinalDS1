package maker.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import maker.model.Producto;
import maker.model.ProductoBD;

import java.util.Collections;


public class ControladoraInventario {

    @FXML
    private TableView<Producto> tablaInventario;
    @FXML
    private TableColumn<Producto, String> tvCodigo;
    @FXML
    private TableColumn<Producto, String> tvProducto;
    @FXML
    private TableColumn<Producto, String> tvStock;
    @FXML
    private TableColumn<Producto, String> tvPrecio;
    @FXML
    private TableColumn<Producto, String> tvCoste;
    @FXML
    private TableColumn<Producto, String> tvEstado;
    @FXML
    private TextField tfBuscar;


    private ProductoBD bd;

    //datos de prueba para productos que se agregan a la factura
    private ObservableList<Producto> listaProductos = FXCollections.observableArrayList();

    public ObservableList<Producto> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(ObservableList<Producto> listaProductos) {
        this.listaProductos = listaProductos;
    }
    //*************************


    //se crea objeto de tipo controladora ini que pasara los datos desde controladora inicio
    ///**********************************
    private ControladoraInicio controladoraIni;

    public void setControlIni(ControladoraInicio controladoraIni) {
        this.controladoraIni = controladoraIni;
    }
    ///**********************************


    @FXML
    public void initialize() {

        bd = new ProductoBD();


        //agregar tipos de datos que se ingresaran a las columnas de los items de producto
        tvCodigo.setCellValueFactory(cellData -> cellData.getValue().codigoProperty());
        tvProducto.setCellValueFactory(cellData -> cellData.getValue().descripcionProperty());
        tvStock.setCellValueFactory(cellData -> cellData.getValue().cantidadProperty());
        tvPrecio.setCellValueFactory(cellData -> cellData.getValue().precioVentaProperty());
        tvCoste.setCellValueFactory(cellData -> cellData.getValue().costeProperty());
        tvEstado.setCellValueFactory(cellData -> cellData.getValue().estadoProperty());

        actualizaListaInventario();

        addToTabla();
    }


    @FXML
    public void editarProducto() {
        Producto productoToEditar = tablaInventario.getSelectionModel().getSelectedItem();
        if (productoToEditar != null) {
            controladoraIni.setProductoToEditar(productoToEditar);
            controladoraIni.mostrarVentanaAddInventario();
        }
    }

    @FXML
    public void addProducto() {
        controladoraIni.setProductoToEditar(null);
        controladoraIni.mostrarVentanaAddInventario();
    }

    @FXML
    public void inactivarActivarProducto() {
        Producto productoToDelete = tablaInventario.getSelectionModel().getSelectedItem();
        if (productoToDelete != null) {
            if (productoToDelete.getEstado().equals("Activo")) {
                bd.inactivarActivar(productoToDelete.getCodigo(), "Inactivo");
            } else {
                bd.inactivarActivar(productoToDelete.getCodigo(), "Activo");
            }
            actualizaListaInventario();
            addToTabla();
        }
    }


    public void addToTabla() {
        tablaInventario.setEditable(true);
        tablaInventario.setItems(listaProductos);
    }

    public void actualizaListaInventario() {
        //trae informacion de la base de datos y ordena por cantidad
        listaProductos = bd.mostrartable();
        Collections.sort(listaProductos, (Producto p1, Producto p2) -> (new Integer(p1.getCantidad()).compareTo(new Integer(p2.getCantidad()))));
    }

    @FXML
    public void buscarProducto() {
        if (!tfBuscar.getText().isEmpty()) {
            String codigo = tfBuscar.getText();
            ObservableList<Producto> listaEncontrada = bd.busquedaProducto(codigo);
            tablaInventario.setEditable(true);
            tablaInventario.setItems(listaEncontrada);
        } else {
            addToTabla();
        }
    }

    @FXML
    public void reabastecerStock() {
        Producto itemToReabastecer = tablaInventario.getSelectionModel().getSelectedItem();
        if (itemToReabastecer != null) {
            int cantidad = bd.cantidadActualizada(itemToReabastecer);
            bd.actualizarStock(itemToReabastecer, cantidad + 9, "+");
            if(cantidad == 0 && itemToReabastecer.getEstado().equals("Inactivo")){
                bd.inactivarActivar(itemToReabastecer.getCodigo(),"Activo");
            }
            actualizaListaInventario();
            addToTabla();
        }
    }
}
