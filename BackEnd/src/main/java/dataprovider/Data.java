package dataprovider;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Data {

    private static final String URL = "jdbc:postgresql://ep-patient-flower-a20t22c9.eu-central-1.aws.neon.tech/APa?user=APa_owner&password=KcfmTdl5U3Bp&sslmode=require";
    private Connection connection = null;

    private static Data instance;

    private Data() {
    }

    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            instance.createConnection();
        }
        return instance.connection;
    }


    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void rollback () {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /// private's

    private void createConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL);
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            System.err.println(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Data getInstance() {
        if (instance == null) {
            synchronized (Data.class) {
                if (instance == null) {
                    instance = new Data();
                }
            }
        }
        return instance;
    }

}
