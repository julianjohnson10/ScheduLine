package Controller;

import DAO.appointmentDAO;
import DAO.contactDAO;
import DAO.customerDAO;
import DAO.userDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ResourceBundle;

import static Utilities.alertError.raiseAlert;

public class CreateAppointmentController implements Initializable {

    public Button cancelButton;
    public TextField titleField;
    public ComboBox<String> contactBox;
    public Button createApptButton;
    public TextArea descriptionField;
    public TextField locationField;
    public TextField typeField;
    public DatePicker datePicker;
    public ComboBox<LocalTime> startTime;
    public ComboBox<LocalTime> endTime;
    public TextField apptIDField;
    public ComboBox<Integer> customerBox;

    @FXML
    public void cancel(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/View/MainForm.fxml"));
        Scene scene = new Scene(parent);
        Stage window = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.centerOnScreen();
        window.show();
    }

    @FXML
    public void createAppt(ActionEvent event) throws SQLException, IOException {
        int contactID = contactDAO.getContactID(contactBox.getValue());
        int userID = userDAO.getUserInfo().getUserId();
        if(titleField.getText().isEmpty()|descriptionField.getText().isEmpty()|locationField.getText().isEmpty()|typeField.getText().isEmpty()|contactBox.getValue().isEmpty()){
            raiseAlert("Error", "Appointment fields cannot be empty", Alert.AlertType.ERROR);
        }
        else{
            appointmentDAO.createAppt(titleField.getText(), descriptionField.getText(), locationField.getText(),contactBox.getValue(),typeField.getText(),datePicker.getValue(),startTime.getValue(),endTime.getValue(), contactID,userID);
            MainFormController.mainMenu(event);
        }

    }


        /**
         * ERROR: NullPointerException: Cannot invoke "String.isEmpty()" because the return value of "javafx.scene.control.SingleSelectionModel.getSelectedItem()" is null
         * @param url
         * @param resourceBundle
         */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            contactBox.setItems(contactDAO.getContactNames());
            apptIDField.setText(String.valueOf(appointmentDAO.getAppointmentID()));
            customerBox.setItems(customerDAO.getCustomerIDs());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
