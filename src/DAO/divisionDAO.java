package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import static DAO.JDBC_Connector.connection;

/**
 * Data Access Object for division data in the database.
 */
public abstract class divisionDAO {

    /**
     * Retrieves a list of countries from the country table.
     * @return Array list of country names.
     * @throws SQLException handler for SQL errors.
     */
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

    /**
     * Retrieves a list of states and provinces from the database.
     * @param country The country acts as a filter. Only retrieves states or provinces with the specified country.
     * @return Array list of states and provinces.
     * @throws SQLException sql exception handler.
     */
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

    /**
     * SQLSyntaxErrorException: Table 'client_schedule.first_level_division' doesn't exist. Fix: Misspelled.
     * @param division the division name.
     * @return The division ID.
     * @throws SQLException SQL exception handler.
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
}
