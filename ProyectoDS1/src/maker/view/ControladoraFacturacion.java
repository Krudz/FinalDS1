package maker.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import maker.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class ControladoraFacturacion {

    @FXML
    private Label numeracionFactura;
    @FXML
    private Label nombreCliente;
    @FXML
    private Label telefonoCliente;
    @FXML
    private Label ccCliente;
    @FXML
    private Label emailCliente;
    @FXML
    private ComboBox cbSelectorPagoFactura;
    @FXML
    private ComboBox cbSelectorClientes;
    @FXML
    private TableView<Producto> tablaProductosFactura;
    @FXML
    private TableColumn<Producto, String> tvCodigo;
    @FXML
    private TableColumn<Producto, String> tvDescripcion;
    @FXML
    private TableColumn<Producto, String> tvSubtotal;
    @FXML
    private Label lValorTotal;
    @FXML
    private Label direccionCliente;
    @FXML
    private Label lFecha;


    //productos que se agregan a la factura
    private ObservableList<Producto> listaproductosAddedToFactura = FXCollections.observableArrayList();

    private ObservableList<Cliente> listaClientesActuales = FXCollections.observableArrayList();


    //se crea objeto de tipo controladora ini que pasara los datos desde controladora inicio
    ///**********************************
    private ControladoraInicio controladoraIni;

    public void setControlIni(ControladoraInicio controladoraIni) {
        this.controladoraIni = controladoraIni;
    }
    ///**********************************


    private boolean isClienteSelected = false;
    private Cliente datosClienteFacturacion;

    public void setDatosClienteFacturacion(Cliente datosClienteFacturacion) {
        this.datosClienteFacturacion = datosClienteFacturacion;
    }
    ///**********************************


    //variables bd
    private FacturaBD bdFactura;
    private ClienteBD bdCliente;
    private ItemFacturaBD bdItemFactura;
    private ProductoBD bdProducto;


    public int fijarConsecutivo() {
        //revisar el ultimo valor dado al consecutivo de la factura
        return bdFactura.contarRegistros() + 1;
    }


    @FXML
    public void initialize() {

        lFecha.setText(LocalDate.now().toString());

        bdFactura = new FacturaBD();
        bdCliente = new ClienteBD();
        bdItemFactura = new ItemFacturaBD();
        bdProducto = new ProductoBD();

        numeracionFactura.setText("00" + fijarConsecutivo());

        //inicializar forma de pago
        cbSelectorPagoFactura.getItems().add(0, "Tarjeta");
        cbSelectorPagoFactura.getItems().add(1, "Efectivo");

        listaClientesActuales = bdCliente.mostrartable();


        int count = 0;

        //inicializar lista de clientes traida de la base de datos
        for (int i = 0; i < listaClientesActuales.size(); i++) {
            //filtra para que solo se muestren los clientes activos
            if (listaClientesActuales.get(i).getEstado().equals("Activo")) {
                cbSelectorClientes.getItems().add(count, listaClientesActuales.get(i).getNombre() + " " + listaClientesActuales.get(i).getApellido());
                count++;
            }
        }
        cbSelectorClientes.setVisibleRowCount(5);

        //agregar tipos de datos que se ingresaran a las columnas de los items de producto
        tvCodigo.setCellValueFactory(cellData -> cellData.getValue().codigoProperty());
        tvDescripcion.setCellValueFactory(cellData -> cellData.getValue().descripcionProperty());
        tvSubtotal.setCellValueFactory(cellData -> cellData.getValue().precioVentaProperty());
    }


    //a traves del objeto controladora inicio se usa el metodo para mostrar el contenido del panel
    @FXML
    public void mostrarAddProductoFacturacion() {
        /*revisarFecha();*/
        controladoraIni.setObjFact(this);
        controladoraIni.mostrarPaneladdProductoFacturacion();

        //se activa runningprocess
        controladoraIni.setRunningProcess(true);
    }


    //prueba,,,, para cuando se añada un cliente, este se pasara el objeto y se muestra en los respectivos labels
    public void mostrarDatosTablaProductos(ObservableList<Producto> listaProductosAddingToFacturacion, Cliente nuevoCliente) {

        //prueba con paso de objeto para obtener fecha
        if (controladoraIni.getObjFact() != null) {


            //se coloca la anterior seleccion de tipo de pago -efectivo - tarjeta
            int posSelectorPago = controladoraIni.getObjFact().cbSelectorPagoFactura.getSelectionModel().getSelectedIndex();
            cbSelectorPagoFactura.getSelectionModel().select(posSelectorPago);


            //se coloca la anterior seleccion de cliente para que coincida con el que esta en los campos
            int posSelectorCliente = controladoraIni.getObjFact().cbSelectorClientes.getSelectionModel().getSelectedIndex();
            cbSelectorClientes.getSelectionModel().select(posSelectorCliente);


            //verifica si el cliente que se va a añadir es nuevo
            if (nuevoCliente == null) {
                //para pasar cliente por objeto
                datosClienteFacturacion = controladoraIni.objFact.datosClienteFacturacion;


                //revisa que los datos del cliente no esten vacios
                if (datosClienteFacturacion != null) {
                    colocarDatosCliente();
                }
            } else {
                datosClienteFacturacion = nuevoCliente;
                colocarDatosCliente();
                cbSelectorClientes.getSelectionModel().select(cbSelectorClientes.getItems().size() - 1);
            }
        }


        //recibe la lista de los productos que se van añadiendo
        if (listaProductosAddingToFacturacion != null) {
            listaproductosAddedToFactura = listaProductosAddingToFacturacion;
            tablaProductosFactura.setEditable(true);
            tablaProductosFactura.setItems(listaproductosAddedToFactura);

            addValorTotalProgresivo();
        }
    }


    //se suma a medida que se vaya añadiendo un nuevo item en la tabla
    public void addValorTotalProgresivo() {
        double subtotal = 0;
        String valorAconvertir = "";
        for (int i = 0; i < tablaProductosFactura.getItems().size(); i++) {
            valorAconvertir = tablaProductosFactura.getItems().get(i).getPrecioVenta();
            subtotal += Double.parseDouble(valorAconvertir);
        }
        lValorTotal.setText("$ " + subtotal);
    }


    //para ir añadiendo los datos de los clientes a los label
    @FXML
    public void seleccionarCliente() {
        try {
            String nombre = cbSelectorClientes.getSelectionModel().getSelectedItem().toString();
            datosClienteFacturacion = buscarDatos(nombre.trim());
            isClienteSelected = true;

            //se activa runningprocess
            controladoraIni.setRunningProcess(true);

            if (datosClienteFacturacion != null) {
                colocarDatosCliente();
                //si llego a existir un cliente nuevo asignado, se debe nulear para que no lo vuelva a traer
                controladoraIni.setNuevoCliente(null);
            } else {
                System.out.println("no encontrado");
            }
        } catch (Exception e) {
            System.out.println("seleccion cero");
        }
    }


    //metodo para buscar en el arraylist el cliente determinado
    public Cliente buscarDatos(String nombreCliente) {
        for (int i = 0; i < listaClientesActuales.size(); i++) {
            if (nombreCliente.equals(listaClientesActuales.get(i).getNombre() + " "
                    + listaClientesActuales.get(i).getApellido())) {
                return listaClientesActuales.get(i);
            }
        }
        return null;
    }


    //quitar producto de la lista de facturas
    public void quitarProductoFactura() {
        Producto itemAborrar = tablaProductosFactura.getSelectionModel().getSelectedItem();
        if (itemAborrar != null) {
            //se borra de la tabla y se reestablece el stock de productos
            int cantidad = bdProducto.cantidadActualizada(itemAborrar);
            bdProducto.actualizarStock(itemAborrar, cantidad, "+");

            //si la cantidad es cero, y se quita de facturacion, se reestablece
            if(cantidad == 0){
                bdProducto.inactivarActivar(itemAborrar.getCodigo(),"Activo");
            }

            tablaProductosFactura.getItems().remove(itemAborrar);

            //para que actualice el valor total
            addValorTotalProgresivo();
        }
        else {
            System.out.println("no hay items para borrar");
        }
    }


    //colocar los datos del cliente
    private void colocarDatosCliente() {
        nombreCliente.setText(datosClienteFacturacion.getNombre() + " " + datosClienteFacturacion.getApellido());
        direccionCliente.setText(datosClienteFacturacion.getDomicilio());
        telefonoCliente.setText(datosClienteFacturacion.getTelefono());
        ccCliente.setText("" + datosClienteFacturacion.getIdentificacion());
        emailCliente.setText(datosClienteFacturacion.getEmail());
    }


    //cuando se presione el boton generarFactura se inicia una verificacion previa para luego enviar datos.
    @FXML
    public void generarFactura() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

        if (cbSelectorPagoFactura.getSelectionModel().getSelectedIndex() >= 0
                && datosClienteFacturacion != null && tablaProductosFactura.getItems().size() >= 0) {

            String numeroRef = numeracionFactura.getText();
            String cliente = ccCliente.getText().trim();

            //ajustar hora
            String hora = "";
            if (LocalDateTime.now().getMinute() <= 9) {
                hora = LocalDateTime.now().getHour() + ":0" + LocalDateTime.now().getMinute();
            } else {
                hora = LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute();
            }

            String medioPago = cbSelectorPagoFactura.getSelectionModel().getSelectedItem().toString();
            double total = Double.parseDouble(lValorTotal.getText().substring(2,lValorTotal.getText().length()));
            double utilidad = 0;


            for (int i = 0; i < listaproductosAddedToFactura.size(); i++) {
                utilidad += Double.parseDouble(listaproductosAddedToFactura.get(i).getPrecioVenta()) - Double.parseDouble(listaproductosAddedToFactura.get(i).getCoste());
            }

            Factura facturaData = new Factura(numeroRef, cliente, LocalDate.now(), medioPago,
                    total, utilidad, controladoraIni.getUsuarioLogueado().getNombre() + " " + controladoraIni.getUsuarioLogueado().getApellido(), hora);

            bdFactura.registrar(facturaData);


            //se registra en tabla itemfactura los productos de cada factura.
            for (int i = 0; i < listaproductosAddedToFactura.size(); i++) {
                ItemFactura itemFactura = new ItemFactura(numeroRef, listaproductosAddedToFactura.get(i).getCodigo(),
                        listaproductosAddedToFactura.get(i).getDescripcion(), listaproductosAddedToFactura.get(i).getPrecioVenta(), "" + 1,
                        listaproductosAddedToFactura.get(i).getPrecioVenta());

                bdItemFactura.registrar(itemFactura);
            }

            numeracionFactura.setText("000" + fijarConsecutivo());


            //AJUSTAR CLIENTE QUE ESTABA INACTIVO
            if (datosClienteFacturacion.getEstado().equals("Inactivo")) {
                bdCliente.inactivarActivar(datosClienteFacturacion.getIdentificacion(), "Activo");
            }

            //mostrar ventana de factura exitosa
            controladoraIni.mostrarVentanaExitosa();
            PDFactura nuevaFactura = new PDFactura(facturaData);

            limpiarCamposFacturacion();
            controladoraIni.objFact.setDatosClienteFacturacion(null);
        } else {
            controladoraIni.getMainApp().mostrarVentanaError();
        }
    }


    //limpiar campos de facturacion.
    public void limpiarCamposFacturacion() {
        cbSelectorClientes.getSelectionModel().clearSelection();
        cbSelectorPagoFactura.getSelectionModel().clearSelection();

        if (listaproductosAddedToFactura.size() > 0) {
            tablaProductosFactura.getItems().remove(0, listaproductosAddedToFactura.size());
        }

        nombreCliente.setText("");
        telefonoCliente.setText("");
        ccCliente.setText("");
        emailCliente.setText("");
        direccionCliente.setText("");
        listaproductosAddedToFactura = null;
        lValorTotal.setText("");

        datosClienteFacturacion = null;
    }


    //permite abrir la ventana de crear cliente
    @FXML
    public void crearCliente() {
        controladoraIni.setObjFact(this);
        controladoraIni.setIngresoFromFacturacion(true);

        //se desactiva runningprocess
        controladoraIni.setRunningProcess(false);
        controladoraIni.mostrarPanelClientes();
    }


}
