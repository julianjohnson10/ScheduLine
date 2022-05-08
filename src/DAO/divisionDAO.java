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

        while(results.next()) {
            divisionList.add(results.getString("Country"));
        }
        return divisionList;
    }
}
