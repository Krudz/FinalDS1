package maker.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    public Connection getConnection(){
        try {
            return DriverManager.getConnection("jdbc:postgresql:ds1", "postgres", "fence0gras");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
