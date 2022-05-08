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
            appointment = new Appointment(results.getInt("Appointment_ID"), results.getString("Title"),
                    results.getString("Description"), results.getString("Location"), results.getString("Type"),
                    results.getDate("Start").toLocalDate(), results.getDate("End").toLocalDate(),results.getDate("Create_Date").toLocalDate(),
                    results.getString("Created_By"),results.getTimestamp("Last_Update"),results.getString("Last_Updated_By"),results.getInt("Customer_ID"),
                    results.getInt("User_ID"),results.getInt("Contact_ID"));
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
