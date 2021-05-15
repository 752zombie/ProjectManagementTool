package com.example.projecttool.controllers;

import com.example.projecttool.models.User;
import com.example.projecttool.models.project.Project;
import com.example.projecttool.models.project.Task;
import com.example.projecttool.repositories.ProjectRepository;
import com.example.projecttool.repositories.ShareProjectRepository;
import com.example.projecttool.repositories.TaskRepository;
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

    ProjectService projectService = new ProjectService();
    TaskService taskService = new TaskService();
    ShareProjectService shareProjectService = new ShareProjectService();


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
           Project project = projectService.nameYourProject(user.getId(), projectName, projectStart, projectEnd);
           ArrayList<Task> projectTasks = taskService.getTasks(project.getProjectId());

           session.setAttribute("project", project);
           session.setAttribute("projectTasks", projectTasks);


            return "project/current-project";
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return "project/edit-failed";
        }
    }

        @GetMapping("/see-all-projects")
    public String seeProjectList(HttpSession session, Model model) {

            try {
                User user = (User) session.getAttribute("user");
                ArrayList<Project> projectList = projectService.seeProjectList(user.getId());
                // WHY DOES THIS ONLY WORK WITH Model
                model.addAttribute("projectList", projectList);

                return "project/all-projects";

            } catch (SQLException e) {
                System.out.println("Error getting project");
            }
            return "project/failed-getting-tasks";
        }


    @PostMapping("choose-project-to-edit")
    public String editProject(@RequestParam("id") Integer projectId, @RequestParam("action") String action, HttpSession session, Model model) {

        try {
            User user = (User) session.getAttribute("user");

            // Add current project to session
            Project project = projectService.getProject(projectId);

            // Add task list to View
            ArrayList<Task> projectTasks = taskService.getTasks(projectId);
            session.setAttribute("projectTasks", projectTasks);
            session.setAttribute("project", project);

            // Deletes row from project
            if (action.equals("Delete")) {
                projectService.deleteProject(projectId);
                // WHY DOES THIS ONLY WORK WITH Model
                ArrayList<Project> projectList = projectService.seeProjectList(user.getId());
                model.addAttribute("projectList", projectList);


                return "project/all-projects";

            }
            // Ignores shared project
            else if (action.equals("Ignore")){
                shareProjectService.ignoreProject(projectId, user.getId());
                // WHY DOES THIS ONLY WORK WITH Model
                ArrayList<Project> sharedProjects = shareProjectService.getSharedProjects(user.getId());
                model.addAttribute("projectList", sharedProjects);

                return "share-project/shared-with-me";

            }


         if (projectService.isReadOnly(projectId, user.getId())) {

             return "share-project/read-only";

         } else {return "project/current-project";}

        } catch (SQLException s) {
            System.out.println("something went wrong editing the project");
        }

        return "project/failed-getting-tasks";
    }


    @PostMapping("/edit-task")
    public String editTask(@RequestParam("id") int taskId, @RequestParam("name") String name, @RequestParam("description") String description,
                           @RequestParam("priority") String priority, @RequestParam("start_time") String start_time,
                           @RequestParam("end_time") String end_time, @RequestParam("estimated-hours-day") int estimatedHoursDay,
                           @RequestParam("estimated-hours-total") int estimatedHoursTotal,
                            @RequestParam("action") String action,  @RequestParam("count-weekends") String countWeekends, HttpSession session) {



        try {
            // Needs current project to get Tasks
            Project project = (Project) session.getAttribute("project");

            // Adds row to project
            if (action.equals("Save")) {
                taskService.editTask(taskId, name, description, priority, start_time, end_time, estimatedHoursTotal, estimatedHoursDay, countWeekends);

            }
            // Deletes row from project
            else if (action.equals("Delete")) {
                taskService.deleteTask(taskId);

            }

            // Sorts and directs edited tasks to View
            ArrayList<Task> projectTasks = taskService.getTasks(project.getProjectId());


            session.setAttribute("project", project);
            session.setAttribute("projectTasks", projectTasks);


        } catch (SQLException s) {
            System.out.println("project editing failed from DB");
            return "project/edit-failed";
        }
        return "project/current-project";
    }


    @PostMapping("add-row-to-tasks")
    public String addRowToTask(@RequestParam("name") String name, @RequestParam("description") String description, @RequestParam("priority") String priority,
                               @RequestParam("start_time") String start_time, @RequestParam("end_time") String end_time,
                               @RequestParam("estimated-hours-day") int estimatedHoursDay, @RequestParam("estimated-hours-total") int estimatedHoursTotal,
                               @RequestParam("count-weekends") String countWeekends, HttpSession session) {

        try {



            // We need project id to edit Task
            Project project = (Project) session.getAttribute("project");


            // Adds rows to DB
            taskService.addRowToTask(project.getProjectId(), name, description, priority, start_time, end_time, estimatedHoursDay, estimatedHoursTotal, countWeekends);

            // Directs tasks to View
            ArrayList<Task> projectTasks = taskService.getTasks(project.getProjectId());
            session.setAttribute("projectTasks", projectTasks);
            session.setAttribute("project", project);


            return "project/current-project";
        } catch (SQLException s) {
            System.out.println("something went wrong adding row to task");
        }

        return "project/failed-getting-tasks";
    }
    @GetMapping("return-current-project")
    public String returnToProject(){

        return "project/current-project";
    }
}
