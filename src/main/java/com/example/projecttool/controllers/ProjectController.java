package com.example.projecttool.controllers;

import com.example.projecttool.models.User;
import com.example.projecttool.models.project.Project;
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
        ArrayList<ProjectTest> projectList = ProjectRepository.getProject();


        model.addAttribute("projectList", projectList);

        return "project/old-project";

    }

    @PostMapping("add-row")
    public String addRowToProject(@RequestParam("id") int id, @RequestParam("name") String name, @RequestParam("description") String description,
                                  @RequestParam("start_time") String start_time, @RequestParam("end_time") String end_time){

        ProjectRepository.addRowToProject(id, name, description, start_time, end_time);

        return "redirect:/old-project";
    }


}
