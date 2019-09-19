package maker.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import maker.model.Cliente;
import maker.model.Usuario;
import maker.model.UsuarioBD;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ControladoraPerfilAdd {

    @FXML
    private TextField tfCedula;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfApellido;
    @FXML
    private TextField tfTelefono;
    @FXML
    private TextField tfDomicilio;
    @FXML
    private PasswordField pfContraseña;
    @FXML
    private DatePicker dpSeleccionarFecha;
    @FXML
    private ComboBox cbGenero;
    @FXML
    private ComboBox cbTipoDeCuenta;


    //************************ se crea obj de tipo controladora inicio
    private ControladoraInicio controladoraIni;

    public ControladoraInicio getControladoraIni() {
        return controladoraIni;
    }

    public void setControladoraIni(ControladoraInicio controladoraIni) {
        this.controladoraIni = controladoraIni;
    }
    //************************


    //************************ LISTA DE PERFILES GUARDADOS
    private ObservableList<Cliente> listaPerfiles = FXCollections.observableArrayList();

    public ObservableList<Cliente> getListaPerfiles() {
        return listaPerfiles;
    }

    public void setListaPerfiles(ObservableList<Cliente> listaPerfiles) {
        this.listaPerfiles = listaPerfiles;
    }
    //************************

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private UsuarioBD bd;
    private boolean isForEdit;
    private boolean isInactivo;


    @FXML
    public void initialize() {
        isInactivo = false;
        //coloca valores por defecto en genero
        cbGenero.getItems().add(0, "M");
        cbGenero.getItems().add(1, "F");

        //coloca valores por defecto en genero
        cbTipoDeCuenta.getItems().add(0, "Estandar");
        cbTipoDeCuenta.getItems().add(1, "Administrador");

        bd = new UsuarioBD();
        isForEdit = false;

    }

    public boolean validarCamposVacios() {
        try {
            int cedula = Integer.parseInt(tfCedula.getText());
            if (tfApellido.getText().isEmpty() || tfNombre.getText().isEmpty() ||
                    cbGenero.getSelectionModel().getSelectedIndex() < 0 || tfDomicilio.getText().isEmpty() || tfTelefono.getText().isEmpty() ||
                    pfContraseña.getText().isEmpty() || cbTipoDeCuenta.getSelectionModel().getSelectedIndex() < 0
                    || dpSeleccionarFecha.getEditor().getText().equals("".trim()) || dpSeleccionarFecha.getEditor().getText().equals("Seleccionar".trim()) ||
                    dpSeleccionarFecha.getValue().isAfter(LocalDate.now()) || !dpSeleccionarFecha.getValue().isBefore(LocalDate.now().minusYears(18))) {
                System.out.println("error de datos, falta completar");
                return false;
            }
            return true;
        }catch (Exception e){
            return false;
        }
    }


    @FXML
    public void aceptar() {
        if (validarCamposVacios()) {

            System.out.println("pasa la validacion de datos");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

            String cedula = tfCedula.getText();
            String nombre = tfNombre.getText();
            String apellido = tfApellido.getText();
            LocalDate fecha =  LocalDate.parse(dpSeleccionarFecha.getEditor().getText(), formatter);
            String telefono = tfTelefono.getText();
            String genero = cbGenero.getSelectionModel().getSelectedItem().toString();
            String domicilio = tfDomicilio.getText();
            String tipoDeCuenta = cbTipoDeCuenta.getSelectionModel().getSelectedItem().toString();
            String contrasenna = pfContraseña.getText();

            try {
                Password password = new ExtendedPassword(Integer.parseInt(contrasenna));
                contrasenna = "" + password.storePassword();

            } catch (Exception e) {
                //si se digita una contraseña de tipo string genera error, solo permite numeros
                controladoraIni.getMainApp().mostrarVentanaError();
                return;
            }

            Usuario usuario = new Usuario(cedula, nombre, apellido, fecha, genero, domicilio, tipoDeCuenta, contrasenna, telefono, "Activo");

           if(!isForEdit){
               if(!bd.registrar(usuario)){
                   controladoraIni.getMainApp().mostrarVentanaError();
                   return;
               }
           }else{
               if(isInactivo){usuario.setEstado("Inactivo");}
               bd.actualizar(usuario);
               isForEdit = true;
           }

            controladoraIni.mostrarVentanaExitosa();
            limpiarCampos();
            controladoraIni.mostrarPanelPerfiles();
        } else {
            controladoraIni.getMainApp().mostrarVentanaError();
        }
    }


    @FXML
    public void regresar(){
        controladoraIni.mostrarPanelPerfiles();
    }



    public void limpiarCampos() {
        tfNombre.setText("");
        tfApellido.setText("");
        tfCedula.setText("");
        cbGenero.getSelectionModel().clearSelection();
        cbTipoDeCuenta.getSelectionModel().clearSelection();
        tfDomicilio.setText("");
        tfTelefono.setText("");
        pfContraseña.setText("");
        dpSeleccionarFecha.getEditor().setText("Seleccionar");
    }

    public void mostrarDatos(Usuario usuarioToEdit){
        tfNombre.setText(usuarioToEdit.getNombre());
        tfApellido.setText(usuarioToEdit.getApellido());
        tfCedula.setText(usuarioToEdit.getIdentificacion());
        tfCedula.setEditable(false);
        cbGenero.getSelectionModel().select(usuarioToEdit.getGenero());
        cbTipoDeCuenta.getSelectionModel().select(usuarioToEdit.getCargo());
        tfDomicilio.setText(usuarioToEdit.getDomicilio());
        tfTelefono.setText(usuarioToEdit.getTelefono());
        pfContraseña.setText(usuarioToEdit.getContrasenna());
        dpSeleccionarFecha.setValue(usuarioToEdit.getFechaNacimiento());
        isForEdit = true;
        if(usuarioToEdit.getEstado().equals("Inactivo")){
            isInactivo = true;
        }
    }

    @FXML
    public void ayuda(){
        controladoraIni.mostrarVentanaAyudaPerfil();
    }



}
