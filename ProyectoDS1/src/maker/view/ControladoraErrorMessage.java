package maker.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class ControladoraErrorMessage {

    private Stage dialogStage;



    @FXML
    public void initialize(){

    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }


    //cierra la ventana en el momento de dar clic en aceptar.
    @FXML
    public void aceptarError(){
        dialogStage.close();
    }


}
