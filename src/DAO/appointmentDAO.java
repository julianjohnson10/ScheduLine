package DAO;

import Controller.LoginController;
import Model.Appointment;
import javafx.collections.ObservableList;

import java.sql.*;

import static DAO.JDBC_Connector.connection;

/**
 * SQLException: Column 'Created_Date' not found.
 * Fix: Typo in name. 'Create_Date'.
 */

public abstract class appointmentDAO {

    public static ObservableList<Appointment> getAllAppts() throws SQLException {
        String sqlStatement = "SELECT * FROM appointments";

        PreparedStatement statement = connection.prepareStatement(sqlStatement);
        ResultSet results = statement.executeQuery();

        Appointment appointment;
        while (results.next()) {
            appointment = new Appointment();
            appointment.setApptId(results.getInt("Appointment_ID"));
            appointment.setTitle(results.getString("Title"));
            appointment.setDescription(results.getString("Description"));
            appointment.setLocation(results.getString("Location"));
            appointment.setType(results.getString("Type"));
            appointment.setStartDate(results.getDate("Start"));
            appointment.setEndDate(results.getDate("End"));
            appointment.setCreatedDate(results.getDate("Create_Date"));
            appointment.setCreatedBy(results.getString("Created_By"));
            appointment.setLastUpdate(results.getTimestamp("Last_Update"));
            appointment.setLastUpdatedBy(results.getString("Last_Updated_By"));
            appointment.setCustomerId(results.getInt("Customer_ID"));
            appointment.setUserId(results.getInt("User_ID"));
            appointment.setContactId(results.getInt("Contact_ID"));
            Appointment.addAppointment(appointment);
        }
        return Appointment.getAllAppointments();
    }

    public static ObservableList<Appointment> getUserAppts() throws SQLException {
        String sqlStatement = "SELECT * FROM appointments WHERE User_ID = ?";

        int userId = LoginController.getUserID();

        PreparedStatement statement = connection.prepareStatement(sqlStatement);
        statement.setInt(1, userId);
        ResultSet results = statement.executeQuery();

        Appointment appointment;
        while (results.next()) {
            appointment = new Appointment();
            appointment.setApptId(results.getInt("Appointment_ID"));
            appointment.setTitle(results.getString("Title"));
            appointment.setDescription(results.getString("Description"));
            appointment.setLocation(results.getString("Location"));
            appointment.setType(results.getString("Type"));
            appointment.setStartDate(results.getDate("Start"));
            appointment.setEndDate(results.getDate("End"));
            appointment.setCreatedDate(results.getDate("Create_Date"));
            appointment.setCreatedBy(results.getString("Created_By"));
            appointment.setLastUpdate(results.getTimestamp("Last_Update"));
            appointment.setLastUpdatedBy(results.getString("Last_Updated_By"));
            appointment.setCustomerId(results.getInt("Customer_ID"));
            appointment.setUserId(results.getInt("User_ID"));
            appointment.setContactId(results.getInt("Contact_ID"));
            Appointment.addAppointment(appointment);
        }
        return Appointment.getAllAppointments();
    }

    public static int createAppt(String Title, String Description, String Location, String Type, Date Start, Date End, Date create_date, String created_by, Timestamp last_update, String last_updated_by, Integer customerId, Integer userId, Integer contactId) throws SQLException {
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

}
