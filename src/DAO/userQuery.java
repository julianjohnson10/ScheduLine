package DAO;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
}
