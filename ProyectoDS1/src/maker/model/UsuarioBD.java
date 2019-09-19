package maker.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;


public class UsuarioBD {

    private Connection connection;

    public UsuarioBD() {
        connection = new ConnectionFactory().getConnection();

    }

    public boolean registrar(Usuario usuario){

        String sql = "INSERT INTO public.Usuario (docIdUs, nombreUs, apellidoUs, fechaNacUs, telefonoUs, generoUs, direccionUs, cargoUs, passwordUs, estado)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        System.out.println(sql);

        return statementSQL(usuario,sql);
    }

    public void actualizar(Usuario usuario) {

        String sql = "UPDATE public.Usuario SET nombreUs=?, apellidoUs=?, fechaNacUs=?, telefonoUs=?, generoUs=?, direccionUs=?, cargoUs=?, passwordUs=?, estado=?" +
                " WHERE docIdUs=?";

        System.out.println(sql);

        updateStatementSQL(usuario, sql);
    }

    public void inactivarActivarUsuario(String identificacion, String datoAI) {

        String sql = "UPDATE ds1.public.usuario SET estado = '" + datoAI + "'  WHERE docidus LIKE '" + identificacion + "'";

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



    public ObservableList<Usuario> consultar(String identificacion){

        String sql = "SELECT * FROM ds1.public.Usuario WHERE docIdUs like '" + identificacion + "' ";

        ObservableList<Usuario> listaUsuarios = FXCollections.observableArrayList();

        System.out.println(sql);

        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                String cedula = resultSet.getString("docIdUs");
                String nombre = resultSet.getString("nombreUs");
                String apellido = resultSet.getString("apellidoUs");
                LocalDate fecha =  resultSet.getDate("fechaNacUs").toLocalDate();
                String telefono = resultSet.getString("telefonoUs");
                String genero = resultSet.getString("generoUs");
                String domicilio = resultSet.getString("direccionUs");
                String tipoDeCuenta = resultSet.getString("cargoUs");
                String contrasenna = resultSet.getString("passwordUs");
                String estado = resultSet.getString("estado");


                Usuario usuario = new Usuario(cedula,nombre,apellido,fecha,genero,domicilio,tipoDeCuenta,contrasenna,telefono, estado);

                listaUsuarios.add(usuario);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            return null;
        }
        return listaUsuarios;
    }



    public ObservableList<Usuario> mostrarTable(){

        ObservableList<Usuario> listaUsuarios = FXCollections.observableArrayList();

        String sql = "SELECT * FROM ds1.public.Usuario";

        System.out.println(sql);


        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                String cedula = resultSet.getString("docIdUs");
                String nombre = resultSet.getString("nombreUs");
                String apellido = resultSet.getString("apellidoUs");
                LocalDate fecha =  resultSet.getDate("fechaNacUs").toLocalDate();
                String telefono = resultSet.getString("telefonoUs");
                String genero = resultSet.getString("generoUs");
                String domicilio = resultSet.getString("direccionUs");
                String tipoDeCuenta = resultSet.getString("cargoUs");
                String contrasenna = resultSet.getString("passwordUs");
                String estado = resultSet.getString("estado");

                Usuario usuario = new Usuario(cedula,nombre,apellido,fecha,genero,domicilio,tipoDeCuenta,contrasenna,telefono, estado);

                listaUsuarios.add(usuario);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            return null;
        }
        return listaUsuarios;
    }





    //se acciona de acuerdo al sql.
    public boolean statementSQL(Usuario usuario, String sql){
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, usuario.getIdentificacion());
            statement.setString(2, usuario.getNombre());
            statement.setString(3, usuario.getApellido());
            statement.setObject(4, usuario.getFechaNacimiento());
            statement.setString(5, usuario.getTelefono());
            statement.setString(6, usuario.getGenero());
            statement.setString(7, usuario.getDomicilio());
            statement.setString(8, usuario.getCargo());
            statement.setString(9, usuario.getContrasenna());
            statement.setString(10, usuario.getEstado());

            statement.execute();
            statement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //se acciona de acuerdo al sql.
    public void updateStatementSQL(Usuario usuario, String sql){
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, usuario.getNombre());
            statement.setString(2, usuario.getApellido());
            statement.setObject(3, usuario.getFechaNacimiento());
            statement.setString(4, usuario.getTelefono());
            statement.setString(5, usuario.getGenero());
            statement.setString(6, usuario.getDomicilio());
            statement.setString(7, usuario.getCargo());
            statement.setString(8, usuario.getContrasenna());
            statement.setString(9, usuario.getEstado());
            statement.setString(10, usuario.getIdentificacion());

            statement.execute();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
