package Model;

import DAO.appointmentDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class Appointment {
    private static final ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    private int apptId;
    private String title;
    private String description;
    private String location;
    private String contactName;
    private String type;
    private String startDate;
    private String endDate;
    private int customerId;
    private int userId;
    private int contactId;

    public Appointment() {

    }

    public static ObservableList<Appointment> getAllAppointments() throws SQLException {
        ObservableList<Appointment> appts = appointmentDAO.getAllAppts();
        allAppointments.setAll(appts);
        return allAppointments;
    }

    public static void addAppointment(Appointment newAppointment) {
        allAppointments.add(newAppointment);
    }

    public static void deleteAppointment(Appointment newAppointment) {
        allAppointments.remove(newAppointment);
    }


    public int getApptId() {
        return apptId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }


    public String getLocation() {
        return location;
    }

    public String getContactName() {
        return contactName;
    }

    public String getType() {
        return type;
    }

    public String getStartDate() {
        return startDate;
    }


    public String getEndDate() {
        return endDate;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getUserId() {
        return userId;
    }

    public int getContactId() {
        return contactId;
    }

    public void setApptId(int apptId) {
        this.apptId = apptId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }
}

