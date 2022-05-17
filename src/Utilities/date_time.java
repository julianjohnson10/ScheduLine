package Utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class date_time {

    public static String timestamp(Timestamp timestamp) {
        SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return date.format(timestamp);
    }

    public static ObservableList<String> startList = FXCollections.observableArrayList();

    public static void setStartList(){
        String pattern = "hh:mma";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        ZoneId zone = ZoneId.of("US/Eastern");
        ZoneId defaultZone = ZoneId.systemDefault();
        LocalDate localDate = LocalDate.now();

        for(int i = 8; i < 22; i++){
            for(int n = 0; n<=30; n+=30){
                LocalTime localTime = LocalTime.of(i,n);
                ZonedDateTime zonedDateTime = ZonedDateTime.of(localDate,localTime,zone);
                ZonedDateTime zonedDateTime1 = zonedDateTime.withZoneSameInstant(ZoneId.of(String.valueOf(defaultZone)));
                startList.add(zonedDateTime1.format(dateTimeFormatter));
            }
        }
    }

    public static void printDates(){
        String pattern = "HH:mm";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        ZoneId zone = ZoneId.of("US/Eastern");
        ZoneId defaultZone = ZoneId.systemDefault();
        LocalDate localDate = LocalDate.now();


        for(int i = 8; i < 22; i++){
            for(int n = 0; n<=30; n+=30){
                LocalTime localTime = LocalTime.of(i,n);

                ZonedDateTime zonedDateTime = ZonedDateTime.of(localDate,localTime,zone);
                ZonedDateTime zonedDateTime1 = zonedDateTime.withZoneSameInstant(ZoneId.of(String.valueOf(defaultZone)));

                System.out.println(zonedDateTime1);
            }
        }
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
