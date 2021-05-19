package com.example.projecttool.controllers;

import com.example.projecttool.models.User;
import com.example.projecttool.models.project.Project;
import com.example.projecttool.models.project.Task;
import com.example.projecttool.util.ErrorHandler;
import com.example.projecttool.services.ProjectService;
import com.example.projecttool.services.ShareProjectService;
import com.example.projecttool.services.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;

@Controller
public class ProjectController {


    @GetMapping("/task-list")
    public String taskList(HttpSession session) {
        User user = (User) session.getAttribute("user");
        Project project = (Project) session.getAttribute("project");
        if (user == null || project == null) {
            return ErrorHandler.setCurrentError("You must be logged in and have selected a project to access this page", session);
        }

        try {
            ArrayList<Task> projectTasks = TaskService.getTasks(project.getProjectId());
            session.setAttribute("projectTasks", projectTasks);
        }

        catch (SQLException e) {
            return ErrorHandler.setCurrentError("Something went wrong retrieving tasks", session);
        }

        return "project/current-project";

    }

    @GetMapping("/create-project")
    public String createProject() {

        return "project/create-project";
    }

    @PostMapping("start-new-project")
    public String nameYourProject(@RequestParam("project_name") String projectName, @RequestParam("project_start") String projectStart,
                                  @RequestParam("project_end") String projectEnd, HttpSession session) {


        try {
            User user = (User) session.getAttribute("user");

            // Creates a project
           Project project = ProjectService.nameYourProject(user.getId(), projectName, projectStart, projectEnd);
           ArrayList<Task> projectTasks = TaskService.getTasks(project.getProjectId());

           session.setAttribute("project", project);
           session.setAttribute("projectTasks", projectTasks);


            return "project/current-project";
        } catch (SQLException e) { ;
            return ErrorHandler.setCurrentError("Something went wrong editing project", session);
        }
    }

        @GetMapping("/see-all-projects")
    public String seeProjectList(HttpSession session, Model model) {

            try {
                User user = (User) session.getAttribute("user");
                ArrayList<Project> projectList = ProjectService.seeProjectList(user.getId());
                // WHY DOES THIS ONLY WORK WITH Model
                model.addAttribute("projectList", projectList);

                return "project/all-projects";

            } catch (SQLException e) {
                return ErrorHandler.setCurrentError("Something went wrong retrieving project list", session);
            }
        }


    @PostMapping("choose-project-to-edit")
    public String editProject(@RequestParam("id") Integer projectId, @RequestParam("action") String action, HttpSession session, Model model) {

        try {
            User user = (User) session.getAttribute("user");

            // Add current project to session
            Project project = ProjectService.getProject(projectId);
            session.setAttribute("project", project);

            // Deletes row from project
            if (action.equals("Delete")) {
                ProjectService.deleteProject(projectId);
                // WHY DOES THIS ONLY WORK WITH Model
                ArrayList<Project> projectList = ProjectService.seeProjectList(user.getId());
                model.addAttribute("projectList", projectList);


                return "project/all-projects";

            }
            // Ignores shared project
            else if (action.equals("Ignore")){
                ShareProjectService.ignoreProject(projectId, user.getId());
                // WHY DOES THIS ONLY WORK WITH Model
                ArrayList<Project> sharedProjects = ShareProjectService.getSharedProjects(user.getId());
                model.addAttribute("projectList", sharedProjects);

                return "share-project/shared-with-me";

            }


         if (ProjectService.isReadOnly(projectId, user.getId())) {

             return "share-project/read-only";

         } else {return "redirect:/task-list";}

        } catch (SQLException s) {
            return ErrorHandler.setCurrentError("Something went wrong editing project", session);
        }

    }


    @PostMapping("/edit-task")
    public String editTask(@RequestParam("id") int taskId, @RequestParam("name") String name, @RequestParam("description") String description,
                           @RequestParam("priority") String priority, @RequestParam("start_time") String start_time,
                           @RequestParam("end_time") String end_time, @RequestParam("estimated-hours-day") int estimatedHoursDay,
                            @RequestParam("action") String action,  @RequestParam("count-weekends") String countWeekends, HttpSession session) {



        try {
            // Needs current project to get Tasks
            Project project = (Project) session.getAttribute("project");

            // Save changes to row
            if (action.equals("Save")) {
                TaskService.editTask(taskId, name, description, priority, start_time, end_time, estimatedHoursDay, countWeekends);
            }
            // Deletes row from project
            else if (action.equals("Delete")) {
                TaskService.deleteTask(taskId);

            }

            // Sorts and directs edited tasks to View
            ArrayList<Task> projectTasks = TaskService.getTasks(project.getProjectId());

            session.setAttribute("projectTasks", projectTasks);


        } catch (SQLException s) {
            return ErrorHandler.setCurrentError("Something went wrong editing project", session);
        }
        return "redirect:/task-list";
    }


    @PostMapping("add-row-to-tasks")
    public String addRowToTask(@RequestParam("name") String name, @RequestParam("description") String description, @RequestParam("priority") String priority,
                               @RequestParam("start_time") String start_time, @RequestParam("end_time") String end_time,
                               @RequestParam("count-weekends") String countWeekends, @RequestParam("estimated-hours-day") int estimatedHoursDay,
                               HttpSession session) {

        try {



            // We need project id to edit Task
            Project project = (Project) session.getAttribute("project");

            // Adds rows to DB
            TaskService.addRowToTask(project.getProjectId(), name, description, priority, start_time, end_time, estimatedHoursDay, countWeekends);

            // Directs tasks to View
            ArrayList<Task> projectTasks = TaskService.getTasks(project.getProjectId());
            session.setAttribute("projectTasks", projectTasks);


            return "redirect:/task-list";
        } catch (SQLException s) {
            return ErrorHandler.setCurrentError("Something went wrong editing project", session);
        }

    }
    @GetMapping("help-page")
    public String projectHelp(){

        return "project/help-page";
    }
}
