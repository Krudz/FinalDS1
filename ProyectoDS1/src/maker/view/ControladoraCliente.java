package maker.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import maker.model.Cliente;
import maker.model.ClienteBD;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ControladoraCliente {


    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfApellido;
    @FXML
    private TextField tfCedula;
    @FXML
    private ComboBox cbGenero;
    @FXML
    private TextField tfDomicilio;
    @FXML
    private TextField tfTelefono;
    @FXML
    private TextField tfEmail;
    @FXML
    private DatePicker dpSelectorFecha;
    @FXML
    private Button bRegresarFact;
    @FXML
    private  Button bMostrarListaC;


    //*********************manejo de botones
    public Button getbRegresarFact() {
        return bRegresarFact;
    }

    public Button getbMostrarListaC() {
        return bMostrarListaC;
    }
    //***************************

    //************************ se crea obj de tipo controladora inicio
    private ControladoraInicio controladoraIni;

    public void setControladoraIni(ControladoraInicio controladoraIni) {
        this.controladoraIni = controladoraIni;
    }
    //************************

    //************************ LISTA DE CLIENTES GUARDADOS
    private ObservableList<Cliente> listaClientes = FXCollections.observableArrayList();

    private ClienteBD bd;
    //************************


    //***********
    private Cliente clienteToEditar;

    public void editarCliente(Cliente clienteToEditar) {
        this.clienteToEditar = clienteToEditar;
        tfCedula.setEditable(false);
        completarCamposForEditar();
    }
    //************************

    //**************si el cliente a editar esta inactivo
    private boolean isInactivo;


    @FXML
    public void initialize() {

        isInactivo = false;
        bd = new ClienteBD();

        //coloca valores por defecto en genero
        cbGenero.getItems().add(0, "M");
        cbGenero.getItems().add(1, "F");

        actualizaListaClientes();

    }


    public void completarCamposForEditar() {
        try {
            // el que parsea
            SimpleDateFormat parseador = new SimpleDateFormat("yyyy-MM-dd");
            // el que formatea
            SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");

            tfNombre.setText(clienteToEditar.getNombre());
            tfApellido.setText(clienteToEditar.getApellido());
            tfCedula.setText(clienteToEditar.getIdentificacion());
            dpSelectorFecha.setValue(clienteToEditar.getFechaNacimiento());
            cbGenero.getSelectionModel().selectFirst();
            tfDomicilio.setText(clienteToEditar.getDomicilio());
            tfTelefono.setText(clienteToEditar.getTelefono());
            tfEmail.setText(clienteToEditar.getEmail());
            if(clienteToEditar.getEstado().equals("Inactivo")){
                isInactivo = true;
            }

        } catch (Exception pe) {
            pe.printStackTrace();
        }
    }


    @FXML
    public void addCliente() {
        if (validarCamposVacios()) {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

            String identificacion = tfCedula.getText();
            String nombre = tfNombre.getText();
            String apellido = tfApellido.getText();
            LocalDate fechaNacimiento = LocalDate.parse(dpSelectorFecha.getEditor().getText(), formatter);
            String genero = cbGenero.getSelectionModel().getSelectedItem().toString();
            String domicilio = tfDomicilio.getText();
            String telefono = tfTelefono.getText();
            String email = tfEmail.getText();

            Cliente nuevoCliente = new Cliente(identificacion, nombre, apellido, fechaNacimiento, genero,
                    domicilio, telefono, email, "Activo");

            if (!controladoraIni.isIngresoFromListarCliente()) {
                bd.registrar(nuevoCliente);
                listaClientes.add(nuevoCliente);
                actualizaListaClientes();
            } else {
                if(isInactivo){nuevoCliente.setEstado("Inactivo");}
                bd.actualizar(nuevoCliente);
                controladoraIni.setIngresoFromListarCliente(false);
                controladoraIni.setIngresoFromListarCliente(false);
                tfCedula.setEditable(true);
                actualizaListaClientes();
            }

            controladoraIni.mostrarVentanaExitosa();
            limpiarCampos();



            //si viene de facturacion, se devolvera para continuar la misma.
            if (controladoraIni.isIngresoFromFacturacion()) {
                controladoraIni.setNuevoCliente(nuevoCliente);
                controladoraIni.mostrarPanelFacturacion();
            }
        }
        else{
            controladoraIni.getMainApp().mostrarVentanaError();
        }
    }


    @FXML
    public void modificarListaCliente() {

        if (!controladoraIni.isIngresoFromFacturacion()) {
            controladoraIni.setListaClientes(listaClientes);
            controladoraIni.mostrarPanelListarClientes();

        }
    }


    @FXML
    public void regresarFacturacion() {
        //valida si viene desde facturacion o se aplica directamente en la ventana.
        if (controladoraIni.isIngresoFromFacturacion()) {
            controladoraIni.mostrarPanelFacturacion();
        }

    }


    public boolean validarCamposVacios() {
        try{
        int cedula = Integer.parseInt(tfCedula.getText());
        if (tfApellido.getText().isEmpty() || tfNombre.getText().isEmpty() ||
                cbGenero.getSelectionModel().getSelectedIndex() < 0 || tfDomicilio.getText().isEmpty() || tfTelefono.getText().isEmpty() ||
                tfEmail.getText().isEmpty() || dpSelectorFecha.getEditor().getText().equals("".trim()) || dpSelectorFecha.getEditor().getText().equals("Seleccionar".trim()) ||
        !dpSelectorFecha.getValue().isBefore(LocalDate.now()) || !dpSelectorFecha.getValue().isBefore(LocalDate.now().minusYears(8))) {
            return false;
        }
        return true;
        }catch (Exception e){
            return false;
        }
    }


    public void limpiarCampos() {
        tfNombre.setText("");
        tfApellido.setText("");
        tfCedula.setText("");
        cbGenero.getSelectionModel().clearSelection();
        tfDomicilio.setText("");
        tfTelefono.setText("");
        tfEmail.setText("");
        dpSelectorFecha.getEditor().setText("Seleccionar");
    }

    public void actualizaListaClientes() {
        //trae informacion de la base de datos
        listaClientes = bd.mostrartable();
    }

    @FXML
    public void ayuda(){
        controladoraIni.mostrarVentanaAyudaCliente();
    }

}
