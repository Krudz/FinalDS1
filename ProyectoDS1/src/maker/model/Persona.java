package maker.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public abstract class Persona {

    //definicion de variables
    private StringProperty identificacion;
    private StringProperty nombre;
    private StringProperty apellido;
    private ObjectProperty<LocalDate> fechaNacimiento;
    private StringProperty genero;
    private StringProperty domicilio;

    public Persona(String identificacion, String nombre, String apellido, LocalDate fechaNacimiento, String genero, String domicilio) {
        this.identificacion = new SimpleStringProperty(identificacion);
        this.nombre = new SimpleStringProperty(nombre);
        this.apellido = new SimpleStringProperty(apellido);
        this.fechaNacimiento = new SimpleObjectProperty<LocalDate>(fechaNacimiento) ;
        this.genero = new SimpleStringProperty(genero);
        this.domicilio = new SimpleStringProperty(domicilio);
    }


    public String getIdentificacion() {
        return identificacion.get();
    }

    public StringProperty identificacionProperty() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion.set(identificacion);
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

    public String getApellido() {
        return apellido.get();
    }

    public StringProperty apellidoProperty() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido.set(apellido);
    }


    public LocalDate getFechaNacimiento() {
        return fechaNacimiento.get();
    }

    public ObjectProperty<LocalDate> fechaNacimientoProperty() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento.set(fechaNacimiento);
    }

    public String getGenero() {
        return genero.get();
    }

    public StringProperty generoProperty() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero.set(genero);
    }

    public String getDomicilio() {
        return domicilio.get();
    }

    public StringProperty domicilioProperty() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio.set(domicilio);
    }
}
