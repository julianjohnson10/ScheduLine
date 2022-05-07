package DAO;


import Model.Customer;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
            customer.setCreateDate((LocalDateTime) results.getObject("Create_Date"));
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
            return results.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void deleteCustomer(int customerID) throws SQLException{
        String sqlStatement = "DELETE FROM customers WHERE Customer_ID = ?";
        PreparedStatement statement = connection.prepareStatement(sqlStatement);
        statement.setInt(1, customerID);
        statement.executeUpdate();
    }

    public static int createCustomer(String customer_name, String address, String postal_code, String phone) throws SQLException {

        LocalDate date = LocalDate.now(); // Use a date picker.
        LocalTime time = LocalTime.ofSecondOfDay(LocalTime.now().toSecondOfDay());
        LocalDateTime localDateTime = LocalDateTime.of(date, time);

        String sqlStatement = "INSERT INTO Customers(Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES(?,?,?,?,?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sqlStatement);

        statement.setString(1, customer_name);
        statement.setString(2, address);
        statement.setString(3, postal_code);
        statement.setString(4, phone);
        statement.setObject(5, localDateTime);

        String createdBy = "test";

        String lastUpdatedBy = "test";
        int divID = 1;

        statement.setString(6, createdBy);
        statement.setObject(7, localDateTime);
        statement.setString(8, lastUpdatedBy);
        statement.setInt(9, divID);


        Customer customer = new Customer();
        customer.setCustomerID(customerID());
        customer.setCustomerName(customer_name);
        customer.setAddress(address);
        customer.setPostalCode(postal_code);
        customer.setPhoneNumber(phone);
        customer.setCreateDate(localDateTime);
        customer.setCreatedBy(createdBy);
        customer.setLastUpdate(Timestamp.from(Instant.now()));
        customer.setLastUpdatedBy(lastUpdatedBy);
        customer.setDivID(divID);
        Customer.addCustomer(customer);


        return statement.executeUpdate();
    }

    public static int customerID() throws SQLException {
        String sqlStatement = "SELECT MAX(Customer_ID) FROM customers";
        PreparedStatement statement = connection.prepareStatement(sqlStatement);
        ResultSet result = statement.executeQuery();
        int customerID = 0;
        while (result.next()) {
            customerID = result.getInt("MAX(Customer_ID)");
        }
        return customerID + 1;
    }
}
