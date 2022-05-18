package Controller;

import DAO.userDAO;
import Model.User;
import Utilities.Locales;
import Utilities.activityLogger;
import Utilities.alertError;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

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
    public Label tzLabel;
    public static Integer userId;
    ResourceBundle resourceBundle = ResourceBundle.getBundle("Utilities/Nat", Locale.FRENCH);
    private final FadeTransition fadeOut = new FadeTransition(Duration.seconds(2.0));

    @FXML
    private void exitPlatform(){
        Stage stage = (Stage) exitButton.getScene().getWindow();
        Optional<ButtonType> alertOption = alertError.raiseAlert(stage,"Exit?", resourceBundle.getString("ExitMessage"), Alert.AlertType.CONFIRMATION);
        if(alertOption.isPresent() && alertOption.get() == ButtonType.OK) {
            Platform.exit();
        }
    }

    /**
     * Login method holds all login logic, and runs queries from the userQuery dao.
     */

    @FXML
    public void login() throws SQLException, IOException, InterruptedException {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        //get text in username form.
        userId = Integer.parseInt(userIDField.getText());
        //get text in password form.
        String password = passwordField.getText();

        User loggedInUser = new User();
        if(userDAO.getLogin(userId, password)){


            loggedInUser.setUserName(userDAO.getUserNamefromID(userId));
            loggedInUser.setUserId(userId);
            User.addUser(loggedInUser);

            activityLogger.logActivity(User.getUser().getUserName(), userDAO.getLogin(userId,password));
            MainFormController.mainMenu(stage);
        }
        else {
            if(userDAO.checkUser(userId)){
                loggedInUser.setUserName(userDAO.getUserNamefromID(userId));
                loggedInUser.setUserId(userId);
                User.addUser(loggedInUser);

                errorLabel.setText(resourceBundle.getString("LoginError"));
                fadeOut.setNode(errorLabel);
                fadeOut.playFromStart();

                activityLogger.logActivity(User.getUser().getUserName(), userDAO.getLogin(userId,password));
            }
            errorLabel.setText(resourceBundle.getString("LoginError"));
        }


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

        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setCycleCount(1);
        fadeOut.setAutoReverse(false);

        uidLabel.setText(resourceBundle.getString("User_ID") + ":");
        pwdLabel.setText(resourceBundle.getString("Password") + ":");
        loginButton.setText(resourceBundle.getString("LoginButton"));
        timeZoneLabel.setText(Locales.getZoneId());
        exitButton.setText(resourceBundle.getString("Cancel"));
        headerLabel.setText(resourceBundle.getString("Header"));
        tzLabel.setText(resourceBundle.getString("TimeZone") + ":");
    }

}
