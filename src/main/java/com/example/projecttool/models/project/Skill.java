package com.example.projecttool.models.project;

public class Skill {
    private String name;
    private int id;

    public Skill(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public Skill(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
