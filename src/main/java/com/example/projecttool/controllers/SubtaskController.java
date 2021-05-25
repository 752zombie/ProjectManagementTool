package com.example.projecttool.controllers;

import com.example.projecttool.models.project.Employee;
import com.example.projecttool.models.project.Skill;
import com.example.projecttool.models.user.User;
import com.example.projecttool.models.project.Project;
import com.example.projecttool.models.project.Subtask;
import com.example.projecttool.services.EmployeeService;
import com.example.projecttool.services.ProjectService;
import com.example.projecttool.services.TaskService;
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
            Project project = (Project) session.getAttribute("project");
            ArrayList<Employee> employees = SubtaskService.getAllEmployees(user.getId(), project.getProjectId());
            session.setAttribute("allEmployees", employees);
            Integer taskId = (Integer) session.getAttribute("taskId");
            String taskName = TaskService.getTaskName(taskId);
            session.setAttribute("taskName", taskName);
            ArrayList<Subtask> subtasks = SubtaskService.getSubtasks(taskId);
            session.setAttribute("subtasks", subtasks);
            ArrayList<Skill> skills = EmployeeService.getAllSkills(user.getId());
            session.setAttribute("allSkills", skills);

            if (!ProjectService.hasAccess(project.getProjectId(), user.getId())) {
                return ErrorHandler.setCurrentError("You do not have access to that project", session);
            }
        }

        catch (SQLException e) {
            e.printStackTrace();
            return ErrorHandler.setCurrentError("Something went wrong loading the subtask page", session);
        }

        return "project/subtasks";
    }

    @PostMapping("save-subtask")
    public String saveSubtask(@RequestParam("subtask-name") String name, @RequestParam("subtask-description") String description,
                              @RequestParam("start-time") String startTime, @RequestParam("end-time") String endTime,
                              @RequestParam("subtask-id") Integer subtaskId, @RequestParam("hours-to-complete") int hoursToComplete, HttpSession session) {

        try {
            User user = (User) session.getAttribute("user");
            Project project = (Project) session.getAttribute("project");
            SubtaskService.updateSubtask(subtaskId, name, description, startTime, endTime, hoursToComplete, project.getProjectId(), user.getId());
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
            User user = (User) session.getAttribute("user");
            Project project = (Project) session.getAttribute("project");
            Integer taskId = (Integer) session.getAttribute("taskId");
            SubtaskService.addNewSubtaskToTask(taskId, name, description, startTime, endTime, hoursToComplete, project.getProjectId(), user.getId());
        }

        catch (SQLException e) {
            return ErrorHandler.setCurrentError("Something went wrong adding new subtask to task", session);
        }

        return "redirect:view-subtasks";
    }

    @PostMapping("/delete-subtask")
    public String deleteSubtask(@RequestParam("subtask-id") Integer subtaskId, HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");
            Project project = (Project) session.getAttribute("project");
            SubtaskService.deleteSubtask(subtaskId, project.getProjectId(), user.getId());
        }

        catch (SQLException e) {
            return ErrorHandler.setCurrentError("Something went deleting subtask", session);
        }

        return "redirect:view-subtasks";
    }

    @PostMapping("/add-employee-to-subtask")
    public String addEmployeeToSubtask(@RequestParam("employee-id") Integer employeeId, @RequestParam("subtask-id") Integer subtaskId, HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");
            Project project = (Project) session.getAttribute("project");
            SubtaskService.addEmployeeToSubtask(subtaskId, employeeId, project.getProjectId(), user.getId());
        }

        catch (SQLException e) {
            return ErrorHandler.setCurrentError("Something went wrong adding employee to subtask", session);
        }

        return  "redirect:/view-subtasks";
    }

    @PostMapping("/remove-employee-from-subtask")
    public String removeEmployeeFromSubtask(@RequestParam("employee-id") Integer employeeId, @RequestParam("subtask-id") Integer subtaskId, HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");
            Project project = (Project) session.getAttribute("project");
            SubtaskService.removeEmployeeFromSubtask(subtaskId, employeeId, project.getProjectId(), user.getId());
        }

        catch (SQLException e) {
            return ErrorHandler.setCurrentError("Something went wrong removing employee from subtask", session);
        }

        return  "redirect:/view-subtasks";
    }

    @PostMapping("add-skill-to-subtask")
    public String addSkillToSubtask(@RequestParam("subtask-id") Integer subtaskId, @RequestParam("skill-id") Integer skillId, HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");
            Project project = (Project) session.getAttribute("project");
            SubtaskService.addSkillToSubtask(subtaskId, skillId, project.getProjectId(), user.getId());
        }

        catch (SQLException e) {
            return ErrorHandler.setCurrentError("Something went wrong adding skill to subtask", session);
        }

        return  "redirect:/view-subtasks";
    }

    @PostMapping("/remove-skill-from-subtask")
    public String removeSkillFromSubtask(@RequestParam("subtask-id") Integer subtaskId, @RequestParam("skill-id") Integer skillId, HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");
            Project project = (Project) session.getAttribute("project");
            SubtaskService.removeSkillFromSubtask(subtaskId, skillId, project.getProjectId(), user.getId());
        }

        catch (SQLException e) {
            return ErrorHandler.setCurrentError("Something went wrong removing skill from subtask", session);
        }

        return  "redirect:/view-subtasks";
    }
}
