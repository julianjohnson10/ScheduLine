package Controller;

import DAO.appointmentDAO;
import DAO.customerDAO;
import Model.Appointment;
import Model.Customer;
import Utilities.Locales;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
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
import java.sql.Timestamp;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class createCustomer implements Initializable {

    @FXML
    public TableView<Customer> customerTableView;
    public TableColumn<Customer, SimpleIntegerProperty> customerID;
    public TableColumn<Customer, SimpleStringProperty> customerName;
    public TableColumn<Customer, SimpleStringProperty> address;
    public TableColumn<Customer, SimpleStringProperty> postal_code;
    public TableColumn<Customer, SimpleStringProperty> phone;
    public TableColumn<Customer, SimpleObjectProperty<Date>> createDate;
    public TableColumn<Customer, SimpleStringProperty> createdBy;
    public TableColumn<Customer, SimpleObjectProperty<Timestamp>> lastUpdate;
    public TableColumn<Customer, SimpleStringProperty> lastUpdatedBy;
    public TableColumn<Customer, SimpleIntegerProperty> divID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            customerTableView.setItems(customerDAO.getAllCustomers());
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
    }
}
