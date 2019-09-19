package maker.view;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import maker.model.Proveedor;
import maker.model.ProveedorBD;

public class ControladoraProveedorAdd {

    @FXML
    private TextField tfCodigo;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfNIT;
    @FXML
    private TextField tfTelefono;
    @FXML
    private TextField tfDireccion;

    //************************ se crea obj de tipo controladora inicio
    private ControladoraInicio controladoraIni;

    public ControladoraInicio getControladoraIni() {
        return controladoraIni;
    }

    public void setControladoraIni(ControladoraInicio controladoraIni) {
        this.controladoraIni = controladoraIni;
    }
    //************************


    private ProveedorBD bd;
    private boolean isForEditar;
    private boolean isInactivo;

    @FXML
    public void initialize() {
        isForEditar = false;
        isInactivo = false;
        bd = new ProveedorBD();
        tfCodigo.setEditable(false);
        tfCodigo.setText(""+bd.ultimoUtilizado());

    }


    //cierra la ventana en el momento de dar clic en aceptar.
    @FXML
    public void regresar() {
        controladoraIni.mostrarPanelProveedores();
    }

    @FXML
    public void aceptar() {
        if (validarCamposVacios()) {

            String nombre = tfNombre.getText();
            String codigo = tfCodigo.getText();
            String nit = tfNIT.getText();
            String telefono = tfTelefono.getText();
            String direccion = tfDireccion.getText();
            String email = tfEmail.getText();

            Proveedor proveedor = new Proveedor(codigo, nit, nombre, direccion, telefono, email, "Activo");

            if (!isForEditar) {
                if (!bd.registrar(proveedor)) {
                    controladoraIni.getMainApp().mostrarVentanaError();
                    return;
                }
            } else {
                isForEditar = false;
                if(isInactivo){proveedor.setEstado("Inactivo");}
                bd.actualizar(proveedor);
            }
            controladoraIni.mostrarVentanaExitosa();
            controladoraIni.mostrarPanelProveedores();


        } else {
            controladoraIni.getMainApp().mostrarVentanaError();
            return;
        }

    }


    public void mostrarDatos(Proveedor proveedorToEditar) {
        tfNombre.setText(proveedorToEditar.getNombre());
        tfCodigo.setText(proveedorToEditar.getCodigo());
        tfNIT.setText(proveedorToEditar.getNit());
        tfTelefono.setText(proveedorToEditar.getTelefono());
        tfEmail.setText(proveedorToEditar.getEmail());
        tfDireccion.setText(proveedorToEditar.getDireccion());
        isForEditar = true;
        tfCodigo.setEditable(false);
        if(proveedorToEditar.getEstado().equals("Inactivo")){
            isInactivo = true;
        }
    }


    public boolean validarCamposVacios() {
        if (tfNombre.getText().isEmpty() || tfCodigo.getText().isEmpty() || tfNIT.getText().isEmpty() ||
                tfDireccion.getText().isEmpty() || tfTelefono.getText().isEmpty() || tfEmail.getText().isEmpty()) {
            return false;
        }
        return true;
    }

}
