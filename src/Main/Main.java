package Main;

import DAO.JDBC_Connector;
import Utilities.date_time;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.time.*;
import java.time.format.DateTimeFormatter;
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
//    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("Utilities/Nat", Locale.FRENCH); Use this for French test. Changes the title.

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

    public static void main(String[] args) throws Exception {
        /* Start the JDBC Connector */
        JDBC_Connector.openConnection();
        date_time.setStartList();
        launch(args);

        LocalDate localDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime localTime = LocalTime.parse(LocalTime.now().format(formatter));
        LocalDateTime localDateTime = LocalDateTime.of(localDate,localTime);
//        ZoneId myZone = ZoneId.of("US/Eastern");
        ZoneId myZone = ZoneId.systemDefault();
//        ZoneId estZone = ZoneId.of("America/New York"); // Get EST time
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, myZone);
        System.out.println(zonedDateTime);

        JDBC_Connector.closeConnection();
    }
}
