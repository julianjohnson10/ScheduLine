package Utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalTime;

public class date_time {

    public static String timestamp(Timestamp timestamp) {
        SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return date.format(timestamp);
    }

    public static ObservableList<LocalTime> timeList = FXCollections.observableArrayList();

    public static ObservableList<LocalTime> getTimeList(){
        timeList.add(LocalTime.now());
        return null;
    }

//
//    public static ObservableList<LocalTime> getTimeList{
//
//        LocalTime.of(1,0);
//
//    }

}
//    LocalDate date = LocalDate.now();
//    LocalTime time = LocalTime.ofSecondOfDay(LocalTime.now().toSecondOfDay());
//
//    LocalDateTime localDateTime = LocalDateTime.of(date, time);
//    ZoneId zoneId = ZoneId.systemDefault();
//    ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime,zoneId);
//
//    System.out.println(zonedDateTime.toLocalDate().toString() + " " + zonedDateTime.toLocalTime().toString()); // Concatenation of dates and times.\
//    System.out.println("User's time: " + zonedDateTime);
//
//    ZoneId utcZone = ZoneId.of("UTC");
//    ZonedDateTime utcZonedDateTime = ZonedDateTime.ofInstant(zonedDateTime.toInstant(), utcZone);
//        System.out.println("Local Time to UTC: " + utcZonedDateTime);
//
//    zonedDateTime = ZonedDateTime.ofInstant(utcZonedDateTime.toInstant(),zoneId);
//        System.out.println("UTC To User Time: ");
//    }
