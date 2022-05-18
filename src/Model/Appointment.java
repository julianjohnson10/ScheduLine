package Model;

import DAO.appointmentDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Appointment {
    private static final ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    private int apptId;
    private String title;
    private String description;
    private String location;
    private String contactName;
    private String type;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
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
    public static ObservableList<Appointment> getWeekly() throws SQLException {
        ObservableList<Appointment> appts = appointmentDAO.getWeekly();
        allAppointments.setAll(appts);
        return allAppointments;
    }
    public static ObservableList<Appointment> getMonthly() throws SQLException {
        ObservableList<Appointment> appts = appointmentDAO.getMonthly();
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
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mma");
        ZonedDateTime zdt =  startDate.atZone(ZoneId.of("UTC"));
        ZonedDateTime zdtTarget = zdt.withZoneSameInstant(ZoneId.systemDefault());
        LocalDateTime localDateTime = zdtTarget.toLocalDateTime();
        return localDateTime.format(format);
    }

    public LocalDateTime getStartLDT() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mma");
        ZonedDateTime zdt =  startDate.atZone(ZoneId.of("UTC"));
        ZonedDateTime zdtTarget = zdt.withZoneSameInstant(ZoneId.systemDefault());
        return zdtTarget.toLocalDateTime();
    }


    public String getEndDate() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mma");
        ZonedDateTime zdt =  endDate.atZone(ZoneId.of("UTC"));
        ZonedDateTime zdtTarget = zdt.withZoneSameInstant(ZoneId.systemDefault());
        LocalDateTime localDateTime = zdtTarget.toLocalDateTime();
        return localDateTime.format(format);
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

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDateTime endDate) {
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

