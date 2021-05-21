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

    public static void addNewSubtaskToTask(int taskId, String subtaskName, String subtaskDescription,
                                           String startTime, String endTime, int hoursToComplete,
                                           int projectId, int userId) throws SQLException {
        if (ProjectService.canEdit(projectId, userId)) {
            SubtaskRepository.addNewSubtaskToTask(taskId, subtaskName, subtaskDescription, startTime, endTime, hoursToComplete);
        }

    }

    public static void updateSubtask(int subtaskId, String name, String description, String startDate, String endDate,
                                     int hoursToComplete, int projectId, int userId) throws SQLException{

        if (ProjectService.canEdit(projectId, userId)) {
            SubtaskRepository.updateSubtask(subtaskId, name, description, startDate, endDate, hoursToComplete);
        }

    }

    public static void deleteSubtask(int subtaskId, int projectId, int userId) throws SQLException {
        if (ProjectService.canEdit(projectId, userId)) {
            SubtaskRepository.deleteSubtask(subtaskId);
        }

    }

    public static ArrayList<Employee> getAllEmployees(int userId, int projectId) throws SQLException {
        ArrayList<Employee> employees = new ArrayList<>();
        if (ProjectService.canEdit(projectId, userId)) {
            employees = SubtaskRepository.getAllEmployees(userId);
        }

        return employees;
    }

    public static void addEmployeeToSubtask(int subtaskId, int employeeId, int projectId, int userId) throws SQLException{
        if (ProjectService.canEdit(projectId, userId)) {
            SubtaskRepository.addEmployeeToSubtask(subtaskId, employeeId);
        }

    }

    public static void removeEmployeeFromSubtask(int subtaskId, int employeeId, int projectId, int userId) throws SQLException{
        if (ProjectService.canEdit(projectId, userId)) {
            SubtaskRepository.removeEmployeeFromSubtask(subtaskId, employeeId);
        }

    }

    public static void addSkillToSubtask(int subtaskId, int skillId, int projectId, int userId) throws SQLException{
        if (ProjectService.canEdit(projectId, userId)) {
            SubtaskRepository.addSkillToSubtask(subtaskId, skillId);
        }
    }

    public static void removeSkillFromSubtask(int subtaskId, int skillId, int projectId, int userId) throws SQLException{
        if (ProjectService.canEdit(projectId, userId)) {
            SubtaskRepository.removeSkillFromSubtask(subtaskId, skillId);
        }
    }
}
