package com.example.projecttool.services;

import com.example.projecttool.models.Employee;
import com.example.projecttool.models.project.Subtask;
import com.example.projecttool.repositories.SubtaskRepository;
import com.example.projecttool.repositories.TaskRepository;

import java.sql.SQLException;
import java.util.ArrayList;

public class SubtaskService {
    public static ArrayList<Subtask> getSubtasks(int taskId) {
        return TaskRepository.getRelatedSubtasks(taskId);
    }

    public static void addNewSubtaskToTask(int taskId, String subtaskName, String subtaskDescription, String startTime, String endTime, int hoursToComplete) throws SQLException {
        SubtaskRepository.addNewSubtaskToTask(taskId, subtaskName, subtaskDescription, startTime, endTime, hoursToComplete);
    }

    public static void updateSubtask(int subtaskId, String name, String description, String startDate, String endDate, int hoursToComplete) throws SQLException{
        SubtaskRepository.updateSubtask(subtaskId, name, description, startDate, endDate, hoursToComplete);
    }

    public static void deleteSubtask(int subtaskId) throws SQLException {
        SubtaskRepository.deleteSubtask(subtaskId);
    }

    public static ArrayList<Employee> getAllEmployees(int userId) throws SQLException {
        return SubtaskRepository.getAllEmployees(userId);
    }

    public static void addEmployeeToSubtask(int subtaskId, int employeeId) throws SQLException{
        SubtaskRepository.addEmployeeToSubtask(subtaskId, employeeId);
    }

    public static void removeEmployeeFromSubtask(int subtaskId, int employeeId) throws SQLException{
        SubtaskRepository.removeEmployeeFromSubtask(subtaskId, employeeId);
    }
}
