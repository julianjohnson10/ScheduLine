package DAO;


import Controller.LoginController;
import Controller.MainFormController;
import Model.Appointment;
import Model.Customer;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static DAO.JDBC_Connector.connection;

public abstract class divisionDAO {

    public static void getCountries() throws SQLException {
        String sqlStatement = "SELECT Country FROM countries";

        PreparedStatement statement = connection.prepareStatement(sqlStatement);
        ResultSet results = statement.executeQuery();

        System.out.println(results.getString("Country"));
    }
}
