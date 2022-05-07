package Model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;

public class Appointment {
    private static final ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    private final SimpleIntegerProperty apptId;
    private final SimpleStringProperty title;
    private final SimpleStringProperty description;
    private final SimpleStringProperty location;
    private final SimpleStringProperty type;
    private final SimpleObjectProperty<Date> startDate;
    private final SimpleObjectProperty<Date> endDate;
    private final SimpleObjectProperty<Date> createdDate;
    private final SimpleStringProperty createdBy;
    private final SimpleObjectProperty<Timestamp> lastUpdate;
    private final SimpleStringProperty lastUpdatedBy;
    private final SimpleIntegerProperty customerId;
    private final SimpleIntegerProperty userId;
    private final SimpleIntegerProperty contactId;

    public Appointment() {
        this.apptId = new SimpleIntegerProperty();
        this.title = new SimpleStringProperty();
        this.description = new SimpleStringProperty();
        this.location = new SimpleStringProperty();
        this.type = new SimpleStringProperty();
        this.startDate = new SimpleObjectProperty<>();
        this.endDate = new SimpleObjectProperty<>();
        this.createdDate = new SimpleObjectProperty<>();
        this.createdBy = new SimpleStringProperty();
        this.lastUpdate = new SimpleObjectProperty<>();
        this.lastUpdatedBy = new SimpleStringProperty();
        this.customerId = new SimpleIntegerProperty();
        this.userId = new SimpleIntegerProperty();
        this.contactId = new SimpleIntegerProperty();
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
        return apptId.get();
    }

    public void setApptId(int apptId) {
        this.apptId.set(apptId);
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public String getLocation() {
        return location.get();
    }

    public void setLocation(String location) {
        this.location.set(location);
    }

    public String getType() {
        return type.get();
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public Date getStartDate() {
        return startDate.get();
    }

    public void setStartDate(Date startDate) {
        this.startDate.set(startDate);
    }

    public Date getEndDate() {
        return endDate.get();
    }

    public void setEndDate(Date endDate) {
        this.endDate.set(endDate);
    }

    public Date getCreatedDate() {
        return createdDate.get();
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate.set(createdDate);
    }

    public String getCreatedBy() {
        return createdBy.get();
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy.set(createdBy);
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy.get();
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy.set(lastUpdatedBy);
    }

    public int getCustomerId() {
        return customerId.get();
    }

    public void setCustomerId(int customerId) {
        this.customerId.set(customerId);
    }

    public int getContactId() {
        return contactId.get();
    }

    public void setContactId(int contactId) {
        this.contactId.set(contactId);
    }

    public int getUserId() {
        return userId.get();
    }

    public void setUserId(int userId) {
        this.userId.set(userId);
    }

    public Timestamp getLastUpdate() {
        return lastUpdate.get();
    }
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate.set(lastUpdate);
    }
}

