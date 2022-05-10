package Controller;

import DAO.customerDAO;
import DAO.divisionDAO;
import Main.Main;
import Model.Appointment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static Utilities.alertBox.raiseAlert;


public class CreateCustomerController implements Initializable {
    public TextField nameTextField;
    public TextField addressTextField;
    public TextField postalTextField;
    public TextField phoneTextField;
    public TextField customerIDField;
    public ComboBox<String> countryBox;
    public ComboBox<String> stateProvince;
    public Button cancelButton;

    @FXML
    public void createCustomer() throws SQLException {

        if(nameTextField.getText().isEmpty()|addressTextField.getText().isEmpty()|postalTextField.getText().isEmpty()|phoneTextField.getText().isEmpty()){
            raiseAlert("Error", "Customer fields cannot be empty", Alert.AlertType.ERROR);
        }
        else{
            customerDAO.createCustomer(nameTextField.getText(), addressTextField.getText(), postalTextField.getText(), phoneTextField.getText(), countryBox.getSelectionModel().getSelectedItem().toString(), stateProvince.getSelectionModel().getSelectedItem().toString());
        }

    }

    @FXML
    public void cancel(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/View/MainForm.fxml"));
        Scene scene = new Scene(parent);
        Stage window = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);

        window.show();
    }

    /**
     *
     * @param event Event is handled on the login controller. when the login is true, mainMenu is called.
     * @throws IOException exceptions
     * ERROR: Runtime Exception: Error resolving onAction='#createAppt'
     */
    public static void createCustomerMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("/View/CreateCustomerForm.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Rectangle2D screen = Screen.getPrimary().getVisualBounds();

//        stage.setY(screen.getHeight() / 2);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    public void updateState(ActionEvent event) throws SQLException {
        int countryID = divisionDAO.getCountryID(countryBox.getValue());
        stateProvince.setItems(divisionDAO.getStatesProvinces(countryID));
        System.out.println("pressed");
    }

    /**
     * ERROR: NullPointerException: Cannot invoke "String.isEmpty()" because the return value of "javafx.scene.control.SingleSelectionModel.getSelectedItem()" is null
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            countryBox.setItems(divisionDAO.getCountries());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(countryBox.getValue() != null) {
            int countryID = 0;
            try {
                countryID = divisionDAO.getCountryID(countryBox.getValue());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                stateProvince.setItems(divisionDAO.getStatesProvinces(countryID));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("Value is null");
        }
    }
}
