package com.example.projecttool.services;

import com.example.projecttool.models.project.Project;
import com.example.projecttool.models.project.Task;
import com.example.projecttool.repositories.ShareProjectRepository;
import com.example.projecttool.repositories.TaskRepository;
import com.example.projecttool.util.DueDateCalculator;
import com.example.projecttool.util.PrioritySorter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TaskService {

    public static Map<Integer, Task> getTasks(Integer projectId) throws SQLException {


        Map<Integer, Task> allTasks = ProjectService.getInstance().getTaskMap(projectId);

        for (Task task : allTasks.values()) {
            try {
                DueDateCalculator dueDateCalculator = new DueDateCalculator();
                String dueDate = dueDateCalculator.calculateDueDate(task.getEstimatedHoursPrDay(),
                        task.getEstimatedHoursTotal(), task.getStart_time(), task.getCountWeekends(),
                        getAmountOfEmployeesAssigned(task.getId()));
                task.setEnd_time_calculated(dueDate);
                task.setEndTimeCanBeCalculated(true);
            }

            catch (ArithmeticException e) {
                task.setEndTimeCanBeCalculated(false);
            }
        }

        //PrioritySorter.sortTasksByPriority(allTasks);

        return allTasks;

    }

    public static void editTask(int taskId, String taskName, String description, String priority, String start_time, String end_time,
                         int estimatedHoursDay, String countWeekends, int projectId, int userId) throws SQLException {

        boolean canEdit = ShareProjectRepository.getAccessLevel(projectId, userId).equals("read-and-edit");
        boolean isOwner = ProjectService.isOwner(projectId, userId);
        if (isOwner || canEdit) {
            TaskRepository.editTask(taskId, taskName, description, priority, start_time, end_time, estimatedHoursDay, countWeekends);
            Task task = ProjectService.getInstance().getTask(projectId, taskId);
            task.setName(taskName);
            task.setDescription(description);
            task.setPriority(priority);
            task.setStart_time(start_time);
            task.setEnd_time(end_time);
            task.setEstimatedHoursPrDay(estimatedHoursDay);
            task.setCountWeekends(countWeekends);
        }
    }



    public static void deleteTask(int projectId, int taskId) throws SQLException {
        ProjectService.getInstance().deleteTask(projectId, taskId);
        TaskRepository.deleteTask(taskId);
    }

    public static void addRowToTask(int projectId, String name, String description, String priority, String start_time, String end_time,
                             int estimatedHoursDay, String countWeekends, int userId) throws SQLException {

        boolean canEdit = ShareProjectRepository.getAccessLevel(projectId, userId).equals("read-and-edit");
        boolean isOwner = ProjectService.isOwner(projectId, userId);

        if (isOwner || canEdit) {
            Project project = ProjectService.getInstance().getProjectInMap(projectId);
            TaskRepository.addRowToTask(projectId, name, description, priority, start_time, end_time, estimatedHoursDay, countWeekends);
            int taskId = TaskRepository.getNewestTaskId(projectId);
            Task task = new Task(taskId, name, description, start_time, end_time, priority, TaskRepository.getTotalHoursToComplete(taskId), estimatedHoursDay, countWeekends);
            project.addTask(task);
        }

    }

    public static int getAmountOfEmployeesAssigned(int taskId) throws SQLException{
        return TaskRepository.getTotalNumberOfEmployeesAssigned(taskId);
    }


    public static String getTaskName(Integer taskId) throws SQLException {

        return TaskRepository.getTaskName(taskId);
    }

}
