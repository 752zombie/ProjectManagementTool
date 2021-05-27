package com.example.projecttool.controllers;

import com.example.projecttool.models.user.User;
import com.example.projecttool.models.project.Project;
import com.example.projecttool.models.project.Task;
import com.example.projecttool.services.ProjectService;
import com.example.projecttool.services.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

@Controller
public class TaskController {


    @GetMapping("/task-list")
    public String taskList(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        Project project = (Project) session.getAttribute("project");
        if (user == null || project == null) {
            return ErrorHandlerController.setCurrentError("You must be logged in and have selected a project to access this page", session);
        }

        try {

            if (!ProjectService.hasAccess(project.getProjectId(), user.getId())) {
                return ErrorHandlerController.setCurrentError("You do not have access to that project", session);
            }

            Map<Integer, Task> tasks = TaskService.getTasks(project.getProjectId());
            session.setAttribute("projectTasks", tasks);
            session.setAttribute("isReadOnly", ProjectService.isReadOnly(project.getProjectId(), user.getId()));

        }

        catch (SQLException e) {
            return ErrorHandlerController.setCurrentError("Something went wrong retrieving tasks", session);
        }

        return "project/tasks";

    }

    @PostMapping("/edit-task")
    public String editTask(@RequestParam("id") int taskId, @RequestParam("name") String name, @RequestParam("description") String description,
                           @RequestParam("priority") String priority, @RequestParam("start_time") String start_time,
                           @RequestParam("end_time") String end_time, @RequestParam("estimated-hours-day") int estimatedHoursDay,
                           @RequestParam("action") String action, @RequestParam("count-weekends") String countWeekends, HttpSession session) {

        try {
            // Needs current project to get Tasks
            Project project = (Project) session.getAttribute("project");
            User user = (User) session.getAttribute("user");

            // Save changes to row
            if (action.equals("Save")) {
                TaskService.editTask(taskId, name, description, priority, start_time, end_time, estimatedHoursDay, countWeekends, project.getProjectId(), user.getId());
            }
            // Deletes row from project
            else if (action.equals("Delete")) {
                TaskService.deleteTask(project.getProjectId(), taskId);
            }



        } catch (SQLException s) {
            return ErrorHandlerController.setCurrentError("Something went wrong editing project", session);
        }
        return "redirect:/task-list";
    }


    @PostMapping("add-row-to-tasks")
    public String addRowToTask(@RequestParam("name") String name, @RequestParam("description") String description, @RequestParam("priority") String priority,
                               @RequestParam("start_time") String start_time, @RequestParam("end_time") String end_time,
                               @RequestParam("count-weekends") String countWeekends, @RequestParam("estimated-hours-day") int estimatedHoursDay,
                               HttpSession session) {

        try {


            User user = (User) session.getAttribute("user");

            // We need project id to edit Task
            Project project = (Project) session.getAttribute("project");

            // Adds rows to DB
            TaskService.addRowToTask(project.getProjectId(), name, description, priority, start_time, end_time, estimatedHoursDay, countWeekends, user.getId());



            return "redirect:/task-list";
        } catch (SQLException e) {
            return ErrorHandlerController.setCurrentError("Something went wrong editing project", session);
        }

    }
    @GetMapping("help-page")
    public String projectHelp(){

        return "project/help-page";
    }

}
