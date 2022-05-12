package Controller;

import DAO.appointmentDAO;
import DAO.customerDAO;
import Main.Main;
import Model.Appointment;
import Model.Customer;
import javafx.beans.property.SimpleIntegerProperty;
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

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZonedDateTime;
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
//    public TableColumn<Appointment, String> contactName;
    public TableColumn<Appointment, String> typeCol;
    public TableColumn<Appointment, ZonedDateTime> startCol;
    public TableColumn<Appointment, ZonedDateTime> endCol;
//    public TableColumn<Appointment, SimpleObjectProperty<Date>> createDateCol;
//    public TableColumn<Appointment, SimpleStringProperty> createdByCol;
//    public TableColumn<Appointment, SimpleObjectProperty<Timestamp>> lastUpdateCol;
//    public TableColumn<Appointment, SimpleStringProperty> lastUpdatedByCol;
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
    public TabPane tabPane;
//    SelectionModel<Tab> selectionModel = tabPane.getSelectionModel().getSelectedItem();

    /**
     *
     * @param event Event for clicking the deleteCustomer button.
     * @throws SQLException Raise SQL errors.
     */
    @FXML
    public void deleteCustomer(ActionEvent event) throws SQLException, IOException {
        Customer selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
        if(selectedCustomer != null){
            //Delete selected customer
            Optional<ButtonType> result = raiseAlert("Are you sure?", "Are you sure you want to delete this customer?", Alert.AlertType.CONFIRMATION);
            if(result.isPresent() && result.get() == ButtonType.OK) {
                if(!appointmentDAO.checkCustomer(selectedCustomer.getCustomerID())){
                    customerDAO.deleteCustomer(selectedCustomer.getCustomerID());
                    Customer.deleteCustomer(selectedCustomer);
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
    public void deleteAppt(ActionEvent event) throws SQLException {
        Appointment selectedAppointment = appointmentTableView.getSelectionModel().getSelectedItem();
        if(selectedAppointment != null){
            //Delete selected appointment
            Optional<ButtonType> result = raiseAlert("Are you sure?", "Are you sure you want to delete this appointment?", Alert.AlertType.CONFIRMATION);
            if(result.isPresent() && result.get() == ButtonType.OK) {
                appointmentDAO.deleteAppt(selectedAppointment.getApptId());
                Appointment.deleteAppointment(selectedAppointment);
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
    public void createCustomerMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("/View/CreateCustomerForm.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    public void createApptMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("/View/CreateAppointmentForm.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
        tabPane.getSelectionModel().selectedItemProperty();
    }

    /**
     *
     * @param event Event is handled on the login controller. when the login is true, mainMenu is called.
     * @throws IOException exceptions
     * ERROR: Runtime Exception: Error resolving onAction='#createAppt'
     */
    public static void mainMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("/View/MainForm.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    public void updateAppt(){

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
