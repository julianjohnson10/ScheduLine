package DAO;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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


//    public static int getUser(int userId) throws SQLException {
//        String sqlStatement = "SELECT * FROM users WHERE User_ID = ?";
//        PreparedStatement statement = connection.prepareStatement(sqlStatement);
//        statement.setInt(1, userId);
//        ResultSet results = statement.executeQuery();
//
//        try {
//            if(results.next()) {
//                return true;
//            }
//            else if(!results.next()){
//                return false;
//            }
//            else {
//                return false;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }

