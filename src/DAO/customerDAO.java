package DAO;


import Model.Appointment;
import Model.Customer;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static DAO.JDBC_Connector.connection;

public abstract class customerDAO {

    public static ObservableList<Customer> getAllCustomers() throws SQLException {
        String sqlStatement = "SELECT * FROM customers";

        PreparedStatement statement = connection.prepareStatement(sqlStatement);
        ResultSet results = statement.executeQuery();

        Customer customer;
        while (results.next()) {
            customer = new Customer();
            customer.setCustomerID(results.getInt("Customer_ID"));
            customer.setCustomerName(results.getString("Customer_Name"));
            customer.setAddress(results.getString("Address"));
            customer.setPostalCode(results.getString("Postal_Code"));
            customer.setPhoneNumber(results.getString("Phone"));
            customer.setCreateDate(results.getDate("Create_Date"));
            customer.setCreatedBy(results.getString("Created_By"));
            customer.setLastUpdate(results.getTimestamp("Last_Update"));
            customer.setLastUpdatedBy(results.getString("Last_Updated_By"));
            customer.setDivID(results.getInt("Division_ID"));
            Customer.addCustomer(customer);
        }
        return Customer.getAllCustomers();
    }

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
