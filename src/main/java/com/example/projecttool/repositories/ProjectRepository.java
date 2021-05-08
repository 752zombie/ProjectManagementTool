package com.example.projecttool.repositories;

import com.example.projecttool.models.project.Project;
import com.example.projecttool.services.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class ProjectRepository {


    public static int createProject(int id, String project_name, String project_start, String project_end){
        Connection connection = DatabaseConnection.getConnection();

        try {

            String command = String.format("INSERT INTO project (name, owner_id, start_time, end_time) values ('%s', '%d', '%s', '%s')", project_name, id, project_start, project_end);
            PreparedStatement statement = connection.prepareStatement(command);
            statement.execute();

        }

        catch (SQLException e) {
            System.out.println("Error creating new project to DB");
        }
        return getNewProjectId();
    }

    private static int getNewProjectId() {
        Connection connection = DatabaseConnection.getConnection();
        int project_id = 0;

        try {
            String command = String.format("SELECT MAX(project_id) FROM project");
            PreparedStatement statement = connection.prepareStatement(command);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                project_id = resultSet.getInt(1);

            }

        } catch (SQLException e) {

            System.out.println("Something went wrong getting project_id");
        }

        return project_id;
    }

    public static ArrayList<Project> getProjects(int userId){

        Connection connection = DatabaseConnection.getConnection();
        ArrayList<Project> projectList = new ArrayList<>();

        try {

            String command = String.format("SELECT * FROM project WHERE owner_id = '%d'", userId );
            PreparedStatement statement = connection.prepareStatement(command);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int project_id = resultSet.getInt("project_id");
                String project_name = resultSet.getString("name");
                String start_time = resultSet.getString("start_time");
                String end_time = resultSet.getString("end_time");

                projectList.add(new Project(project_id, project_name, start_time, end_time));
            }
        }

        catch (SQLException e) {
            System.out.println("Error getting project");
        }

        return projectList;
    }

    public static int getProjectId(String project_name) {


        Connection connection = DatabaseConnection.getConnection();
        int project_id = 0;


        try {
            String command = String.format("SELECT project_id FROM project WHERE name = '%s'", project_name);
            PreparedStatement statement = connection.prepareStatement(command);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                project_id = resultSet.getInt(1);

            }

        } catch (SQLException e) {

            System.out.println("Something went wrong getting project_id");
        }

        return project_id;
    }

    public static Project getCurrentProjectObject(String project_name) {

        Connection connection = DatabaseConnection.getConnection();
        Project project = null;

        try {
            // currently only getting the oldest project with that name
            String command = String.format("SELECT * FROM project WHERE name = '%s' LIMIT 1", project_name);
            PreparedStatement statement = connection.prepareStatement(command);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("project_id");
                String name = resultSet.getString("name");
                String start_time = resultSet.getString("start_time");
                String end_time = resultSet.getString("end_time");

                project = new Project(id, name, start_time, end_time);
            }
        } catch (SQLException e) {
            System.out.println("Error getting project");
        }

        return project;


    }

    public static Project getProject(int projectId) {
        Connection connection = DatabaseConnection.getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM project WHERE project_id = ?");
            statement.setInt(1, projectId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("project_id");
                String name = resultSet.getString("name");
                String start_time = resultSet.getString("start_time");
                String end_time = resultSet.getString("end_time");

                return new Project(id, name, start_time, end_time);
            }

            else {
                throw new NoSuchElementException();
            }
        }

        catch (SQLException e) {
            throw new NoSuchElementException();
        }
    }

}


