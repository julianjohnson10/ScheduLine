package Model;

import DAO.appointmentDAO;
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
    private String description;
    private String location;
    private String type;
    private Date startDate;
    private Date endDate;
    private Date createdDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private int customerId;
    private int userId;
    private int contactId;

    public Appointment(int apptId, String title, String description, String location, String type, Date startDate, Date endDate, Date createdDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy, int customerId, int userId, int contactId) {
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

    public static void mainMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Main.Main.class.getResource("/View/createAppointment.fxml"));
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

}

