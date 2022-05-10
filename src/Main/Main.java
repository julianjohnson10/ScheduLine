package Main;

import DAO.JDBC_Connector;
import DAO.divisionDAO;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.*;
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
        stage.centerOnScreen();
        stage.show();
        stage.setResizable(false);
    }

    public static void main(String[] args) throws Exception {
        /* Start the JDBC Connector */
        JDBC_Connector.openConnection();

        launch(args);
        divisionDAO.getCountries();
//        Date date = Date.valueOf(String.valueOf(LocalDate.now()));
        ZoneId zone = ZoneId.of("UTC");
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now(zone));

        LocalDate date = LocalDate.now(); // Use a date picker.
        //        LocalTime time = LocalTime.of(01,16);
        LocalTime time = LocalTime.ofSecondOfDay(LocalTime.now().toSecondOfDay());

        LocalDateTime localDateTime = LocalDateTime.of(date, time);

        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime,zoneId);

        System.out.println(localDateTime);
        System.out.println(zonedDateTime.toLocalDate()); // Local Date
        System.out.println(zonedDateTime.toLocalTime()); // Local Time
        System.out.println(zonedDateTime.toLocalDate().toString() + " " + zonedDateTime.toLocalTime().toString()); // Concatenation of dates and times.\
        System.out.println("User's time: " + zonedDateTime);

        ZoneId utcZone = ZoneId.of("UTC");
        ZonedDateTime utcZonedDateTime = ZonedDateTime.ofInstant(zonedDateTime.toInstant(), utcZone);
        System.out.println("Local Time to UTC: " + utcZonedDateTime);

        zonedDateTime = ZonedDateTime.ofInstant(utcZonedDateTime.toInstant(),zoneId);
        System.out.println("UTC To User Time: " + zonedDateTime);
        JDBC_Connector.closeConnection();
    }
}
