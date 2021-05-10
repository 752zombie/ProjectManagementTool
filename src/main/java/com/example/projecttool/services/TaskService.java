package com.example.projecttool.services;

import com.example.projecttool.models.project.Task;
import com.example.projecttool.repositories.TaskRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TaskService {

    public ArrayList<Task> getTasks(Integer projectId) throws SQLException {

        ArrayList<Task> allTasks = TaskRepository.getTasks(projectId);

        PrioritySorterService.sortTasks(allTasks);

        return allTasks;

    }


    public void editTask(int taskId, String name, String description, String priority, String start_time, String end_time,
                         int estimatedHoursTotal, int estimatedHoursDay) throws SQLException {

        TaskRepository.editTask(taskId, name, description, priority, start_time, end_time, estimatedHoursTotal, estimatedHoursDay);
    }

    public void deleteTask(int taskId) throws SQLException {

        TaskRepository.deleteTask(taskId);
    }

    public void addRowToTask(int projectId, String name, String description, String priority, String start_time, String end_time,
                             int estimatedHoursTotal, int estimatedHoursDay) throws SQLException {

        TaskRepository.addRowToTask(projectId, name, description, priority, start_time, end_time, estimatedHoursTotal, estimatedHoursDay);
    }
}
