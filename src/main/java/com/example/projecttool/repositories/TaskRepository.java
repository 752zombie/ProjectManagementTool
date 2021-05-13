package com.example.projecttool.repositories;

import com.example.projecttool.models.project.Subtask;
import com.example.projecttool.models.project.Task;
import com.example.projecttool.services.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class TaskRepository {


    public static ArrayList<Task> getTasks(int projectId) throws SQLException {

        Connection connection = DatabaseConnection.getConnection();
        ArrayList<Task> taskList = new ArrayList<>();

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM tasks WHERE project_id = ?");
        statement.setInt(1, projectId);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String project_name = resultSet.getString("task_name");
            String project_description = resultSet.getString("task_description");
            String priority = resultSet.getString("priority");
            String start_time = resultSet.getString("start_time");
            String end_time = resultSet.getString("end_time");
            int estimatedHours = resultSet.getInt("estimated_hours");
            int estimatedHoursPrDay = resultSet.getInt("estimated_hours_day");
            String end_time_calculated = resultSet.getString("end_time_calculated");

           taskList.add(new Task(id, project_name, project_description, start_time, end_time_calculated, end_time, priority, estimatedHours, estimatedHoursPrDay));
        }

        return taskList;
    }


    public static void editTask(int taskId, String taskName, String description, String priority, String start_time, String end_time_calculated, String end_time, int estimatedHoursTotal, int estimatedHoursPrDay) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();

        PreparedStatement statement = connection.prepareStatement("UPDATE tasks SET task_name = ?, " +
                "task_description = ?, start_time = ?, end_time = ?, priority = ?, " +
                "estimated_hours = ?, estimated_hours_day = ?, end_time_calculated = ? WHERE id = ?");

        statement.setString(1, taskName);
        statement.setString(2, description);
        statement.setDate(3, java.sql.Date.valueOf(start_time));
        statement.setDate(4, java.sql.Date.valueOf(end_time));
        statement.setString(5, priority);
        statement.setInt(6, estimatedHoursTotal);
        statement.setInt(7, estimatedHoursPrDay);
        statement.setDate(8, java.sql.Date.valueOf(end_time_calculated));
        statement.setInt(9, taskId);

        statement.execute();

    }


    public static void addRowToTask(int project_id, String task_name, String task_description, String priority, String start_time,
                                    String end_time_calculated, String end_time, int estimatedHoursTotal, int estimatedHoursPrDay) throws SQLException {


        Connection connection = DatabaseConnection.getConnection();

       // String command = String.format("INSERT INTO tasks (project_id, task_name, task_description, start_time, end_time, priority, estimated_hours, estimated_hours_day) values ('%d', '%s', '%s', '%s', '%s', '%s', '%d', '%d')", project_id, task_name, task_description, start_time, end_time, priority, estimatedHoursTotal, estimatedHoursPrDay);
        PreparedStatement statement = connection.prepareStatement("INSERT INTO tasks (project_id, " +
                "task_name, task_description, start_time, end_time, priority, estimated_hours, " +
                "estimated_hours_day, end_time_calculated) values (?, ?, ?, ?, ?, ?, ?, ?, ?)");

        statement.setInt(1, project_id);
        statement.setString(2, task_name);
        statement.setString(3, task_description);
        statement.setDate(4, java.sql.Date.valueOf(start_time));
        statement.setDate(5, java.sql.Date.valueOf(end_time));
        statement.setString(6, priority);
        statement.setInt(7, estimatedHoursTotal);
        statement.setInt(8, estimatedHoursPrDay);
        statement.setDate(9, java.sql.Date.valueOf(end_time_calculated));

        statement.execute();

    }


    public static ArrayList<Subtask> getRelatedSubtasks(int taskId) {
        Connection connection = DatabaseConnection.getConnection();
        ArrayList<Subtask> subtasks = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM subtasks WHERE task = ? ORDER BY id");
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
                subtasks.add(subtask);
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



