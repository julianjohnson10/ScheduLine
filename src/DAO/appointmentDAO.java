package DAO;

import Model.Appointment;
import javafx.collections.FXCollections;
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
        String sqlStatement = "SELECT * FROM appointments LEFT JOIN contacts ON contacts.Contact_ID = appointments.Contact_ID;";
        PreparedStatement statement = connection.prepareStatement(sqlStatement);
        ResultSet results = statement.executeQuery();
        ObservableList<Appointment> apptsList = FXCollections.observableArrayList();

        while (results.next()) {
            Appointment appointment = new Appointment();
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            appointment.setApptId(results.getInt("Appointment_ID"));
            appointment.setTitle(results.getString("Title"));
            appointment.setDescription(results.getString("Description"));
            appointment.setLocation(results.getString("Location"));
            appointment.setContactName(results.getString("Contact_Name"));
            appointment.setType(results.getString("Type"));

            ZonedDateTime start_dateZ = ((LocalDateTime) results.getObject("Start")).atZone(ZoneId.systemDefault());
            String startDate = start_dateZ.format(format);
            appointment.setStartDate(startDate);

            LocalDateTime endDate = (LocalDateTime) results.getObject("End");

//            appointment.setEndDate(endDate);
            System.out.println(results.getString("End"));
//            System.out.println(end_dateZ);
            System.out.println(endDate);

            appointment.setCustomerId(results.getInt("Customer_ID"));
            appointment.setUserId(results.getInt("User_ID"));
            appointment.setContactId(results.getInt("Contact_ID"));

            Appointment.addAppointment(appointment);

            apptsList.add(appointment);
        }
        return apptsList;
    }

    public static int createAppt(String Title, String Description, String Location, String contact, String Type, LocalDate apptDate, LocalTime startTime, LocalTime endTime, Integer customerId, Integer userId) throws SQLException {
        String sqlStatement = "INSERT INTO Appointments(Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement statement = connection.prepareStatement(sqlStatement);
        statement.setString(1, Title);
        statement.setString(2, Description);
        statement.setString(3, Location);
        statement.setString(4, Type);
        statement.setObject(5, LocalDate.now());
        statement.setObject(6, LocalDate.now());
        statement.setObject(7, LocalDateTime.now());
        statement.setString(8, userDAO.getUserInfo().getUserName());
        statement.setObject(9, LocalDateTime.now());
        statement.setString(10, userDAO.getUserInfo().getUserName());
        statement.setInt(11, customerId);
        statement.setInt(12, userId);

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
