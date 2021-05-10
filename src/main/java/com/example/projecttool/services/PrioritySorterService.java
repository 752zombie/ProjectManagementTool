package com.example.projecttool.services;

import com.example.projecttool.models.project.Task;

import java.util.ArrayList;

public class PrioritySorterService {

  public ArrayList<Task> sortTasks(ArrayList<Task> taskList){

      for (Task task : taskList) {
          System.out.println(task.getPriority());
      }


      taskList.sort((m1, m2) -> {
        String p1 = m1.getPriority();
        String p2 = m2.getPriority();

        if(p1 == null) return 1;
        if(p2 == null) return -1;
        if(p1.equals(p2)) return 0;
        if(p1.equals("high") && (p2.equals("medium") || p2.equals("low")))
            return -1;
        if(p1.equals("medium") && p2.equals("low"))
            return -1;
        return 1;
      });
      for (Task task : taskList) {
          System.out.println("edited:" + task.getPriority() + " Name " + task.getName());
      }

    return taskList;
  }




}
