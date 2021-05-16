package com.example.projecttool.services;

import com.example.projecttool.models.project.Task;
import com.example.projecttool.repositories.TaskRepository;

import java.sql.SQLException;
import java.util.ArrayList;

public class TaskService {


    public ArrayList<Task> getTasks(Integer projectId) throws SQLException {


        ArrayList<Task> allTasks = TaskRepository.getTasks(projectId);

        for (Task task : allTasks) {
            try {
                DueDateCalculator dueDateCalculator = new DueDateCalculator();
                String[] endDateCalcAndWeekChoice = dueDateCalculator.dueDate(task.getEstimatedHoursPrDay(),
                        task.getEstimatedHoursTotal(), task.getStart_time(), task.getCountWeekends(),
                        getAmountOfEmployeesAssigned(task.getId()));
                task.setEnd_time_calculated(endDateCalcAndWeekChoice[0]);
                task.setEndTimeCanBeCalculated(true);
            }

            catch (ArithmeticException e) {
                task.setEndTimeCanBeCalculated(false);
            }
        }

        PrioritySorterService.sortTasksByPriority(allTasks);

        return allTasks;

    }

    public void editTask(int taskId, String taskName, String description, String priority, String start_time, String end_time,
                         int estimatedHoursDay, String countWeekends) throws SQLException {


       TaskRepository.editTask(taskId, taskName, description, priority, start_time, end_time, estimatedHoursDay, countWeekends);

       // SHOULD week CHOICE BE ADDED TO TASK OR A NEW TABLE
    }

    public void deleteTask(int taskId) throws SQLException {

        TaskRepository.deleteTask(taskId);
    }

    public void addRowToTask(int projectId, String name, String description, String priority, String start_time, String end_time,
                             int estimatedHoursDay, String countWeekends) throws SQLException {

        TaskRepository.addRowToTask(projectId, name, description, priority, start_time, end_time, estimatedHoursDay, countWeekends);

    }

    public int getAmountOfEmployeesAssigned(int taskId) throws SQLException{
        return TaskRepository.getTotalNumberOfEmployeesAssigned(taskId);
    }

    public int getEstimatedTimeToComplete(int taskId) throws SQLException{
        return TaskRepository.getTotalHoursToComplete(taskId);
    }
}
