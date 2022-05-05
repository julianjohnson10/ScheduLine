package Main;

import DAO.JDBC_Connector;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Main class. The main method is called in this class.
 * @author Julian Johnson
 * FUTURE ENHANCEMENT:
 *
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
        stage.setScene(new Scene(root));
        stage.show();
        stage.setResizable(false);
    }

    public static void main(String[] args) throws Exception {
        /* Start the JDBC Connector */
        JDBC_Connector.openConnection();
        launch(args);
        Date date = Date.valueOf(String.valueOf(LocalDate.now()));
        ZoneId zone = ZoneId.of("UTC");
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now(zone));

        JDBC_Connector.closeConnection();
        System.out.println("ENDING PROGRAM");
    }
}
