package com.example.projecttool.repositories;

import com.example.projecttool.models.project.ProjectTest;
import com.example.projecttool.services.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProjectRepository {

    public static void editProject(int id, String name, String description) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();


        String command = String.format("UPDATE test_project SET project_name = '%s', project_description = '%s' WHERE id = '%d'", name, description, id);
        PreparedStatement statement = connection.prepareStatement(command);
        statement.execute();

    }


    public static void addRowToProject(int id, String name, String description, String start_time, String end_time) {


            Connection connection = DatabaseConnection.getConnection();

            try {

                String command = String.format("INSERT INTO test_project (project_name, project_description, start_time, end_time) values ('%s', '%s', '%s', '%s')", name, description, start_time, end_time);
                PreparedStatement statement = connection.prepareStatement(command);
                statement.execute();

            }

            catch (SQLException e) {
                System.out.println("Error adding row to database");
            }
        }

    public static ArrayList<ProjectTest> getProject() {

        Connection connection = DatabaseConnection.getConnection();
        ArrayList<ProjectTest> projectList = new ArrayList<>();

        try {

            String command = String.format("SELECT * FROM test_project");
            PreparedStatement statement = connection.prepareStatement(command);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String project_name = resultSet.getString("project_name");
                String project_description =  resultSet.getString("project_description");
                String start_time = resultSet.getString("start_time");
                String end_time = resultSet.getString("end_time");
                projectList.add(new ProjectTest(id, project_name, project_description, start_time, end_time));
            }
        }

        catch (SQLException e) {
            System.out.println("Error getting project");
        }

        return projectList;
    }

    }


