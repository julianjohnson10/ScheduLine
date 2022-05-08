package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;

public class Appointment {
    private static final ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    private final int apptId;
    private final String title;
    private final String description;
    private final String location;
    private final String type;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final LocalDate createdDate;
    private final String createdBy;
    private final Timestamp lastUpdate;
    private final String lastUpdatedBy;
    private final int customerId;
    private final int userId;
    private final int contactId;

    public Appointment(int apptId, String title, String description, String location, String type, LocalDate startDate, LocalDate endDate, LocalDate createdDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy, int customerId, int userId, int contactId) {
        this.apptId = apptId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }

    /**
     *
     * @param event Event is handled on the login controller. when the login is true, mainMenu is called.
     * @throws IOException exceptions
     * ERROR: Runtime Exception: Error resolving onAction='#createAppt'
     */
    public static void mainMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Main.Main.class.getResource("/View/MainForm.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static ObservableList<Appointment> getAllAppointments() {
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

    public String getType() {
        return type;
    }

    public LocalDate getStartDate() {
        return startDate;
    }


    public LocalDate getEndDate() {
        return endDate;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Timestamp getLastUpdate(){
        return lastUpdate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getContactId() {
        return contactId;
    }

    public int getUserId() {
        return userId;
    }
}

