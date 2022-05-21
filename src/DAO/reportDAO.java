package DAO;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static DAO.JDBC_Connector.connection;

/**
 * The report Database Access Object returns information for use in the reports in the "Reports" tab of the Main Form.
 */
public abstract class reportDAO {

    /**
     * Gets each type of appointment per month.
     * @param month A month integer value is passed in.
     * @return a list of distinct types for each month.
     * @throws SQLException exception handler.
     */
    public static List<String> getTypes(Integer month) throws SQLException {
        //Distinct will turn all values into unique, so i don't have to reiterate through the list again and pluck them out.
        String sqlStatement = "SELECT DISTINCT Type FROM appointments WHERE MONTH(Start) = ?";
        List<String> typeList = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement(sqlStatement);
        statement.setInt(1,month);
        ResultSet results = statement.executeQuery();
        while (results.next()) {
            String type = results.getString("Type");
            typeList.add(type);
        }
        return typeList;
    }

    /**
     * Gather a list of integers. These will be used in the first report.
     * @param type The appointment type.
     * @param month A month integer.
     * @return a list of the counts of the appointment types from the appointments table, based on a specific month.
     * @throws SQLException
     */
    public static List<Integer> generateReport1(String type, int month) throws SQLException {
        String sqlStatement = "SELECT COUNT(Type) FROM appointments WHERE MONTH(Start) = ? AND Type = ?";
        List<Integer> countList = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement(sqlStatement);
        statement.setInt(1, month);
        statement.setString(2, type);
        ResultSet results = statement.executeQuery();
        while (results.next()) {
            Integer count = results.getInt("COUNT(Type)");
            countList.add(count);
        }
        return countList;
    }

    /**
     * Return a list of appointment information to generate report 2 with.
     * @param contact The contact name gets passed in.
     * @return a list of appointment information to add to the report.
     * @throws SQLException exception handler.
     */
    public static List<String> generateReport2(String contact) throws SQLException {
        String sqlStatement = "SELECT Appointment_ID,Title,Type,Description,Start,End,Customer_ID FROM appointments LEFT JOIN contacts ON contacts.Contact_ID = appointments.Contact_ID WHERE Contact_Name = ?";
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy h:mma");
        List<String> apptsList = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement(sqlStatement);
        statement.setString(1,contact);
        ResultSet results = statement.executeQuery();
        while (results.next()) {
            int apptID = results.getInt("Appointment_ID");
            String title = results.getString("Title");
            String type = results.getString("Type");
            String desc = results.getString("Description");
            Timestamp start = results.getTimestamp("Start");
            Timestamp end = results.getTimestamp("End");
            LocalDateTime startDate = start.toLocalDateTime();
            LocalDateTime endDate = end.toLocalDateTime();
            ZonedDateTime szdt =  startDate.atZone(ZoneId.of("UTC"));
            ZonedDateTime szdtTarget = szdt.withZoneSameInstant(ZoneId.systemDefault());
            LocalDateTime slocalDateTime = szdtTarget.toLocalDateTime();
            ZonedDateTime ezdt =  endDate.atZone(ZoneId.of("UTC"));
            ZonedDateTime ezdtTarget = ezdt.withZoneSameInstant(ZoneId.systemDefault());
            LocalDateTime elocalDateTime = ezdtTarget.toLocalDateTime();

            int customerID = results.getInt("Customer_ID");
            apptsList.add("Appointment ID: " + apptID + "\nTitle: " + title + "\nType: " + type + "\nDescription: " + desc + "\nStart Date: " + slocalDateTime.format(format) + "\nEnd Date: " + elocalDateTime.format(format) + "\nCustomer ID: " + customerID);
        }
        return apptsList;
    }

    public static List<String> generateReport3(String country) throws SQLException{
        String sqlStatement = "SELECT Customer_ID,Customer_Name,Address,Postal_Code,Phone,Division FROM customers LEFT JOIN first_level_divisions ON first_level_divisions.Division_ID = customers.Division_ID LEFT JOIN countries ON countries.Country_ID = first_level_divisions.Country_ID WHERE Country = ?";
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy h:mma");
        List<String> customerList = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement(sqlStatement);
        statement.setString(1,country);
        ResultSet results = statement.executeQuery();
        while (results.next()) {
            int customerId = results.getInt("Customer_ID");
            String customerName = results.getString("Customer_Name");
            String address = results.getString("Address");
            String postalCode = results.getString("Postal_Code");
            String phone = results.getString("Phone");
            String division = results.getString("Division");
            customerList.add("Customer ID: " + customerId + "\nCustomer Name: " + customerName + "\nAddress: " + address +  "\nState/Province: " + division + "\nPostal Code: " + postalCode + "\nPhone Number: " + phone);
        }
        return customerList;
    }
}