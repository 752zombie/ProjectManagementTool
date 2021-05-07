package com.example.projecttool.repositories;

import com.example.projecttool.models.project.Task;
import com.example.projecttool.services.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TaskRepository {


    public static ArrayList<Task> getTasks(int project_id) {

        Connection connection = DatabaseConnection.getConnection();
        ArrayList<Task> taskList = new ArrayList<>();

        try {

            String command = String.format("SELECT * FROM tasks WHERE project_id = '%d'", project_id);
            PreparedStatement statement = connection.prepareStatement(command);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String project_name = resultSet.getString("task_name");
                String project_description =  resultSet.getString("task_description");
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


    public static void createTask(int project_id){



        Connection connection = DatabaseConnection.getConnection();

        try {

            String command = String.format("INSERT INTO tasks (project_id) values ('%d')", project_id);
            PreparedStatement statement = connection.prepareStatement(command);
            statement.execute();

        }

        catch (SQLException e) {
            System.out.println("Error creating new task to DB");
        }

    }

}
