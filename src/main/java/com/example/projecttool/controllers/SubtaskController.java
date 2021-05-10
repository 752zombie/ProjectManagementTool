package com.example.projecttool.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class SubtaskController {

    @PostMapping("/view-subtasks")
    public String viewSubtasks() {
        return "project/edit-task";
    }
}
