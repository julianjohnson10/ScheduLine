package Controller;

import DAO.appointmentDAO;
import DAO.customerDAO;
import Model.Appointment;
import Model.Customer;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.ResourceBundle;

import static Utilities.alertBox.raiseAlert;

public class MainFormController implements Initializable {
    /**
     * TableView for customer data.
     */
    public TableView<Customer> customerTableView;

    /**
     * Customer ID column.
     */
    public TableColumn<Customer, SimpleIntegerProperty> customerID;

    /**
     * Customer Name column.
     */
    public TableColumn<Customer, SimpleStringProperty> customerName;

    /**
     * Customer address column.
     */
    public TableColumn<Customer, SimpleStringProperty> address;

    /**
     * Customer postal code column.
     */
    public TableColumn<Customer, SimpleStringProperty> postal_code;

    /**
     * Customer phone number column.
     */
    public TableColumn<Customer, SimpleStringProperty> phone;

    /**
     * Customer created date column.
     */
    public TableColumn<Customer, SimpleObjectProperty<Date>> createDate;

    /**
     * Customer created by column.
     */
    public TableColumn<Customer, SimpleStringProperty> createdBy;

    /**
     * Customer last update column.
     */
    public TableColumn<Customer, SimpleObjectProperty<Timestamp>> lastUpdate;

    /**
     * Customer last updated by column.
     */
    public TableColumn<Customer, SimpleStringProperty> lastUpdatedBy;

    /**
     * Customer Division ID column.
     */
    public TableColumn<Customer, SimpleIntegerProperty> divID;

    public TableView<Appointment> appointmentTableView;
    public TableColumn<Appointment, SimpleIntegerProperty> apptIDColumn;
    public TableColumn<Appointment, SimpleStringProperty> titleColumn;
    public TableColumn<Appointment, SimpleStringProperty> descriptionColumn;
    public TableColumn<Appointment, SimpleStringProperty> locCol;
    public TableColumn<Appointment, SimpleStringProperty> typeCol;
    public TableColumn<Appointment, SimpleObjectProperty<Date>> startCol;
    public TableColumn<Appointment, SimpleObjectProperty<Date>> endCol;
    public TableColumn<Appointment, SimpleObjectProperty<Date>> createDateCol;
    public TableColumn<Appointment, SimpleStringProperty> createdByCol;
    public TableColumn<Appointment, SimpleObjectProperty<Timestamp>> lastUpdateCol;
    public TableColumn<Appointment, SimpleStringProperty> lastUpdatedByCol;
    public TableColumn<Appointment, SimpleIntegerProperty> customerIDCol;
    public TableColumn<Appointment, SimpleIntegerProperty> userIDCol;
    public TableColumn<Appointment, SimpleIntegerProperty> contactIDCol;

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
    public TextField nameTextField;
    public TextField addressTextField;
    public TextField postalTextField;
    public TextField phoneTextField;
    public ComboBox countryBox;

    /**
     *
     * @param event Event for clicking the deleteCustomer button.
     * @throws SQLException Raise SQL errors.
     */
    @FXML
    public void deleteCustomer(ActionEvent event) throws SQLException {
        Customer selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
        if(selectedCustomer != null){
            //Delete selected customer
            Optional<ButtonType> result = raiseAlert("Are you sure?", "Are you sure you want to delete this customer?", Alert.AlertType.CONFIRMATION);
            if(result.isPresent() && result.get() == ButtonType.OK) {
                if(!appointmentDAO.checkCustomer(selectedCustomer.getCustomerID())){
                    customerDAO.deleteCustomer(selectedCustomer.getCustomerID());
                    Customer.deleteCustomer(selectedCustomer);
                    customerTableView.refresh();
                    customerTableView.getSelectionModel().clearSelection();
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
                appointmentTableView.refresh();
                appointmentTableView.getSelectionModel().clearSelection();
            }
        }
        else {
            raiseAlert("Error", "You have not selected an appointment", Alert.AlertType.ERROR);
        }
    }

//    @FXML
//    public void updateCustomer(Customer customer){
//        Customer selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
//        if(selectedCustomer != null){
//            customerDAO.updateCustomer(selectedCustomer)
//        }
//        else {
//            raiseAlert("Error", "You have not selected a customer to update.", Alert.AlertType.ERROR);
//        }
//    }

    @FXML
    public void createCustomer() throws SQLException {

        if(nameTextField.getText().isEmpty()|addressTextField.getText().isEmpty()|postalTextField.getText().isEmpty()|phoneTextField.getText().isEmpty()){
            raiseAlert("Error", "Customer fields cannot be empty", Alert.AlertType.ERROR);
        }
        else{
            int rows = customerDAO.createCustomer(nameTextField.getText(), addressTextField.getText(), postalTextField.getText(), phoneTextField.getText());
            if(rows > 0){
                System.out.println("Success");
            }
        }

    }

    @FXML
    public void updateAppt(){

    }

    /**
     * Clears the selections from both tableviews when switching tabs.
     */
    public void resetTable() {
        try{
            if(customerTableView.getSelectionModel().getSelectedItem() != null) {
                customerTableView.getSelectionModel().clearSelection();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            customerTableView.setItems(customerDAO.getAllCustomers());
            appointmentTableView.setItems(appointmentDAO.getAllAppts());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        customerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        postal_code.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        createDate.setCellValueFactory(new PropertyValueFactory<>("createDate"));
        createdBy.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        lastUpdate.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
        lastUpdatedBy.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));
        divID.setCellValueFactory(new PropertyValueFactory<>("divID"));
        apptIDColumn.setCellValueFactory(new PropertyValueFactory<>("apptId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        locCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        createDateCol.setCellValueFactory(new PropertyValueFactory<>("createdDate"));
        createdByCol.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        lastUpdateCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
        lastUpdatedByCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userIDCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        contactIDCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));

        countryBox.setCellFactory(new PropertyValueFactory<>(""));
    }
}
