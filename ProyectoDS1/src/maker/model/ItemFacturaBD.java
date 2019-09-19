package maker.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;


public class ItemFacturaBD {

    private Connection connection;

    public ItemFacturaBD() {
        connection = new ConnectionFactory().getConnection();
    }

    public void registrar(ItemFactura item){

        String sql = "INSERT INTO ds1.public.itemfactura VALUES( '" + item.getNroRefFactura() + "'," + "'" + item.getCodRefProd() + "'," + "'" + item.getDescripcion() +
                "'," + "'" + item.getValorUnitario() + "'," + "'" + item.getCantidad() + "'," + "'" + item.getSubtotal() + "')";

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


    //**
    //consulta lista comprados
    public ArrayList<String>listaCompradosFecha(LocalDate fecha){

        ArrayList<String> lista = new ArrayList<>();
        String sql = "SELECT DISTINCT  itemfactura.descripcion " +
                "FROM factura NATURAL JOIN itemfactura WHERE factura." + "\"fechaFactura\" = " + "'" + fecha + "'";
        System.out.println(sql);

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String dato = resultSet.getString(1);
                lista.add(dato);
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return lista;
    }


    //consulta cantidad por cada comprado
    public int cantidad(LocalDate fecha, String itemVendido){
        String sql = "SELECT COUNT(itemfactura.descripcion) from factura NATURAL JOIN itemfactura " +
                "WHERE factura.\"fechaFactura\" = '" + fecha +  "' AND " +
                "itemfactura.descripcion like '" + itemVendido + "'";
        System.out.println(sql);
        int count = 0;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return count;
    }



    //consulta codigo de cada elemento comprado
    public String codigoRef(String itemVendido){
        String sql = "SELECT itemfactura." + "\"codRefProd\" from itemfactura where " +
                " itemfactura.descripcion like " + "'" + itemVendido + "'";

        System.out.println(sql);
        String codigoRef = "";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                codigoRef = resultSet.getString(1);
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return codigoRef;
    }





}
