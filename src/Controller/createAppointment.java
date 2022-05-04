package Controller;

import DAO.appointmentDAO;
import Main.Main;
import Model.Appointment;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class createAppointment implements Initializable
{

    @FXML private TableView<Appointment> appointmentTableView;
    @FXML public TableColumn<Appointment, Integer> apptIDColumn;
    @FXML public TableColumn<Appointment, String> titleColumn;
    @FXML public TableColumn<Appointment, String> descriptionColumn;
    @FXML public TableColumn<Appointment, String> locCol;
    @FXML public TableColumn<Appointment, String> typeCol;
    @FXML public TableColumn<Appointment, Date> startCol;
    @FXML public TableColumn<Appointment, Date> endCol;
    @FXML public TableColumn<Appointment, Date> createDateCol;
    @FXML public TableColumn<Appointment, String> createdBy;
    @FXML public TableColumn<Appointment, String> lastUpdate;
    @FXML public TableColumn<Appointment, Integer> customerID;
    @FXML public TableColumn<Appointment, Integer> userID;
    @FXML public TableColumn<Appointment, Integer> contactID;


    private ObservableList<Appointment> appointments;

    public createAppointment() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            appointmentDAO.selectAppointments();

            appointmentTableView.setItems(Appointment.getAllAppointments());
            apptIDColumn.setCellValueFactory(new PropertyValueFactory<>("Appointment_ID"));
            titleColumn.setCellValueFactory(new PropertyValueFactory<>("Title"));
            descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("Description"));
            locCol.setCellValueFactory(new PropertyValueFactory<>("Location"));
            typeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
            startCol.setCellValueFactory(new PropertyValueFactory<>("Start"));
            endCol.setCellValueFactory(new PropertyValueFactory<>("End"));
            createDateCol.setCellValueFactory(new PropertyValueFactory<>("Create_Date"));
            createdBy.setCellValueFactory(new PropertyValueFactory<>("Created_By"));
            lastUpdate.setCellValueFactory(new PropertyValueFactory<>("Last_Updated_By"));
            customerID.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
            userID.setCellValueFactory(new PropertyValueFactory<>("User_ID"));
            contactID.setCellValueFactory(new PropertyValueFactory<>("Contact_ID"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addCustomerMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("/View/CreateCustomerForm.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
