package maker.view;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class ControladoraExitoMessage {

    private Stage dialogStage;

    //************************ se crea obj de tipo controladora inicio
    private ControladoraInicio controladoraIni;

    public ControladoraInicio getControladoraIni() {
        return controladoraIni;
    }

    public void setControladoraIni(ControladoraInicio controladoraIni) {
        this.controladoraIni = controladoraIni;
    }
    //************************

    @FXML
    public void initialize(){

    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }


    //cierra la ventana en el momento de dar clic en aceptar.
    @FXML
    public void aceptada(){
        dialogStage.close();
    }
}
