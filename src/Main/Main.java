package Main;

import DAO.JDBC_Connector;
import Utilities.alertBox;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import java.time.*;
import java.util.Locale;
import java.util.Optional;
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
    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("Utilities/Nat", Locale.getDefault());
    private static int apptID = 0;

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

        Parent root = FXMLLoader.load(getClass().getResource("/View/LoginForm.fxml"));
        stage.setTitle(resourceBundle.getString("Title"));
        stage.setScene(new Scene(root));
        stage.setOnCloseRequest(windowEvent -> {
            windowEvent.consume();
            shutdown(stage);
        });
        stage.show();
        stage.setResizable(false);
    }

    public void shutdown(Stage stage){
        Optional<ButtonType> alertOption = alertBox.raiseAlert(resourceBundle.getString("ExitTitle"), resourceBundle.getString("ExitMessage"), Alert.AlertType.CONFIRMATION);
        if(alertOption.isPresent() && alertOption.get() == ButtonType.OK) {
            Platform.exit();
        }
    }

    /**
     *
     * @return apptID. Counts up for each appt.
     */
    public static int generateApptID() {
        return apptID++;
    }

    public static void main(String[] args) throws Exception {
        /* Start the JDBC Connector */
        JDBC_Connector.openConnection();

//        ZoneId.getAvailableZoneIds().stream().filter(zone->zone.contains("America")).forEach(System.out::println);
        //Get current Date
        LocalDate date = LocalDate.now(); // Use a date picker.
//        LocalTime time = LocalTime.of(01,16);
        LocalTime time = LocalTime.ofSecondOfDay(LocalTime.now().toSecondOfDay());

        LocalDateTime localDateTime = LocalDateTime.of(date, time);
        System.out.println(localDateTime);
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime,zoneId);

        System.out.println(zonedDateTime.toLocalDate()); // Local Date
        System.out.println(zonedDateTime.toLocalTime()); // Local Time
        System.out.println(zonedDateTime.toLocalDate().toString() + " " + zonedDateTime.toLocalTime().toString()); // Concatenation of dates and times.\
        System.out.println("User's time: " + zonedDateTime);

        ZoneId utcZone = ZoneId.of("UTC");
        ZonedDateTime utcZonedDateTime = ZonedDateTime.ofInstant(zonedDateTime.toInstant(), utcZone);
        System.out.println("Local Time to UTC: " + utcZonedDateTime);

        zonedDateTime = ZonedDateTime.ofInstant(utcZonedDateTime.toInstant(),zoneId);
        System.out.println("UTC To User Time: ");

        launch(args);
        JDBC_Connector.closeConnection();
    }
}
