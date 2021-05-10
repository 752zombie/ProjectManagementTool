package com.example.projecttool.services;

import com.example.projecttool.models.project.Subtask;
import com.example.projecttool.repositories.TaskRepository;

import java.util.ArrayList;

public class SubtaskService {
    public ArrayList<Subtask> getSubtasks(int taskId) {
        return TaskRepository.getRelatedSubtasks(taskId);
    }
}
