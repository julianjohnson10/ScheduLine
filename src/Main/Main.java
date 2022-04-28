package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;

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

    public static void main(String[] args) throws SQLException {
//        DBConnection.openConnection();
        launch();
//        Statement selectStatement = DBConnection.connection.createStatement();
//        ResultSet resultSet = selectStatement.executeQuery("SELECT * FROM appointments");
//        System.out.println(resultSet.getString(1));
//        DBConnection.closeConnection();
    }
}
