package Main;

import DAO.JDBC_Connector;
import DAO.userQuery;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.Statement;
import java.time.*;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

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

    private static int apptID = 0;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View/LoginForm.fxml"));
        stage.setTitle("Login");
        stage.setScene(new Scene(root, 473, 316));
        stage.setResizable(false);
        stage.show();
    }

    /**
     *
     * @return apptID. Counts up for each appt.
     */
    public static int generateApptID() {
        return apptID++;
    }

    public static void main(String[] args) throws Exception {
        JDBC_Connector.openConnection();
//        userQuery.select();

//        Locale fr_FR = new Locale("fr", "FR");
//        Locale en_US = new Locale("en", "US");
//
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Enter a language: en, fr");
//        String language = scanner.next();
//
//        if(language.equals("fr")){
//            Locale.setDefault(fr_FR);
//        }
//        else if(language.equals("en")){
//            Locale.setDefault(en_US);
//        }
//        else
//            System.out.println("Language not supported");
//        ZoneId.getAvailableZoneIds().stream().filter(zone->zone.contains("America")).forEach(System.out::println);
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
        ResourceBundle resourceBundle = ResourceBundle.getBundle("Utilities/Nat", Locale.getDefault());

        try
        {
            if (Locale.getDefault().getLanguage().equals("fr")) {
                System.out.println(resourceBundle.getString("Username"));
            }
            else if (Locale.getDefault().getLanguage().equals("en"))
                System.out.println(resourceBundle.getString("Username"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }


        launch(args);


        JDBC_Connector.closeConnection();
    }
}
