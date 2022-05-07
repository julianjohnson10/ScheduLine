package Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Customer {
    private static final ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    private final SimpleIntegerProperty customerID;
    private final SimpleStringProperty customerName;
    private final SimpleStringProperty address;
    private final SimpleStringProperty postalCode;
    private final SimpleStringProperty phoneNumber;
    private final SimpleObjectProperty<LocalDateTime> createDate;
    private final SimpleStringProperty createdBy;
    private final SimpleObjectProperty<Timestamp> lastUpdate;
    private final SimpleStringProperty lastUpdatedBy;
    private final SimpleIntegerProperty divID;

    public Customer(){
        this.customerID = new SimpleIntegerProperty();
        this.customerName = new SimpleStringProperty();
        this.address = new SimpleStringProperty();
        this.postalCode = new SimpleStringProperty();
        this.phoneNumber = new SimpleStringProperty();
        this.createDate = new SimpleObjectProperty<>();
        this.createdBy = new SimpleStringProperty();
        this.lastUpdate = new SimpleObjectProperty<>();
        this.lastUpdatedBy = new SimpleStringProperty();
        this.divID = new SimpleIntegerProperty();
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
        return customerID.get();
    }

    public String getCustomerName(){
        return customerName.get();
    }

    public String getAddress(){
        return address.get();
    }

    public String getPostalCode(){
        return postalCode.get();
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public LocalDateTime getCreateDate() {
        return createDate.get();
    }

    public String getCreatedBy() {
        return createdBy.get();
    }

    public Timestamp getLastUpdate() {
        return lastUpdate.get();
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy.get();
    }

    public int getDivID(){
        return divID.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate.set(createDate);
    }

    public void setCustomerID(int customerID) {
        this.customerID.set(customerID);
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy.set(createdBy);
    }

    public void setCustomerName(String customerName) {
        this.customerName.set(customerName);
    }

    public void setDivID(int divID) {
        this.divID.set(divID);
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate.set(lastUpdate);
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy.set(lastUpdatedBy);
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    public void setPostalCode(String postalCode) {
        this.postalCode.set(postalCode);
    }
}
