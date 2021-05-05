package com.example.projecttool.models.project;

import com.example.projecttool.models.Employee;
import com.example.projecttool.models.Skill;
import java.util.ArrayList;
import java.util.List;

public class Subtask {
    private int start;
    private int end;
    private List<Employee> assignedEmployees;
    private List<Skill> requiredSkills;
    private String description;

    public Subtask(int start, int end, String description) {
        this.start = start;
        this.end = end;
        this.description = description;
        assignedEmployees = new ArrayList<>();
        requiredSkills = new ArrayList<>();
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

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
