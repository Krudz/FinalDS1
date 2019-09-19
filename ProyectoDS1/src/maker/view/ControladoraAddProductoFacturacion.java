package maker.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import maker.model.*;

import java.util.Collections;

public class ControladoraAddProductoFacturacion {

    @FXML
    private TableView<Producto> tablaProductosToAdd;
    @FXML
    private TableColumn<Producto, String> tvCodigo;
    @FXML
    private TableColumn<Producto, String> tvProducto;
    @FXML
    private TableColumn<Producto, String> tvStock;
    @FXML
    private TableColumn<Producto, String> tvCoste;
    @FXML
    private TableColumn<Producto, String> tvPrecioVenta;
    @FXML
    private TableColumn<Producto, String> tvEstado;


    private ObservableList<Producto> listaProductos = FXCollections.observableArrayList();
    private ControladoraInicio controladoraIni;


    private ProductoBD bd;

    public void setControlIni(ControladoraInicio controladoraIni) {
        this.controladoraIni = controladoraIni;
    }



    @FXML
    public void initialize() {

        bd = new ProductoBD();

        listaProductos = bd.mostrartable();

        //ordenamiento de los componentes de la lista
        Collections.sort(listaProductos, (Producto p1, Producto p2) -> (new Integer(p1.getCantidad()).compareTo(new Integer(p2.getCantidad()))));


        //se cargan los datos a la tabla
        //agregar tipos de datos que se ingresaran a las columnas de los items de producto
        tvCodigo.setCellValueFactory(cellData -> cellData.getValue().codigoProperty());
        tvProducto.setCellValueFactory(cellData -> cellData.getValue().descripcionProperty());
        tvStock.setCellValueFactory(cellData -> cellData.getValue().cantidadProperty());
        tvCoste.setCellValueFactory(cellData -> cellData.getValue().costeProperty());
        tvPrecioVenta.setCellValueFactory(cellData -> cellData.getValue().precioVentaProperty());
        tvEstado.setCellValueFactory(cellData -> cellData.getValue().estadoProperty());


        tablaProductosToAdd.setEditable(true);
        tablaProductosToAdd.setItems(listaProductos);
    }

    //METODO PARA INVOCAR EL RETORNO A LA PAGINA PRINCIPAL DE FACTURACION
    //en cuanto se de clic en regresar sin seleccionar nada
    @FXML
    public void regresarAFacturacion() {
        controladoraIni.mostrarPanelFacturacion();
    }


    //CUANDO SE SELECCIONA AÑADIR ITEM
    @FXML
    public void addProductoToFactura() {
        try {

            //producto seleccionado en tabla
            Producto productoToAdd = tablaProductosToAdd.getSelectionModel().getSelectedItem();
            int cantidadStock = Integer.parseInt(productoToAdd.getCantidad());

            if (cantidadStock <= 0 || productoToAdd.getEstado().equals("Inactivo")) {
                productoToAdd = null;
                controladoraIni.getMainApp().mostrarVentanaError();
            }

            else {

                if (productoToAdd != null) {
                    //pasar el objeto para la controladora inicio
                    controladoraIni.addProductoSeleccionadoToFactura(productoToAdd);

                    bd.actualizarStock(productoToAdd, cantidadStock,"-");

                    //si pasa de 1 a 0 el stock
                    if(cantidadStock == 1){
                        bd.inactivarActivar(productoToAdd.getCodigo(),"Inactivo");
                    }

                } else {
                    System.out.println("no se añadio ningun producto");
                }
                controladoraIni.mostrarPanelFacturacion();
            }
        } catch (NullPointerException nu) {
            System.out.println("ocurre null pointer excp");
        }
    }


}
