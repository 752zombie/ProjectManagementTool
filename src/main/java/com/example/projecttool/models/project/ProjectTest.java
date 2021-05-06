package com.example.projecttool.models.project;

public class ProjectTest {

    int id;
    String project_name;
    String project_description;
    String start_time;
    String end_time;

    public ProjectTest(int id, String project_name, String project_description, String start_time, String end_time) {
        this.id = id;
        this.project_name = project_name;
        this.project_description = project_description;
        this.start_time = start_time;
        this.end_time = end_time;
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

    public String getStart_time() {
        return start_time;
    }

    public String getEnd_time() {
        return end_time;
    }
}
