package com.example.projecttool.controllers;

import com.example.projecttool.models.Employee;
import com.example.projecttool.models.Skill;
import com.example.projecttool.models.User;
import com.example.projecttool.repositories.EmployeeRepository;
import com.example.projecttool.repositories.SubtaskRepository;
import com.example.projecttool.util.ErrorHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;

@Controller
public class EmployeeController {

    @GetMapping("employee-manager")
    public String employeeManager(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ErrorHandler.setCurrentError("You need to be logged in to access the employee manager", session);
        }

        try {
            ArrayList<Employee> employees = SubtaskRepository.getAllEmployees(user.getId());
            session.setAttribute("allEmployees", employees);
            ArrayList<Skill> skills = EmployeeRepository.getAllSkills(user.getId());
            session.setAttribute("allSkills", skills);
        }

        catch (SQLException e) {
            return ErrorHandler.setCurrentError("Error retrieving employees", session);
        }

        return "project/employee-manager";
    }

    @PostMapping("change-employee-name")
    public String changeEmployeeName(@RequestParam("emp-id") Integer employeeId, @RequestParam("emp-name") String name, HttpSession session) {
        try {
            EmployeeRepository.changeEmployeeName(employeeId, name);
        }

        catch (SQLException e) {
            return ErrorHandler.setCurrentError("Something went wrong updating employee name", session);
        }

        return "redirect:/employee-manager";
    }

    @PostMapping("/delete-employee")
    public String deleteEmployee(@RequestParam("emp-id") Integer employeeId, HttpSession session) {
        try {
            EmployeeRepository.deleteEmployee(employeeId);
        }

        catch (SQLException e) {
            return ErrorHandler.setCurrentError("Error deleting employee", session);
        }

        return "redirect:/employee-manager";
    }

    @PostMapping("create-new-employee")
    public String createNewEmployee(@RequestParam("emp-name") String employeeName, HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");
            EmployeeRepository.createNewEmployee(employeeName, user.getId());
        }

        catch (SQLException e) {
            return ErrorHandler.setCurrentError("Error creating employee", session);
        }

        return "redirect:/employee-manager";
    }

    @PostMapping("/create-new-skill")
    public String createNewSkill(@RequestParam("skill-name") String skillName, HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");
            EmployeeRepository.createNewSkill(user.getId(), skillName);
        }

        catch (SQLException e) {
            e.printStackTrace();
            return ErrorHandler.setCurrentError("Error creating new skill", session);
        }

        return "redirect:/employee-manager";
    }

    @PostMapping("/delete-skill")
    public String deleteSkill(@RequestParam("skill-to-delete") Integer skillId, HttpSession session) {
        try {
            EmployeeRepository.deleteSkill(skillId);
        }

        catch (SQLException e) {
            return ErrorHandler.setCurrentError("Error deleting skill", session);
        }

        return "redirect:/employee-manager";
    }

    @PostMapping("/add-skill-to-employee")
    public String addSkillToEmployee(@RequestParam("emp-id") Integer employeeId, @RequestParam("skill-id") Integer skillId, HttpSession session) {
        try {
            EmployeeRepository.addSkillToEmployee(employeeId, skillId);
        }

        catch (SQLException e) {
            return ErrorHandler.setCurrentError("Error adding skill to employee", session);
        }

        return "redirect:/employee-manager";
    }

    @PostMapping("remove-skill-from-employee")
    public String removeSkillFromEmployee(@RequestParam("emp-id") Integer employeeId, @RequestParam("skill-id") Integer skillId, HttpSession session) {
        try {
            EmployeeRepository.removeSkillFromEmployee(employeeId, skillId);
        }

        catch (SQLException e) {
            return ErrorHandler.setCurrentError("Error removing skill from employee", session);
        }

        return "redirect:/employee-manager";
    }
}
