package Utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class date_time {

    public static Timestamp setTimestamp() {
        LocalDateTime localDateTime = LocalDateTime.now();
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
        ZonedDateTime zonedDateTime1 = zonedDateTime.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime localDateTime1 = zonedDateTime1.toLocalDateTime();
        return Timestamp.valueOf(localDateTime1);
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

        String pattern = "hh:mma";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime localDate = LocalDateTime.now();
        LocalTime localTime = LocalTime.of(localDate.getHour(),localDate.getMinute());
        System.out.println(localTime.format(dateTimeFormatter));


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
