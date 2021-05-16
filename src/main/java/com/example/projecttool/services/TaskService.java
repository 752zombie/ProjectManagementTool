package com.example.projecttool.services;

import com.example.projecttool.models.project.Task;
import com.example.projecttool.repositories.TaskRepository;

import java.sql.SQLException;
import java.util.ArrayList;

public class TaskService {


    public ArrayList<Task> getTasks(Integer projectId) throws SQLException {


        ArrayList<Task> allTasks = TaskRepository.getTasks(projectId);

        PrioritySorterService.sortTasksByPriority(allTasks);

        return allTasks;

    }


    //TODO: should also account for ArithmeticException
    public void editTask(int taskId, String taskName, String description, String priority, String start_time, String end_time,
                         int estimatedHoursTotal, int estimatedHoursDay, String countWeekends, int numberOfEmployees) throws SQLException {

        DueDateCalculator dueDate = new DueDateCalculator();
        String[] endTimeCalcAndWeekChoice = dueDate.dueDate(estimatedHoursDay, estimatedHoursTotal, start_time, countWeekends, numberOfEmployees);

       TaskRepository.editTask(taskId, taskName, description, priority, start_time, endTimeCalcAndWeekChoice[0], end_time, estimatedHoursTotal, estimatedHoursDay, endTimeCalcAndWeekChoice[1]);

       // SHOULD week CHOICE BE ADDED TO TASK OR A NEW TABLE
    }

    public void deleteTask(int taskId) throws SQLException {

        TaskRepository.deleteTask(taskId);
    }

    public void addRowToTask(int projectId, String name, String description, String priority, String start_time, String end_time,
                             int estimatedHoursDay, int estimatedHoursTotal, String countWeekends, int numberOfEmployees) throws SQLException {


        DueDateCalculator dueDate = new DueDateCalculator();
        String[] endTimeCalcAndWeekChoice = dueDate.dueDate(estimatedHoursDay, estimatedHoursTotal, start_time, countWeekends, numberOfEmployees);

        TaskRepository.addRowToTask(projectId, name, description, priority, start_time, endTimeCalcAndWeekChoice[0], end_time, estimatedHoursTotal, estimatedHoursDay, endTimeCalcAndWeekChoice[1]);

        // SHOULD week CHOICE BE ADDED TO TASK OR A NEW TABLE
    }

    public int getAmountOfEmployeesAssigned(int taskId) throws SQLException{
        return TaskRepository.getTotalNumberOfEmployeesAssigned(taskId);
    }

    public int getEstimatedTimeToComplete(int taskId) throws SQLException{
        return TaskRepository.getTotalHoursToComplete(taskId);
    }
}
