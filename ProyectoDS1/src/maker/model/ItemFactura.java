package maker.model;

public class ItemFactura {
    private String nroRefFactura;
    private String codRefProd;
    private String descripcion;
    private String valorUnitario;
    private String cantidad;
    private String subtotal;

    public ItemFactura(String nroRefFactura,String codRefProd, String descripcion,  String valorUnitario, String cantidad, String subtotal) {
        this.nroRefFactura = nroRefFactura;
        this.codRefProd = codRefProd;
        this.descripcion = descripcion;
        this.valorUnitario = valorUnitario;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
    }

    public String getNroRefFactura() {
        return nroRefFactura;
    }

    public void setNroRefFactura(String nroRefFactura) {
        this.nroRefFactura = nroRefFactura;
    }

    public String getCodRefProd() {
        return codRefProd;
    }

    public void setCodRefProd(String codRefProd) {
        this.codRefProd = codRefProd;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(String valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }
}
