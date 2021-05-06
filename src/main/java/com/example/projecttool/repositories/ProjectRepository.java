package com.example.projecttool.repositories;


import com.example.projecttool.models.Employee;
import com.example.projecttool.models.Skill;
import com.example.projecttool.services.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProjectRepository {

    public static void editProject(int id, String name, String description) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();


        String command = String.format("UPDATE test_project SET project_name = '%s', project_description = '%s' WHERE id = '%d'", name, description, id);
        PreparedStatement statement = connection.prepareStatement(command);
        statement.execute();

    }

    public static void addEmployeeToSubtask(int subtaskId, int employeeId) {

    }

    public static void removeEmployeeFromSubtask(int subtaskId, int employeeId) {

    }

    public static void addSkillToSubtask(int subtaskId, String skillName) {

    }

    public static void removeSkillFromSubtask(int subtaskId, String skillName) {

    }
}
