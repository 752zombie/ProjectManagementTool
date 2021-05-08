package com.example.projecttool.repositories;

import com.example.projecttool.models.project.Project;
import com.example.projecttool.models.project.Subtask;
import com.example.projecttool.models.project.Task;
import com.example.projecttool.services.DatabaseConnection;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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


    public static int createTask(int project_id, int userId) {

        Connection connection = DatabaseConnection.getConnection();
        int taskId = 0;

        try {

            // THE REMAINING 'TASKS' VALUES ARE CREATED AS NULL VALUES
            String command = String.format("INSERT INTO tasks (project_id, owner_id) values ('%d', '%d')", project_id, userId);
            PreparedStatement statement = connection.prepareStatement(command);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                taskId = resultSet.getInt("id");
            }

        } catch (SQLException e) {
            System.out.println("Error creating new task to DB");

        }
        return taskId;
    }


    public static void editTask(int taskId, String name, String description, String start_time, String end_time) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();


        String command = String.format("UPDATE tasks SET task_name = '%s', task_description = '%s', start_time = '%s', end_time = '%s' WHERE id = '%d'", name, description, start_time, end_time, taskId);
        PreparedStatement statement = connection.prepareStatement(command);
        statement.execute();

    }


    public static void addRowToTask(int project_id, String task_name, String task_description, String start_time, String end_time) {


        Connection connection = DatabaseConnection.getConnection();

        try {

            String command = String.format("INSERT INTO tasks (project_id, task_name, task_description, start_time, end_time) values ('%d', '%s', '%s', '%s', '%s')", project_id, task_name, task_description, start_time, end_time);
            PreparedStatement statement = connection.prepareStatement(command);
            statement.execute();

        }

        catch (SQLException e) {
            System.out.println("Error adding row to database");
        }
    }


    /*
    public static HashMap<Integer, Subtask> getRelatedSubtasks(int taskId) {
        Connection connection = DatabaseConnection.getConnection();
        HashMap<Integer, Subtask> subtasks = new HashMap<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM subtasks WHERE task = ?");
            statement.setInt(1, taskId);
            ResultSet resultSet = statement.executeQuery();


            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("subtask_name");
                String description = resultSet.getString("subtask_description");
                Date startTime = resultSet.getDate("start_time");
                Date endTime = resultSet.getDate("end_time");

                Subtask subtask = new Subtask(id, name, description, startTime, endTime,
                        SubtaskRepository.getAssignedEmployees(id), SubtaskRepository.getRequiredSkills(id));
                subtasks.put(id, subtask);
            }

        }

        catch (SQLException e) {
            System.out.println("Error could not find any subtasks for that task");
        }

        return subtasks;
    }

     */


}
