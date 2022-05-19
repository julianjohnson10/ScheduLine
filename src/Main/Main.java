package Main;

import DAO.JDBC_Connector;
import Utilities.date_time;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Main class. The main method is called in this class.
 * @author Julian Johnson
 */

public class Main extends Application {
/***
 * Runtime Exception: Location is required.
 * Wasn't pointing to the correct FXML file for the main form.
 */
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("Utilities/Nat", Locale.getDefault());

    /**
     * ERROR: Exception in Application Start Method
     * Caused by: javafx.fxml.LoadException: Error resolving onAction='#loginButton', either the event handler is not in the Namespace or there is an error in the script.
     * FIX: added loginButton action to the FXML.
     * ERROR
     * @param stage Login Form stage.
     * @throws Exception exception
     */

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/LoginForm.fxml")));
        stage.setTitle(resourceBundle.getString("Header"));
        stage.centerOnScreen();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Entry point of program. Starts the JDBC connection. Closes the connection as the last thing it does before the program closes.
     * @param args argts
     * @throws Exception exception handler
     */
    public static void main(String[] args) throws Exception {
        /* Start the JDBC Connector */
        date_time.printDates();
        JDBC_Connector.openConnection();
        date_time.setStartList();
        launch(args);
        JDBC_Connector.closeConnection();
    }
}
