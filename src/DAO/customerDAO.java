package DAO;


import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static DAO.JDBC_Connector.connection;

public abstract class customerDAO {

    public static ObservableList<Customer> getAllCustomers() throws SQLException {
        String sqlStatement = "SELECT * FROM customers";
        ObservableList<Customer> customerList = FXCollections.observableArrayList();
        PreparedStatement statement = connection.prepareStatement(sqlStatement);
        ResultSet results = statement.executeQuery();

        while (results.next()) {
            Customer customer = new Customer();
            customer.setCustomerID(results.getInt("Customer_ID"));
            customer.setCustomerName(results.getString("Customer_Name"));
            customer.setAddress(results.getString("Address"));
            customer.setPostalCode(results.getString("Postal_Code"));
            customer.setPhoneNumber(results.getString("Phone"));
            customer.setDivID(results.getInt("Division_ID"));
            customerList.add(customer);
        }
        return customerList;
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

    public static void createCustomer(String customer_name, String address, String postal_code, String phone, String country, String division) throws SQLException {

        LocalDate date = LocalDate.now(); // Use a date picker.
        LocalTime time = LocalTime.ofSecondOfDay(LocalTime.now().toSecondOfDay());
        LocalDate localDateTime = LocalDate.from(LocalDateTime.of(date, time));

        String sqlStatement = "INSERT INTO customers(Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES(?,?,?,?,?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sqlStatement);

        statement.setString(1, customer_name);
        statement.setString(2, address);
        statement.setString(3, postal_code);
        statement.setString(4, phone);
        statement.setObject(5, LocalDateTime.now());

        statement.setString(6, userDAO.getUserInfo().getUserName());
        statement.setObject(7, LocalDateTime.now());
        statement.setString(8, userDAO.getUserInfo().getUserName());
        statement.setInt(9, divisionDAO.getDivisionID(division));


        Customer customer = new Customer();
        customer.setCustomerName(customer_name);
        customer.setAddress(address);
        customer.setPostalCode(postal_code);
        customer.setPhoneNumber(phone);

        customer.setCreateDate(localDateTime);
//        customer.setCreatedBy(createdBy);
        customer.setLastUpdate(Timestamp.from(Instant.now()));
//        customer.setLastUpdatedBy(lastUpdatedBy);
//        customer.setDivID(divID);
        Customer.addCustomer(customer);


        statement.executeUpdate();
    }

    public static int getCustomerID() throws SQLException {
        String sqlStatement = "SELECT MAX(Customer_ID) FROM customers";
        PreparedStatement statement = connection.prepareStatement(sqlStatement);
        ResultSet result = statement.executeQuery();
        int customerID = 0;
        while (result.next()) {
            customerID = result.getInt("MAX(Customer_ID)");
        }
        return customerID;
    }
}
