package Model;

import DAO.appointmentDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Appointment Class. Defines the model for an appointment object.
 */
public class Appointment {

    /**
     * Array list of all appointment objects.
     */
    private static final ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    /**
     * Appointment ID.
     */
    private int apptId;

    /**
     * Appointment Title.
     */
    private String title;

    /**
     * Appointment description.
     */
    private String description;

    /**
     * Appointment location
     */
    private String location;

    /**
     * Appointment's contact name.
     */
    private String contactName;

    /**
     * Appointment type.
     */
    private String type;

    /**
     * Appointment start date.
     */
    private LocalDateTime startDate;

    /**
     * Appointment end date.
     */
    private LocalDateTime endDate;

    /**
     * Appointment's customer id.
     */
    private int customerId;

    /**
     * Appointment's user ID.
     */
    private int userId;

    /**
     * Appointment's contact ID.
     */
    private int contactId;

    /**
     * @return Array list of all appointments.
     * @throws SQLException SQL Exception handler.
     */
    public static ObservableList<Appointment> getAllAppointments() throws SQLException {
        ObservableList<Appointment> appts = appointmentDAO.getAllAppts();
        allAppointments.setAll(appts);
        return allAppointments;
    }

    /**
     * @return Array list of all appointments this week.
     * @throws SQLException SQL Exception handler.
     */
    public static ObservableList<Appointment> getWeekly() throws SQLException {
        ObservableList<Appointment> appts = appointmentDAO.getWeekly();
        allAppointments.setAll(appts);
        return allAppointments;
    }

    /**
     * @return Array list of all appointments this month.
     * @throws SQLException SQL Exception handler.
     */
    public static ObservableList<Appointment> getMonthly() throws SQLException {
        ObservableList<Appointment> appts = appointmentDAO.getMonthly();
        allAppointments.setAll(appts);
        return allAppointments;
    }

    /**
     * Adds an appointment to the appointments array.
     * @param newAppointment The appointment to add.
     */
    public static void addAppointment(Appointment newAppointment) {
        allAppointments.add(newAppointment);
    }

    /**
     * Removes an appointment from the allAppointments array.
     * @param newAppointment the appointment to remove.
     */
    public static void deleteAppointment(Appointment newAppointment) {
        allAppointments.remove(newAppointment);
    }

    /**
     * @return the Appointment ID.
     */
    public int getApptId() {
        return apptId;
    }

    /**
     * @return The appointment Title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return The appointment Description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return The appointment location.
     */
    public String getLocation() {
        return location;
    }

    /**
     * @return The appointment's contact name.
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * @return The appointment Type.
     */
    public String getType() {
        return type;
    }

    /**
     * @return the start date converted from TZ @ UTC to the system default TZ, to a string.
     */
    public String getStartDate() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy h:mma");
        ZonedDateTime zdt =  startDate.atZone(ZoneId.of("UTC"));
        ZonedDateTime zdtTarget = zdt.withZoneSameInstant(ZoneId.systemDefault());
        LocalDateTime localDateTime = zdtTarget.toLocalDateTime();
        return localDateTime.format(format);
    }

    /**
     * @return The startdate, in localdatetime.
     */
    public LocalDateTime getStartLDT() {
        ZonedDateTime zdt =  startDate.atZone(ZoneId.of("UTC"));
        ZonedDateTime zdtTarget = zdt.withZoneSameInstant(ZoneId.systemDefault());
        return zdtTarget.toLocalDateTime();
    }

    /**
     * @return The enddate, in localdatetime.
     */
    public LocalDateTime getEndLDT() {
        ZonedDateTime zdt =  endDate.atZone(ZoneId.of("UTC"));
        ZonedDateTime zdtTarget = zdt.withZoneSameInstant(ZoneId.systemDefault());
        return zdtTarget.toLocalDateTime();
    }

    /**
     * @return The end date, converted from UTC zone to the system default.
     */
    public String getEndDate() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy h:mma");
        ZonedDateTime zdt =  endDate.atZone(ZoneId.of("UTC"));
        ZonedDateTime zdtTarget = zdt.withZoneSameInstant(ZoneId.systemDefault());
        LocalDateTime localDateTime = zdtTarget.toLocalDateTime();
        return localDateTime.format(format);
    }

    /**
     * @return The customer ID number.
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * @return The user ID number.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the appointment ID.
     * @param apptId The appointment ID.
     */
    public void setApptId(int apptId) {
        this.apptId = apptId;
    }

    /**
     * Sets the appointment Title.
     * @param title appointment title.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets the appointment description.
     * @param description the appointment description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets the appointment location.
     * @param location Appointment location.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Sets the contact name.
     * @param contactName The appointment contact name.
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * Sets the appointment type.
     * @param type Appointment type.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Sets the start date.
     * @param startDate the appointment start date.
     */
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    /**
     * Sets the end date for the appointment.
     * @param endDate Appointment end date.
     */
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    /**
     * Sets the customer ID of the appointment.
     * @param customerId Appointment customer ID.
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Sets the appointment user ID.
     * @param userId The appointment's UserID.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Sets the appointment's contact ID.
     * @param contactId Appointment contact ID.
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }
}

