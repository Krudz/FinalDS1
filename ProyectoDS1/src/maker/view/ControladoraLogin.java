package maker.view;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import maker.MainApp;
import maker.model.Usuario;
import maker.model.UsuarioBD;

import java.io.IOException;

public class ControladoraLogin {

    @FXML
    private TextField userName;
    @FXML
    private PasswordField password;

    //se crea un objeto de tipo mainApp
    private MainApp mainApp;

    private UsuarioBD bd;


    //constructor
    public ControladoraLogin() {
    }

    //inicializador
    public void initialize() {
        bd = new UsuarioBD();
    }

    //cargamos una variable para login layout
    private AnchorPane loginLayout;


    //se toma el objeto de tipo main para interactuar ventanas
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }


    //cuando se presiona el boton iniciar sesion
    @FXML
    public void iniciarSesion() {
        Usuario usuarioEncontrado = validacionUsuario();
        if (usuarioEncontrado != null) {
            mostrarMenuInicio(usuarioEncontrado);
        } else {
            mainApp.mostrarVentanaError();
        }
    }


    //metodo para validar al usuario
    public Usuario validacionUsuario() {
        if (!userName.getText().isEmpty() && !password.getText().isEmpty()) {
            try{
                //aca es donde valida la parte del usuario en la bd
                String usuario = userName.getText();
                int contrasena = Integer.parseInt(password.getText());
                Usuario usuarioEncontrado = null;
                ObservableList<Usuario> listUserUnique = bd.consultar(usuario);

                if(listUserUnique.size() > 0 && listUserUnique.get(0).getEstado().equals("Activo")){
                    usuarioEncontrado = listUserUnique.get(0);
                }

                if (usuarioEncontrado != null) {
                    Password password = new ExtendedPassword(Integer.parseInt(usuarioEncontrado.getContrasenna()));

                    if (contrasena == password.getEncryptedPassword()) {
                        return usuarioEncontrado;
                    }
                }
            }catch(NumberFormatException exp){
                return null;
            }
        }
        return null;
    }


    //mis pruebas para ajustar organizacion
    //se muestra la ventana de inicio luego de iniciar sesion
    public void mostrarMenuInicio(Usuario usuarioEncontrado) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ControladoraLogin.class.getResource("inicio.fxml"));
            loginLayout = (AnchorPane) loader.load();

            //se muestra la escena contenida en el loginLayout
            Scene scene = new Scene(loginLayout);
            mainApp.getPrimaryStage().setScene(scene);

            //da al controlador acceso a la mainapp
            ControladoraInicio controladora = loader.getController();
            controladora.setMainApp(mainApp);
            controladora.setUsuarioLogueado(usuarioEncontrado);

            //se deshabilitan labels si es cajero
            if(usuarioEncontrado.getCargo().equals("Estandar")){
                controladora.getlInventario().setDisable(true);
                controladora.getlPerfiles().setDisable(true);
                controladora.getlProveedores().setDisable(true);
                controladora.getlReportes().setDisable(true);
                controladora.getiInventario().setOpacity(0.3);
                controladora.getiProveedores().setOpacity(0.3);
                controladora.getiPerfiles().setOpacity(0.3);
                controladora.getiReportes().setOpacity(0.3);

            }

            controladora.getlUsuarioLogueado().setText(usuarioEncontrado.getNombre() + " " + usuarioEncontrado.getApellido());

            //nuevo para split
            controladora.setInicioStage(mainApp.getPrimaryStage());

            mainApp.getPrimaryStage().show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
