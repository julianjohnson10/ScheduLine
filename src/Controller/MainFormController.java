package Controller;

import DAO.*;
import Main.Main;
import Model.Appointment;
import Model.Customer;
import Model.User;
import Utilities.date_time;
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
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;

import static Utilities.alertError.raiseAlert;

/**
 * The Main Form holds the Appointments Tab, the Customers Tab, and the Reports tab.
 * If Log Out is clicked, the main form will reinitialize, to include alerts if there is an appointment within 15 minutes.
 *
 * FUTURE ENHANCEMENT: I want to note the last selected tab, and pass it to main form. Currently, when you create a customer and are taken back here
 * to the main form, the appointments tab will get sent to the tabPane selectionmodel, because it is the first tab in the pane.
 *
 * FUTURE ENHANCEMENT: I tried to center everything 'properly'. There is a slight flicker. CenterOnScreen doesn't seem to be a viable option, no matter
 * if it placed before or after show.
 *
 * FUTURE ENHANCEMENT: Multi-monitor support. If you use 2 monitors, and move the application to the secondary screen after launch, the alert boxes will
 * still only show on the primary monitor.
 */
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

    /**
     * Appointments TableView.
     */
    public TableView<Appointment> appointmentTableView;

    /**
     * Appointment ID column.
     */
    public TableColumn<Appointment, Integer> apptIDColumn;

    /**
     * Appointment Title column.
     */
    public TableColumn<Appointment, String> titleColumn;

    /**
     * Appointment Description column.
     */
    public TableColumn<Appointment, String> descriptionColumn;

    /**
     * Appointment Location column.
     */
    public TableColumn<Appointment, String> locCol;

    /**
     * Appointment Type column.
     */
    public TableColumn<Appointment, String> typeCol;

    /**
     * Appointment Start Time column.
     */
    public TableColumn<Appointment, String> startCol;

    /**
     * Appointment End column.
     */
    public TableColumn<Appointment, String> endCol;

    /**
     * Appointment CustomerID column.
     */
    public TableColumn<Appointment, Integer> customerIDCol;

    /**
     * Appointment TableView's User ID column.
     */
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

    /**
     * ComboBox of countries.
     */
    public ComboBox<String> countryBox;

    /**
     * Appointment TableView Contact column.
     */
    public TableColumn<Appointment, String> contactCol;

    /**
     * Appointment ID textfield on Appointments Tab.
     */
    public TextField apptIDField;

    /**
     * ComboBox of Customer ID values.
     */
    public ComboBox<Integer> customerBox;

    /**
     * Title textfield in appointments tab.
     */
    public TextField titleField;

    /**
     * Description text area in appointments tab.
     */
    public TextArea descriptionField;

    /**
     * Type textfield in appointments tab.
     */
    public TextField typeField;

    /**
     * Location textfield in appointments tab.
     */
    public TextField locationField;

    /**
     * ComboBox of Contact names in appointments tab.
     */
    public ComboBox<String> contactBox;

    /**
     * DatePicker in appointments tab.
     */
    public DatePicker datePicker;

    /**
     * Combobox of start times.
     */
    public ComboBox<String> startTime;

    /**
     * Combobox of end times.
     */
    public ComboBox<String> endTime;

    /**
     * Combobox of states/provinces in customer tab.
     */
    public ComboBox<String> stateProvince;

    /**
     * Apply button on Customers tab.
     */
    public Button applyUpdateCustomer;

    /**
     * Apply button on Appointments tab.
     */
    public Button applyUpdateAppt;

    /**
     * Customer ID textfield. It's always disabled. It shows the currently selected Customer's ID.
     */
    public TextField customerIDField;

    /**
     * Customer Name textField in customers tab.
     */
    public TextField nameTextField;

    /**
     * City textField in customers tab.
     */
    public TextField cityTextField;

    /**
     * Address textField in customers tab.
     */
    public TextField addressTextField;

    /**
     * Postal Code textField in customers tab.
     */
    public TextField postalTextField;

    /**
     * Phone textField in customers tab.
     */
    public TextField phoneTextField;

    /**
     * Combobox of User IDs in appointments tab.
     */
    public ComboBox<Integer> userBox;

    /**
     * Button that clears the customer fields on the customer tab.
     */
    public Button clearCustomer;

    /**
     * Label that appears on the customers tab when a customer is deleted.
     */
    public Label deletedLabel;

    /**
     * 4 second fadeout transition for the deletedLabel/deletedLabelAppt labels.
     */
    private final FadeTransition fadeOut = new FadeTransition(Duration.seconds(4.0));

    /**
     * Label that appears on the appointments tab when an appointment is canceled.
     */
    public Label deletedLabelAppt;

    /**
     * All Radio button on appointments tab. It shows all the appointments in the database.
     */
    public RadioButton allRadio;

    /**
     * "This Week" Radio button on appointments tab. It shows all the appointments in the database.
     */
    public RadioButton weekRadio;

    /**
     * "This Month" Radio button on appointments tab. It shows all the appointments in the database.
     */
    public RadioButton monthRadio;

    /**
     * Town textfield in customers tab.
     */
    public TextField townField;
    public Button logoutButton;
    public TextArea report1;
    public TextArea report2;
    public TextArea report3;

    /**
     * Deletes the selected customer from the database.
     * @throws SQLException for SQL errors.
     */
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

                    deletedLabel.setText("Customer "+ selectedCustomer.getCustomerName() + " has been deleted!"); //sets the label to show which customer was deleted. Plays a fade transition.
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

    /**
     * Deletes the selected Appointment from the database.
     * @throws SQLException for SQL errors.
     */
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
                if(allRadio.isSelected()){
                    appointmentTableView.setItems(appointmentDAO.getAllAppts());
                }
                else if(weekRadio.isSelected()){
                    appointmentTableView.setItems(appointmentDAO.getWeekly());
                }
                else{
                    appointmentTableView.setItems(appointmentDAO.getMonthly());
                }

            }
        }
        else {
            raiseAlert("Error", "You have not selected an appointment", Alert.AlertType.ERROR);
        }
    }


    /**
     * Sends to Create Customer form.
     * @param event When the "Create Customer" button is pressed.
     * @throws IOException IO Exception handler.
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

    /**
     * Sends to Create Appointment Form.
     * @param event When the "Create Appointment" button is pressed.
     * @throws IOException IO Exception handler.
     */
    @FXML
    private void createApptMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/View/CreateAppointmentForm.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
        stage.centerOnScreen();
    }

    /**
     * Sends the user back to the Main Form.
     * ERROR: RuntimeException: java.lang.reflect.InvocationTargetException
     * I forgot to add an event handler for clearing customer update fields.
     * @param stage retrieves the last stage.
     * @throws IOException exception handler.
     * ERROR: Runtime Exception: Error resolving onAction='#createAppt'
     */
    public static void mainMenu(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/View/MainForm.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
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

    /**
     * Gets the selected appointment and fills the Update Appointment fields on the Appointments tab.
     */
    @FXML
    public void updateApptFields() {
        Appointment selectedAppointment = appointmentTableView.getSelectionModel().getSelectedItem();

        if(selectedAppointment != null){
            DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy h:mma");
            DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("h:mma");
            Integer apptId = selectedAppointment.getApptId();
            Integer customerID = selectedAppointment.getCustomerId();
            Integer userID = selectedAppointment.getUserId();
            String title = selectedAppointment.getTitle();
            String description = selectedAppointment.getDescription();
            String type = selectedAppointment.getType();
            String location = selectedAppointment.getLocation();
            String contact = selectedAppointment.getContactName();
            LocalDateTime startDate = selectedAppointment.getStartLDT();
            LocalDateTime endDate = LocalDateTime.parse(selectedAppointment.getEndDate(),format);

            apptIDField.setText(String.valueOf(apptId));
            customerBox.setValue(customerID);
            titleField.setText(title);
            descriptionField.setText(description);
            typeField.setText(type);
            locationField.setText(location);
            contactBox.setValue(contact);
            datePicker.setValue(LocalDate.from(startDate));
            startTime.setValue(startDate.toLocalTime().format(formatTime));
            endTime.setValue(endDate.toLocalTime().format(formatTime));
            userBox.setValue(userID);
        }
        else {
            raiseAlert("Select an appointment:", "Please select an appointment to update.", Alert.AlertType.INFORMATION);
        }

    }

    /**
     * Gets the selected customer and fills the Update Customer fields on the Customers tab.
     * Alertbox will show if no customer is selected.
     */
    @FXML
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

    /**
     * Updates the stateProvince ComboBox with the appropriate state or province based on the country selection.
     * It also disables the town textfield if UK is not selected.
     * @throws SQLException SQL error exception handler.
     */
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
     * Method for when the apply button is clicked on the Appointments tab. Time overlap logic occurs here, for update cases.
     * NullPointerException: Cannot invoke "java.lang.Integer.intValue()" because "userId" is null
     * forgot to implement updating the user in the updateApptFields method
     * @throws SQLException sqlexception handler.
     */
    public void applyUpdateAppt() throws SQLException {
        ObservableList<Appointment> allAppointments = Appointment.getAllAppointments();

        if(titleField.getText().isEmpty()||descriptionField.getText().isEmpty()||typeField.getText().isEmpty()||locationField.getText().isEmpty()) {
            raiseAlert("Error", "Fields must not be left empty or blank.", Alert.AlertType.ERROR);
        }
        else{
            DateTimeFormatter format = DateTimeFormatter.ofPattern("h:mma");
            Integer userID = userBox.getValue();
            Integer customerID = customerBox.getValue();
            Integer contactID = contactDAO.getContactID(contactBox.getValue());
            Integer apptID = Integer.valueOf(apptIDField.getText());
            String title = titleField.getText();
            String description = descriptionField.getText();
            String location = locationField.getText();
            String type = typeField.getText();
            LocalDate apptDate = datePicker.getValue();
            LocalTime start = LocalTime.parse(startTime.getValue(), format);
            LocalTime end = LocalTime.parse(endTime.getValue(), format);
            Timestamp lastUpdate = Timestamp.valueOf(LocalDateTime.now());
            String username = User.getUser().getUserName();

            LocalDateTime startDate = LocalDateTime.from(LocalDateTime.of(apptDate, start).atZone(ZoneId.of("UTC")));
            LocalDateTime endDate = LocalDateTime.from(LocalDateTime.of(apptDate, end).atZone(ZoneId.of("UTC")));
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

            for (Appointment appointment : allAppointments) {
                Integer custID = appointment.getCustomerId();
                Integer appt_ID = appointment.getApptId();
                LocalDateTime aStart = appointment.getStartLDT();
                LocalDateTime aEnd = appointment.getEndLDT();

                //Check if customer ID matches, and don't compare the same appointment.
                boolean b = custID.equals(customerID) && !appt_ID.equals(apptID);
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
            appointmentDAO.updateAppt(apptID, title, description, location, type, apptDate, start, end, lastUpdate, username, customerID, userID, contactID);
            if(allRadio.isSelected()){
                appointmentTableView.setItems(Appointment.getAllAppointments());
            }
            else if(weekRadio.isSelected()){
                appointmentTableView.setItems(Appointment.getWeekly());
            }
            else{
                appointmentTableView.setItems(Appointment.getMonthly());
            }
        }
    }

    /**
     * Method for when the apply button is clicked on the Customers tab. Gets the selected customer and updates it with the new information entered.
     * @throws SQLException Exception handler.
     */
    public void applyUpdateCustomer() throws SQLException {
        Customer selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();

        String city;
        String town;
        String postalCode;
        String phoneNumber;
        String lastUpdatedby;
        int divisionID;
        int customerID;
        String customerName;
        String address;

        if (selectedCustomer != null) {
            if (Objects.equals(countryBox.getValue(), "UK")) {

                customerID = Integer.parseInt(customerIDField.getText());
                divisionID = divisionDAO.getDivisionID(stateProvince.getValue());
                customerName = nameTextField.getText();
                address = addressTextField.getText();
                city = cityTextField.getText();
                town = townField.getText();
                postalCode = postalTextField.getText();
                phoneNumber = phoneTextField.getText();
                lastUpdatedby = User.getUser().getUserName();

                if (nameTextField.getText() == null || townField.getText() == null || countryBox.getValue() == null || stateProvince.getValue() == null || addressTextField.getText() == null || cityTextField.getText() == null || postalTextField.getText() == null || phoneTextField.getText() == null) {
                    raiseAlert("Error", "Fields must not be left blank.", Alert.AlertType.ERROR);
                }
                else{
                    customerDAO.updateCustomer(customerID, customerName, address, city, town, postalCode, phoneNumber, lastUpdatedby, divisionID);
                    customerTableView.setItems(Customer.getAllCustomers());
                    clear();
                }
            }
            else {
                customerID = Integer.parseInt(customerIDField.getText());
                divisionID = divisionDAO.getDivisionID(stateProvince.getValue());
                customerName = nameTextField.getText();
                address = addressTextField.getText();
                city = cityTextField.getText();
                postalCode = postalTextField.getText();
                phoneNumber = phoneTextField.getText();
                lastUpdatedby = User.getUser().getUserName();

                if (nameTextField.getText().isEmpty() || countryBox.getValue().isEmpty() || stateProvince.getValue().isEmpty() || addressTextField.getText().isEmpty() || cityTextField.getText().isEmpty() || postalTextField.getText().isEmpty() || phoneTextField.getText().isEmpty()) {
                    raiseAlert("Error", "Fields must not be left blank.", Alert.AlertType.ERROR);
                }
                else{
                    customerDAO.updateCustomer(customerID, customerName, address, city, null, postalCode, phoneNumber, lastUpdatedby, divisionID);
                    customerTableView.setItems(Customer.getAllCustomers());
                    clear();
                }
            }
        }
        else {
            raiseAlert("Error", "Select a customer from the table and click 'Update Customer'", Alert.AlertType.ERROR);
        }
    }

    /**
     * Clears all fields required to update customer and appointment.
     */
    public void clear() {
        customerTableView.getSelectionModel().clearSelection();
        appointmentTableView.getSelectionModel().clearSelection();
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

    /**
     * Sets the appointments tableview with all appointments. If there aren't any, the placeholder changes appropriately.
     * @throws SQLException exception handler.
     */
    public void allFilter() throws SQLException {
        appointmentTableView.setPlaceholder(new Label("There are no appointments in the database. Click the 'Create Appointment' button below to add one."));
        appointmentTableView.setItems(Appointment.getAllAppointments());
    }

    /**
     * Sets the appointments tableview with all appointments for this week. If there aren't any, the placeholder changes appropriately.
     * @throws SQLException exception handler.
     */
    public void weekFilter() throws SQLException {
        appointmentTableView.setPlaceholder(new Label("There are no appointments scheduled for this week."));
        appointmentTableView.setItems(Appointment.getWeekly());
    }

    /**
     * Sets the appointments tableview with all appointments for this month. If there aren't any, the placeholder changes appropriately.
     * @throws SQLException exception handler.
     */
    public void monthFilter() throws SQLException {
        appointmentTableView.setPlaceholder(new Label("There are no appointments scheduled for this month."));
        appointmentTableView.setItems(Appointment.getMonthly());
    }

    /**
     * Generates the first report in the Reports tab.
     * @throws SQLException exception handler for SQL errors.
     */
    @FXML
    private void generateReport1() throws SQLException {
        //for 1-12 months.
        for (int i = 1; i <= 12; i++) {
            String month = Month.of(i).getDisplayName(TextStyle.FULL,Locale.US);
            report1.appendText(month + ":\n\n");
            List<String> typesList = reportDAO.getTypes(i);

            if(typesList.size() == 0){
                report1.appendText("\t0 appointments for the month of " + month + "\n\n");
            }

            for(String type : typesList){

                List<Integer> countList = reportDAO.generateReport1(type,i);
                report1.appendText("\tAppointment Type- " + type + ": Total = ");
                for(Integer count : countList){
                    report1.appendText(String.valueOf(count));
                    report1.appendText("\n\n");
                }
            }
        }

    }

    /**
     * Generates the second report in the Reports tab.
     * @throws SQLException exception handler for SQL errors.
     */
    @FXML
    private void generateReport2() throws SQLException {
        ObservableList<String> allContacts = contactDAO.getContactNames();
        report2.clear();
        for(String contact : allContacts){
            List<String> apptsList = reportDAO.generateReport2(contact);

            if(apptsList.size() != 0){
                if(apptsList.size() == 1){
                    report2.appendText(contact + " has 1 appointment listed below:\n\n");
                }
                else{
                    report2.appendText(contact + " has " + apptsList.size() + " appointments listed below:\n\n");
                }
                for (String appt : apptsList){
                    report2.appendText(appt);
                    report2.appendText("\n\n");
                }
                report2.appendText("___________________________________\n\n");
            }
            else{
                report2.appendText(contact + " has 0 appointments.\n\n");
            }
        }
    }

    /**
     * Generates the third report in the Reports tab.
     * @throws SQLException exception handler for SQL errors.
     */
    @FXML
    private void generateReport3() throws SQLException {
        ObservableList<String> countries = divisionDAO.getCountries();
        report3.clear();
        for(String country : countries){
            List<String> customerList = reportDAO.generateReport3(country);

            if(customerList.size() != 0){
                if(customerList.size() == 1){
                    report3.appendText(country + " has 1 customer listed below:\n\n");
                }
                else{
                    report3.appendText(country + " customers: " + customerList.size() + "\n\n");
                }
                for (String appt : customerList){
                    report3.appendText(appt);
                    report3.appendText("\n\n");
                }
                report3.appendText("___________________________________\n\n");
            }
            else{
                report3.appendText(country + " customers: " + 0 + "\n\n");
            }
        }
    }

    public void copyReport1() {
        String s = report1.getText();
        StringSelection stringSelection = new StringSelection(s);
        Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
        cb.setContents(stringSelection,null);
    }

    public void copyReport2() {
        String s = report2.getText();
        StringSelection stringSelection = new StringSelection(s);
        Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
        cb.setContents(stringSelection,null);
    }

    public void copyReport3() {
        String s = report3.getText();
        StringSelection stringSelection = new StringSelection(s);
        Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
        cb.setContents(stringSelection,null);
    }

    /**
     * Boolean value that once initialize is called, will turn false after the
     * time alert is called, so it doesn't repeat.
     */
    private static boolean first = true;
    /**
     * Initializes the main form. Sets the tableviews.
     * LAMBDA JUSTIFICATION: The lambda expression passes an event handler to the 'Log Out' button, that takes the user back to the Login screen.
     * @param url url
     * @param resourceBundle resourcebundle
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Appointment> allAppointments = null;
        try {
            allAppointments = Appointment.getAllAppointments();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        LocalDateTime now = LocalDateTime.now();

        //Lambda expression sets an onaction event to the login button. The event takes the user back to the Login Form.
        logoutButton.setOnAction( event ->
        {
            Parent root = null;
            try {
                root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/View/LoginForm.fxml")));
            } catch (IOException e) {
                e.printStackTrace();
            }
            //I set first again, when log out is called, so the mainform can go back to its initial state.
            first = true;
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            assert root != null;
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
            stage.centerOnScreen();
        });

        startTime.setItems(date_time.startList);
        endTime.setItems(date_time.endList);

        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setCycleCount(1);
        fadeOut.setAutoReverse(false);

        customerTableView.setPlaceholder(new Label("There are no customers in the database. Click the 'Create Customer' button below to add one."));

        try {

            if(allRadio.isSelected()){
                appointmentTableView.setPlaceholder(new Label("There are no appointments in the database. Click the 'Create Appointment' button below to add one."));
                appointmentTableView.setItems(Appointment.getAllAppointments());
            }
            else if(weekRadio.isSelected()){
                appointmentTableView.setItems(Appointment.getWeekly());
            }
            else{
                appointmentTableView.setItems(Appointment.getMonthly());
            }

            customerTableView.setItems(Customer.getAllCustomers());
            customerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            address.setCellValueFactory(new PropertyValueFactory<>("address"));
            postal_code.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
            phone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
            divID.setCellValueFactory(new PropertyValueFactory<>("divID"));

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

        assert allAppointments != null;

        if (!first){
            return;
        }
        //Checks for upcoming appointments within 15 minutes. If there are none, then a green label will briefly appear.
        //If there are, an alert will show
        for(Appointment appointment: allAppointments){
            //Only do it once. Do not reinitialize.
            first = false;

            long timelapse = ChronoUnit.MINUTES.between(now,appointment.getStartLDT());
            if((appointment.getUserId()==User.getUser().userId) && (timelapse>0 && timelapse<=15)){
                raiseAlert("Upcoming appointment:","An appointment is coming up in " + timelapse + " minutes.\n\nAppointment ID: " +appointment.getApptId()+"\nAppointment Date: "+appointment.getStartDate(), Alert.AlertType.INFORMATION);
                return;
            }
        }
        deletedLabelAppt.setText("You have no upcoming appointments!");
        fadeOut.setNode(deletedLabelAppt);
        fadeOut.playFromStart();
    }

    /**
     * Clears all the report TextAreas.
     */
    public void clearReports() {
        report1.clear();
        report2.clear();
        report3.clear();
    }
}
