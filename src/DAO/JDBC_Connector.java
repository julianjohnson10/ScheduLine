package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class for connecting to the database.
 */
public abstract class JDBC_Connector {

    /**
     * Protocol is JDBC.
     */
    private static final String protocol = "jdbc";

    /**
     * SQL vendor.
     */
    private static final String vendor = ":mysql:";

    /**
     * IP of the DB connection. Localhost in our case.
     */
    private static final String location = "//127.0.0.1/";

    /**
     * Name of the database.
     */
    private static final String databaseName = "client_schedule";

    /**
     * Connection string.
     */
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL

    /**
     * Driver reference.
     */
    private static final String driver = "com.mysql.cj.jdbc.Driver";

    /**
     * Username for the db connection.
     */
    private static final String userName = "sqlUser";

    /**
     * Password for the DB connection.
     */
    private static final String password = "Passw0rd!";

    /**
     * The DB connection object.
     */
    public static Connection connection;

    /**
     * Method for opening the DB connection.
     * @throws ClassNotFoundException exception handler.
     * @throws SQLException exception handler.
     * @throws Exception handler for other exceptions.
     */
    public static void openConnection() throws ClassNotFoundException, SQLException, Exception
    {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(jdbcUrl, userName, password); // Connection Reference
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
    }

    /**
     * Method for closing the connection.
     * @throws ClassNotFoundException exception handler.
     * @throws SQLException handler for SQL errors.
     * @throws Exception handler for all other exceptions.
     */
    public static void closeConnection() throws ClassNotFoundException, SQLException, Exception
    {
        try {
            connection.close();
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
    }

}
