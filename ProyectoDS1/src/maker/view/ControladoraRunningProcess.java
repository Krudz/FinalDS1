package maker.view;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import maker.model.ProductoBD;

public class ControladoraRunningProcess {
    private Stage dialogStage;


    //********************* se crea objeto de tipo controlador inicio
    private ControladoraInicio controladoraIni;

    public ControladoraInicio getControladoraIni() {
        return controladoraIni;
    }

    public void setControladoraIni(ControladoraInicio controladoraIni) {
        this.controladoraIni = controladoraIni;
    }
    //************************************



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

    @FXML
    public void cancelarError(){
        controladoraIni.setRunningProcess(false);
        controladoraIni.setIngresoFromFacturacion(false);
        controladoraIni.setNuevoCliente(null);
        controladoraIni.setClienteToEditar(null);
        dialogStage.close();
        controladoraIni.reestablecerStock();
    }




}
