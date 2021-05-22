package com.example.projecttool.models;

import java.util.List;

public class Employee {
    int id;
    String name;
    List<Skill> skills;
    private int hoursPerDay;

    public Employee(int id, String name, List<Skill> skills, int hoursPerDay) {
        this.id = id;
        this.name = name;
        this.skills = skills;
        this.hoursPerDay = hoursPerDay;
    }

    public Employee(String name, List<Skill> skills) {
        this.name = name;
        this.skills = skills;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public int getHoursPerDay() {
        return hoursPerDay;
    }

    public void setHoursPerDay(int hoursPerDay) {
        this.hoursPerDay = hoursPerDay;
    }

    public boolean hasSkill(int skillId) {
        for (Skill skill : skills) {
            if (skill.getId() == skillId) {
                return true;
            }

        }
        return false;
    }
}
