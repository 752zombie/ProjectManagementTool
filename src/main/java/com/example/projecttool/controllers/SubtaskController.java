package com.example.projecttool.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SubtaskController {

    @PostMapping("/view-subtasks")
    public String viewSubtasks() {
        return "project/edit-task";
    }
}
