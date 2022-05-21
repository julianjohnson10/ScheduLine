package Utilities;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * Class for creating custom alert messages as an alert box.
 */
public class alertError {
    /**
     * Custom Alert Box.
     * @param title Title of the alert box.
     * @param message Message to the user.
     * @param alertType Alertbox type.
     * @return The alertBox.
     */
    public static Optional<ButtonType> raiseAlert(String title, String message, AlertType alertType){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(alertType.toString());
        alert.setContentText(message);
        return alert.showAndWait();
    }
}
