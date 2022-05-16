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

    public static ObservableList<String> getStatesProvinces(String country) throws SQLException {
        String sqlStatement =
                "SELECT Division FROM first_level_divisions " +
                "LEFT JOIN countries ON countries.Country_ID = first_level_divisions.Country_ID " +
                "WHERE countries.Country LIKE ? " +
                "ORDER BY Division ASC";
        ObservableList<String> statesProvincesList = FXCollections.observableArrayList();

        PreparedStatement statement = connection.prepareStatement(sqlStatement);
        statement.setString(1, country);
        ResultSet results = statement.executeQuery();

        while (results.next()) {
            statesProvincesList.add(results.getString("Division"));
        }
        return statesProvincesList;
    }

    public static int getCountryID(String country) throws SQLException {
        String sqlStatement = "SELECT Country_ID FROM countries WHERE Country = ?";
        PreparedStatement statement = connection.prepareStatement(sqlStatement);
        statement.setString(1, country);
        ResultSet results = statement.executeQuery();
        if (results.next()) {
            return results.getInt("Country_ID");
        }
        return 0;
    }

    /**
     * SQLSyntaxErrorException: Table 'client_schedule.first_level_division' doesn't exist. Misspelled.
     * @param division
     * @return
     * @throws SQLException
     */
    public static int getDivisionID(String division) throws SQLException {
        String sqlStatement = "SELECT Division_ID FROM first_level_divisions WHERE Division LIKE ?";
        PreparedStatement statement = connection.prepareStatement(sqlStatement);
        statement.setString(1, division);
        ResultSet results = statement.executeQuery();
        if (results.next()) {
            return results.getInt("Division_ID");
        }
        return 0;
    }

//    public static int getDivID() {
//    }
}
