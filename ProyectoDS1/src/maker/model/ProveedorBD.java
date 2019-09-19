package maker.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;


public class ProveedorBD {

    private Connection connection;

    public ProveedorBD() {
        connection = new ConnectionFactory().getConnection();

    }

    public ObservableList<Proveedor> mostrartable() {

        ObservableList<Proveedor> listaProveedores = FXCollections.observableArrayList();

        String sql = "SELECT codigoProv, nitProv, nombreProv, direccionProv , telefonoProv, emailProv, estado FROM ds1.public.proveedor";

        System.out.println(sql);

        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                String codigo = resultSet.getString("codigoProv");
                String nit = resultSet.getString("nitProv");
                String nombre = resultSet.getString("nombreProv");
                String direccion = resultSet.getString("direccionProv");
                String telefono = resultSet.getString("telefonoProv");
                String email = resultSet.getString("emailProv");
                String estado = resultSet.getString("estado");

                Proveedor proveedor = new Proveedor(codigo, nit, nombre, direccion, telefono, email, estado);

                listaProveedores.add(proveedor);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return listaProveedores;
    }

    public boolean registrar(Proveedor proveedor) {

        String sql = "INSERT INTO public.Proveedor (codigoProv, nitProv, nombreProv, direccionProv, telefonoProv, emailProv, estado)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?)";

        System.out.println(sql);

        return statementSQL(proveedor, sql);
    }

    public void actualizar(Proveedor proveedor) {

        String sql = "UPDATE public.Proveedor SET nitProv=?, nombreProv=?, direccionProv=?, telefonoProv=?, emailProv=?, estado=?" +
                " WHERE codigoProv=?";

        System.out.println(sql);

        statementSQLUPDATE(proveedor, sql);
    }

    public void inactivarActivar(String codigoP, String datoAI) {

        String sql = "UPDATE ds1.public.Proveedor SET estado = '" + datoAI + "'  WHERE codigoProv LIKE '" + codigoP + "'";

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

    public ObservableList<Proveedor> busquedaProveedor(String codigoP) {

        ObservableList<Proveedor> listaProveedores = FXCollections.observableArrayList();

        String sql = "SELECT * FROM ds1.public.Proveedor WHERE codigoProv like '%" + codigoP + "%' ";

        System.out.println(sql);

        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                String codigo = resultSet.getString("codigoProv");
                String nit = resultSet.getString("nitProv");
                String nombre = resultSet.getString("nombreProv");
                String direccion = resultSet.getString("direccionProv");
                String telefono = resultSet.getString("telefonoProv");
                String email = resultSet.getString("emailProv");
                String estado = resultSet.getString("estado");


                Proveedor proveedor = new Proveedor(codigo, nit, nombre, direccion, telefono, email, estado);

                listaProveedores.add(proveedor);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return listaProveedores;
    }

    //se acciona de acuerdo al sql.
    public boolean statementSQL(Proveedor proveedor, String sql) {
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, proveedor.getCodigo());
            statement.setString(2, proveedor.getNit());
            statement.setString(3, proveedor.getNombre());
            statement.setString(4, proveedor.getDireccion());
            statement.setString(5, proveedor.getTelefono());
            statement.setString(6, proveedor.getEmail());
            statement.setString(7, proveedor.getEstado());

            statement.execute();
            statement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public void statementSQLUPDATE(Proveedor proveedor, String sql) {
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, proveedor.getNit());
            statement.setString(2, proveedor.getNombre());
            statement.setString(3, proveedor.getDireccion());
            statement.setString(4, proveedor.getTelefono());
            statement.setString(5, proveedor.getEmail());
            statement.setString(6, proveedor.getEstado());
            statement.setString(7, proveedor.getCodigo());

            statement.execute();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    //devuelve la cantidad ultima utilizada
    public int ultimoUtilizado() {
        String sql = "SELECT MAX(codigoprov) FROM ds1.public.Proveedor";

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
            return 1091;
        }
        return cantidad;
    }
}
