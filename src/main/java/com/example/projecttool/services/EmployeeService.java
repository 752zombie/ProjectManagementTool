package com.example.projecttool.services;

import com.example.projecttool.models.Employee;
import com.example.projecttool.models.Skill;
import com.example.projecttool.repositories.EmployeeRepository;
import com.example.projecttool.repositories.SubtaskRepository;

import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeService {
    public static ArrayList<Employee> getAllEmployees(int userId) throws SQLException {
        return SubtaskRepository.getAllEmployees(userId);
    }

    public static ArrayList<Skill> getAllSkills(int userId) throws SQLException{
        return EmployeeRepository.getAllSkills(userId);
    }

    public static void changeEmployeeName(int employeeId, String name) throws SQLException{
        EmployeeRepository.changeEmployeeName(employeeId, name);
    }

    public static void deleteEmployee(int employeeId) throws SQLException{
        EmployeeRepository.deleteEmployee(employeeId);
    }

    public static void createNewEmployee(String name, int userId) throws SQLException{
        EmployeeRepository.createNewEmployee(name, userId, 0);
    }

    public static void createNewSkill(int userId, String skillName) throws SQLException{
        EmployeeRepository.createNewSkill(userId, skillName);
    }

    public static void deleteSkill(int skillId) throws SQLException{
        EmployeeRepository.deleteSkill(skillId);
    }

    public static void addSkillToEmployee(int employeeId, int skillId) throws SQLException{
        EmployeeRepository.addSkillToEmployee(employeeId, skillId);
    }

    public static void removeSkillFromEmployee(int employeeId, int skillId) throws SQLException{
        EmployeeRepository.removeSkillFromEmployee(employeeId, skillId);
    }
}
