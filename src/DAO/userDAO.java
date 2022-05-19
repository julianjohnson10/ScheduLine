package DAO;

import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import static DAO.JDBC_Connector.connection;

/**
 * DAO class for retrieving user data from the database.
 */
public abstract class userDAO {

    /**
     * Get the user and password, and see if they match a record in the DB.
     * @param userId User ID
     * @param password Password
     * @return Boolean true if the user and pass are the same. False if not.
     * @throws SQLException SQL Exception handler.
     */
    public static boolean getLogin(Integer userId, String password) throws SQLException {
        String sqlStatement = "SELECT * FROM users WHERE User_ID = ? AND Password = ?";
        PreparedStatement statement = connection.prepareStatement(sqlStatement);
        statement.setInt(1, userId);
        statement.setString(2, password);
        ResultSet results = statement.executeQuery();

        return results.next();
    }

    public static ObservableList<Integer> getUserIDs() throws SQLException {
        String sqlStatement = "SELECT * FROM users";
        ObservableList<Integer> userIDs = FXCollections.observableArrayList();
        PreparedStatement statement = connection.prepareStatement(sqlStatement);
        ResultSet results = statement.executeQuery();

        while (results.next()) {
            userIDs.add(results.getInt("User_ID"));
        }
        return userIDs;
    }

    /**
     * Method for retrieving the users Name.
     * @return the username for the overloaded userID.
     * @throws SQLException SQLException
     */
    public static String getUserNamefromID(int userID) throws SQLException {
        String sqlStatement = "SELECT User_Name FROM users WHERE User_ID = ?";
        PreparedStatement statement = connection.prepareStatement(sqlStatement);
        statement.setInt(1, userID);
        ResultSet results = statement.executeQuery();

        try {
            if(results.next()){
                return results.getString("User_Name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sqlStatement;
    }

    /**
     * Checks if the user with specified userID exists in the database.
     * @param userId the users id.
     * @return boolean value.
     * @throws SQLException exception handler.
     */
    public static boolean checkUser(Integer userId) throws SQLException {
        String sqlStatement = "SELECT * FROM users WHERE User_ID = ?";
        PreparedStatement statement = connection.prepareStatement(sqlStatement);
        statement.setInt(1, userId);
        ResultSet resultSet = statement.executeQuery();

        return resultSet.next();
    }
}


