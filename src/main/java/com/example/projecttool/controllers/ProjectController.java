package com.example.projecttool.controllers;

import com.example.projecttool.models.User;
import com.example.projecttool.models.project.Project;
import com.example.projecttool.models.project.Task;
import com.example.projecttool.repositories.ProjectRepository;
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
   public String nameYourProject(@RequestParam("project_name") String project_name, @RequestParam("project_start") String project_start,
                                 @RequestParam("project_end") String project_end, HttpSession session) {



       User user = (User) session.getAttribute("user");
        // CREATES A PROJECT
       int project_id = ProjectRepository.createProject(user.getId(), project_name, project_start, project_end);

       Project project = new Project(project_id, project_name, project_start, project_end);

       session.setAttribute("project", project);

       System.out.println("start new project:");
       System.out.println(project.getProjectId());


       // CREATES AN EMPTY TASK IN THE PROJECT
       TaskRepository.createTask(project_id, user.getId());

       return "project/old-project";
   }

    @GetMapping("/see-all-projects")
    public String seeProjectList(HttpSession session, Model model) {

        User user = (User) session.getAttribute("user");

        ArrayList<Project> allProjects = ProjectRepository.getProjects(user.getId());

        model.addAttribute("projectList", allProjects);

        return "project/all-projects";
    }


   @PostMapping("choose-project-to-edit")
   public String editProject(@RequestParam("project-name") String project_name, Model model, HttpSession session){

        // Add current project to session
        Project project = ProjectRepository.getCurrentProjectObject(project_name);
        session.setAttribute("project", project);

        // Directs tasks to View
        int project_id = ProjectRepository.getProjectId(project_name);
        ArrayList<Task> projectTasks = TaskRepository.getTasks(project_id);
        model.addAttribute("projectTasks", projectTasks);

       return "project/old-project";
   }

    @PostMapping("/edit-task")
    public String editTask(@RequestParam("id") int taskId, @RequestParam("name") String name, @RequestParam("description") String description,
                           @RequestParam("start_time") String start_time, @RequestParam("end_time") String end_time, HttpSession session, Model model) {

        try {
            // Need current project to get tasks
            Project project = (Project) session.getAttribute("project");
            // SAVES EDITED TASK TO DB
            TaskRepository.editTask(taskId, name, description, start_time, end_time);

            // Directs tasks to View
            ArrayList<Task> projectTasks = TaskRepository.getTasks(project.getProjectId());
            model.addAttribute("projectTasks", projectTasks);



            return "project/old-project";

        } catch (SQLException s) {
            return "project/edit-failed";
        }
    }


    @PostMapping("add-row-to-tasks")
    public String addRowToTask(@RequestParam("name") String name, @RequestParam("description") String description,
                               @RequestParam("start_time") String start_time, @RequestParam("end_time") String end_time, HttpSession session, Model model){



        // WE NEED TO GET project_id to edit the TASK
        Project project = (Project) session.getAttribute("project");
        User user = (User) session.getAttribute("user");


        // ADDS ROW TO DB
        TaskRepository.addRowToTask(project.getProjectId(), name, description, start_time, end_time);


        // Directs tasks to View
        ArrayList<Task> projectTasks = TaskRepository.getTasks(project.getProjectId());
        model.addAttribute("projectTasks", projectTasks);



        return "project/old-project";
    }

}
