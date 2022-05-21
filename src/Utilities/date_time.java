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
    public static ObservableList<String> endList = FXCollections.observableArrayList();

    /**
     * Time is restricted to display as local time, relative to 8-22 hours EST, tacking on 30 minutes for each hour increment.
     * The start time gets added to the array in the double for.
     */
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

    /**
     * End time list gets filled.
     * LAMBDA JUSTIFICATION: The lambda expression makes it easier to add 30 minutes to each start time, and add it to the list of end times.
     * @param startList the start time array.
     */
    public static void setEndList(ObservableList<String> startList) {

        String pattern = "h:mma";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);

        //Lambda expression
        startList.forEach(
                start -> {
                    LocalTime localTime = LocalTime.parse(start,dateTimeFormatter).plusMinutes(30);
                    endList.add(localTime.format(dateTimeFormatter));
                }
        );
    }
}