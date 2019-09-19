package maker.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Proveedor {

    private StringProperty codigo;
    private StringProperty nit;
    private StringProperty nombre;
    private StringProperty direccion;
    private StringProperty telefono;
    private StringProperty email;
    private StringProperty estado;

    public Proveedor(String codigo, String nit, String nombre, String direccion, String telefono, String email, String estado) {
        this.codigo = new SimpleStringProperty(codigo);
        this.nit = new SimpleStringProperty(nit);
        this.nombre = new SimpleStringProperty(nombre);
        this.direccion = new SimpleStringProperty(direccion);
        this.telefono = new SimpleStringProperty(telefono);
        this.email = new SimpleStringProperty(email);
        this.estado = new SimpleStringProperty(estado);
    }

    public String getCodigo() {
        return codigo.get();
    }

    public StringProperty codigoProperty() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo.set(codigo);
    }

    public String getNombre() {
        return nombre.get();
    }

    public StringProperty nombreProperty() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public String getNit() {
        return nit.get();
    }

    public StringProperty nitProperty() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit.set(nit);
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

    public String getDireccion() {
        return direccion.get();
    }

    public StringProperty direccionProperty() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion.set(direccion);
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
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
