package com.example.projecttool.controllers;

import com.example.projecttool.models.Employee;
import com.example.projecttool.models.User;
import com.example.projecttool.repositories.SubtaskRepository;
import com.example.projecttool.services.ErrorHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
        }

        catch (SQLException e) {
            return ErrorHandler.setCurrentError("Error retrieving employees", session);
        }

        return "project/employee-manager";
    }
}
