package DAO;

import Model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

import static DAO.JDBC_Connector.connection;

/**
 * SQLException: Column 'Created_Date' not found.
 * Fix: Typo in name. 'Create_Date'.
 */

public abstract class appointmentDAO {

    static ObservableList<Appointment> appointments = FXCollections.observableArrayList();

    public static ObservableList<Appointment> selectAppointments() throws SQLException {
        String sqlStatement = "SELECT * FROM appointments";
        PreparedStatement statement = connection.prepareStatement(sqlStatement);
        ResultSet results = statement.executeQuery();

        while (results.next()) {
            int apptId = results.getInt("Appointment_ID");
            String title = results.getString("Title");
            String description = results.getString("Description");
            String location = results.getString("Location");
            String Type = results.getString("Type");
            Date start_date = results.getDate("Start");
            Date end_date = results.getDate("End");
            Date create_date = results.getDate("Create_Date");
            String created_by = results.getString("Created_By");
            Timestamp last_update = results.getTimestamp("Last_Update");
            String last_updated_by = results.getString("Last_Updated_By");
            int customer_id = results.getInt("Customer_ID");
            int user_id = results.getInt("User_ID");
            int contact_id = results.getInt("Contact_ID");

            Appointment appointment = new Appointment(apptId, title, description, location, Type, start_date, end_date, create_date, created_by, last_update, last_updated_by, customer_id, user_id, contact_id);
            Appointment.addAppointment(appointment);

        }
        return Appointment.getAllAppointments();
    }
}
