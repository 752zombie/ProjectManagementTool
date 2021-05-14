package com.example.projecttool.repositories;

import com.example.projecttool.services.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EmployeeRepository {
    public static void changeEmployeeName(int employeeId, String name) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();

        PreparedStatement statement = connection.prepareStatement("UPDATE employees SET emp_name = ? WHERE emp_id = ?");
        statement.setString(1, name);
        statement.setInt(2, employeeId);
        statement.execute();
    }

    public static void deleteEmployee(int employeeId) throws SQLException{
        Connection connection = DatabaseConnection.getConnection();

        PreparedStatement statement = connection.prepareStatement("DELETE FROM employees WHERE emp_id = ?");
        statement.setInt(1, employeeId);
        statement.execute();
    }

    public static void createNewEmployee(String employeeName, int userId) throws SQLException{
        Connection connection = DatabaseConnection.getConnection();

        PreparedStatement statement = connection.prepareStatement("INSERT INTO employees(emp_name, user_id) values (?, ?)");
        statement.setString(1, employeeName);
        statement.setInt(2, userId);
        statement.execute();
    }
}
