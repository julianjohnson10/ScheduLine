package Controller;

import DAO.contactDAO;
import Main.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CreateAppointmentController implements Initializable {

    public Button cancelButton;
    public TextField customerNameField;
    public ComboBox<String> contactBox;
    public Button createApptButton;

    @FXML
    public static void createApptMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("/View/CreateAppointmentForm.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
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
         * ERROR: NullPointerException: Cannot invoke "String.isEmpty()" because the return value of "javafx.scene.control.SingleSelectionModel.getSelectedItem()" is null
         * @param url
         * @param resourceBundle
         */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            contactBox.setItems(contactDAO.getContacts());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
