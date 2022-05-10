package Controller;

import DAO.customerDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import static Utilities.alertBox.raiseAlert;


public class CreateCustomerController {
    public TextField nameTextField;
    public TextField addressTextField;
    public TextField postalTextField;
    public TextField phoneTextField;

    @FXML
    public void createCustomer() throws SQLException {

        if(nameTextField.getText().isEmpty()|addressTextField.getText().isEmpty()|postalTextField.getText().isEmpty()|phoneTextField.getText().isEmpty()){
            raiseAlert("Error", "Customer fields cannot be empty", Alert.AlertType.ERROR);
        }
        else{
            customerDAO.createCustomer(nameTextField.getText(), addressTextField.getText(), postalTextField.getText(), phoneTextField.getText());
        }

    }

    /**
     *
     * @param event Event is handled on the login controller. when the login is true, mainMenu is called.
     * @throws IOException exceptions
     * ERROR: Runtime Exception: Error resolving onAction='#createAppt'
     */
    public static void createCustomerMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Main.Main.class.getResource("/View/CreateCustomerForm.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}
