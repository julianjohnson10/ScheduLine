package Controller;

import DAO.appointmentDAO;
import DAO.userDAO;
import Main.Main;
import Model.Appointment;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class appointmentFormController implements Initializable
{

    @FXML public Button createCustomerButton;
    @FXML public TableView<Appointment> appointmentTableView;
    @FXML private TableColumn<Appointment, SimpleIntegerProperty> apptIDColumn;
    @FXML private TableColumn<Appointment, SimpleStringProperty> titleColumn;
    @FXML private TableColumn<Appointment, SimpleStringProperty> descriptionColumn;
    @FXML private TableColumn<Appointment, SimpleStringProperty> locCol;
    @FXML private TableColumn<Appointment, SimpleStringProperty> typeCol;
    @FXML private TableColumn<Appointment, SimpleObjectProperty<Date>> startCol;
    @FXML private TableColumn<Appointment, SimpleObjectProperty<Date>> endCol;
    @FXML private TableColumn<Appointment, SimpleObjectProperty<Date>> createDateCol;
    @FXML private TableColumn<Appointment, SimpleStringProperty> createdBy;
    @FXML private TableColumn<Appointment, SimpleObjectProperty<Timestamp>> lastUpdate;
    @FXML private TableColumn<Appointment, SimpleStringProperty> lastUpdatedBy;
    @FXML private TableColumn<Appointment, SimpleIntegerProperty> customerID;
    @FXML private TableColumn<Appointment, SimpleIntegerProperty> userID;
    @FXML private TableColumn<Appointment, SimpleIntegerProperty> contactID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            appointmentTableView.setItems(appointmentDAO.getAllAppts());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        apptIDColumn.setCellValueFactory(new PropertyValueFactory<>("apptId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        locCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        createDateCol.setCellValueFactory(new PropertyValueFactory<>("createdDate"));
        createdBy.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        lastUpdate.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
        lastUpdatedBy.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));
        customerID.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userID.setCellValueFactory(new PropertyValueFactory<>("userId"));
        contactID.setCellValueFactory(new PropertyValueFactory<>("contactId"));
    }

    @FXML
    private void createCustomer(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("/View/CreateCustomerForm.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
