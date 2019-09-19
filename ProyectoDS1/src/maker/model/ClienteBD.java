package maker.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;


public class ClienteBD {

    private Connection connection;

    public ClienteBD() {
        connection = new ConnectionFactory().getConnection();

    }

    public ObservableList<Cliente> mostrartable(){

        ObservableList<Cliente> clientes = FXCollections.observableArrayList();

        String sql = "SELECT docIdCl, nombreCl, apellidoCl, fechaNacCl , generoCl, direccionCl, telefonoCl, emailCl, estado FROM ds1.public.Cliente";

        System.out.println(sql);

        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                String cedula = resultSet.getString("docIdCl");
                String nombre = resultSet.getString("nombreCl");
                String apellido = resultSet.getString("apellidoCl");
                LocalDate fecha =  resultSet.getDate("fechaNacCl").toLocalDate();
                String genero = resultSet.getString("generoCl");
                String domicilio = resultSet.getString("direccionCl");
                String telefono = resultSet.getString("telefonoCl");
                String email = resultSet.getString("emailCl");
                String estado = resultSet.getString("estado");

                Cliente cliente = new Cliente(cedula,nombre,apellido,fecha,genero,domicilio,telefono,email,estado);

                clientes.add(cliente);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return clientes;
    }

    public void registrar(Cliente cliente){

        String sql = "INSERT INTO public.Cliente (docIdCl, nombreCl, apellidoCl, fechaNacCl, generoCl, direccionCl, telefonoCl, emailCl, estado)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        System.out.println(sql);

        statementSQL(cliente,sql);
    }

    public boolean actualizar(Cliente cliente){

        String sql = "UPDATE public.Cliente SET nombreCl=?,apellidoCl=?,fechaNacCl=?,generoCl=?,direccionCl=?,telefonoCl=?,emailCl=?, estado=? WHERE docIdCl=?";

        System.out.println(sql);

        return statementSQLUPDATE(cliente,sql);
    }

    public void inactivarActivar(String identificacion, String datoAI){

        String sql = "UPDATE ds1.public.Cliente SET estado = '" + datoAI + "'  WHERE docIdCl LIKE '" + identificacion + "'";

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

    public Cliente consultar(String identificacion){

        String sql = "SELECT * FROM ds1.public.Cliente WHERE docIdCl like '%" + identificacion + "%' ";

        System.out.println(sql);

        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                String cedula = resultSet.getString("docIdCl");
                String nombre = resultSet.getString("nombreCl");
                String apellido = resultSet.getString("apellidoCl");
                LocalDate fecha =  resultSet.getDate("fechaNacCl").toLocalDate();
                String genero = resultSet.getString("generoCl");
                String domicilio = resultSet.getString("direccionCl");
                String telefono = resultSet.getString("telefonoCl");
                String email = resultSet.getString("emailCl");
                String estado = resultSet.getString("estado");

                Cliente cliente = new Cliente(cedula,nombre,apellido,fecha,genero,domicilio,telefono,email,estado);

                return cliente;

            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        System.out.println("se retorna el cliente null");
        return null;
    }


    public boolean statementSQL(Cliente cliente, String sql){
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, cliente.getIdentificacion());
            statement.setString(2, cliente.getNombre());
            statement.setString(3, cliente.getApellido());
            statement.setObject(4, cliente.getFechaNacimiento());
            statement.setString(5, cliente.getGenero());
            statement.setString(6, cliente.getDomicilio());
            statement.setString(7, cliente.getTelefono());
            statement.setString(8, cliente.getEmail());
            statement.setString(9, cliente.getEstado());

            statement.execute();
            statement.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean statementSQLUPDATE(Cliente cliente, String sql){
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, cliente.getNombre());
            statement.setString(2, cliente.getApellido());
            statement.setObject(3, cliente.getFechaNacimiento());
            statement.setString(4, cliente.getGenero());
            statement.setString(5, cliente.getDomicilio());
            statement.setString(6, cliente.getTelefono());
            statement.setString(7, cliente.getEmail());
            statement.setString(8, cliente.getEstado());
            statement.setString(9, cliente.getIdentificacion());

            statement.execute();
            statement.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public ObservableList<Cliente> buscarClientes(String identificacion){

        ObservableList<Cliente> listaClientes = FXCollections.observableArrayList();

        String sql = "SELECT * FROM ds1.public.Cliente WHERE docIdCl like '%" + identificacion + "%' ";

        System.out.println(sql);

        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                String cedula = resultSet.getString("docIdCl");
                String nombre = resultSet.getString("nombreCl");
                String apellido = resultSet.getString("apellidoCl");
                LocalDate fecha =  resultSet.getDate("fechaNacCl").toLocalDate();
                String genero = resultSet.getString("generoCl");
                String domicilio = resultSet.getString("direccionCl");
                String telefono = resultSet.getString("telefonoCl");
                String email = resultSet.getString("emailCl");
                String estado = resultSet.getString("estado");

                Cliente cliente = new Cliente(cedula,nombre,apellido,fecha,genero,domicilio,telefono,email, estado);

                listaClientes.add(cliente);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return listaClientes;
    }




}
