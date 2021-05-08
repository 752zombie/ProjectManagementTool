package com.example.projecttool.controllers;

import com.example.projecttool.models.User;
import com.example.projecttool.models.project.Project;
import com.example.projecttool.repositories.ProjectRepository;
import com.example.projecttool.repositories.ShareProjectRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
public class ShareProjectController {

/*
    @PostMapping("share-project")
    public String shareRequest(@RequestParam("receiverName") String receiverName, HttpSession session){


        User user = (User) session.getAttribute("user");
        ArrayList<Project> projectItemsShared = ProjectRepository.getProject(user.getId());

        ShareProjectRepository.shareProject(receiverName, projectItemsShared, user.getId());


        return "shareCompleted";

    }

 */
}
