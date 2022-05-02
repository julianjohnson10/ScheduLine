package Controller;

import Model.Appointment;
import Utilities.Locales;
import Utilities.alertBox;
import javafx.application.Platform;
import javafx.event.Event;
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
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    public Button loginButton;
    public Button exitButton;
    public TextField userIDField;
    public PasswordField passwordField;
    public Label errorLabel;
    public Label timeZoneLabel;
    public Label uidLabel;
    public Label pwdLabel;
    public Label headerLabel;

    ResourceBundle resourceBundle = ResourceBundle.getBundle("Utilities/Nat", Locale.getDefault());

    @FXML
    private void exitPlatform(){

        Optional<ButtonType> alertOption = alertBox.raiseAlert("Exit?", resourceBundle.getString("ExitMessage"), Alert.AlertType.CONFIRMATION);
        if(alertOption.isPresent() && alertOption.get() == ButtonType.OK) {
            Platform.exit();
        }
    }

    /**
     *
     * @param actionEvent Login button event. Runs a DAO user/pass query.
     * @throws SQLException For all SQL errors.
     * Login method holds all login logic, and runs queries from the userQuery dao.
     */

    @FXML
    public void login(javafx.event.ActionEvent actionEvent) throws SQLException, IOException {

        //get text in username form.
        int userID = Integer.parseInt(userIDField.getText());

        //get text in password form.
        String password = passwordField.getText();
        //run a query on both username and password to determine if they match in the database.

        if(DAO.userDAO.getLogin(String.valueOf(userID), password)){
            Appointment.apptsMenu(actionEvent);
        }
        else {
            errorLabel.setText(resourceBundle.getString("LoginError"));
        }
    }

    /**
     * ERROR: Missing Resource Exception
     * Can't find resource for bundle java.util.PropertyResourceBundle, key Time Zone
     * Fix: Accidentally added a space. The resourceBundle calls the Time Zone as "TimeZone"
     * @param url url
     * @param resourceBundle resourceBundle for the language conversions
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        resourceBundle = ResourceBundle.getBundle("Utilities/Nat", Locale.getDefault());
        uidLabel.setText(resourceBundle.getString("User_ID") + ":");
        pwdLabel.setText(resourceBundle.getString("Password") + ":");
        loginButton.setText(resourceBundle.getString("LoginButton"));
        timeZoneLabel.setText(Locales.getZoneId());
        exitButton.setText(resourceBundle.getString("Cancel"));
        headerLabel.setText(resourceBundle.getString("Header"));


        if(Objects.equals(Locales.getLanguage(), "en")){
            System.out.println("Current Language: English");

        }
        else if (Objects.equals(Locales.getLanguage(), "fr")){
            System.out.println("Current Language: English");
        }
    }

}
