package com.example.projecttool.services;

import com.example.projecttool.models.project.Subtask;
import com.example.projecttool.repositories.SubtaskRepository;
import com.example.projecttool.repositories.TaskRepository;

import java.sql.SQLException;
import java.util.ArrayList;

public class SubtaskService {
    public static ArrayList<Subtask> getSubtasks(int taskId) {
        return TaskRepository.getRelatedSubtasks(taskId);
    }

    public static void addNewSubtaskToTask(int taskId, String subtaskName, String subtaskDescription, String startTime, String endTime) throws SQLException {
        SubtaskRepository.addNewSubtaskToTask(taskId, subtaskName, subtaskDescription, startTime, endTime);
    }

    public static void updateSubtask(int subtaskId, String name, String description, String startDate, String endDate) throws SQLException{
        SubtaskRepository.updateSubtask(subtaskId, name, description, startDate, endDate);
    }

    public static void deleteSubtask(int subtaskId) throws SQLException {
        SubtaskRepository.deleteSubtask(subtaskId);
    }
}
