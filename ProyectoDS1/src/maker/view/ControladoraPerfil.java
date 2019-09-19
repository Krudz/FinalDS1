package maker.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import maker.model.Usuario;
import maker.model.UsuarioBD;
import java.util.Collections;

public class ControladoraPerfil {

    @FXML
    private TableView<Usuario> tablaPerfiles;
    @FXML
    private TableColumn<Usuario,String> tvIdentificacion;
    @FXML
    private TableColumn<Usuario,String> tvNombre;
    @FXML
    private TableColumn<Usuario,String> tvGenero;
    @FXML
    private TableColumn<Usuario,String> tvDireccion;
    @FXML
    private TableColumn<Usuario,String> tvTelefono;
    @FXML
    private TableColumn<Usuario,String> tvCargo;
    @FXML
    private TableColumn<Usuario,String> tvEstado;
    @FXML
    private TextField tfBuscar;

    private Stage stage;

    private UsuarioBD bd;

    //datos de prueba para productos que se agregan a la factura
    private ObservableList<Usuario> listaUsuarios = FXCollections.observableArrayList();


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

        bd = new UsuarioBD();

        //agregar tipos de datos que se ingresaran a las columnas de los items de proveedor
        tvIdentificacion.setCellValueFactory(cellData -> cellData.getValue().identificacionProperty());
        tvNombre.setCellValueFactory(cellData -> cellData.getValue().nombreProperty().concat(" " + cellData.getValue().apellidoProperty().getValue()));
        tvGenero.setCellValueFactory(cellData -> cellData.getValue().generoProperty());
        tvTelefono.setCellValueFactory(cellData -> cellData.getValue().telefonoProperty());
        tvDireccion.setCellValueFactory(cellData -> cellData.getValue().domicilioProperty());
        tvCargo.setCellValueFactory(cellData -> cellData.getValue().cargoProperty());
        tvEstado.setCellValueFactory(cellData -> cellData.getValue().estadoProperty());

        actualizaListaContactos();

        addToTabla();
    }



    @FXML
    public void inactivarActivarUsuario(){
        Usuario usuarioToInactivar = tablaPerfiles.getSelectionModel().getSelectedItem();
        if(usuarioToInactivar != null && !usuarioToInactivar.getCargo().equals("Administrador")){
            if(usuarioToInactivar.getEstado().equals("Activo")){
                bd.inactivarActivarUsuario(usuarioToInactivar.getIdentificacion(), "Inactivo");
            }
            else{
                bd.inactivarActivarUsuario(usuarioToInactivar.getIdentificacion(), "Activo");
            }
            actualizaListaContactos();
            addToTabla();
        }
        else{
            controladoraIni.getMainApp().mostrarVentanaError();
        }
    }


    @FXML
    public void addUsuario(){
        controladoraIni.setUsuarioToEditar(null);
        controladoraIni.mostrarPanelPerfilesAdd();
    }

    public void addToTabla(){
        tablaPerfiles.setEditable(true);
        tablaPerfiles.setItems(listaUsuarios);
    }

    public void actualizaListaContactos(){
        //trae informacion de la base de datos
        listaUsuarios = bd.mostrarTable();
        Collections.sort(listaUsuarios, (Usuario p1, Usuario p2) -> p1.getApellido().compareTo(p2.getApellido()));
    }


    @FXML
    public void buscarUsuario(){
        if(!tfBuscar.getText().isEmpty()){
            String identificacion = tfBuscar.getText();
            ObservableList<Usuario> listaEncontrada = bd.consultar(identificacion);
            tablaPerfiles.setEditable(true);
            tablaPerfiles.setItems(listaEncontrada);
        }
        else{
            addToTabla();
        }
    }


    @FXML
    public void editarUsuario(){
        Usuario usuarioToEditar = tablaPerfiles.getSelectionModel().getSelectedItem();
        if(usuarioToEditar != null){
            Password password = new ExtendedPassword(Integer.parseInt(usuarioToEditar.getContrasenna()));
            System.out.println("contraseña decriptada"+password.getEncryptedPassword());
            usuarioToEditar.setContrasenna(""+password.getEncryptedPassword());
            if(usuarioToEditar != null){
                controladoraIni.setUsuarioToEditar(usuarioToEditar);
                controladoraIni.mostrarPanelPerfilesAdd();
            }
        }
    }


}
