package com.example.projecttool.repositories;

import com.example.projecttool.models.Employee;
import com.example.projecttool.models.Skill;
import com.example.projecttool.services.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SubtaskRepository {

    public static void addEmployeeToSubtask(int subtaskId, int employeeId) {
        Connection connection = DatabaseConnection.getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO emp_subtask(emp_id, subtask_id) values (?, ?)");
            statement.setInt(1, employeeId);
            statement.setInt(2, subtaskId);
        }

        catch (SQLException e) {
            System.out.println("Error adding employee to subtask");
        }
    }

    public static void removeEmployeeFromSubtask(int subtaskId, int employeeId) {
        Connection connection = DatabaseConnection.getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM emp_subtask WHERE emp_id = ? AND subtask_id = ?");
            statement.setInt(1, employeeId);
            statement.setInt(2, subtaskId);
        }

        catch (SQLException e) {
            System.out.println("Error removing employee from subtask");
        }
    }

    public static void addSkillToSubtask(int subtaskId, int skillId) {
        Connection connection = DatabaseConnection.getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO subtask_skill(subtask_id, skill_id) values (?, ?)");
            statement.setInt(1, subtaskId);
            statement.setInt(2, skillId);
        }

        catch (SQLException e) {
            System.out.println("Error adding skill to subtask");
        }
    }

    public static void removeSkillFromSubtask(int subtaskId, int skillId) {
        Connection connection = DatabaseConnection.getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM subtask_skill WHERE subtask_id = ? AND skill_id = ?");
            statement.setInt(1, subtaskId);
            statement.setInt(2, skillId);
        }

        catch (SQLException e) {
            System.out.println("Error removing skill from subtask");
        }
    }

    public static void addSkillToEmployee(int employeeId, int skillId) {
        Connection connection = DatabaseConnection.getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO emp_skill(emp_id, skill_id) values (?, ?)");
            statement.setInt(1,employeeId);
            statement.setInt(2, skillId);
        }

        catch (SQLException e) {
            System.out.println("Error adding skill to employee");
        }
    }

    public static void removeSkillFromEmployee(int employeeId, int skillId) {
        Connection connection = DatabaseConnection.getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM emp_skill WHERE emp_id = ? AND skill_id = ?");
            statement.setInt(1, employeeId);
            statement.setInt(2, skillId);
        }

        catch (SQLException e) {
            System.out.println("Error removing skill from employee");
        }
    }

    public static ArrayList<Employee> getAssignedEmployees(int subtaskId) {
        Connection connection = DatabaseConnection.getConnection();
        ArrayList<Employee> employees = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM emp_subtask " +
                    "INNER JOIN employees " +
                    "ON emp_subtask.emp_id = employees.emp_id " +
                    "WHERE emp_subtask.subtask_id = ?");
            statement.setInt(1, subtaskId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int employeeId = resultSet.getInt("emp_id");
                String employeeName = resultSet.getString("emp_name");
                ArrayList<Skill> skills = getEmployeeSkills(employeeId);

                employees.add(new Employee(employeeId, employeeName, skills));

            }

        }

        catch (SQLException e) {
            System.out.println("Error getting assigned employees");
        }

        return employees;
    }

    private static ArrayList<Skill> getEmployeeSkills(int employeeID) {
        Connection connection = DatabaseConnection.getConnection();
        ArrayList<Skill> skills = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM emp_skills " +
                    "INNER JOIN skills " +
                    "ON emp_skill.skill_id = skills.skill_id " +
                    "WHERE emp_id = ?");
            statement.setInt(1, employeeID);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {
                String skillName = resultSet.getString("skill_name");
                skills.add(new Skill(skillName));
            }
        }

        catch (SQLException e) {
            e.printStackTrace();
        }

        return skills;
    }

    public static ArrayList<Skill> getRequiredSkills(int subtaskId) {
        Connection connection = DatabaseConnection.getConnection();
        ArrayList<Skill> skills = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM subtask_skill " +
                    "INNER JOIN skills " +
                    "ON subtask_skill.skill_id = skills.skill_id " +
                    "WHERE subtask_id = ?");
            statement.setInt(1, subtaskId);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {
                String skillName = resultSet.getString("skill_name");
                skills.add(new Skill(skillName));
            }
        }

        catch (SQLException e) {
            e.printStackTrace();
        }

        return skills;
    }


}
