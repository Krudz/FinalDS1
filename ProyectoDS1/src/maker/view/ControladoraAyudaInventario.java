package maker.view;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class ControladoraAyudaInventario {

    private Stage dialogStage;

    @FXML
    public void initialize(){

    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }


    //cierra la ventana en el momento de dar clic en aceptar.
    @FXML
    public void aceptar(){
        dialogStage.close();
    }
}
