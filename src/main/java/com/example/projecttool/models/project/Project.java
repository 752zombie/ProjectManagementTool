package com.example.projecttool.models.project;

import com.example.projecttool.models.User;
import com.example.projecttool.repositories.TaskRepository;

import java.util.List;
import java.util.Map;

public class Project {
    int projectId;
    String name;
    String startTime;
    String endTime;
    // The user who created the project
    User owner;
    // List of collaborators excluding owner
    Map<Integer, User> collaborators;

    public Project(int projectId, String name, String startTime, String endTime) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.projectId = projectId;
    }

    public List<Task> getTasks() {
        return TaskRepository.getTasks(projectId);
    }

    public int getProjectId() {
        return projectId;
    }

    public void setId(int id) {
        this.projectId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public Map<Integer, User> getCollaborators() {
        return collaborators;
    }
}
