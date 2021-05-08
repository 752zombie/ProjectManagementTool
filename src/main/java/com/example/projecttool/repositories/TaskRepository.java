package com.example.projecttool.repositories;

import com.example.projecttool.models.project.Subtask;
import com.example.projecttool.models.project.Task;
import com.example.projecttool.services.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class TaskRepository {


    public static ArrayList<Task> getTasks(int project_id) {

        Connection connection = DatabaseConnection.getConnection();
        ArrayList<Task> taskList = new ArrayList<>();

        try {

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM tasks WHERE project_id = ?");
            statement.setInt(1, project_id);
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


    public static void createTask(int project_id, int userId){

        Connection connection = DatabaseConnection.getConnection();

        try {

            // THE REMAINING 'TASKS' VALUES ARE CREATED AS NULL VALUES
            PreparedStatement statement = connection.prepareStatement("INSERT INTO tasks (project_id, owner_id) values (?, ?)");
            statement.setInt(1, project_id);
            statement.setInt(2, userId);
            statement.execute();

        }

        catch (SQLException e) {
            System.out.println("Error creating new task to DB");
        }

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

        }

        catch (SQLException e) {
            System.out.println("Error could not find any subtasks for that task");
        }

        return subtasks;
    }

}
