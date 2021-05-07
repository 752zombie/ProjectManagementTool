package com.example.projecttool.repositories;

import com.example.projecttool.models.User;
import com.example.projecttool.models.project.Project;
import com.example.projecttool.models.project.ProjectTest;
import com.example.projecttool.models.project.Task;
import com.example.projecttool.services.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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


    public static void editProject(int id, String name, String description, String start_time, String end_time) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();


        String command = String.format("UPDATE test_project SET project_name = '%s', project_description = '%s', start_time = '%s', end_time = '%s' WHERE id = '%d'", name, description, start_time, end_time, id);
        PreparedStatement statement = connection.prepareStatement(command);
        statement.execute();

    }


    public static void addRowToProject(int id, String name, String description, String start_time, String end_time) {


            Connection connection = DatabaseConnection.getConnection();

            try {

                String command = String.format("INSERT INTO test_project (user_id, project_name, project_description, start_time, end_time) values ('%d', '%s', '%s', '%s', '%s')", id, name, description, start_time, end_time);
                PreparedStatement statement = connection.prepareStatement(command);
                statement.execute();

            }

            catch (SQLException e) {
                System.out.println("Error adding row to database");
            }
        }


    public static ArrayList<Project> getProjects(int userId){

        Connection connection = DatabaseConnection.getConnection();
        ArrayList<Project> projectList = new ArrayList<>();

        try {

            String command = String.format("SELECT * FROM project WHERE owner_id = '%d'", userId );
            PreparedStatement statement = connection.prepareStatement(command);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                String project_name = resultSet.getString("name");
                String start_time = resultSet.getString("start_time");
                String end_time = resultSet.getString("end_time");

                projectList.add(new Project(project_name, start_time, end_time));
            }
        }

        catch (SQLException e) {
            System.out.println("Error getting project");
        }

        return projectList;
    }

}


