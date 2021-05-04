package com.example.projecttool.models.project;

import com.example.projecttool.models.User;

import java.util.Map;

public class Project {
    int id;
    String name;
    // The user who created the project
    User owner;

    // List of collaborators excluding owner
    Map<Integer, User> collaborators;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Map<Integer, User> getCollaborators() {
        return collaborators;
    }
}
