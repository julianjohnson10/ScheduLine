package Model;

import DAO.customerDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;

/**
 * Customer class.
 */
public class Customer {

    /**
     * List of all customers.
     */
    private static final ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    /**
     * Customer ID.
     */
    private int customerID;

    /**
     * Customer name.
     */
    private String customerName;

    /**
     * Customer address.
     */
    private String address;

    /**
     * Customer postal code.
     */
    private String postalCode;

    /**
     * Customer phone number.
     */
    private String phoneNumber;

    /**
     * Customer country.
     */
    private String country;

    /**
     * Customer created date.
     */
    private LocalDate createDate;

    /**
     * Created By string.
     */
    private String createdBy;

    /**
     * Timestamp for the customer last updated.
     */
    private Timestamp lastUpdate;

    /**
     * String for the user who last updated the customer.
     */
    private String lastUpdatedBy;

    /**
     * Customers state/province.
     */
    private String stateProvince;

    /**
     * Customer city.
     */
    private String city;

    /**
     * Customer Division ID.
     */
    private int divID;

    /**
     * Gets all customers using the customerDAO, and sets the list with all values.
     * @return Array list of customers.
     * @throws SQLException exception handler.
     */
    public static ObservableList<Customer> getAllCustomers() throws SQLException {
        ObservableList<Customer> customers = customerDAO.getAllCustomers();
        allCustomers.setAll(customers);
        return allCustomers;
    }

    /**
     * Adds a customer to the allcustomers list.
     * @param newCustomer the customer to add.
     */
    public static void addCustomer(Customer newCustomer) {
        allCustomers.add(newCustomer);
    }

    /**
     * Removes a customer from the allcustomers list.
     * @param customer the customer to delete.
     */
    public static void deleteCustomer(Customer customer) {
        allCustomers.remove(customer);
    }

    /**
     * Getter for customer ID.
     * @return the customer ID.
     */
    public int getCustomerID(){
        return customerID;
    }

    /**
     * Getter for state/province names.
     * @return the state or province.
     */
    public String getStateProvince(){
        return stateProvince;
    }

    /**
     * Getter for customer name.
     * @return the customer's name.
     */
    public String getCustomerName(){
        return customerName;
    }

    /**
     * Getter for the address
     * @return customer address
     */
    public String getAddress(){
        return address;
    }

    /**
     * Getter for postal code.
     * @return postal code.
     */
    public String getPostalCode(){
        return postalCode;
    }

    /**
     * Getter for phone number.
     * @return the customer's phone number.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Getter for country.
     * @return the customers country.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Address setter
     * @param address the address to set.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Setter for created date.
     * @param createDate the created date to set.
     */
    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    /**
     * Setter for customer ID.
     * @param customerID the customer ID to set.
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * Setter for customer name.
     * @param customerName the customer name to set.
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Setter for division ID.
     * @param divID the division ID to set.
     */
    public void setDivID(int divID) {
        this.divID = divID;
    }

    public int getDivID() {
        return divID;
    }

    /**
     * Setter for last update time.
     * @param lastUpdate the timestamp to set.
     */
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Setter for phone number.
     * @param phoneNumber the phone number to set.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Setter for postal code.
     * @param postalCode the postal code to set.
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Setter for country.
     * @param country the country to set.
     */
    public void setCountry(String country){
        this.country = country;
    }

    /**
     * Setter for state/province data.
     * @param stateProvince the state/province to set.
     */
    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }

}
