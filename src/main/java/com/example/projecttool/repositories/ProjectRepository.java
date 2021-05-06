package com.example.projecttool.repositories;

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


    public static void createProject(int id, String project_name){
        Connection connection = DatabaseConnection.getConnection();

        try {
            createTask(id);
            String command = String.format("INSERT INTO project (name, owner_id, start_time, end_time) values ('%s', '', '', '', '')", project_name, id);
            PreparedStatement statement = connection.prepareStatement(command);
            statement.execute();

        }

        catch (SQLException e) {
            System.out.println("Error creating new project to DB");
        }


    }

    public static void createTask(int id){



        Connection connection = DatabaseConnection.getConnection();

        try {

            String command = String.format("INSERT INTO tasks (user_id, project_name, project_description, start_time, end_time) values ('%d', '', '', '', '')", id);
            PreparedStatement statement = connection.prepareStatement(command);
            statement.execute();

        }

        catch (SQLException e) {
            System.out.println("Error creating new task to DB");
        }

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

            String command = String.format("SELECT * FROM project WHERE user_id = '%d'", userId );
            PreparedStatement statement = connection.prepareStatement(command);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String project_name = resultSet.getString("project_name");
                String project_description =  resultSet.getString("project_description");
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




    public static ArrayList<Task> getTasks(int userId) {

        Connection connection = DatabaseConnection.getConnection();
        ArrayList<Task> taskList = new ArrayList<>();

        try {

            String command = String.format("SELECT * FROM project WHERE user_id = '%d'", userId );
            PreparedStatement statement = connection.prepareStatement(command);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String project_name = resultSet.getString("project_name");
                String project_description =  resultSet.getString("project_description");
                String start_time = resultSet.getString("start_time");
                String end_time = resultSet.getString("end_time");

                taskList.add(new Task(id, project_name, project_description, start_time, end_time));
            }
        }

        catch (SQLException e) {
            System.out.println("Error getting project");
        }

        return taskList;
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


