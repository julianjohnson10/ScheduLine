package DAO;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static DAO.JDBC_Connector.connection;

public abstract class customerDAO {

    /**
     * Creates customer records.
     */
//    public static int create() throws SQLException {
//        String sqlStatement = "INSERT INTO customers(Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES(?,?,?,?,?,?,?,?,?,?)";
//        PreparedStatement statement = connection.prepareStatement(sqlStatement);
//    }


    public static void selectCustomer() throws SQLException {
        String sqlStatement = "SELECT * FROM customers";
        PreparedStatement statement = connection.prepareStatement(sqlStatement);
        ResultSet results = statement.executeQuery();

        while (results.next()) {
            int userId = results.getInt("User_ID");
            String userName = results.getString("User_Name");
        }
    }

    public static boolean getLogin(String username, String password) throws SQLException {
        String sqlStatement = "SELECT * FROM users WHERE User_ID = ? AND Password = ?";
        PreparedStatement statement = connection.prepareStatement(sqlStatement);
        statement.setString(1, username);
        statement.setString(2, password);
        ResultSet results = statement.executeQuery();

        try {
            if(results.next()) {
                int userId = results.getInt("User_ID");

                String pwd = results.getString("Password");
                System.out.println(userId + " " + pwd);
                return true;
            }
            else if(!results.next()){
                return false;
            }
            else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
