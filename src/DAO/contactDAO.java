package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static DAO.JDBC_Connector.connection;

public abstract class contactDAO {

    public static ObservableList<String> getContacts() throws SQLException {
        String sqlStatement = "SELECT Contact_Name FROM contacts";
        ObservableList<String> contactList = FXCollections.observableArrayList();
        PreparedStatement statement = connection.prepareStatement(sqlStatement);
        ResultSet results = statement.executeQuery();

        while (results.next()) {
            contactList.add(results.getString("Contact_Name"));
        }
        return contactList;
    }

    public static String getApptContacts(int contactID) throws SQLException {
        String sqlStatement = "SELECT Contact_Name FROM contacts WHERE Contact_ID = ?";

        PreparedStatement statement = connection.prepareStatement(sqlStatement);
        statement.setInt(1, contactID);
        ResultSet results = statement.executeQuery();

        String apptContact = null;
        while (results.next()) {
            apptContact = results.getString("Contact_Name");
        }
        return apptContact;
    }

}
