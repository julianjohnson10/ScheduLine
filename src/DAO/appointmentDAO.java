package DAO;

import Model.Appointment;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.DatePicker;

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
        String sqlStatement = "SELECT * FROM appointments LEFT JOIN contacts ON contacts.Contact_ID = appointments.Contact_ID;";
        PreparedStatement statement = connection.prepareStatement(sqlStatement);
        ResultSet results = statement.executeQuery();
        ObservableList<Appointment> apptsList = FXCollections.observableArrayList();

        while (results.next()) {
            Appointment appointment = new Appointment();
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mma");

            appointment.setApptId(results.getInt("Appointment_ID"));
            appointment.setTitle(results.getString("Title"));
            appointment.setDescription(results.getString("Description"));
            appointment.setLocation(results.getString("Location"));
            appointment.setContactName(results.getString("Contact_Name"));
            appointment.setType(results.getString("Type"));

            Timestamp start = results.getTimestamp("Start");
            Timestamp end = results.getTimestamp("End");
            LocalDateTime startDate = start.toLocalDateTime();
            LocalDateTime endDate = end.toLocalDateTime();


            appointment.setStartDate(startDate);
            appointment.setEndDate(endDate);
            appointment.setCustomerId(results.getInt("Customer_ID"));
            appointment.setUserId(results.getInt("User_ID"));
            appointment.setContactId(results.getInt("Contact_ID"));

            Appointment.addAppointment(appointment);

            apptsList.add(appointment);
        }
        return apptsList;
    }


    private static Integer getWeekNumber() throws SQLException {
        String sql = "SELECT WEEK(CURDATE())";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
        rs.next();
        return rs.getInt(1);
    }

    private static Integer getMonthNumber() throws SQLException {
        String sql = "SELECT MONTH(CURDATE())";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
        rs.next();
        return rs.getInt(1);
    }

    public static ObservableList<Appointment> getWeekly() throws SQLException {
        Integer weekNumber = getWeekNumber();
        String sqlStatement = "SELECT * FROM appointments LEFT JOIN contacts ON contacts.Contact_ID = appointments.Contact_ID WHERE WEEK(Start) = ? AND YEAR(Start) = YEAR(CURDATE())";

        PreparedStatement statement = connection.prepareStatement(sqlStatement);
        statement.setInt(1,weekNumber);
        ResultSet results = statement.executeQuery();
        ObservableList<Appointment> apptsList = FXCollections.observableArrayList();

        while (results.next()) {
            Appointment appointment = new Appointment();
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mma");
            appointment.setApptId(results.getInt("Appointment_ID"));
            appointment.setTitle(results.getString("Title"));
            appointment.setDescription(results.getString("Description"));
            appointment.setLocation(results.getString("Location"));
            appointment.setContactName(results.getString("Contact_Name"));
            appointment.setType(results.getString("Type"));
            Timestamp start = results.getTimestamp("Start");
            Timestamp end = results.getTimestamp("End");
            LocalDateTime startDate = start.toLocalDateTime();
            LocalDateTime endDate = end.toLocalDateTime();
            appointment.setStartDate(startDate);
            appointment.setEndDate(endDate);
            appointment.setCustomerId(results.getInt("Customer_ID"));
            appointment.setUserId(results.getInt("User_ID"));
            appointment.setContactId(results.getInt("Contact_ID"));
            Appointment.addAppointment(appointment);
            apptsList.add(appointment);
        }
        return apptsList;
    }

    public static ObservableList<Appointment> getMonthly() throws SQLException {
        Integer monthNumber = getMonthNumber();
        String sqlStatement = "SELECT * FROM appointments LEFT JOIN contacts ON contacts.Contact_ID = appointments.Contact_ID WHERE MONTH(Start) = ? AND YEAR(Start) = YEAR(CURDATE())";

        PreparedStatement statement = connection.prepareStatement(sqlStatement);
        statement.setInt(1,monthNumber);
        ResultSet results = statement.executeQuery();
        ObservableList<Appointment> apptsList = FXCollections.observableArrayList();

        while (results.next()) {
            Appointment appointment = new Appointment();
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mma");
            appointment.setApptId(results.getInt("Appointment_ID"));
            appointment.setTitle(results.getString("Title"));
            appointment.setDescription(results.getString("Description"));
            appointment.setLocation(results.getString("Location"));
            appointment.setContactName(results.getString("Contact_Name"));
            appointment.setType(results.getString("Type"));
            Timestamp start = results.getTimestamp("Start");
            Timestamp end = results.getTimestamp("End");
            LocalDateTime startDate = start.toLocalDateTime();
            LocalDateTime endDate = end.toLocalDateTime();
            appointment.setStartDate(startDate);
            appointment.setEndDate(endDate);
            appointment.setCustomerId(results.getInt("Customer_ID"));
            appointment.setUserId(results.getInt("User_ID"));
            appointment.setContactId(results.getInt("Contact_ID"));
            Appointment.addAppointment(appointment);
            apptsList.add(appointment);
        }
        return apptsList;
    }




    /**
     * ERROR: SQLException: Column count doesn't match value count at row 1. Forgot to update the sqlStatement String with the correct columns and bind parameters.
     * @param Title
     * @param Description
     * @param Location
     * @param Type
     * @param apptDate
     * @param startTime
     * @param endTime
     * @throws SQLException
     */
    public static void createAppt(String Title, String Description, String Location, String Type, LocalDate apptDate, LocalTime startTime, LocalTime endTime, Integer customerID, Integer userID, Integer contactID) throws SQLException {
        String sqlStatement = "INSERT INTO Appointments(Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement statement = connection.prepareStatement(sqlStatement);
        LocalDateTime startLDT = LocalDateTime.of(apptDate, startTime);
        ZonedDateTime startZDT = startLDT.atZone(ZoneId.systemDefault());
        ZonedDateTime startTarget = startZDT.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime startDate = startTarget.toLocalDateTime();
        Timestamp startTS = Timestamp.valueOf(startDate);

        LocalDateTime endLDT = LocalDateTime.of(apptDate, endTime);
        ZonedDateTime endZDT = endLDT.atZone(ZoneId.systemDefault());
        ZonedDateTime endTarget = endZDT.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime endDate = endTarget.toLocalDateTime();
        Timestamp endTS = Timestamp.valueOf(endDate);

        statement.setString(1, Title);
        statement.setString(2, Description);
        statement.setString(3, Location);
        statement.setString(4, Type);
        statement.setTimestamp(5, startTS);
        statement.setTimestamp(6, endTS);
        statement.setObject(7, LocalDateTime.now());
        statement.setString(8, User.getUser().getUserName());
        statement.setObject(9, LocalDateTime.now());
        statement.setString(10, User.getUser().getUserName());
        statement.setInt(11, customerID);
        statement.setInt(12, userID);
        statement.setInt(13, contactID);
        statement.executeUpdate();
    }

    public static void updateAppt(Integer apptID, String Title, String Description, String Location, String Type, LocalDate apptDate, LocalTime Start, LocalTime End, Timestamp last_update, String last_updated_by, Integer customerId, Integer userId, Integer contactId) throws SQLException {
        String sqlStatement = "UPDATE Appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";

        PreparedStatement statement = connection.prepareStatement(sqlStatement);
        LocalDateTime startLDT = LocalDateTime.of(apptDate, Start);
        ZonedDateTime startZDT = startLDT.atZone(ZoneId.systemDefault());
        ZonedDateTime startTarget = startZDT.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime startDate = startTarget.toLocalDateTime();
        Timestamp startTS = Timestamp.valueOf(startDate);

        LocalDateTime endLDT = LocalDateTime.of(apptDate, End);
        ZonedDateTime endZDT = endLDT.atZone(ZoneId.systemDefault());
        ZonedDateTime endTarget = endZDT.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime endDate = endTarget.toLocalDateTime();
        Timestamp endTS = Timestamp.valueOf(endDate);

        statement.setString(1, Title);
        statement.setString(2, Description);
        statement.setString(3, Location);
        statement.setString(4, Type);
        statement.setTimestamp(5, startTS);
        statement.setTimestamp(6, endTS);
        statement.setTimestamp(7, last_update);
        statement.setString(8, last_updated_by);
        statement.setInt(9, customerId);
        statement.setInt(10, userId);
        statement.setInt(11, contactId);
        statement.setInt(12, apptID);

        statement.executeUpdate();
    }


    public static void deleteAppt(Integer apptID) throws SQLException {
        String sqlStatement = "DELETE FROM Appointments WHERE Appointment_ID = ?";
        PreparedStatement statement = connection.prepareStatement(sqlStatement);
        statement.setInt(1, apptID);
        statement.executeUpdate();
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

    public static int getAppointmentID() throws SQLException {
        String sqlStatement = "SELECT AUTO_INCREMENT FROM information_schema.TABLES WHERE TABLE_SCHEMA = \"client_schedule\" AND TABLE_NAME = \"appointments\"";
        PreparedStatement statement = connection.prepareStatement(sqlStatement);
        ResultSet result = statement.executeQuery();
        int apptID = 0;
        while (result.next()) {
            apptID = result.getInt("AUTO_INCREMENT");
        }
        return apptID;
    }
}
