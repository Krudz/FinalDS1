package maker.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ProductoBD {

    private Connection connection;

    public ProductoBD() {
        connection = new ConnectionFactory().getConnection();
    }

    public boolean registrar(Producto producto) {

        String sql = "INSERT INTO public.Producto (codRefProd, codigoProv, tipoFormato, nomArtista, album, generoAl, costoProd, precioVentaProd, cantidadStock, estado)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        System.out.println(sql);

        return statementSQL(producto, sql);
    }

    public boolean actualizar(Producto producto) {

        String sql = "UPDATE public.Producto SET codigoProv=?, tipoFormato=?, nomArtista=?, album=?, generoAl=?, costoProd=?, precioVentaProd=?, cantidadStock=?, estado=?" +
                " WHERE codRefProd=?";

        System.out.println(sql);

        return statementSQLUPDATE(producto, sql);
    }


    public ObservableList<Producto> mostrartable() {

        ObservableList<Producto> listaProductos = FXCollections.observableArrayList();

        String sql = "SELECT codrefprod, codigoprov, tipoformato, nomartista, album, generoal, costoprod, precioventaprod, cantidadstock, estado FROM ds1.public.producto";

        System.out.println(sql);

        return accionarSQL(listaProductos, sql);
    }


    public void inactivarActivar(String codigoRef, String datoAI) {

        String sql = "UPDATE ds1.public.Producto SET estado = '" + datoAI + "' WHERE codRefProd LIKE '" + codigoRef + "'";

        System.out.println(sql);

        try {
            PreparedStatement statement = connection.prepareStatement(sql);


            statement.execute();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    public Producto consultar(String codigoP) {


        String sql = "SELECT * FROM ds1.public.Producto WHERE codRefProd like '%" + codigoP + "%' ";

        System.out.println(sql);

        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                String codigo = resultSet.getString("codRefProd");
                String proveedor = resultSet.getString("codigoProv");
                String tipo = resultSet.getString("tipoFormato");
                String artista = resultSet.getString("nomArtista");
                String album = resultSet.getString("album");
                String genero = resultSet.getString("generoAl");
                String coste = resultSet.getString("costoProd");
                String precioVenta = resultSet.getString("precioVentaProd");
                String stock = resultSet.getString("cantidadStock");
                String estado = resultSet.getString("estado");


                Producto producto = new Producto(codigo, proveedor, tipo, artista, album, genero, coste, precioVenta, stock, estado);

                return producto;
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return null;
    }


    //se acciona de acuerdo al sql.
    public boolean statementSQL(Producto producto, String sql) {
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, producto.getCodigo());
            statement.setString(2, producto.getUnProveedor());
            statement.setString(3, producto.getTipo());
            statement.setObject(4, producto.getArtista());
            statement.setString(5, producto.getAlbum());
            statement.setString(6, producto.getGenero());
            statement.setString(7, producto.getCoste());
            statement.setString(8, producto.getPrecioVenta());
            statement.setString(9, producto.getCantidad());
            statement.setString(10, producto.getEstado());

            statement.execute();
            statement.close();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public boolean statementSQLUPDATE(Producto producto, String sql) {
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, producto.getUnProveedor());
            statement.setString(2, producto.getTipo());
            statement.setObject(3, producto.getArtista());
            statement.setString(4, producto.getAlbum());
            statement.setString(5, producto.getGenero());
            statement.setString(6, producto.getCoste());
            statement.setString(7, producto.getPrecioVenta());
            statement.setString(8, producto.getCantidad());
            statement.setString(9, producto.getEstado());
            statement.setString(10, producto.getCodigo());


            statement.execute();
            statement.close();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    //descontar de stock
    public void actualizarStock(Producto item, int cantidad, String signo) {

        String sql = "UPDATE public.Producto SET cantidadstock = " + (cantidad + signo + 1) +
                " WHERE codRefProd like '" + item.getCodigo() + "'";

        System.out.println(sql);

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.execute();

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    //devuelve la cantidad actual del item
    public int cantidadActualizada(Producto item) {
        String sql = "SELECT cantidadstock FROM ds1.public.producto WHERE codrefprod like '" + item.getCodigo() + "'";

        System.out.println(sql);

        int cantidad = 0;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                cantidad = resultSet.getInt(1);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return cantidad;
    }



    //devuelve la cantidad ultima utilizada
    public int ultimoUtilizado() {
        String sql = "SELECT MAX(codrefprod) FROM ds1.public.producto";

        System.out.println(sql);

        int cantidad = 1;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                cantidad += Integer.parseInt(resultSet.getString(1));
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
           return 1;
        }
        return cantidad;
    }


    //busqueda de producto.
    public ObservableList<Producto> busquedaProducto(String codRef) {

        ObservableList<Producto> listaProductos = FXCollections.observableArrayList();

        String sql = "SELECT codrefprod, codigoprov, tipoformato, nomartista, album, generoal, costoprod, precioventaprod, cantidadstock, estado FROM ds1.public.producto " +
                "WHERE codrefprod LIKE '%" + codRef + "%'";

        System.out.println(sql);

        return accionarSQL(listaProductos, sql);
    }


    //metodo para retornar un observable list en consulta
    public ObservableList<Producto> accionarSQL(ObservableList<Producto> listaProductos, String sql) {
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                String codigo = resultSet.getString("codRefProd");
                String proveedor = resultSet.getString("codigoProv");
                String tipo = resultSet.getString("tipoFormato");
                String artista = resultSet.getString("nomArtista");
                String album = resultSet.getString("album");
                String genero = resultSet.getString("generoAl");
                String coste = resultSet.getString("costoProd");
                String precioVenta = resultSet.getString("precioVentaProd");
                String stock = resultSet.getString("cantidadStock");
                String estado = resultSet.getString("estado");

                Producto producto = new Producto(codigo, proveedor, tipo, artista, album, genero, coste, precioVenta, stock, estado);

                listaProductos.add(producto);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return listaProductos;
    }


}