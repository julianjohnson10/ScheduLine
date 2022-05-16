package Controller;

import DAO.appointmentDAO;
import DAO.contactDAO;
import DAO.customerDAO;
import DAO.userDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import static Utilities.alertError.raiseAlert;

public class CreateAppointmentController implements Initializable {

    /**
     * Cancel button on Create Appointment Form
     */
    public Button cancelButton;

    /**
     * Title textfield on Create Appointment Form
     */
    public TextField titleField;

    /**
     * ComboBox of contact names from the database.
     */
    public ComboBox<String> contactBox;

    /**
     * Apply button on create appointment form.
     */
    public Button createApptButton;

    /**
     * Description textarea for the user to enter large descriptions.
     */
    public TextArea descriptionField;

    /**
     * Location textfield.
     */
    public TextField locationField;

    /**
     * Type textfield.
     */
    public TextField typeField;

    /**
     * Datepicker on create appointment form.
     */
    public DatePicker datePicker;

    /**
     * Combobox of local start times that map to EST.
     */
    public ComboBox<LocalTime> startTime;

    /**
     * Combobox of local end times that map to EST.
     */
    public ComboBox<LocalTime> endTime;

    /**
     * Combobox of customer IDs
     */
    public ComboBox<Integer> customerBox;
    public TextField apptIDField;
    public ComboBox<Integer> userBox;


    @FXML
    public void cancel(ActionEvent event) throws IOException {
        MainFormController.mainMenu(event);
    }

    @FXML
    public void createAppt(ActionEvent event) throws SQLException, IOException {
        String contact_name = contactBox.getValue();
        Integer contactID = contactDAO.getContactID(contact_name);
        if(titleField.getText().isEmpty()|descriptionField.getText().isEmpty()|locationField.getText().isEmpty()|typeField.getText().isEmpty()|contactBox.getValue()==null){
            raiseAlert("Error", "Appointment fields cannot be empty", Alert.AlertType.ERROR);
        }
        else{
            appointmentDAO.createAppt(titleField.getText(), descriptionField.getText(), locationField.getText(),contactBox.getValue(),typeField.getText(),datePicker.getValue(),startTime.getValue(),endTime.getValue(), customerBox.getValue(),userBox.getValue(),contactID);
            MainFormController.mainMenu(event);
        }

    }

    /**
     * ERROR: NullPointerException: Cannot invoke "String.isEmpty()" because the return value of "javafx.scene.control.SingleSelectionModel.getSelectedItem()" is null
     * @param url url
     * @param resourceBundle resourcebundle for languages
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            contactBox.setItems(contactDAO.getContactNames());
            customerBox.setItems(customerDAO.getCustomerIDs());
            userBox.setItems(userDAO.getUserIDs());

            startTime.setValue(LocalTime.now());

            endTime.setValue(LocalTime.now());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
