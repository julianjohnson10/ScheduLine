package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static DAO.JDBC_Connector.connection;

public abstract class contactDAO {

    public static ObservableList<String> getContactNames() throws SQLException {
        String sqlStatement = "SELECT Contact_Name FROM contacts";
        ObservableList<String> contactList = FXCollections.observableArrayList();
        PreparedStatement statement = connection.prepareStatement(sqlStatement);
        ResultSet results = statement.executeQuery();

        while (results.next()) {
            contactList.add(results.getString("Contact_Name"));
        }
        return contactList;
    }

    /**
     * Returns the contact's ID based on the name of the contact.
     * @param contact_name the contact's Name.
     * @return the contact's ID
     * @throws SQLException
     */
    public static int getContactID(String contact_name) throws SQLException {
        String sqlStatement = "SELECT * FROM contacts WHERE Contact_Name = ?";

        PreparedStatement statement = connection.prepareStatement(sqlStatement);
        statement.setString(1, contact_name);
        ResultSet results = statement.executeQuery();
        int contactID = 0;
        while (results.next()) {
            contactID = results.getInt("Contact_ID");
        }
        return contactID;

    }
}
