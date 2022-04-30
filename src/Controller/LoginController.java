package Controller;

import Model.Appointment;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    public Button loginButton;
    public TextField usernameField;
    public PasswordField passwordField;

    @FXML
    private void exitPlatform(){
        Platform.exit();
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
        String userName = usernameField.getText();
        System.out.println("Username: " + userName);

        //get text in password form.
        String password = passwordField.getText();

        //run a query on both username and password to determine if they match in the database.
        if(DAO.userQuery.getLogin(userName, password)){
            System.out.println("Login Successful!");
            Appointment.apptsMenu(actionEvent);
        }
        else {
            System.out.println("Login Failed!");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        System.out.println("HELLO");
    }
}
