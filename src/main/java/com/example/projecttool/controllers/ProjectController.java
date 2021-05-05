package com.example.projecttool.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProjectController {

    @GetMapping("/create-project")
    public String profilePage() {
        return "create-project";
    }

}
