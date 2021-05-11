package com.example.projecttool.controllers;

import com.example.projecttool.models.project.Subtask;
import com.example.projecttool.services.SubtaskService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;


@Controller
public class SubtaskController {

    @PostMapping("edit-task-with-subtasks")
    public String viewSubtasks(@RequestParam("id") Integer taskId, HttpSession session)
    {
        ArrayList<Subtask> subtasks = SubtaskService.getSubtasks(taskId);
        session.setAttribute("subtasks", subtasks);
        session.setAttribute("taskId", taskId);

        return "redirect:view-subtasks";
    }

    @GetMapping("/view-subtasks")
    public String viewSubtask2() {
        return "project/edit-task";
    }

    @PostMapping("add-subtask-to-task")
    public String addSubtaskToTask(@RequestParam("subtask-name") String name, @RequestParam("subtask-description") String description,
                                   @RequestParam("start-time") String startTime, @RequestParam("end-time") String endTime,
                                   @RequestParam("task-id") Integer taskId, HttpSession session) {

        try {
            SubtaskService.addNewSubtaskToTask(taskId, name, description, startTime, endTime);
            ArrayList<Subtask> subtasks = SubtaskService.getSubtasks(taskId);
            session.setAttribute("subtasks", subtasks);
            session.setAttribute("taskId", taskId);

        }

        catch (SQLException e) {
            return "project/failed-getting-tasks";
        }

        return "redirect:/view-subtasks";
    }
}
