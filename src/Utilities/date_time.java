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

    public static void setStartList() {

        String pattern = "h:mma";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        ZoneId zone = ZoneId.of("US/Eastern");
        ZoneId defaultZone = ZoneId.systemDefault();
        LocalDate localDate = LocalDate.now();

        for (int i = 8; i < 22; i++) {
            for (int n = 0; n <= 30; n += 30) {
                LocalTime localTime = LocalTime.of(i, n);
                ZonedDateTime zonedDateTime = ZonedDateTime.of(localDate, localTime, zone);
                ZonedDateTime zonedDateTime1 = zonedDateTime.withZoneSameInstant(ZoneId.of(String.valueOf(defaultZone)));
                startList.add(zonedDateTime1.format(dateTimeFormatter));
            }
        }
    }

    public static void printDates() {

        String pattern = "h:mma";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime localDate = LocalDateTime.now();
        LocalTime localTime = LocalTime.of(localDate.getHour(), localDate.getMinute());
        System.out.println(localTime.format(dateTimeFormatter));
    }
}