package maker.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import maker.MainApp;
import maker.model.*;

import java.io.IOException;

public class ControladoraInicio {

    @FXML
    private AnchorPane splitBottom;
    @FXML
    private SplitPane splitAbs;
    @FXML
    private Label lUsuarioLogueado;
    @FXML
    private Label lInventario;
    @FXML
    private Label lProveedores;
    @FXML
    private Label lPerfiles;
    @FXML
    private Label lReportes;
    @FXML
    private ImageView iInventario;
    @FXML
    private ImageView iReportes;
    @FXML
    private ImageView iProveedores;
    @FXML
    private ImageView iPerfiles;



    /****************************
     para manejar los labels e imagenes de acuerdo al usuario que ingrese
     */
    public Label getlInventario() {
        return lInventario;
    }

    public Label getlProveedores() {
        return lProveedores;
    }

    public Label getlPerfiles() {
        return lPerfiles;
    }

    public Label getlReportes() {
        return lReportes;
    }

    public ImageView getiInventario() {
        return iInventario;
    }

    public ImageView getiReportes() {
        return iReportes;
    }

    public ImageView getiProveedores() {
        return iProveedores;
    }

    public ImageView getiPerfiles() {
        return iPerfiles;
    }

    //****************************


    public Label getlUsuarioLogueado() {
        return lUsuarioLogueado;
    }

    //**************establece un ingreso para cliente desde facturacion
    private boolean ingresoFromFacturacion = false;

    public boolean isIngresoFromFacturacion() {
        return ingresoFromFacturacion;
    }

    public void setIngresoFromFacturacion(boolean ingresoFromFacturacion) {
        this.ingresoFromFacturacion = ingresoFromFacturacion;
    }
    //**************

    //**************establece un ingreso para cliente desde listarcliente
    private boolean ingresoFromListarCliente = false;

    public boolean isIngresoFromListarCliente() {
        return ingresoFromListarCliente;
    }

    public void setIngresoFromListarCliente(boolean ingresoFromListarCliente) {
        this.ingresoFromListarCliente = ingresoFromListarCliente;
    }
    //**************

    //establecer control sobre el proceso que se este realizando
    //*******************************************
    private boolean runningProcess = false;

    public boolean isRunningProcess() {
        return runningProcess;
    }

    public void setRunningProcess(boolean runningProcess) {
        this.runningProcess = runningProcess;
    }
    //*******************************************


    //creacion de observablelist que me ira mostrando a medida que vaya agregando
    private ObservableList<Producto> listaProductosAddingToFacturacion = FXCollections.observableArrayList();

    public ObservableList<Producto> getListaProductosAddingToFacturacion() {
        return listaProductosAddingToFacturacion;
    }

    //*******************************************

    //creacion de observable list para ir mostrando el cliente que se añade
    private ObservableList<Cliente> listaClientes = FXCollections.observableArrayList();

    public void setListaClientes(ObservableList<Cliente> listaClientes) {
        this.listaClientes = listaClientes;
    }
    //*******************


    //creacion de objeto cliente para cada nuevo cliente que se va creando
    //*******************
    private Cliente nuevoCliente;

    public void setNuevoCliente(Cliente nuevoCliente) {
        this.nuevoCliente = nuevoCliente;
    }
    //*******************


    //*******************objeto facturacion
    ControladoraFacturacion objFact = null;

    public ControladoraFacturacion getObjFact() {
        return objFact;
    }

    public void setObjFact(ControladoraFacturacion objFact) {
        this.objFact = objFact;
    }
    //**************************


    //*******************objeto cliente para editar
    Cliente clienteToEditar;


    public void setClienteToEditar(Cliente clienteToEditar) {
        this.clienteToEditar = clienteToEditar;
    }
    //*******************

    //*******************objeto proveedor para editar
    Proveedor proveedorToEditar;

    public void setProveedorToEditar(Proveedor proveedorToEditar) {
        this.proveedorToEditar = proveedorToEditar;
    }
    //*******************

    //******************* obj Producto a editar
    Producto productoToEditar;


    public void setProductoToEditar(Producto productoToEditar) {
        this.productoToEditar = productoToEditar;
    }
    //*******************

