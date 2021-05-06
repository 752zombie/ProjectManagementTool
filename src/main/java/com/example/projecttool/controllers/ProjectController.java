package com.example.projecttool.controllers;

import com.example.projecttool.models.User;
import com.example.projecttool.models.project.ProjectTest;
import com.example.projecttool.repositories.ProjectRepository;
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



   @PostMapping("name-your-project")
   public String nameYourProject(@RequestParam("project_name") String project_name,  HttpSession session, Model model) {

       User user = (User) session.getAttribute("user");
       ProjectRepository.createProject(user.getId(), project_name);



       return "project/all-projects";
   }


    @PostMapping("/edit-project")
    public String editProject(@RequestParam("id") int id, @RequestParam("name") String name, @RequestParam("description") String description,
                              @RequestParam("start_time") String start_time, @RequestParam("end_time") String end_time) {


        try {

          ProjectRepository.editProject(id, name, description, start_time, end_time);

            return "redirect:/old-project";

        } catch (SQLException s) {
            return "project/edit-failed";
        }
    }

    @GetMapping("old-project")
    public String getProject(Model model, HttpSession session) {


        User user = (User) session.getAttribute("user");

        ArrayList<ProjectTest> projectList = ProjectRepository.getProject(user.getId());


        model.addAttribute("projectList", projectList);

        return "project/old-project";

    }

    @PostMapping("add-row")
    public String addRowToProject(@RequestParam("name") String name, @RequestParam("description") String description,
                                  @RequestParam("start_time") String start_time, @RequestParam("end_time") String end_time, HttpSession session){

        User user = (User) session.getAttribute("user");

        ProjectRepository.addRowToProject(user.getId(), name, description, start_time, end_time);

        return "redirect:/old-project";
    }

}
