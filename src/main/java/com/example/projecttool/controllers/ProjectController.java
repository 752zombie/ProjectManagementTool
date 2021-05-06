package com.example.projecttool.controllers;

import com.example.projecttool.repositories.ProjectRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;

@Controller
public class ProjectController {

    @GetMapping("/create-project")
    public String profilePage() {
        return "create-project";
    }


    @PostMapping("edit-project")
    public String editProject(@RequestParam("id") int id, @RequestParam("name") String name, @RequestParam("description") String description) {


        try {


            System.out.println(name + description);


            ProjectRepository.editProject(id, name, description);

            return "create-project";

        } catch (SQLException s) {
            return "edit-failed";
        }
    }

}
