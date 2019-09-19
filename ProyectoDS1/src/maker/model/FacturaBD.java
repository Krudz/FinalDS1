package maker.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;


public class FacturaBD {

    private Connection connection;

    public FacturaBD() {
        connection = new ConnectionFactory().getConnection();

    }

    //para el consecutivo de la factura
    public int contarRegistros() {
        String sql = "SELECT count(*) FROM ds1.public.factura";
        System.out.println(sql);
        int cantidad = -1;

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


    public void registrar(Factura factura) {

        String sql = "INSERT INTO ds1.public.factura VALUES( '" + factura.getNumeroRef() + "'," + "'" + factura.getDatosCliente() + "'," + "'" + factura.getFechaDeVenta().toString() +
                "'," + "'" + factura.getMedioDePago() + "'," + "'" + factura.getTotalVenta() + "'," + "'" + factura.getUtilidad() + "'," + "'" + factura.getVendedor() + "',"
                + "'" + factura.getHora() + "')";

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



    public ObservableList<Factura> consultar(LocalDate fechaFacturaS) {

        ObservableList<Factura> facturasPorDia = FXCollections.observableArrayList();

        String sql = "SELECT * FROM ds1.public.Factura WHERE " + "\"fechaFactura\"" + " = '" + fechaFacturaS + "'";

        System.out.println(sql);

        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                String numeroRef = resultSet.getString("nroRefFactura");
                String cliente = resultSet.getString("docIdCl");
                LocalDate fecha = resultSet.getDate("fechaFactura").toLocalDate();
                String medioPago = resultSet.getString("medioPago");
                double total = resultSet.getDouble("totalFactura");
                double utilidad = resultSet.getDouble("utilidad");
                String vendedor = resultSet.getString("vendedor");
                String hora = resultSet.getString("hora");

                Factura factura = new Factura(numeroRef, cliente, fecha, medioPago, total, utilidad, vendedor,hora);

                facturasPorDia.add(factura);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return facturasPorDia;
    }

    //retorna listaItem de acuerdo al numero de ref de factura
    public ObservableList<ItemFactura> subconsultaFactura(String nroFac) {
        ObservableList<ItemFactura> listaItems = FXCollections.observableArrayList();
        String sql = "SELECT * FROM ds1.public.Factura NATURAL JOIN ds1.public.itemfactura WHERE " + "\"nroRefFactura\"" + " like '" + nroFac + "'";

        System.out.println(sql);

        try {
            PreparedStatement statement = connection.prepareStatement(sql);


            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                String nroRefFactura = resultSet.getString("nroRefFactura");
                String codRefProd = resultSet.getString("codRefProd");
                String descripcion = resultSet.getString("descripcion");
                String valorUnitario = resultSet.getString("valorUnitario");
                String cantidad = resultSet.getString("cantidad");
                String subtotal = resultSet.getString("subtotal");

                ItemFactura item = new ItemFactura(nroRefFactura, codRefProd, descripcion, valorUnitario, cantidad, subtotal);
                listaItems.add(item);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return listaItems;
    }


}
