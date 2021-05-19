package com.example.projecttool.controllers;

import com.example.projecttool.models.Employee;
import com.example.projecttool.models.Skill;
import com.example.projecttool.models.User;
import com.example.projecttool.models.project.Subtask;
import com.example.projecttool.services.EmployeeService;
import com.example.projecttool.util.ErrorHandler;
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
        session.setAttribute("taskId", taskId);

        return "redirect:view-subtasks";
    }

    @GetMapping("/view-subtasks")
    public String viewSubtask2(HttpSession session)
    {
        try {
            User user = (User) session.getAttribute("user");
            ArrayList<Employee> employees = SubtaskService.getAllEmployees(user.getId());
            session.setAttribute("allEmployees", employees);
            Integer taskId = (Integer) session.getAttribute("taskId");
            ArrayList<Subtask> subtasks = SubtaskService.getSubtasks(taskId);
            session.setAttribute("subtasks", subtasks);
            ArrayList<Skill> skills = EmployeeService.getAllSkills(user.getId());
            session.setAttribute("allSkills", skills);
        }

        catch (SQLException e) {
            return ErrorHandler.setCurrentError("Something went wrong loading the subtask page", session);
        }

        return "project/edit-task";
    }

    @PostMapping("save-subtask")
    public String saveSubtask(@RequestParam("subtask-name") String name, @RequestParam("subtask-description") String description,
                              @RequestParam("start-time") String startTime, @RequestParam("end-time") String endTime,
                              @RequestParam("subtask-id") Integer subtaskId, @RequestParam("hours-to-complete") int hoursToComplete, HttpSession session) {

        try {
            SubtaskService.updateSubtask(subtaskId, name, description, startTime, endTime, hoursToComplete);
        }

        catch (SQLException e) {
            return ErrorHandler.setCurrentError("Something went wrong saving changes", session);
        }

        return "redirect:view-subtasks";

    }

    @PostMapping("/add-new-subtask")
    public String addNewSubtask(@RequestParam("subtask-name") String name, @RequestParam("subtask-description") String description,
                                @RequestParam("start-time") String startTime, @RequestParam("end-time") String endTime,
                                @RequestParam("hours-to-complete") int hoursToComplete, HttpSession session) {

        try {
            Integer taskId = (Integer) session.getAttribute("taskId");
            SubtaskService.addNewSubtaskToTask(taskId, name, description, startTime, endTime, hoursToComplete);
        }

        catch (SQLException e) {
            return ErrorHandler.setCurrentError("Something went wrong adding new subtask to task", session);
        }

        return "redirect:view-subtasks";
    }

    @PostMapping("/delete-subtask")
    public String deleteSubtask(@RequestParam("subtask-id") Integer subtaskId, HttpSession session) {
        try {
            SubtaskService.deleteSubtask(subtaskId);
        }

        catch (SQLException e) {
            return ErrorHandler.setCurrentError("Something went deleting subtask", session);
        }

        return "redirect:view-subtasks";
    }

    @PostMapping("/add-employee-to-subtask")
    public String addEmployeeToSubtask(@RequestParam("employee-id") Integer employeeId, @RequestParam("subtask-id") Integer subtaskId, HttpSession session) {
        try {
            SubtaskService.addEmployeeToSubtask(subtaskId, employeeId);
        }

        catch (SQLException e) {
            return ErrorHandler.setCurrentError("Something went wrong adding employee to subtask", session);
        }

        return  "redirect:/view-subtasks";
    }

    @PostMapping("/remove-employee-from-subtask")
    public String removeEmployeeFromSubtask(@RequestParam("employee-id") Integer employeeId, @RequestParam("subtask-id") Integer subtaskId, HttpSession session) {
        try {
            SubtaskService.removeEmployeeFromSubtask(subtaskId, employeeId);
        }

        catch (SQLException e) {
            return ErrorHandler.setCurrentError("Something went wrong removing employee from subtask", session);
        }

        return  "redirect:/view-subtasks";
    }

    @PostMapping("add-skill-to-subtask")
    public String addSkillToSubtask(@RequestParam("subtask-id") Integer subtaskId, @RequestParam("skill-id") Integer skillId, HttpSession session) {
        try {
            SubtaskService.addSkillToSubtask(subtaskId, skillId);
        }

        catch (SQLException e) {
            return ErrorHandler.setCurrentError("Something went wrong adding skill to subtask", session);
        }

        return  "redirect:/view-subtasks";
    }

    @PostMapping("/remove-skill-from-subtask")
    public String removeSkillFromSubtask(@RequestParam("subtask-id") Integer subtaskId, @RequestParam("skill-id") Integer skillId, HttpSession session) {
        try {
            System.out.println(subtaskId);
            System.out.println(skillId);
            SubtaskService.removeSkillFromSubtask(subtaskId, skillId);
        }

        catch (SQLException e) {
            return ErrorHandler.setCurrentError("Something went wrong removing skill from subtask", session);
        }

        return  "redirect:/view-subtasks";
    }
}
