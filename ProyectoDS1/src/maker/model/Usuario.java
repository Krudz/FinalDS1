package maker.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class Usuario extends Persona {

    private StringProperty cargo;
    private StringProperty contrasenna;
    private StringProperty telefono;
    private StringProperty estado;

    public Usuario(String identificacion, String nombre, String apellido, LocalDate fechaNacimiento, String genero,
                   String domicilio, String cargo, String contraseña, String telefono, String estado) {
        super(identificacion, nombre, apellido, fechaNacimiento, genero, domicilio);
        this.cargo = new SimpleStringProperty(cargo);
        this.contrasenna = new SimpleStringProperty(contraseña);
        this.telefono = new SimpleStringProperty(telefono);
        this.estado = new SimpleStringProperty(estado);
    }

    public String getCargo() {
        return cargo.get();
    }

    public StringProperty cargoProperty() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo.set(cargo);
    }

    public String getContrasenna() {
        return contrasenna.get();
    }

    public StringProperty contrasennaProperty() {
        return contrasenna;
    }

    public void setContrasenna(String contrasenna) {
        this.contrasenna.set(contrasenna);
    }

    public String getTelefono() {
        return telefono.get();
    }

    public StringProperty telefonoProperty() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono.set(telefono);
    }

    public String getEstado() {
        return estado.get();
    }

    public StringProperty estadoProperty() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado.set(estado);
    }
}