    //******************* obj Usuario a editar
    Usuario usuarioToEditar;

    public void setUsuarioToEditar(Usuario usuarioToEditar) {
        this.usuarioToEditar = usuarioToEditar;
    }
    //*******************


    //*******************objeto main
    //se pasa el objeto de tipo MainApp
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public MainApp getMainApp() {
        return mainApp;
    }
    //***************************


    //*******************objeto usuario encontrado
    private Usuario usuarioLogueado;

    public void setUsuarioLogueado(Usuario usuarioLogueado) {
        this.usuarioLogueado = usuarioLogueado;
    }

    public Usuario getUsuarioLogueado() {
        return usuarioLogueado;
    }
    //*******************

    private Stage inicioStage;
    private MainApp mainApp;
    private ProductoBD bdProducto;


    @FXML
    private void initialize() {
        bdProducto = new ProductoBD();

    }

    public void setInicioStage(Stage inicioStage) {
        this.inicioStage = inicioStage;
    }


    @FXML
    public void cerrarSesion() {
        mainApp.loginLayout();
        reestablecerStock();
    }


    @FXML
    public void mostrarPanelFacturacion() {
        try {
            //se carga contenido de facturacion a splitBottom
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/facturacion.fxml"));
            splitBottom = (AnchorPane) loader.load();

            ControladoraFacturacion ctrlFacturacion;
            ctrlFacturacion = loader.getController();

            ctrlFacturacion.setControlIni(this);

            ctrlFacturacion.mostrarDatosTablaProductos(listaProductosAddingToFacturacion, nuevoCliente);

            //se añade el contenido de facturacion al split inferior
            splitAbs.getItems().set(1, splitBottom);

            //limpia campos luego de recepcionar el cambio de estado de tarea
            if (!isRunningProcess()) {
                ctrlFacturacion.limpiarCamposFacturacion();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void mostrarPaneladdProductoFacturacion() {
        try {
            //se carga contenido de facturacion a splitBottom
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/addProducto-facturacion.fxml"));
            splitBottom = (AnchorPane) loader.load();

            //se pasa el objeto de la clase inicio.
            ControladoraAddProductoFacturacion ctrlAddProductoFac = loader.getController();
            ctrlAddProductoFac.setControlIni(this);

            //se añade el contenido de facturacion al split inferior
            splitAbs.getItems().set(1, splitBottom);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //se carga el objeto pasado por la clase de seleccion de producto para facturacion
    public void addProductoSeleccionadoToFactura(Producto productoSeleccionado) {
        listaProductosAddingToFacturacion.add(productoSeleccionado);
    }


    @FXML
    public void mostrarPanelClientes() {
        try {
            if (!isRunningProcess() || isIngresoFromListarCliente()) {
                //se carga contenido de clientes a splitBottom
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(MainApp.class.getResource("view/Cliente.fxml"));
                splitBottom = (AnchorPane) loader.load();

                ControladoraCliente controladoraCli = loader.getController();
                controladoraCli.setControladoraIni(this);

                if (isIngresoFromFacturacion()) {
                    setRunningProcess(true);
                    controladoraCli.getbMostrarListaC().disableProperty().setValue(true);
                }
                else{
                    controladoraCli.getbRegresarFact().disableProperty().setValue(true);
                }

                if (clienteToEditar != null) {
                    controladoraCli.editarCliente(clienteToEditar);
                }

                //limpia campos luego de recepcionar el cambio de estado de tarea
                if (!isRunningProcess()) {
                    controladoraCli.limpiarCampos();
                }

                //se añade el contenido de clientes al split inferior
                splitAbs.getItems().set(1, splitBottom);
            } else {
                mostrarVentanaRunningProcess();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void mostrarPanelListarClientes() {
        try {
            //se carga contenido de facturacion a splitBottom
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/listarcliente.fxml"));
            splitBottom = (AnchorPane) loader.load();

            //se pasa el objeto de la clase inicio.
            ControladoraListarCliente ctrlListarClie = loader.getController();
            ctrlListarClie.setControladoraIni(this);


            //se añade el contenido de facturacion al split inferior
            splitAbs.getItems().set(1, splitBottom);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void mostrarPanelPerfiles() {
        try {
            if (usuarioLogueado.getCargo().equalsIgnoreCase("Administrador")) {
                if (!isRunningProcess()) {
                    //se carga contenido de perfiles a splitBottom
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(MainApp.class.getResource("view/perfiles.fxml"));
                    splitBottom = (AnchorPane) loader.load();

                    //se pasa el objeto de la clase inicio.
                    ControladoraPerfil ctrlPerfil = loader.getController();
                    ctrlPerfil.setControlIni(this);

                    //se añade el contenido de perfiles al split inferior
                    splitAbs.getItems().set(1, splitBottom);

                } else {
                    mostrarVentanaRunningProcess();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void mostrarPanelPerfilesAdd() {
        try {
            if (!isRunningProcess()) {
                //se carga contenido de perfiles a splitBottom
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(MainApp.class.getResource("view/perfilAdd.fxml"));
                splitBottom = (AnchorPane) loader.load();

                //se pasa el objeto de la clase inicio.
                ControladoraPerfilAdd ctrlPerfil = loader.getController();
                ctrlPerfil.setControladoraIni(this);

                if (usuarioToEditar != null) {
                    ctrlPerfil.mostrarDatos(usuarioToEditar);
                }

                //se añade el contenido de perfiles al split inferior
                splitAbs.getItems().set(1, splitBottom);

            } else {
                mostrarVentanaRunningProcess();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void mostrarPanelProveedores() {
        try {
            if (usuarioLogueado.getCargo().equalsIgnoreCase("Administrador")) {
                if (!isRunningProcess()) {
                    //se carga contenido de proveedores a splitBottom
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(MainApp.class.getResource("view/proveedores.fxml"));
                    splitBottom = (AnchorPane) loader.load();

                    //se pasa el objeto de la clase inicio.
                    ControladoraProveedores ctrlProveedor = loader.getController();
                    ctrlProveedor.setControlIni(this);

                    //se añade el contenido de proveedores al split inferior
                    splitAbs.getItems().set(1, splitBottom);
                } else {
                    mostrarVentanaRunningProcess();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void mostrarVentanaAddProveedores() {
        try {
            if (!isRunningProcess()) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(MainApp.class.getResource("view/proveedorAdd.fxml"));
                splitBottom = (AnchorPane) loader.load();

                ControladoraProveedorAdd controller = loader.getController();

                //se pasa objeto de tipo controlador inicio
                controller.setControladoraIni(this);

                if (proveedorToEditar != null) {
                    controller.mostrarDatos(proveedorToEditar);
                }

                //se añade el contenido de perfiles al split inferior
                splitAbs.getItems().set(1, splitBottom);

            } else {
                mostrarVentanaRunningProcess();
            }
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }


    @FXML
    public void mostrarPanelInventario() {
        try {
            if (usuarioLogueado.getCargo().equalsIgnoreCase("Administrador")) {
                if (!isRunningProcess()) {
                    //se carga contenido de inventario a splitBottom
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(MainApp.class.getResource("view/inventario.fxml"));
                    splitBottom = (AnchorPane) loader.load();

                    //se pasa el objeto de la clase inicio.
                    ControladoraInventario ctrlInventario = loader.getController();
                    ctrlInventario.setControlIni(this);


                    //se añade el contenido de inventario al split inferior
                    splitAbs.getItems().set(1, splitBottom);
                } else {
                    mostrarVentanaRunningProcess();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void mostrarVentanaAddInventario() {
        try {
            if (!isRunningProcess()) {

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(MainApp.class.getResource("view/inventarioAddProducto.fxml"));
                splitBottom = (AnchorPane) loader.load();

                ControladoraInventarioAddProducto controller = loader.getController();

                //se pasa objeto de tipo controlador inicio
                controller.setControladoraIni(this);

                if (productoToEditar != null) {
                    controller.mostrarDatos(productoToEditar);
                }

                //se añade el contenido de perfiles al split inferior
                splitAbs.getItems().set(1, splitBottom);

            } else {
                mostrarVentanaRunningProcess();
            }

        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }


    @FXML
    public void mostrarPanelReportes() {
        try {
            if (usuarioLogueado.getCargo().equalsIgnoreCase("Administrador")) {
                if (!isRunningProcess()) {
                    //se carga contenido de reporte a splitBottom
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(MainApp.class.getResource("view/reporte.fxml"));
                    splitBottom = (AnchorPane) loader.load();

                    //se pasa objeto de tipo controlador inicio
                    ControladoraReporte controller = loader.getController();
                    controller.setControladoraIni(this);

                    //se añade el contenido de reporte al split inferior
                    splitAbs.getItems().set(1, splitBottom);
                } else {
                    mostrarVentanaRunningProcess();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void mostrarPanelSoporte() {
        try {
            if (!isRunningProcess()) {
                //se carga contenido de soporte a splitBottom
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(MainApp.class.getResource("view/soporte.fxml"));
                splitBottom = (AnchorPane) loader.load();

                //se añade el contenido de soporte al split inferior
                splitAbs.getItems().set(1, splitBottom);
            } else {
                mostrarVentanaRunningProcess();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //cuando se acciona una ventana de error por proceso que actualmente se esta ejecutando, se llama a la ventana emergente
    @FXML
    public void mostrarVentanaRunningProcess() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/runningProcess.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            //se crea el stage de dialog
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Tarea en ejecución");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initOwner(mainApp.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            //colocar persona en el controlador
            ControladoraRunningProcess controller = loader.getController();

            //se pasa objeto de tipo controlador inicio
            controller.setControladoraIni(this);

            //pasa el stage para mostrar la ventana emergente
            controller.setDialogStage(dialogStage);
            dialogStage.showAndWait();


        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }


    //cuando se acciona aceptar success, se llama a la ventana emergente
    @FXML
    public void mostrarVentanaExitosa() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/exitoMessage.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            //se crea el stage de dialog
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Operación Satisfactoria");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initOwner(mainApp.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            //colocar persona en el controlador
            ControladoraExitoMessage controller = loader.getController();

            //se pasa objeto de tipo controlador inicio
            controller.setControladoraIni(this);

            //pasa el stage para mostrar la ventana emergente
            controller.setDialogStage(dialogStage);
            dialogStage.showAndWait();

        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }


    //metodo para actualizar stock en un cierre de ventana
    public void reestablecerStock() {
        //actualiza cantidades de base de datos.
        for (int i = 0; i < getListaProductosAddingToFacturacion().size(); i++) {
            System.out.println("se reestablece stock");
            int cantidad = bdProducto.cantidadActualizada(getListaProductosAddingToFacturacion().get(i));
            bdProducto.actualizarStock(getListaProductosAddingToFacturacion().get(i), cantidad, "+");
            bdProducto.inactivarActivar(getListaProductosAddingToFacturacion().get(i).getCodigo(),"Activo");
        }
    }





    //************
    //ventanas de ayuda
    //cuando se acciona aceptar success, se llama a la ventana emergente
    public void mostrarVentanaAyudaPerfil() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/ayudaPerfil.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            //se crea el stage de dialog
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Ayuda");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initOwner(mainApp.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            //colocar persona en el controlador
            ControladoraAyudaPerfil controller = loader.getController();

            //pasa el stage para mostrar la ventana emergente
            controller.setDialogStage(dialogStage);
            dialogStage.showAndWait();

        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }


    public void mostrarVentanaAyudaInventario() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/ayudaInventario.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            //se crea el stage de dialog
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Ayuda");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initOwner(mainApp.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            //colocar persona en el controlador
            ControladoraAyudaInventario controller = loader.getController();

            //pasa el stage para mostrar la ventana emergente
            controller.setDialogStage(dialogStage);
            dialogStage.showAndWait();

        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    public void mostrarVentanaAyudaCliente() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/ayudaCliente.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            //se crea el stage de dialog
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Ayuda");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initOwner(mainApp.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            //colocar persona en el controlador
            ControladoraAyudaCliente controller = loader.getController();

            //pasa el stage para mostrar la ventana emergente
            controller.setDialogStage(dialogStage);
            dialogStage.showAndWait();

        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }


}
