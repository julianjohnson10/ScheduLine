//package Utilities;
//
//import java.time.*;
//
//public class dateClass {
//
//    LocalDate date = LocalDate.now(); // Use a date picker.
//    //        LocalTime time = LocalTime.of(01,16);
//    LocalTime time = LocalTime.ofSecondOfDay(LocalTime.now().toSecondOfDay());
//
//    LocalDateTime localDateTime = LocalDateTime.of(date, time);
//    System.out.println(localDateTime);
//    ZoneId zoneId = ZoneId.systemDefault();
//    ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime,zoneId);
//
//    System.out.println(zonedDateTime.toLocalDate()); // Local Date
//    System.out.println(zonedDateTime.toLocalTime()); // Local Time
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
