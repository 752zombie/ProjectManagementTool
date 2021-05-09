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


    public static ArrayList<Task> getTasks(int project_id) throws SQLException {

        Connection connection = DatabaseConnection.getConnection();
        ArrayList<Task> taskList = new ArrayList<>();

        String command = String.format("SELECT * FROM tasks WHERE project_id = '%d'", project_id);
        PreparedStatement statement = connection.prepareStatement(command);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String project_name = resultSet.getString("task_name");
            String project_description = resultSet.getString("task_description");
            String priority = resultSet.getString("priority");
            String start_time = resultSet.getString("start_time");
            String end_time = resultSet.getString("end_time");


            taskList.add(new Task(id, project_name, project_description, start_time, end_time, priority));
        }

        return taskList;
    }


    public static void editTask(int taskId, String name, String description, String priority, String start_time, String end_time) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();


        String command = String.format("UPDATE tasks SET task_name = '%s', task_description = '%s', priority = '%s', start_time = '%s', end_time = '%s'  WHERE id = '%d'", name, description, priority, start_time, end_time, taskId);
        PreparedStatement statement = connection.prepareStatement(command);
        statement.execute();

    }


    public static void addRowToTask(int project_id, String task_name, String task_description, String priority, String start_time, String end_time) throws SQLException {


        Connection connection = DatabaseConnection.getConnection();

        String command = String.format("INSERT INTO tasks (project_id, task_name, task_description, start_time, end_time, priority) values ('%d', '%s', '%s', '%s', '%s', '%s')", project_id, task_name, task_description, start_time, end_time, priority);
        PreparedStatement statement = connection.prepareStatement(command);
        statement.execute();

    }


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

        } catch (SQLException e) {
            System.out.println("Error could not find any subtasks for that task");
        }

        return subtasks;
    }


    public static void deleteTask(int taskId) throws SQLException {

        Connection connection = DatabaseConnection.getConnection();

        PreparedStatement statement = connection.prepareStatement("DELETE FROM tasks WHERE id = ?");
        statement.setInt(1, taskId);
        statement.execute();
    }

}



