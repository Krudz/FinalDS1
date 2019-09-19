package maker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import maker.view.*;

import java.io.IOException;

public class MainApp extends Application {


    //Se crea el stage y el anchorpane(para el login)
    private Stage primaryStage;
    private AnchorPane loginLayout;


    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("SIGIMU");
        loginLayout();

        //icono de la aplicacion
        this.primaryStage.getIcons().add(new Image("file:music-player.png"));
    }


    //se inicializar el login
    public void loginLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/login.fxml"));
            loginLayout = (AnchorPane) loader.load();

            //se muestra la escena contenida en el loginLayout
            Scene scene = new Scene(loginLayout);
            primaryStage.setScene(scene);

            //da al controlador acceso a la mainapp
            ControladoraLogin controladora = loader.getController();
            controladora.setMainApp(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //ventana de error cuando datos son faltantes o incongruentes
    public void mostrarVentanaError() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/errorMessage.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            //se crea el stage de dialog
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Error");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initOwner(getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            //colocar persona en el controlador
            ControladoraErrorMessage controller = loader.getController();

            //pasa el stage para mostrar la ventana emergente
            controller.setDialogStage(dialogStage);
            dialogStage.showAndWait();



        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }


    //get para el primary stage
    public Stage getPrimaryStage() {
        return primaryStage;
    }


    public static void main(String[] args) {
        launch(args);

    }









}
