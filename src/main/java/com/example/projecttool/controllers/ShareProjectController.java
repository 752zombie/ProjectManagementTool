package com.example.projecttool.controllers;

import com.example.projecttool.models.User;
import com.example.projecttool.models.project.Project;
import com.example.projecttool.repositories.ProjectRepository;
import com.example.projecttool.repositories.ShareProjectRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
public class ShareProjectController {




    @GetMapping("share-project")
    public String shareProject(){

        return "share-project/share-with";

    }

    @PostMapping("share-project-with")
    public String shareProject(@RequestParam("receiverMail") String receiverMail, HttpSession session){

      Project project = (Project) session.getAttribute("project");
        ShareProjectRepository.shareProject(receiverMail, project.getProjectId());


        return "share-project/share-complete";

    }

    @GetMapping("shared-with-me")
    public String sharedWithMe(HttpSession session, Model model) {

        User user = (User) session.getAttribute("user");

        // Add project to View
        ArrayList<Project> sharedProjects = ShareProjectRepository.getSharedProjects(user.getId());
        model.addAttribute("projectList", sharedProjects);

        return "share-project/shared-with-me";
    }

}
