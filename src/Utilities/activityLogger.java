package Utilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

/**
 * Class for logging Login activity.
 */
public class activityLogger {

    /**
     * Each attempt is logged and appended to a file.
     * @param user the user name.
     * @param login If login was true or false.
     * @throws IOException exception handler.
     */
    public static void logActivity(String user, boolean login) throws IOException {

        Path filePath = Path.of("login_activity.txt");
        if(login){
            Files.writeString(filePath, "User: " +user+"\nLogin Status: Successful Login\nTimeStamp: " + date_time.setTimestamp() + "\n\n", CREATE, APPEND);
        }
        else{
            Files.writeString(filePath, "User: " +user+ "\nLogin Status: Failed Login Attempt\nTimeStamp: " + date_time.setTimestamp() + "\n\n", CREATE, APPEND);
        }
    }
}
