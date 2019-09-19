package maker.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import maker.model.Producto;
import maker.model.Proveedor;
import maker.model.ProveedorBD;

import java.util.Collections;

public class ControladoraProveedores {

    @FXML
    private TableView<Proveedor> tablaProveedores;
    @FXML
    private TableColumn<Proveedor,String> tvCodigo;
    @FXML
    private TableColumn<Proveedor,String> tvNombre;
    @FXML
    private TableColumn<Proveedor,String> tvNIT;
    @FXML
    private TableColumn<Proveedor,String> tvTelefono;
    @FXML
    private TableColumn<Proveedor,String> tvDireccion;
    @FXML
    private TableColumn<Proveedor,String> tvEstado;
    @FXML
    private TextField tfBuscar;

    private Stage stage;

    private ProveedorBD bd;

    //datos de prueba para productos que se agregan a la factura
    private ObservableList<Proveedor> listaProveedores = FXCollections.observableArrayList();


    //se crea objeto de tipo controladora ini que pasara los datos desde controladora inicio
    ///**********************************
    private ControladoraInicio controladoraIni;

    public void setControlIni(ControladoraInicio controladoraIni) {
        this.controladoraIni = controladoraIni;
    }
    ///**********************************

    public void setStage(Stage stage){
        this.stage = stage;
    }

    @FXML
    public void initialize(){

        bd = new ProveedorBD();

        //agregar tipos de datos que se ingresaran a las columnas de los items de proveedor
        tvCodigo.setCellValueFactory(cellData -> cellData.getValue().codigoProperty());
        tvNombre.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        tvNIT.setCellValueFactory(cellData -> cellData.getValue().nitProperty());
        tvTelefono.setCellValueFactory(cellData -> cellData.getValue().telefonoProperty());
        tvDireccion.setCellValueFactory(cellData -> cellData.getValue().direccionProperty());
        tvEstado.setCellValueFactory(cellData -> cellData.getValue().estadoProperty());


        actualizaListaProveedores();

        addToTabla();
    }


    @FXML
    public void editarProveedor(){
        Proveedor proveedorToEditar = tablaProveedores.getSelectionModel().getSelectedItem();
        if(proveedorToEditar != null){
            controladoraIni.setProveedorToEditar(proveedorToEditar);
            controladoraIni.mostrarVentanaAddProveedores();
        }
    }

    @FXML
    public void inactivarActivarProveedor(){
        Proveedor proveedorToDelete = tablaProveedores.getSelectionModel().getSelectedItem();
        if(proveedorToDelete != null){
            if(proveedorToDelete.getEstado().equals("Activo")){
                bd.inactivarActivar(proveedorToDelete.getCodigo(),"Inactivo");
            }
            else{
                bd.inactivarActivar(proveedorToDelete.getCodigo(),"Activo");
            }

            actualizaListaProveedores();
            addToTabla();
        }
    }


    @FXML
    public void addProveedor(){
        controladoraIni.setProveedorToEditar(null);
        controladoraIni.mostrarVentanaAddProveedores();
    }

    public void addToTabla(){
        tablaProveedores.setEditable(true);
        tablaProveedores.setItems(listaProveedores);
    }

    public void actualizaListaProveedores(){
        //trae informacion de la base de datos
        listaProveedores = bd.mostrartable();
        Collections.sort(listaProveedores, (Proveedor p1, Proveedor p2) -> (new Integer(p1.getCodigo()).compareTo(new Integer(p2.getCodigo()))));
    }


    @FXML
    public void buscarProveedor(){
        if(!tfBuscar.getText().isEmpty()){
            String codigo = tfBuscar.getText();
            ObservableList<Proveedor> listaEncontrada = bd.busquedaProveedor(codigo);
            tablaProveedores.setEditable(true);
            tablaProveedores.setItems(listaEncontrada);
        }
        else{
            addToTabla();
        }

    }
}
