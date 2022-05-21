package Controller;

import DAO.appointmentDAO;
import DAO.contactDAO;
import DAO.customerDAO;
import Model.Appointment;
import Model.User;
import Utilities.date_time;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import static Utilities.alertError.raiseAlert;

/**
 * Controller Class for creating appointments.
 */
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
    public ComboBox<String> startTime;

    /**
     * Combobox of local end times that map to EST.
     */
    public ComboBox<String> endTime;

    /**
     * Combobox of customer IDs
     */
    public ComboBox<Integer> customerBox;

    /**
     * Text field for appointment ID. Disabled.
     */
    public TextField apptIDField;

    /**
     * ComboBox of userIDs to choose from on the Create Appointment Form.
     */
    public ComboBox<Integer> userBox;

    /**
     * Takes the user back to the main menu.
     * @throws IOException exception handler.
     */
    @FXML
    public void cancel() throws IOException {

        Stage stage = (Stage) cancelButton.getScene().getWindow();
        MainFormController.mainMenu(stage);
    }

    /**
     * Creates the appointment when the "Create" button is pressed on the Create Appointment Form.
     * Time overlap logic occurs here, for appointment creation cases.
     * @throws SQLException exception handler for SQL errors.
     * @throws IOException exception handler.
     */
    @FXML
    public void createAppt() throws SQLException, IOException {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        ObservableList<Appointment> allAppointments = Appointment.getAllAppointments();


        if(customerBox.getValue() == null||titleField.getText().isEmpty()||descriptionField.getText().isEmpty()||typeField.getText().isEmpty()||locationField.getText().isEmpty()||contactBox.getValue() == null||datePicker.getValue() == null||startTime.getValue().isEmpty()||endTime.getValue().isEmpty()) {
            raiseAlert("Error", "Fields must not be left empty or blank.", Alert.AlertType.ERROR);
        }
        else{

            String contact_name = contactBox.getValue();
            Integer contactID = contactDAO.getContactID(contact_name);
            Integer userID = User.getUser().userId;
            DateTimeFormatter format = DateTimeFormatter.ofPattern("h:mma");
            Integer customerID = customerBox.getValue();
            LocalDate date = datePicker.getValue();
            LocalTime start_time = LocalTime.parse(startTime.getValue(),format);
            LocalTime end_time = LocalTime.parse(endTime.getValue(),format);

            LocalDateTime startDate = LocalDateTime.from(LocalDateTime.of(date,start_time).atZone(ZoneId.of("UTC")));
            LocalDateTime endDate = LocalDateTime.from(LocalDateTime.of(date,end_time).atZone(ZoneId.of("UTC")));
            LocalDateTime now = LocalDateTime.now();

            if(startDate.isAfter(endDate)){
                raiseAlert("Error","The appointment end time must be after the start time.", Alert.AlertType.ERROR);
                return;
            }
            if(startDate.isEqual(endDate)){
                raiseAlert("Error","An appointment cannot start and end at the same time.", Alert.AlertType.ERROR);
                return;
            }
            if(startDate.isBefore(now)){
                raiseAlert("Error", "The appointment must start in the future.", Alert.AlertType.ERROR);
                return;
            }

            for(Appointment appointment: allAppointments) {
                Integer custID = appointment.getCustomerId();
                LocalDateTime aStart = appointment.getStartLDT();
                LocalDateTime aEnd = appointment.getEndLDT();

                //Check if customer ID matches, and don't compare the same appointment.
                boolean b = custID.equals(customerID);
                if (b && ((startDate.isAfter(aStart) || startDate.isEqual(aStart)) && startDate.isBefore(aEnd))) {
                    raiseAlert("Overlapping Appointments", "This appointment overlaps with another one of customer " + customerID + "'s appointments", Alert.AlertType.ERROR);
                    return;
                }
                if (b && (endDate.isAfter(aStart) && (endDate.isBefore(aEnd)) || endDate.isEqual(aEnd))) {
                    raiseAlert("Overlapping Appointments", "This appointment overlaps with another one of customer " + customerID + "'s appointments", Alert.AlertType.ERROR);
                    return;
                }
                if (b && ((startDate.isBefore(aStart) || startDate.isEqual(aStart)) && (endDate.isAfter(aEnd) || endDate.isEqual(aEnd)))) {
                    raiseAlert("Overlapping Appointments", "This appointment overlaps with another one of customer " + customerID + "'s appointments", Alert.AlertType.ERROR);
                    return;
                }
            }
            appointmentDAO.createAppt(titleField.getText(), descriptionField.getText(), locationField.getText(),typeField.getText(),datePicker.getValue(),LocalTime.parse(startTime.getValue(),format), LocalTime.parse(endTime.getValue(),format), customerBox.getValue(),userID,contactID);
            MainFormController.mainMenu(stage);
        }
    }

    /**
     * Initializes the create appointments controller. Sets initial values on the create appointment form.
     * ERROR: NullPointerException: Cannot invoke "String.isEmpty()" because the return value of "javafx.scene.control.SingleSelectionModel.getSelectedItem()" is null
     * @param url url
     * @param resourceBundle resourcebundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            startTime.setItems(date_time.startList);
            endTime.setItems(date_time.endList);
            contactBox.setItems(contactDAO.getContactNames());
            customerBox.setItems(customerDAO.getCustomerIDs());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
