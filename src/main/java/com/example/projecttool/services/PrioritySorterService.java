package com.example.projecttool.services;

import com.example.projecttool.models.project.Task;

import java.util.ArrayList;

public class PrioritySorterService {

  public static ArrayList<Task> sortTasksByPriority(ArrayList<Task> taskList){

      // Sorts tasks after priority: High, medium, low
      taskList.sort((task1, task2) -> {
        String p1 = task1.getPriority();
        String p2 = task2.getPriority();

        if(p1 == null) return 1;
        if(p2 == null) return -1;
        if(p1.equals(p2)) return 0;
        if(p1.equals("high") && (p2.equals("medium") || p2.equals("low")))
            return -1;
        if(p1.equals("medium") && p2.equals("low"))
            return -1;
        return 1;
      });
      return taskList;
  }

}
