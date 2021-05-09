package com.example.projecttool.controllers;

import com.example.projecttool.models.User;
import com.example.projecttool.models.project.Project;
import com.example.projecttool.models.project.Task;
import com.example.projecttool.repositories.ProjectRepository;
import com.example.projecttool.repositories.ShareProjectRepository;
import com.example.projecttool.repositories.TaskRepository;
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

    @GetMapping("/create-project")
    public String createProject() {

        return "project/create-project";
    }

    @PostMapping("start-new-project")
    public String nameYourProject(@RequestParam("project_name") String projectName, @RequestParam("project_start") String projectStart,
                                  @RequestParam("project_end") String projectEnd, HttpSession session, Model model) {

        User user = (User) session.getAttribute("user");

        // Creates a project
        int project_id = ProjectRepository.createProject(user.getId(), projectName, projectStart, projectEnd);

        // Saves project in session and adds to view
        Project project = new Project(project_id, projectName, projectStart, projectEnd);
        session.setAttribute("project", project);
        model.addAttribute("project", project);

        return "project/old-project";
    }

    @GetMapping("/see-all-projects")
    public String seeProjectList(HttpSession session, Model model) {

        User user = (User) session.getAttribute("user");
        // Lists projects to view
        ArrayList<Project> allProjects = ProjectRepository.getProjects(user.getId());
        model.addAttribute("projectList", allProjects);

        return "project/all-projects";
    }

    //Why do we need to both add the project to the session and the model? One of them should be enough.
    @PostMapping("choose-project-to-edit")
    public String editProject(@RequestParam("id") Integer projectId, Model model, HttpSession session) {

        try {
            User user = (User) session.getAttribute("user");
            // Add current project to session
            Project project = ProjectRepository.getProject(projectId);
            session.setAttribute("project", project);


            // Add project name to View
            model.addAttribute("project", project);

            // Add projects list to View
            ArrayList<Task> projectTasks = TaskRepository.getTasks(projectId);

            model.addAttribute("projectTasks", projectTasks);


         if (ShareProjectRepository.isReadOnly(projectId, user.getId())) {
             return "share-project/read-only";

         }
         else return "project/old-project";

        } catch (SQLException s) {
            System.out.println("something went wrong editing the project");
        }

        return "project/failed-getting-tasks";
    }


    @PostMapping("/edit-task")
    public String editTask(@RequestParam("id") int taskId, @RequestParam("name") String name, @RequestParam("description") String description, @RequestParam("priority") String priority,
                           @RequestParam("start_time") String start_time, @RequestParam("end_time") String end_time, @RequestParam("estimated-hours") int estimatedHours, HttpSession session, Model model,
                           @RequestParam("action") String action) {

        try {
            System.out.println(priority);

            // Needs current project to get Tasks
            Project project = (Project) session.getAttribute("project");

            // Adds row to project
            if (action.equals("Save")) {
                TaskRepository.editTask(taskId, name, description, priority, start_time, end_time, estimatedHours);

            }
            // Deletes row from project
            else if (action.equals("Delete")) {
                TaskRepository.deleteTask(taskId);

            }
            // Directs edited tasks to View
            ArrayList<Task> projectTasks = TaskRepository.getTasks(project.getProjectId());

            model.addAttribute("project", project);
            model.addAttribute("projectTasks", projectTasks);

        } catch (SQLException s) {
            return "project/edit-failed";
        }
        return "project/old-project";
    }


    @PostMapping("add-row-to-tasks")
    public String addRowToTask(@RequestParam("name") String name, @RequestParam("description") String description, @RequestParam("priority") String priority,
                               @RequestParam("start_time") String start_time, @RequestParam("end_time") String end_time, @RequestParam("estimated-hours") int estimatedHours, HttpSession session, Model model) {

        try {

            // We need project id to edit Task
            Project project = (Project) session.getAttribute("project");

            // Adds rows to DB
            TaskRepository.addRowToTask(project.getProjectId(), name, description, priority, start_time, end_time, estimatedHours);


            // Directs tasks to View
            ArrayList<Task> projectTasks = TaskRepository.getTasks(project.getProjectId());
            model.addAttribute("projectTasks", projectTasks);
            model.addAttribute("project", project);


            return "project/old-project";
        } catch (SQLException s) {
            System.out.println("something went wrong adding row to task");
        }

        return "project/failed-getting-tasks";
    }
}
