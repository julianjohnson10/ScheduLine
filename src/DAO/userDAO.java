package DAO;


import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import static DAO.JDBC_Connector.connection;

public abstract class userDAO {

    public static boolean getLogin(Integer userId, String password) throws SQLException {
        String sqlStatement = "SELECT * FROM users WHERE User_ID = ? AND Password = ?";
        PreparedStatement statement = connection.prepareStatement(sqlStatement);
        statement.setInt(1, userId);
        statement.setString(2, password);
        ResultSet results = statement.executeQuery();

        try {
            if (results.next()) {
                return true;
            } else if (!results.next()) {
                return false;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static User getUserInfo() throws SQLException {

        String sqlStatement = "SELECT User_ID, User_Name FROM users";
        PreparedStatement statement = connection.prepareStatement(sqlStatement);
        ResultSet results = statement.executeQuery();

        User user = new User();

        while (results.next()) {
            user.setUserId(results.getInt("User_ID"));
            user.setUserName(results.getString("User_Name"));
            User.addUser(user);
        }
        return user;
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
     *
     *
     * @return a boolean value that checks if the current user is "admin".
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
     *
     * @param userId Integer for the userid.
     * @return a boolean value that checks if the current user is "admin".
     * @throws SQLException SQLException
     */
    public static boolean isAdmin(Integer userId) throws SQLException {
        String sqlStatement = "SELECT * FROM users WHERE User_ID = ?";
        PreparedStatement statement = connection.prepareStatement(sqlStatement);
        statement.setInt(1, userId);
        ResultSet results = statement.executeQuery();

        try {
            if (results.next()) {
                String userName = results.getString("User_Name");
                System.out.println(userName);
                if (Objects.equals(userName, "admin")) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}


