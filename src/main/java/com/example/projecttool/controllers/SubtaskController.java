package com.example.projecttool.controllers;

import com.example.projecttool.models.Employee;
import com.example.projecttool.models.User;
import com.example.projecttool.models.project.Subtask;
import com.example.projecttool.repositories.SubtaskRepository;
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
    public String viewSubtask2(HttpSession session)
    {
        try {
            User user = (User) session.getAttribute("user");
            ArrayList<Employee> employees = SubtaskRepository.getAllEmployees(user.getId());
            session.setAttribute("allEmployees", employees);
            Integer taskId = (Integer) session.getAttribute("taskId");
            ArrayList<Subtask> subtasks = SubtaskService.getSubtasks(taskId);
            session.setAttribute("subtasks", subtasks);
        }

        catch (SQLException e) {
            return "project/failed-getting-tasks";
        }

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

    @PostMapping("save-subtask")
    public String saveSubtask(@RequestParam("subtask-name") String name, @RequestParam("subtask-description") String description,
                              @RequestParam("start-time") String startTime, @RequestParam("end-time") String endTime,
                              @RequestParam("subtask-id") Integer subtaskId, HttpSession session) {

        try {
            SubtaskService.updateSubtask(subtaskId, name, description, startTime, endTime);
            Integer taskId =  (Integer) session.getAttribute("taskId");
            ArrayList<Subtask> subtasks = SubtaskService.getSubtasks(taskId);
            session.setAttribute("subtasks", subtasks);

        }

        catch (SQLException e) {
            return "project/failed-getting-tasks";
        }

        return "redirect:view-subtasks";

    }

    @PostMapping("/add-new-subtask")
    public String addNewSubtask(@RequestParam("subtask-name") String name, @RequestParam("subtask-description") String description,
                                @RequestParam("start-time") String startTime, @RequestParam("end-time") String endTime, HttpSession session) {

        try {
            Integer taskId = (Integer) session.getAttribute("taskId");
            SubtaskService.addNewSubtaskToTask(taskId, name, description, startTime, endTime);
            ArrayList<Subtask> subtasks = SubtaskService.getSubtasks(taskId);
            session.setAttribute("subtasks", subtasks);
        }

        catch (SQLException e) {
            return "project/failed-getting-tasks";
        }

        return "redirect:view-subtasks";
    }

    @PostMapping("/delete-subtask")
    public String deleteSubtask(@RequestParam("subtask-id") Integer subtaskId, HttpSession session) {
        try {
            SubtaskService.deleteSubtask(subtaskId);
            Integer taskId = (Integer) session.getAttribute("taskId");
            ArrayList<Subtask> subtasks = SubtaskService.getSubtasks(taskId);
            session.setAttribute("subtasks", subtasks);
        }

        catch (SQLException e) {
            return "project/failed-getting-tasks";
        }

        return "redirect:view-subtasks";
    }

    @PostMapping("add-employee-to-subtask")
    public String addEmployeeToSubtask(@RequestParam("employee-id") Integer employeeId, @RequestParam("subtask-id") Integer subtaskId) {
        try {
            System.out.println(employeeId);
            System.out.println(subtaskId);
            SubtaskRepository.addEmployeeToSubtask(subtaskId, employeeId);
        }

        catch (SQLException e) {
            return "project/failed-getting-tasks";
        }

        return  "redirect:/view-subtasks";
    }
}
