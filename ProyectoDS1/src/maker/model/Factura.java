package maker.model;


import java.time.LocalDate;

public class Factura {
    private String numeroRef;
    private String datosCliente;
    private LocalDate fechaDeVenta;
    private String medioDePago;
    private double totalVenta;
    private double utilidad;
    private String vendedor;
    private String hora;

    public Factura(String numeroRef, String datosCliente, LocalDate fechaDeVenta,  String medioDePago, double totalVenta, double utilidad, String vendedor, String hora) {
        this.numeroRef = numeroRef;
        this.fechaDeVenta = fechaDeVenta;
        this.datosCliente = datosCliente;
        this.totalVenta = totalVenta;
        this.medioDePago = medioDePago;
        this.utilidad = utilidad;
        this.vendedor = vendedor;
        this.hora = hora;
    }


    public String getNumeroRef() {
        return numeroRef;
    }

    public void setNumeroRef(String numeroRef) {
        this.numeroRef = numeroRef;
    }

    public String getDatosCliente() {
        return datosCliente;
    }

    public void setDatosCliente(String datosCliente) {
        this.datosCliente = datosCliente;
    }

    public LocalDate getFechaDeVenta() {
        return fechaDeVenta;
    }

    public void setFechaDeVenta(LocalDate fechaDeVenta) {
        this.fechaDeVenta = fechaDeVenta;
    }

    public String getMedioDePago() {
        return medioDePago;
    }

    public void setMedioDePago(String medioDePago) {
        this.medioDePago = medioDePago;
    }

    public double getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(double totalVenta) {
        this.totalVenta = totalVenta;
    }

    public double getUtilidad() {
        return utilidad;
    }

    public void setUtilidad(double utilidad) {
        this.utilidad = utilidad;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}
