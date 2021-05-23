package com.example.projecttool.repositories;

import com.example.projecttool.models.Skill;
import com.example.projecttool.services.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

        PreparedStatement statement = connection.prepareStatement("INSERT INTO employees(emp_name, user_id) VALUES (?, ?)");
        statement.setString(1, employeeName);
        statement.setInt(2, userId);
        statement.execute();
    }

    public static void createNewSkill(int userId, String skillName) throws SQLException{
        Connection connection = DatabaseConnection.getConnection();

        PreparedStatement statement = connection.prepareStatement("INSERT INTO skills(skill_name, user_id) VALUES (?, ?)");
        statement.setString(1, skillName);
        statement.setInt(2, userId);
        statement.execute();
    }

    public static void deleteSkill(int skillId) throws SQLException{
        Connection connection = DatabaseConnection.getConnection();

        PreparedStatement statement = connection.prepareStatement("DELETE FROM skills WHERE skill_id = ?");
        statement.setInt(1, skillId);
        statement.execute();
    }

    public static ArrayList<Skill> getAllSkills(int userId) throws SQLException{
        Connection connection = DatabaseConnection.getConnection();
        ArrayList<Skill> skills = new ArrayList<>();

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM skills WHERE user_id = ? ORDER BY skill_name ASC");
        statement.setInt(1, userId);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int skillId = resultSet.getInt("skill_id");
            String skillName = resultSet.getString("skill_name");
            skills.add(new Skill(skillName, skillId));
        }

        return skills;

    }

    public static void addSkillToEmployee(int employeeId, int skillId) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();

        PreparedStatement statement = connection.prepareStatement("INSERT INTO emp_skill(emp_id, skill_id) VALUES (?, ?)");
        statement.setInt(1,employeeId);
        statement.setInt(2, skillId);
        statement.execute();

    }

    public static void removeSkillFromEmployee(int employeeId, int skillId) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();

        PreparedStatement statement = connection.prepareStatement("DELETE FROM emp_skill WHERE emp_id = ? AND skill_id = ?");
        statement.setInt(1, employeeId);
        statement.setInt(2, skillId);
        statement.execute();

    }



}
