package Controller;

import DAO.userDAO;
import Model.User;
import Utilities.Locales;
import Utilities.activityLogger;
import Utilities.alertError;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Login Controller class. Holds all of the logic that is required in the loginform.
 */
public class LoginController implements Initializable {

    /**
     * Login button on the login form.
     */
    public Button loginButton;

    /**
     * Exit button on the login form.
     */
    public Button exitButton;

    /**
     * User ID field in the login form. Accepts only a userID, not a username.
     */
    public TextField userIDField;

    /**
     * Password textfield in the login form.
     */
    public PasswordField passwordField;

    /**
     * Label in the loginform for when errors occur, such as an incorrect userID/password combination.
     */
    public Label errorLabel;

    /**
     * Label for the systemdefault timezone.
     */
    public Label timeZoneLabel;

    /**
     * User ID label.
     */
    public Label uidLabel;

    /**
     * Password label.
     */
    public Label pwdLabel;

    /**
     * Label for the header.
     */
    public Label headerLabel;

    /**
     * Label for the words "Time Zone:". These labels will change depending on the system defauly language selection.
     */
    public Label tzLabel;


    /**
     * ResourceBundle for multiple language support.
     */
    ResourceBundle resourceBundle = ResourceBundle.getBundle("Utilities/Nat", Locale.getDefault());
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
     * If the login proves true, then the user will be taken to the main form.
     * @throws SQLException exception handler.
     * @throws IOException exception handler.
     */
    @FXML
    public void login() throws SQLException, IOException {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        //get text in username form.


        int userId = 0;
        String password = null;
        User loggedInUser = new User();

        if (userIDField.getText().isBlank() || passwordField.getText().isBlank()) {
            errorLabel.setText(resourceBundle.getString("EmptyMessage"));
            fadeOut.setNode(errorLabel);
            fadeOut.playFromStart();
        }
        else{
            try {
                userId = Integer.parseInt(userIDField.getText());
            } catch (NumberFormatException ignored) {
                errorLabel.setText(resourceBundle.getString("IDNotString"));
                fadeOut.setNode(errorLabel);
                fadeOut.playFromStart();
            }
            //get text in password form.
            password = passwordField.getText();
            if (userDAO.getLogin(userId, password)) {


                loggedInUser.setUserName(userDAO.getUserNamefromID(userId));
                loggedInUser.setUserId(userId);
                User.addUser(loggedInUser);

                activityLogger.logActivity(User.getUser().getUserName(), userDAO.getLogin(userId, password));
                MainFormController.mainMenu(stage);
            } else {
                if (userDAO.checkUser(userId)) {
                    loggedInUser.setUserName(userDAO.getUserNamefromID(userId));
                    loggedInUser.setUserId(userId);
                    User.addUser(loggedInUser);

                    errorLabel.setText(resourceBundle.getString("LoginError"));
                    fadeOut.setNode(errorLabel);
                    fadeOut.playFromStart();

                    activityLogger.logActivity(User.getUser().getUserName(), userDAO.getLogin(userId, password));
                } else {
                    errorLabel.setText(resourceBundle.getString("LoginError"));
                    fadeOut.setNode(errorLabel);
                    fadeOut.playFromStart();
                }
            }
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
