package Utilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;


public class activityLogger {

    public static void logActivity(String user, boolean login) throws IOException {
        Path filePath = Path.of("login_activity.txt");
        if(login){
            Files.writeString(filePath, "User: " +user+"\nLogin Status: Successful Login\nTimeStamp: " + Timestamp.valueOf(LocalDateTime.now()) + "\n\n", CREATE, APPEND);
        }
        else{
            Files.writeString(filePath, "User: " +user+ "\nLogin Status: Failed Login Attempt\nTimeStamp: " + Timestamp.valueOf(LocalDateTime.now()) + "\n\n", CREATE, APPEND);
        }
    }
}
