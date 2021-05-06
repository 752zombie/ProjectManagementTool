package com.example.projecttool.models.project;

public class ProjectTest {

    int id;
    String project_name;
    String project_description;

    public ProjectTest(int id, String project_name, String project_description) {
        this.id = id;
        this.project_name = project_name;
        this.project_description = project_description;
    }

    public String getProject_name() {
        return project_name;
    }

    public String getProject_description() {
        return project_description;
    }

    public int getId() {
        return id;
    }
}
