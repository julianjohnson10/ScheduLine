package Controller;

import DAO.*;
import Main.Main;
import Model.Appointment;
import Model.Customer;
import Model.User;
import javafx.animation.FadeTransition;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

import static Utilities.alertError.raiseAlert;

public class MainFormController implements Initializable {
    /**
     * TableView for customer data.
     */
    public TableView<Customer> customerTableView;

    /**
     * Customer ID column.
     */
    public TableColumn<Customer, Integer> customerID;

    /**
     * Customer Name column.
     */
    public TableColumn<Customer, String> customerName;

    /**
     * Customer address column.
     */
    public TableColumn<Customer, String> address;

    /**
     * Customer postal code column.
     */
    public TableColumn<Customer, String> postal_code;

    /**
     * Customer phone number column.
     */
    public TableColumn<Customer, String> phone;

    /**
     * Customer Division ID column.
     */
    public TableColumn<Customer, SimpleIntegerProperty> divID;

    public TableView<Appointment> appointmentTableView;
    public TableColumn<Appointment, Integer> apptIDColumn;
    public TableColumn<Appointment, String> titleColumn;
    public TableColumn<Appointment, String> descriptionColumn;
    public TableColumn<Appointment, String> locCol;
    public TableColumn<Appointment, String> typeCol;
    public TableColumn<Appointment, ZonedDateTime> startCol;
    public TableColumn<Appointment, ZonedDateTime> endCol;
    public TableColumn<Appointment, Integer> customerIDCol;
    public TableColumn<Appointment, Integer> userIDCol;

    /**
     * Create Appointments button
     */
    public Button createAppt;

    /**
     * Update Appointments button
     */
    public Button updateAppt;

    /**
     * Delete Appointments button
     */
    public Button deleteApptButton;

    /**
     * Create Customer button
     */
    public Button createCustomer;

    /**
     * Update Customer button
     */
    public Button updateCustomer;

    /**
     * Delete Customer button
     */
    public Button deleteCustomer;
    public Tab customerTab;
    public Tab appointmentTab;
    public ComboBox<String> countryBox;
    public TableColumn<Appointment, String> contactCol;
    public TextField apptIDField;
    public ComboBox<Integer> customerBox;
    public TextField titleField;
    public TextArea descriptionField;
    public TextField typeField;
    public TextField locationField;
    public ComboBox<String> contactBox;
    public DatePicker datePicker;
    public ComboBox<LocalTime> startTime;
    public ComboBox<LocalTime> endTime;
    public ComboBox<String> stateProvince;
    public Button applyUpdateCustomer;
    public Button applyUpdateAppt;
    public TextField customerIDField;
    public TextField nameTextField;
    public TextField cityTextField;
    public TextField addressTextField;
    public TextField postalTextField;
    public TextField phoneTextField;
    public ComboBox<Integer> userBox;
    public Button clearCustomer;
    public Label deletedLabel;
    public TabPane tabPane;

    private final FadeTransition fadeOut = new FadeTransition(Duration.seconds(4.0));
    public Label deletedLabelAppt;
    public RadioButton allRadio;
    public RadioButton weekRadio;
    public RadioButton monthRadio;
    public ToggleGroup toggleGroup;
    public TextField townField;


