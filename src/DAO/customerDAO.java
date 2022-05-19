package DAO;

import Model.Customer;
import Model.User;
import Utilities.date_time;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.time.*;

import static DAO.JDBC_Connector.connection;

public abstract class customerDAO {

    public static ObservableList<Customer> getAllCustomers() throws SQLException {
        String sqlStatement = "SELECT * FROM customers " +
                "LEFT JOIN first_level_divisions " +
                "ON first_level_divisions.Division_ID = customers.Division_ID " +
                "LEFT JOIN countries " +
                "ON countries.Country_ID = first_level_divisions.Country_ID";
        ObservableList<Customer> customerList = FXCollections.observableArrayList();
        PreparedStatement statement = connection.prepareStatement(sqlStatement);
        ResultSet results = statement.executeQuery();

        while (results.next()) {
            Customer customer = new Customer();
            customer.setCustomerID(results.getInt("Customer_ID"));
            customer.setCustomerName(results.getString("Customer_Name"));
            customer.setAddress(results.getString("Address"));
            customer.setPostalCode(results.getString("Postal_Code"));
            customer.setCountry(results.getString("Country"));
            customer.setStateProvince(results.getString("Division"));
            customer.setPhoneNumber(results.getString("Phone"));
            customer.setDivID(results.getInt("Division_ID"));
            customerList.add(customer);
        }
        return customerList;
    }

    public static void deleteCustomer(int customerID) throws SQLException{
        String sqlStatement = "DELETE FROM customers WHERE Customer_ID = ?";
        PreparedStatement statement = connection.prepareStatement(sqlStatement);
        statement.setInt(1, customerID);
        statement.executeUpdate();
    }

    public static void createCustomer(String customer_name, String address, String city, String town, String postal_code, String phone, String division) throws SQLException {

        LocalDate date = LocalDate.now(); // Use a date picker.
        LocalTime time = LocalTime.ofSecondOfDay(LocalTime.now().toSecondOfDay());
        LocalDate localDateTime = LocalDate.from(LocalDateTime.of(date, time));

        String newAddress = address.trim();
        String newCity = city.trim();
        String newTown = town.trim();

        String sqlStatement = "INSERT INTO customers(Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES(?,?,?,?,?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sqlStatement);

        statement.setString(1, customer_name);

        if(town.isEmpty()){
            statement.setString(2, newAddress + ", " + newCity);
        }
        else {
            statement.setString(2, newAddress + ", " + newCity + ", " + newTown);
        }

        statement.setString(3, postal_code);
        statement.setString(4, phone);
        statement.setTimestamp(5, date_time.setTimestamp());
        statement.setString(6, User.getUser().getUserName());
        statement.setTimestamp(7, date_time.setTimestamp());
        statement.setString(8, User.getUser().getUserName());
        statement.setInt(9, divisionDAO.getDivisionID(division));

        Customer customer = new Customer();
        customer.setCustomerName(customer_name);
        customer.setPostalCode(postal_code);
        customer.setPhoneNumber(phone);
        customer.setCreateDate(localDateTime);
        customer.setLastUpdate(Timestamp.from(Instant.now()));
        Customer.addCustomer(customer);
        statement.executeUpdate();
    }

    public static void updateCustomer(Integer customerID, String Customer_Name, String Address, String city, String town, String postal_code, String phone, String last_updated_by, Integer divisionID) throws SQLException {
        String sqlStatement = "UPDATE Customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";
        PreparedStatement statement = connection.prepareStatement(sqlStatement);
        statement.setString(1, Customer_Name);

        String x = Address.trim() + ", " + city.trim();
        if(town == null){
            statement.setString(2, x);
        }
        else if(town.isEmpty()){
            statement.setString(2, x);
        }
        else {
            statement.setString(2, Address.trim() + ", " + city.trim() + ", " + town.trim());
        }

        statement.setString(3, postal_code);
        statement.setString(4, phone);
        statement.setTimestamp(5, date_time.setTimestamp());
        statement.setString(6, last_updated_by);
        statement.setInt(7, divisionID);
        statement.setInt(8, customerID);
        statement.executeUpdate();
    }

    public static ObservableList<Integer> getCustomerIDs() throws SQLException {
        String sqlStatement = "SELECT * FROM customers";
        ObservableList<Integer> customerIDs = FXCollections.observableArrayList();
        PreparedStatement statement = connection.prepareStatement(sqlStatement);
        ResultSet results = statement.executeQuery();

        while (results.next()) {
            customerIDs.add(results.getInt("Customer_ID"));
        }
        return customerIDs;
    }
}
