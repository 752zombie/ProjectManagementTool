package com.example.projecttool.repositories;

import com.example.projecttool.models.project.Project;
import com.example.projecttool.services.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class ProjectRepository {


    public static int createProject(int userId, String project_name, String project_start, String project_end) {
        Connection connection = DatabaseConnection.getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO project (name, owner_id, start_time, end_time) values (?, ?, ?, ?)");
            statement.setString(1, project_name);
            statement.setInt(2, userId);
            statement.setDate(3, java.sql.Date.valueOf(project_start));
            statement.setDate(4, java.sql.Date.valueOf(project_end));
            statement.execute();

        } catch (SQLException e) {
            System.out.println("Error creating new project to DB");
        }
        return getNewProjectId(userId);
    }

    private static int getNewProjectId(int userId) {
        Connection connection = DatabaseConnection.getConnection();
        int project_id = 0;

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT MAX(project_id) FROM project WHERE owner_id = ?");
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                project_id = resultSet.getInt(1);

            }

        } catch (SQLException e) {

            System.out.println("Something went wrong getting project_id");
        }

        return project_id;
    }

    //gets all projects for a specific user but without attaching any tasks or subtasks.
    public static ArrayList<Project> getProjects(int userId) {

        Connection connection = DatabaseConnection.getConnection();
        ArrayList<Project> projectList = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM project WHERE owner_id = ?");
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int project_id = resultSet.getInt("project_id");
                String project_name = resultSet.getString("name");
                String start_time = resultSet.getString("start_time");
                String end_time = resultSet.getString("end_time");

                projectList.add(new Project(project_id, project_name, start_time, end_time));
            }
        } catch (SQLException e) {
            System.out.println("Error getting project");
        }

        return projectList;
    }

    public static Project getProject(int projectId) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM project WHERE project_id = ?");
        statement.setInt(1, projectId);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            int id = resultSet.getInt("project_id");
            String name = resultSet.getString("name");
            String start_time = resultSet.getString("start_time");
            String end_time = resultSet.getString("end_time");

            return new Project(id, name, start_time, end_time);
        } else {
            throw new NoSuchElementException();
        }

    }

}



