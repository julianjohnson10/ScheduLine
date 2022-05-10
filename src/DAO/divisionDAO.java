package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import static DAO.JDBC_Connector.connection;

public abstract class divisionDAO {


    public static ObservableList<String> getCountries() throws SQLException {
        String sqlStatement = "SELECT Country FROM countries";
        ObservableList<String> divisionList = FXCollections.observableArrayList();

        PreparedStatement statement = connection.prepareStatement(sqlStatement);
        ResultSet results = statement.executeQuery();

        while (results.next()) {
            divisionList.add(results.getString("Country"));
        }
        return divisionList;
    }

    public static ObservableList<String> getStatesProvinces(int countryID) throws SQLException {
        String sqlStatement = "SELECT Division FROM first_level_divisions WHERE Country_ID = ?";
        ObservableList<String> statesProvincesList = FXCollections.observableArrayList();

        PreparedStatement statement = connection.prepareStatement(sqlStatement);
        statement.setInt(1, countryID);
        ResultSet results = statement.executeQuery();

        while (results.next()) {
            statesProvincesList.add(results.getString("Country"));
        }
        return statesProvincesList;
    }

    public static int getCountryID(String country) throws SQLException {
        String sqlStatement = "SELECT Country_ID FROM countries WHERE Country = ?";
        PreparedStatement statement = connection.prepareStatement(sqlStatement);
        statement.setString(1, country);
        ResultSet results = statement.getResultSet();
        if (results.next()) {
            return results.getInt("Country_ID");
        }
        return 0;
    }
}
