package maker.model;

import javafx.beans.property.*;

public class Producto {

    private StringProperty codigo;
    private StringProperty unProveedor;
    private StringProperty tipo;
    private StringProperty artista;
    private StringProperty album;
    private StringProperty genero;
    private StringProperty coste;
    private StringProperty precioVenta;
    private StringProperty cantidad;
    private StringProperty descripcion;
    private StringProperty estado;

    public Producto() {
    }

    public Producto(String codigo, String unProveedor, String tipo, String artista, String album, String genero, String coste, String precioVenta, String cantidad, String estado) {
        this.codigo = new SimpleStringProperty(codigo);
        this.unProveedor = new SimpleStringProperty(unProveedor);
        this.tipo = new SimpleStringProperty(tipo);
        this.artista = new SimpleStringProperty(artista);
        this.album = new SimpleStringProperty(album);
        this.genero = new SimpleStringProperty(genero);
        this.coste = new SimpleStringProperty(coste);
        this.precioVenta = new SimpleStringProperty(precioVenta);
        this.cantidad = new SimpleStringProperty(cantidad);
        this.descripcion = new SimpleStringProperty(tipo +" - " + artista + " - " +  album + " - " + genero);
        this.estado = new SimpleStringProperty(estado);
    }

    public String getCodigo() {
        return codigo.get();
    }

    public StringProperty codigoProperty() {
        return codigo;
    }


    public String getUnProveedor() {
        return unProveedor.get();
    }

    public StringProperty unProveedorProperty() {
        return unProveedor;
    }

    public void setUnProveedor(String unProveedor) {
        this.unProveedor.set(unProveedor);
    }

    public String getTipo() {
        return tipo.get();
    }

    public StringProperty tipoProperty() {
        return tipo;
    }

    public String getArtista() {
        return artista.get();
    }

    public StringProperty artistaProperty() {
        return artista;
    }

    public String getAlbum() {
        return album.get();
    }

    public StringProperty albumProperty() {
        return album;
    }

    public String getGenero() {
        return genero.get();
    }

    public StringProperty generoProperty() {
        return genero;
    }

    public String getCoste() {
        return coste.get();
    }

    public StringProperty costeProperty() {
        return coste;
    }

    public String getPrecioVenta() {
        return precioVenta.get();
    }

    public StringProperty precioVentaProperty() {
        return precioVenta;
    }

    public String getCantidad() {
        return cantidad.get();
    }

    public StringProperty cantidadProperty() {
        return cantidad;
    }

    public String getDescripcion() {
        return descripcion.get();
    }

    public StringProperty descripcionProperty() {
        return descripcion;
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
