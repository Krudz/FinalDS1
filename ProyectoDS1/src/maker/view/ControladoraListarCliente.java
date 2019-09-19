package maker.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import maker.model.Cliente;
import maker.model.ClienteBD;

import java.util.Collections;

public class ControladoraListarCliente {

    @FXML
    private TableView<Cliente> tablaClientes;
    @FXML
    private TableColumn<Cliente, String> tvNombre;
    @FXML
    private TableColumn<Cliente, String> tvCedula;
    @FXML
    private TableColumn<Cliente, String> tvGenero;
    @FXML
    private TableColumn<Cliente, String> tvDomicilio;
    @FXML
    private TableColumn<Cliente, String> tvTelefono;
    @FXML
    private TableColumn<Cliente, String> tvEmail;
    @FXML
    private TableColumn<Cliente, String> tvEstado;
    @FXML
    private TextField tfBuscar;


    //************************ se crea obj de tipo controladora inicio
    private ControladoraInicio controladoraIni;

    public ControladoraInicio getControladoraIni() {
        return controladoraIni;
    }

    public void setControladoraIni(ControladoraInicio controladoraIni) {
        this.controladoraIni = controladoraIni;
    }
    //************************


    //************************ LISTA DE CLIENTES GUARDADOS
    private ObservableList<Cliente> listaClientes = FXCollections.observableArrayList();


    private ClienteBD bd;


    @FXML
    public void initialize() {

        bd = new ClienteBD();


        //agregar tipos de datos que se ingresaran a las columnas de los items de producto

        tvNombre.setCellValueFactory(cellData -> cellData.getValue().nombreProperty().concat(" " + cellData.getValue().apellidoProperty().getValue()));
        tvCedula.setCellValueFactory(cellData -> cellData.getValue().identificacionProperty());
        tvGenero.setCellValueFactory(cellData -> cellData.getValue().generoProperty());
        tvDomicilio.setCellValueFactory(cellData -> cellData.getValue().domicilioProperty());
        tvTelefono.setCellValueFactory(cellData -> cellData.getValue().telefonoProperty());
        tvEmail.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        tvEstado.setCellValueFactory(cellData -> cellData.getValue().estadoProperty());

        addToTabla();
    }


    @FXML
    public void editarCliente() {
        Cliente clienteToEditar = tablaClientes.getSelectionModel().getSelectedItem();
        if (clienteToEditar != null) {
            controladoraIni.setClienteToEditar(clienteToEditar);
            controladoraIni.setIngresoFromListarCliente(true);
            controladoraIni.setRunningProcess(true);
            controladoraIni.mostrarPanelClientes();
        }
    }


    @FXML
    public void inactivarActivarCliente() {
        Cliente clienteToBorrar = tablaClientes.getSelectionModel().getSelectedItem();
        if (clienteToBorrar != null) {
            if (clienteToBorrar.getEstado().equals("Activo")) {
                bd.inactivarActivar(clienteToBorrar.getIdentificacion(), "Inactivo");
            } else {
                bd.inactivarActivar(clienteToBorrar.getIdentificacion(), "Activo");
            }
            listaClientes = bd.mostrartable();
            addToTabla();
        }
    }

    public void addToTabla() {
        listaClientes = bd.mostrartable();
        Collections.sort(listaClientes, (Cliente c1, Cliente c2) -> (c1.getApellido().compareTo(c2.getApellido())));
        tablaClientes.setEditable(true);
        tablaClientes.setItems(listaClientes);
    }


    @FXML
    public void regresarToCliente() {
        controladoraIni.setRunningProcess(false);
        controladoraIni.setClienteToEditar(null);
        controladoraIni.mostrarPanelClientes();
    }

    @FXML
    public void buscarCliente() {
        if (!tfBuscar.getText().isEmpty()) {
            String identificacion = tfBuscar.getText();
            ObservableList<Cliente> listaEncontrada = bd.buscarClientes(identificacion);
            tablaClientes.setEditable(true);
            tablaClientes.setItems(listaEncontrada);
        } else {
            addToTabla();
        }
    }


}
