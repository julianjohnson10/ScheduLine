package DAO;

import Model.Appointment;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static DAO.JDBC_Connector.connection;

/**
 * SQLException: Column 'Created_Date' not found.
 * Fix: Typo in name. 'Create_Date'.
 */

public abstract class appointmentDAO {

    public static void selectAppointments() throws SQLException {
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
            Date last_update = results.getDate("Last_Update");
            String last_updated_by = results.getString("Last_Updated_By");
            int customer_id = results.getInt("Customer_ID");
            int user_id = results.getInt("User_ID");
            int contact_id = results.getInt("Contact_ID");

            System.out.println(apptId + " " + title);

        }
    }

}
