package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Timestamp;
import java.time.LocalDate;

public class Customer {
    private static final ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    private int customerID;
    private String customerName;
    private String address;
    private String postalCode;
    private String phoneNumber;
    private LocalDate createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private int divID;

    public Customer() {

    }

    public static ObservableList<Customer> getAllCustomers() {
        return allCustomers;
    }

    public static void addCustomer(Customer newCustomer) {
        allCustomers.add(newCustomer);
    }
    public static void deleteCustomer(Customer customer) {
        allCustomers.remove(customer);
    }

    public int getCustomerID(){
        return customerID;
    }

    public String getCustomerName(){
        return customerName;
    }

    public String getAddress(){
        return address;
    }

    public String getPostalCode(){
        return postalCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public int getDivID(){
        return divID;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setDivID(int divID) {
        this.divID = divID;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
