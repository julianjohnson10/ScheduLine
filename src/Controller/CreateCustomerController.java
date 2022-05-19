package Controller;

import DAO.customerDAO;
import DAO.divisionDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.Objects;
import java.util.ResourceBundle;
import static Utilities.alertError.raiseAlert;

/**
 * Controller for creating customers.
 */
public class CreateCustomerController implements Initializable {

    /**
     * Customer Name textfield on the create customers form.
     */
    public TextField nameTextField;

    /**
     * Address textfield on the create customers form.
     */
    public TextField addressTextField;

    /**
     * Postal code textfield on the create customers form.
     */
    public TextField postalTextField;

    /**
     * Phone number textfield on the create customers form.
     */
    public TextField phoneTextField;

    /**
     * Customer ID textfield on the create customers form. Stays disabled.
     */
    public TextField customerIDField;

    /**
     * Combobox of country names.
     */
    public ComboBox<String> countryBox;

    /**
     * Combobox of states and provinces.
     */
    public ComboBox<String> stateProvince;

    /**
     * Cancel button on create customer form.
     */
    public Button cancelButton;

    /**
     * Create button on create customer form.
     */
    public Button createCustomer;

    /**
     * City textfield on create customer form.
     */
    public TextField cityTextField;

    /**
     * Town textfield on create customer form.
     */
    public TextField townField;

    /**
     * List of countries that gets passed to the country combobox.
     */
    private ObservableList<String> countriesList = FXCollections.observableArrayList();

    /**
     * Create customer method. When "Create" is pressed, all of the textfields and comboboxes will be turned into a preparedStatement using the customerDAO.
     * @param event Create button is clicked.
     * @throws SQLException for SQL error handling.
     * @throws IOException exception handler.
     */
    @FXML
    public void createCustomer(ActionEvent event) throws SQLException, IOException {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        if(nameTextField.getText().isEmpty()|addressTextField.getText().isEmpty()|postalTextField.getText().isEmpty()|phoneTextField.getText().isEmpty()|countryBox.getValue()==null|stateProvince.getValue()==null){
            raiseAlert(stage, "Error", "Customer fields cannot be empty", Alert.AlertType.ERROR);
        }
        else{
            customerDAO.createCustomer(nameTextField.getText(), addressTextField.getText(), cityTextField.getText(), townField.getText(), postalTextField.getText(), phoneTextField.getText(), stateProvince.getSelectionModel().getSelectedItem());
            MainFormController.mainMenu(stage);
        }
    }

    /**
     * Sends the user back to the main form.
     * @param actionEvent Cancel button is clicked.
     * @throws IOException exception handler.
     */
    @FXML
    public void cancel(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/MainForm.fxml")));
        Scene scene = new Scene(parent);
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

    /**
     * Updates the list of states and provinces depending on the value in the country combobox.
     * It will also disable the townfield if the country is not the UK, because it is not needed.
     * @throws SQLException for SQL errors.
     */
    public void updateState() throws SQLException {
        ObservableList<String> divisionsList = divisionDAO.getStatesProvinces(countryBox.getValue());
        stateProvince.setItems(divisionsList);
        String country = countryBox.getValue();
        if(Objects.equals(country, "UK")){
            townField.setDisable(false);
        }

    }

    /**
     * Initializes the Create customer controller. Sets initial values for the create customer form.
     * ERROR: NullPointerException: Cannot invoke "String.isEmpty()" because the return value of "javafx.scene.control.SingleSelectionModel.getSelectedItem()" is null
     * @param url url
     * @param resourceBundle resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            countriesList = divisionDAO.getCountries();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        countryBox.setItems(countriesList);
    }
}
