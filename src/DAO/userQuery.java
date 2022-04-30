package DAO;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static DAO.JDBC_Connector.connection;

public abstract class userQuery {

    public static void select() throws SQLException {
        String sqlStatement = "SELECT * FROM users";
        PreparedStatement statement = connection.prepareStatement(sqlStatement);
        ResultSet results = statement.executeQuery();

        while (results.next()) {
            int userId = results.getInt("User_ID");
            String userName = results.getString("User_Name");
            System.out.print(userId + " | ");
            System.out.print(userName + " ");
        }
    }

    public static boolean getLogin(String username, String password) throws SQLException {
        String sqlStatement = "SELECT * FROM users WHERE User_Name = ? AND Password = ?";
        PreparedStatement statement = connection.prepareStatement(sqlStatement);
        statement.setString(1, username);
        statement.setString(2,password);
        ResultSet results = statement.executeQuery();

        if(results.next()) {
            int userId = results.getInt("User_ID");
            String userName = results.getString("User_Name");
            String pwd = results.getString("Password");
            System.out.println(userName + " " + pwd);
            return true;
        }
        else if(!results.next()){
            return false;
        }
        else {
            return false;
        }
    }

}
