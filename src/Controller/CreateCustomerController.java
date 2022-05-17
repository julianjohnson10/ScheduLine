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

public class CreateCustomerController implements Initializable {
    public TextField nameTextField;
    public TextField addressTextField;
    public TextField postalTextField;
    public TextField phoneTextField;
    public TextField customerIDField;
    public ComboBox<String> countryBox;
    public ComboBox<String> stateProvince;
    public Button cancelButton;
    public Button createCustomer;
    public TextField cityTextField;
    public TextField townField;
    private ObservableList<String> countriesList = FXCollections.observableArrayList();

    @FXML
    public void createCustomer() throws SQLException, IOException {
        Stage stage = (Stage) createCustomer.getScene().getWindow();
        if(nameTextField.getText().isEmpty()|addressTextField.getText().isEmpty()|postalTextField.getText().isEmpty()|phoneTextField.getText().isEmpty()|countryBox.getValue()==null|stateProvince.getValue()==null){
            raiseAlert("Error", "Customer fields cannot be empty", Alert.AlertType.ERROR);
        }
        else{
            customerDAO.createCustomer(nameTextField.getText(), addressTextField.getText(), cityTextField.getText(), townField.getText(), postalTextField.getText(), phoneTextField.getText(), stateProvince.getSelectionModel().getSelectedItem());
            MainFormController.mainMenu(stage);
        }
    }

    @FXML
    public void cancel(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/MainForm.fxml")));
        Scene scene = new Scene(parent);
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

    public void updateState() throws SQLException {
        ObservableList<String> divisionsList = divisionDAO.getStatesProvinces(countryBox.getValue());
        stateProvince.setItems(divisionsList);
        String country = countryBox.getValue();
        if(Objects.equals(country, "UK")){
            townField.setDisable(false);
        }

    }


    /**
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
