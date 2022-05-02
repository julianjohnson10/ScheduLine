package Model;

import java.sql.Date;
import java.sql.Timestamp;

public class Customer {
    private int customerID;
    private String customerName;
    private String address;
    private String postalCode;
    private String phoneNumber;
    private Date createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private int divID;

    public Customer(int customerID, String customerName, String address, String postalCode, String phoneNumber, Date createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy, int divID){
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divID = divID;
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

    public Date getCreateDate() {
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

    public void setCreateDate(Date createDate) {
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
