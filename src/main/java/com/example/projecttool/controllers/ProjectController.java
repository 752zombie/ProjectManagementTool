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
                                 @RequestParam("project_end") String project_end, HttpSession session, Model model) {


       User user = (User) session.getAttribute("user");
        // CREATES A PROJECT
       int project_id = ProjectRepository.createProject(user.getId(), project_name, project_start, project_end);

       // CREATES AN EMPTY TASK IN THE PROJECT
       TaskRepository.createTask(project_id);


       return "project/old-project";
   }

    @GetMapping("/see-all-projects")
    public String seeProjectList(HttpSession session, Model model) {

        User user = (User) session.getAttribute("user");

        ArrayList<Project> allProjects = ProjectRepository.getProjects(user.getId());

        model.addAttribute("projectList", allProjects);

        return "project/all-projects";
    }




   @PostMapping("edit-project")
   public String editProject(@RequestParam("project-name") String project_name, Model model){


        int project_id = ProjectRepository.getProjectId(project_name);


        ArrayList<Task> projectTasks = TaskRepository.getTasks(project_id);


        model.addAttribute("projectTasks", projectTasks);

       return "project/old-project";
   }


    /*
    @GetMapping("old-project")
    public String getTask(Model model, HttpSession session) {


        User user = (User) session.getAttribute("user");

        ArrayList<Task> projectList = TaskRepository.getTasks(user.getId());


        model.addAttribute("projectList", projectList);

        return "project/old-project";

    }

     */


    @PostMapping("/edit-tasks")
    public String editTasks(@RequestParam("id") int id, @RequestParam("name") String name, @RequestParam("description") String description,
                            @RequestParam("start_time") String start_time, @RequestParam("end_time") String end_time, HttpSession session) {


        try {

            System.out.println(id);
       // ProjectRepository.getProjectId();

          ProjectRepository.editTask(id, name, description, start_time, end_time);

            return "redirect:/old-project";

        } catch (SQLException s) {
            return "project/edit-failed";
        }
    }


    @PostMapping("add-row")
    public String addRowToTask(@RequestParam("name") String name, @RequestParam("description") String description,
                               @RequestParam("start_time") String start_time, @RequestParam("end_time") String end_time, HttpSession session){

        User user = (User) session.getAttribute("user");

        ProjectRepository.addRowToTask(user.getId(), name, description, start_time, end_time);

        return "redirect:/old-project";
    }



}