    @FXML
    public void deleteCustomer() throws SQLException {
        Customer selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
        if(selectedCustomer != null){
            //Delete selected customer
            Optional<ButtonType> result = raiseAlert("Are you sure?", "Are you sure you want to delete this customer?", Alert.AlertType.CONFIRMATION);
            if(result.isPresent() && result.get() == ButtonType.OK) {
                if(!appointmentDAO.checkCustomer(selectedCustomer.getCustomerID())){
                    customerDAO.deleteCustomer(selectedCustomer.getCustomerID());
                    Customer.deleteCustomer(selectedCustomer);

                    deletedLabel.setText(selectedCustomer.getCustomerName() + " has been deleted!"); //sets the label to show which customer was deleted. Plays a fade transition.
                    fadeOut.setNode(deletedLabel);
                    fadeOut.playFromStart();

                    customerTableView.setItems(customerDAO.getAllCustomers());
                }
                else {
                    raiseAlert("Error",selectedCustomer.getCustomerName() + " still has appointments and cannot be deleted. \n\nPlease delete their appointments first before deleting.", Alert.AlertType.ERROR);
                }
            }
        }
        else {
            raiseAlert("Error", "You have not selected a customer", Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void deleteAppt() throws SQLException {
        Appointment selectedAppointment = appointmentTableView.getSelectionModel().getSelectedItem();
        if(selectedAppointment != null){
            //Delete selected appointment
            Optional<ButtonType> result = raiseAlert("Are you sure?", "Are you sure you want to cancel this appointment?", Alert.AlertType.CONFIRMATION);
            if(result.isPresent() && result.get() == ButtonType.OK) {
                appointmentDAO.deleteAppt(selectedAppointment.getApptId());
                Appointment.deleteAppointment(selectedAppointment);
                deletedLabelAppt.setText("Appointment " + selectedAppointment.getApptId() + " of type " + selectedAppointment.getType() + " has been canceled!"); //sets the label to show which customer was deleted. Plays a fade transition.
                fadeOut.setNode(deletedLabelAppt);
                fadeOut.playFromStart();
                appointmentTableView.setItems(appointmentDAO.getAllAppts());


            }
        }
        else {
            raiseAlert("Error", "You have not selected an appointment", Alert.AlertType.ERROR);
        }
    }


    /**
     *
     * @param event Event is handled on the login controller. when the login is true, mainMenu is called.
     * @throws IOException exceptions
     * ERROR: Runtime Exception: Error resolving onAction='#createAppt'
     */
    @FXML
    private void createCustomerMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/View/CreateCustomerForm.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
    }

    @FXML
    private void createApptMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/View/CreateAppointmentForm.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
        stage.centerOnScreen();
    }

    /**
     * ERROR: RuntimeException: java.lang.reflect.InvocationTargetException: forgot to add an event handler for clearing customer update fields.
     * @param event Event is handled on the login controller. when the login is true, mainMenu is called.
     * @throws IOException exceptions
     * ERROR: Runtime Exception: Error resolving onAction='#createAppt'
     */
    public static void mainMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/View/MainForm.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
    }

