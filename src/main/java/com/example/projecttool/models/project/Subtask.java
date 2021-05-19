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
    private int hoursToComplete;

    public Subtask(int id, String name, String description, Date startTime, Date endTime, List<Employee> employees, List<Skill> skills, int hoursToComplete) {
        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        assignedEmployees = employees;
        requiredSkills = skills;
        this.hoursToComplete = hoursToComplete;
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

    public int getHoursToComplete() {
        return hoursToComplete;
    }

    // do not delete
    public boolean isAssigned(int employeeId) {
        for (Employee employee : assignedEmployees) {
            if (employee.getId() == employeeId) {
                return true;
            }

        }


        return false;
    }

    // do not delete
    public boolean hasSkill(int skillId) {
        for (Skill skill : requiredSkills) {
            if (skill.getId() == skillId) {
                return true;
            }
        }
        return false;
    }
}
