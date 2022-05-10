package DAO;

import Model.Appointment;
import javafx.collections.ObservableList;
import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

import static DAO.JDBC_Connector.connection;

/**
 * SQLException: Column 'Created_Date' not found.
 * Fix: Typo in name. 'Create_Date'.
 */

public abstract class appointmentDAO {
    /**
     *
     * @return List of all appointments
     * @throws SQLException Sql error exceptions.
     * ERROR: SQLException: Column 'Created_Date' not found. Fixed by changing to 'Create_Date'
     */
    public static ObservableList<Appointment> getAllAppts() throws SQLException {
        String sqlStatement = "SELECT * FROM appointments";

        PreparedStatement statement = connection.prepareStatement(sqlStatement);
        ResultSet results = statement.executeQuery();

        while (results.next()) {
            Appointment appointment = new Appointment();
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            appointment.setApptId(results.getInt("Appointment_ID"));
            appointment.setTitle(results.getString("Title"));
            appointment.setDescription(results.getString("Description"));
            appointment.setLocation(results.getString("Location"));
            appointment.setType(results.getString("Type"));

            LocalDateTime start_dateL = (LocalDateTime) results.getObject("Start");
            ZoneId zoneId = ZoneId.systemDefault();
            ZonedDateTime start_dateZ = ZonedDateTime.of(start_dateL,zoneId);

            String startDate = start_dateL.format(format);
            System.out.println("Start Date: " + startDate);
//            String start_ldt = LocalDateTime.parse(start_dateZ.toLocalDateTime().format(format)).toString();
//            System.out.println("Start LDT: " + start_ldt);

//            LocalDateTime startDate = LocalDateTime.parse(start_dateL,format);
//            ZonedDateTime startDateZ = startDate.atZone(ZoneId.of("UTC"));
//            appointment.setStartDate(startDate);


//            LocalDate date = LocalDate.now();
//            System.out.println("Date: " + date);// Use a date picker.
            //        LocalTime time = LocalTime.of(01,16);
//            LocalTime time = LocalTime.ofSecondOfDay(LocalTime.now().toSecondOfDay());


            System.out.println("Local Date: " + start_dateZ.toLocalDate()); // Local Date
            System.out.println("Local Time: " + start_dateZ.toLocalTime()); // Local Time
            System.out.println("Concat: " + start_dateZ.toLocalDate().toString() + " " + start_dateZ.toLocalTime().toString());


            String end_date = results.getString("End");
            LocalDateTime endDate = LocalDateTime.parse(end_date,format);
            ZonedDateTime endDateZ = endDate.atZone(ZoneId.of("UTC"));
//            appointment.setEndDate(endDateZ);

            String create_date = results.getString("Create_Date");
            LocalDateTime createDate = LocalDateTime.parse(create_date,format);
            ZonedDateTime createDateZ = createDate.atZone(ZoneId.of("UTC"));
//            appointment.setCreatedDate(createDateZ);

            String last_update = results.getString("Last_Update");
            LocalDateTime lastUpdate = LocalDateTime.parse(last_update,format);
            ZonedDateTime lastUpdateZ = lastUpdate.atZone(ZoneId.of("UTC"));
//            appointment.setLastUpdate(lastUpdateZ);
            appointment.setCustomerId(results.getInt("Customer_ID"));
            appointment.setUserId(results.getInt("User_ID"));
            Appointment.addAppointment(appointment);
        }
        return Appointment.getAllAppointments();
    }

    public static int createAppt(String Title, String Description, String Location, String Type, Date Start, Date End, Date create_date, String created_by, Timestamp last_update, String last_updated_by, Integer customerId, Integer userId, Integer contactId) throws SQLException {
        String sqlStatement = "INSERT INTO Appointments(Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES(?,?,?,?,?,?,NOW(),?,NOW(),?,?,?,?)";

        PreparedStatement statement = connection.prepareStatement(sqlStatement);
        statement.setString(1, Title);
        statement.setString(2, Description);
        statement.setString(3, Location);
        statement.setString(4, Type);
        statement.setDate(5, Start);
        statement.setDate(6, End);
        statement.setDate(7, create_date);
        statement.setString(8, created_by);
        statement.setTimestamp(9, last_update);
        statement.setString(10, last_updated_by);
        statement.setInt(11, customerId);
        statement.setInt(12, userId);
        statement.setInt(13, contactId);

        return statement.executeUpdate();
    }

    public static int updateAppt(String Title, String Description, String Location, String Type, Date Start, Date End, Date create_date, String created_by, Timestamp last_update, String last_updated_by, Integer customerId, Integer userId, Integer contactId) throws SQLException {
        String sqlStatement = "INSERT INTO Appointments(Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement statement = connection.prepareStatement(sqlStatement);
        statement.setString(1, Title);
        statement.setString(2, Description);
        statement.setString(3, Location);
        statement.setString(4, Type);
        statement.setDate(5, Start);
        statement.setDate(6, End);
        statement.setDate(7, create_date);
        statement.setString(8, created_by);
        statement.setTimestamp(9, last_update);
        statement.setString(10, last_updated_by);
        statement.setInt(11, customerId);
        statement.setInt(12, userId);
        statement.setInt(13, contactId);

        return statement.executeUpdate();
    }


    public static int deleteAppt(Integer apptID) throws SQLException {
        String sqlStatement = "DELETE FROM Appointments WHERE Appointment_ID = ?";
        PreparedStatement statement = connection.prepareStatement(sqlStatement);
        statement.setInt(1, apptID);
        return statement.executeUpdate();
    }

    public static boolean checkCustomer(Integer customerID) throws SQLException {
        String sqlStatement = "SELECT * FROM Appointments WHERE Customer_ID = ?";
        PreparedStatement statement = connection.prepareStatement(sqlStatement);
        statement.setInt(1, customerID);
        ResultSet resultSet = statement.executeQuery();

        try {
            if(resultSet.next()) {
                return true;
            }
            else if(!resultSet.next()){
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
