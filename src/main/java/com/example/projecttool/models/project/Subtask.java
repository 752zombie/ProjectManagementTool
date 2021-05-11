package com.example.projecttool.models.project;

import com.example.projecttool.models.Employee;
import com.example.projecttool.models.Skill;

import java.sql.Date;
import java.util.List;

public class Subtask {

    private int id;
    private List<Employee> assignedEmployees;
    private List<Skill> requiredSkills;
    private String description;
    private String name;
    private Date startTime;
    private Date endTime;

    public Subtask(int id, String name, String description, Date startTime, Date endTime, List<Employee> employees, List<Skill> skills) {
        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        assignedEmployees = employees;
        requiredSkills = skills;
    }

    public void addEmployee(Employee employee) {
        assignedEmployees.add(employee);

    }

    public void removeEmployee(int employeeId) {
        assignedEmployees.removeIf(employee -> employee.getId() == employeeId);
    }

    public void addSkill(Skill skill) {
        requiredSkills.add(skill);
    }

    public void removeSkill(String skillName) {
        requiredSkills.removeIf(skill -> skill.getName().equals(skillName));
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Employee> getAssignedEmployees() {
        return assignedEmployees;
    }

    public List<Skill> getRequiredSkills() {
        return requiredSkills;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }
}
