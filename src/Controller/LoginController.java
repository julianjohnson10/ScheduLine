package Controller;

import DAO.userDAO;
import Utilities.Locales;
import Utilities.alertError;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
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
    public static Integer userId;
    public Label tzLabel;

    ResourceBundle resourceBundle = ResourceBundle.getBundle("Utilities/Nat", Locale.FRENCH);

    @FXML
    private void exitPlatform(){
        Optional<ButtonType> alertOption = alertError.raiseAlert("Exit?", resourceBundle.getString("ExitMessage"), Alert.AlertType.CONFIRMATION);
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
    public void login(ActionEvent actionEvent) throws SQLException, IOException {

        //get text in username form.
        userId = Integer.parseInt(userIDField.getText());
        //get text in password form.
        String password = passwordField.getText();
        //run a query on both username and password to determine if they match in the database.

        if(userDAO.getLogin(userId, password)){

            errorLabel.setText(resourceBundle.getString("LoginSuccess"));

            MainFormController.mainMenu(actionEvent);
        }
        else {
            errorLabel.setText(resourceBundle.getString("LoginError"));
        }
    }

    public static Integer getUserID(){
        return userId;
    }

    /**
     * ERROR: Missing Resource Exception
     * Can't find resource for bundle java.util.PropertyResourceBundle, key Time Zone
     * Fix: Accidentally added a space. The resourceBundle calls the Time Zone as "TimeZone"
     * @param url url
     * @param rb resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        uidLabel.setText(resourceBundle.getString("User_ID") + ":");
        pwdLabel.setText(resourceBundle.getString("Password") + ":");
        loginButton.setText(resourceBundle.getString("LoginButton"));
        timeZoneLabel.setText(Locales.getZoneId());
        exitButton.setText(resourceBundle.getString("Cancel"));
        headerLabel.setText(resourceBundle.getString("Header"));
        tzLabel.setText(resourceBundle.getString("TimeZone") + ":");


//        if(Objects.equals(Locales.getLanguage(), "en")){
//            System.out.println("Current Language: English");
//
//        }
//        else if (Objects.equals(Locales.getLanguage(), "fr")){
//            System.out.println("Current Language: English");
//        }
    }

}