    /**
     * Clears the selections from both tableviews when switching tabs.
     */
    public void resetTable() {
        try{
            if(appointmentTableView.getSelectionModel().getSelectedItem() != null) {
                appointmentTableView.getSelectionModel().clearSelection();
            }
            else if(customerTableView.getSelectionModel().getSelectedItem() != null) {
                customerTableView.getSelectionModel().clearSelection();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateApptFields() {
        Appointment selectedAppointment = appointmentTableView.getSelectionModel().getSelectedItem();
        if(selectedAppointment != null){
            Integer apptId = selectedAppointment.getApptId();
            Integer customerID = selectedAppointment.getCustomerId();
            Integer userID = selectedAppointment.getUserId();
            String title = selectedAppointment.getTitle();
            String description = selectedAppointment.getDescription();
            String type = selectedAppointment.getType();
            String location = selectedAppointment.getLocation();
            String contact = selectedAppointment.getContactName();

            LocalDate date = selectedAppointment.getStartDate();

            apptIDField.setText(String.valueOf(apptId));
            customerBox.setValue(customerID);
            titleField.setText(title);
            descriptionField.setText(description);
            typeField.setText(type);
            locationField.setText(location);
            contactBox.setValue(contact);

            datePicker.setValue(date);

            startTime.setValue(LocalTime.now());
            endTime.setValue(LocalTime.now());
            userBox.setValue(userID);
        }
        else {
            raiseAlert("Select an appointment:", "Please select an appointment to update.", Alert.AlertType.INFORMATION);
        }

    }

    public void updateCustomerFields() {
        Customer selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            Integer customerID = selectedCustomer.getCustomerID();
            String state_province = selectedCustomer.getStateProvince();
            String customerName = selectedCustomer.getCustomerName();
            String address = selectedCustomer.getAddress().trim();
            String postalCode = selectedCustomer.getPostalCode();
            String phone = selectedCustomer.getPhoneNumber();
            String country = selectedCustomer.getCountry();

            countryBox.setValue(country);
            stateProvince.setValue(state_province);

            if(!Objects.equals(countryBox.getValue(), "UK")){
                townField.clear();
                townField.setDisable(true);
            }

            if (address.contains(",")) {
                String[] addRegex = address.split(", ", 3);
                String newAddress = addRegex[0].trim();


                String town = null;
                String city = null;
                try {
                    city = addRegex[1].trim();
                    town = addRegex[2].trim();
                } catch (Exception ignored) {
                }
                addressTextField.setText(newAddress);
                cityTextField.setText(city);
                townField.setText(town);
            }
            else{
                addressTextField.setText(address);
                townField.clear();
                cityTextField.clear();
            }

            customerIDField.setText(String.valueOf(customerID));
            nameTextField.setText(customerName);
            postalTextField.setText(postalCode);
            phoneTextField.setText(phone);
        }
        else {
            raiseAlert("Select a customer:", "Please select a customer to update.", Alert.AlertType.INFORMATION);
        }
    }

    public void updateState() throws SQLException {
        ObservableList<String> divisionsList = divisionDAO.getStatesProvinces(countryBox.getValue());
        stateProvince.setItems(divisionsList);

        if(Objects.equals(countryBox.getValue(), "UK")){
            townField.setDisable(false);
        }
        else if((Objects.equals(countryBox.getValue(), "U.S")|(Objects.equals(countryBox.getValue(), "Canada")))){
            townField.clear();
            townField.setDisable(true);
        }

    }

    /**
     * NullPointerException: Cannot invoke "java.lang.Integer.intValue()" because "userId" is null
     * forgot to implement updating the user in the updateApptFields method
     * @throws SQLException sqlexceptions
     */
    public void applyUpdateAppt() throws SQLException {
        Integer userID = userBox.getValue();
        Integer customerID = customerBox.getValue();
        Integer contactID = contactDAO.getContactID(contactBox.getValue());
        appointmentDAO.updateAppt(Integer.valueOf(apptIDField.getText()),titleField.getText(),descriptionField.getText(),locationField.getText(),typeField.getText(),datePicker.getValue(),datePicker.getValue(), Timestamp.from(Instant.now()), User.getUser().getUserName(), customerID,userID,contactID);
        appointmentTableView.setItems(Appointment.getAllAppointments());
    }

    public void applyUpdateCustomer() throws SQLException {
        Integer userID = User.getUser().getUserId();
        Integer customerID = Integer.valueOf(customerIDField.getText());
        Integer divisionID = divisionDAO.getDivisionID(stateProvince.getValue());
        String customerName = nameTextField.getText();
        String address = addressTextField.getText();
        String city = cityTextField.getText();
        String town = townField.getText();
        String postalCode = postalTextField.getText();
        String phoneNumber = phoneTextField.getText();
        Timestamp lastUpdate = Timestamp.from(Instant.now());
        String lastUpdatedby = User.getUser().getUserName();
        customerDAO.updateCustomer(customerID,customerName,address, city, town, postalCode, phoneNumber, lastUpdate,lastUpdatedby,divisionID);
        customerTableView.setItems(Customer.getAllCustomers());
    }

    public void clear() {
        customerIDField.setText(null);
        nameTextField.setText(null);
        addressTextField.setText(null);
        cityTextField.setText(null);
        postalTextField.setText(null);
        phoneTextField.setText(null);
        countryBox.setValue(null);
        stateProvince.setValue(null);
        apptIDField.setText(null);
        customerBox.setValue(null);
        titleField.setText(null);
        descriptionField.setText(null);
        typeField.setText(null);
        locationField.setText(null);
        contactBox.setValue(null);
        datePicker.setValue(null);
        startTime.setValue(null);
        endTime.setValue(null);
        userBox.setValue(null);
        townField.setText(null);
    }

    public void allFilter() throws SQLException {
        appointmentTableView.setPlaceholder(new Label("There are no appointments in the database. Click the 'Create Appointment' button below to add one."));
        appointmentTableView.setItems(Appointment.getAllAppointments());
    }

    public void weekFilter() throws SQLException {
        appointmentTableView.setPlaceholder(new Label("There are no appointments scheduled for this week."));
        appointmentTableView.setItems(Appointment.getWeekly());
    }

    public void monthFilter() throws SQLException {
        appointmentTableView.setPlaceholder(new Label("There are no appointments scheduled for this month."));
        appointmentTableView.setItems(Appointment.getMonthly());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setCycleCount(1);
        fadeOut.setAutoReverse(false);

        customerTableView.setPlaceholder(new Label("There are no customers in the database. Click the 'Create Customer' button below to add one."));

        try {
            customerTableView.setItems(Customer.getAllCustomers());
            customerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            address.setCellValueFactory(new PropertyValueFactory<>("address"));
            postal_code.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
            phone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
            divID.setCellValueFactory(new PropertyValueFactory<>("divID"));

            appointmentTableView.setItems(Appointment.getAllAppointments());
            apptIDColumn.setCellValueFactory(new PropertyValueFactory<>("apptId"));
            titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
            descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
            locCol.setCellValueFactory(new PropertyValueFactory<>("location"));
            contactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
            typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            startCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
            endCol.setCellValueFactory(new PropertyValueFactory<>("endDate"));
            customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            userIDCol.setCellValueFactory(new PropertyValueFactory<>("userId"));

            contactBox.setItems(contactDAO.getContactNames());
            customerBox.setItems(customerDAO.getCustomerIDs());
            userBox.setItems(userDAO.getUserIDs());
            countryBox.setItems(divisionDAO.getCountries());


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
