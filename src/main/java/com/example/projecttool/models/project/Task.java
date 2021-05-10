package com.example.projecttool.models.project;

import com.example.projecttool.repositories.TaskRepository;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class Task {

    private int id;
    private String name;
    private String description;
    private String priority;
    private int estimatedHoursPrDay;
    private int estimatedHoursTotal;
    private String start_time;
    private String end_time;


    public Task(int id, String name, String description, String start_time, String end_time, String priority, int estimatedHours, int estimatedHoursPrDay) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.start_time = start_time;
        this.end_time = end_time;
        this.priority = priority;
        this.estimatedHoursTotal = estimatedHours;
        this.estimatedHoursPrDay = estimatedHoursPrDay;

    }

    public int getEstimatedHoursPrDay() {
        return estimatedHoursPrDay;
    }

    public ArrayList<Subtask> getSubtasks() {
        return TaskRepository.getRelatedSubtasks(id);
    }

    public int getEstimatedHoursTotal() { return estimatedHoursTotal; }

    public String getName() {
        return name;
    }

    public String getTask() {
        return name;
    }

    public String getDescription() {
        return description;
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

    public String getPriority() {
        return priority;
    }

}



