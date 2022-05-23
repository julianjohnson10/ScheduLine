package DAO;

import Model.Appointment;
import Model.User;
import Utilities.date_time;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.time.*;

import static DAO.JDBC_Connector.connection;

/**
 * Appointment Data Access Object. Runs SQL queries on the Appointments table and return results.
 * SQLException: Column 'Created_Date' not found.
 * Fix: Typo in name. 'Create_Date'.
 */
public abstract class appointmentDAO {

    /**
     * @return List of all appointments. Creates appointment objects for each Appointment currently in the database, and adds them to an observable array.
     * @throws SQLException Sql error exceptions.
     * ERROR: SQLException: Column 'Created_Date' not found. Fixed by changing to 'Create_Date'
     * Attempt#1: My error was that I was pulling in a Timestamp as a timestamp, which gets auto-converted to local time.
     * I decided to switch to the value of the timestamp given the string in the database. Makes more sense.
     * I was very confused about how MST would add two hours. I focused too much time trying to fix my setters and getters in my Appointments class.
     **/
    public static ObservableList<Appointment> getAllAppts() throws SQLException {
        String sqlStatement = "SELECT * FROM appointments LEFT JOIN contacts ON contacts.Contact_ID = appointments.Contact_ID;";
        PreparedStatement statement = connection.prepareStatement(sqlStatement);
        ResultSet results = statement.executeQuery();
        ObservableList<Appointment> apptsList = FXCollections.observableArrayList();

        while (results.next()) {
            Appointment appointment = new Appointment();
            appointment.setApptId(results.getInt("Appointment_ID"));
            appointment.setTitle(results.getString("Title"));
            appointment.setDescription(results.getString("Description"));
            appointment.setLocation(results.getString("Location"));
            appointment.setContactName(results.getString("Contact_Name"));
            appointment.setType(results.getString("Type"));
            Timestamp start = Timestamp.valueOf(results.getString("Start"));
            Timestamp end = Timestamp.valueOf(results.getString("End"));
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
     * @return The current week number, for use in the weekly appointment filter.
     * @throws SQLException exception handler for SQL errors.
     */
    private static Integer getWeekNumber() throws SQLException {
        String sql = "SELECT WEEK(CURDATE())";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
        rs.next();
        return rs.getInt(1);
    }

    /**
     * @return The number of the current month, for use in the monthly filter.
     * @throws SQLException exception handler.
     */
    private static Integer getMonthNumber() throws SQLException {
        String sql = "SELECT MONTH(CURDATE())";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
        rs.next();
        return rs.getInt(1);
    }

    /**
     * @return an Observable array list of all the weekly appointments.
     * @throws SQLException exception handler for SQL errors.
     */
    public static ObservableList<Appointment> getWeekly() throws SQLException {
        Integer weekNumber = getWeekNumber();
        String sqlStatement = "SELECT * FROM appointments LEFT JOIN contacts ON contacts.Contact_ID = appointments.Contact_ID WHERE WEEK(Start) = ? AND YEAR(Start) = YEAR(CURDATE())";

        PreparedStatement statement = connection.prepareStatement(sqlStatement);
        statement.setInt(1,weekNumber);
        ResultSet results = statement.executeQuery();
        ObservableList<Appointment> apptsList = FXCollections.observableArrayList();

        while (results.next()) {
            Appointment appointment = new Appointment();
            appointment.setApptId(results.getInt("Appointment_ID"));
            appointment.setTitle(results.getString("Title"));
            appointment.setDescription(results.getString("Description"));
            appointment.setLocation(results.getString("Location"));
            appointment.setContactName(results.getString("Contact_Name"));
            appointment.setType(results.getString("Type"));
            Timestamp start = Timestamp.valueOf(results.getString("Start"));
            Timestamp end = Timestamp.valueOf(results.getString("End"));
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
     * @return an Observable List of monthly appointments for the monthly filter.
     * @throws SQLException exception handler for SQL errors.
     */
    public static ObservableList<Appointment> getMonthly() throws SQLException {
        Integer monthNumber = getMonthNumber();
        String sqlStatement = "SELECT * FROM appointments LEFT JOIN contacts ON contacts.Contact_ID = appointments.Contact_ID WHERE MONTH(Start) = ? AND YEAR(Start) = YEAR(CURDATE())";

        PreparedStatement statement = connection.prepareStatement(sqlStatement);
        statement.setInt(1,monthNumber);
        ResultSet results = statement.executeQuery();
        ObservableList<Appointment> apptsList = FXCollections.observableArrayList();

        while (results.next()) {
            Appointment appointment = new Appointment();
            appointment.setApptId(results.getInt("Appointment_ID"));
            appointment.setTitle(results.getString("Title"));
            appointment.setDescription(results.getString("Description"));
            appointment.setLocation(results.getString("Location"));
            appointment.setContactName(results.getString("Contact_Name"));
            appointment.setType(results.getString("Type"));
            Timestamp start = Timestamp.valueOf(results.getString("Start"));
            Timestamp end = Timestamp.valueOf(results.getString("End"));
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
     * Database Access Object for creating the appointments. Creates a prepared statement based on the overloaded params.
     * @param Title The title of the appointment.
     * @param Description The description of the appointment.
     * @param Location The location of the appointment.
     * @param Type The type of appointment.
     * @param apptDate The date of the appointment.
     * @param startTime The start time of the appointment.
     * @param endTime The end time of the appointment.
     * @param customerID The customer's ID.
     * @param userID The user's ID.
     * @param contactID The contact's ID.
     * @throws SQLException exception handler for sql errors.
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
        statement.setTimestamp(7, date_time.setTimestamp());
        statement.setString(8, User.getUser().getUserName());
        statement.setTimestamp(9, date_time.setTimestamp());
        statement.setString(10, User.getUser().getUserName());
        statement.setInt(11, customerID);
        statement.setInt(12, userID);
        statement.setInt(13, contactID);
        statement.executeUpdate();
    }

    /**
     * DAO for updating the appointments using the overloaded params.
     * @param apptID Appointment ID.
     * @param Title Appointment Title.
     * @param Description Appointment description.
     * @param Location Appointment location.
     * @param Type Appointment type.
     * @param apptDate Appointment date.
     * @param Start Appointment start time.
     * @param End Appointment end time.
     * @param last_update Appointment last update timestamp.
     * @param last_updated_by Appointment last updated by.
     * @param customerId customer ID.
     * @param userId User ID.
     * @param contactId Contact ID.
     * @throws SQLException exception handler.
     */
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
        statement.setTimestamp(7, date_time.setTimestamp());
        statement.setString(8, last_updated_by);
        statement.setInt(9, customerId);
        statement.setInt(10, userId);
        statement.setInt(11, contactId);
        statement.setInt(12, apptID);

        statement.executeUpdate();
    }

    /**
     * Deletes an appointment with the overloaded appointment ID.
     * @param apptID The appointment ID.
     * @throws SQLException SQL exception handler.
     */
    public static void deleteAppt(Integer apptID) throws SQLException {
        String sqlStatement = "DELETE FROM Appointments WHERE Appointment_ID = ?";
        PreparedStatement statement = connection.prepareStatement(sqlStatement);
        statement.setInt(1, apptID);
        statement.executeUpdate();
    }

    /**
     * Determines if the customer exists in the database based on customer ID.
     * @param customerID The customer ID.
     * @return boolean value based on if customer ID exists.
     * @throws SQLException Exception handler for SQL errors.
     */
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
