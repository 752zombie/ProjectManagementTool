package com.example.projecttool.controllers;

import com.example.projecttool.models.user.User;
import com.example.projecttool.models.project.Project;
import com.example.projecttool.util.ErrorHandler;
import com.example.projecttool.services.ShareProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;

@Controller
public class ShareProjectController {

    @GetMapping("share-project")
    public String shareProject() {

        return "share-project/share-with";

    }

    @PostMapping("share-project-with")
    public String shareProject(@RequestParam("receiverMail") String receiverMail, @RequestParam("edit") String editOrRead, HttpSession session) {
        try {
            Project project = (Project) session.getAttribute("project");
            User user = (User) session.getAttribute("user");

            // Checks if user is about to make himself read-only
            boolean shareSecurity = ShareProjectService.shareProject(user.getEmail(), receiverMail, editOrRead, project.getProjectId());

            if (shareSecurity){
                return "share-project/danger-read-only";
            }

        } catch (SQLException s) {
            return ErrorHandler.setCurrentError("Something went wrong adding collaborator", session);
        }
        return "share-project/share-complete";
    }

    @GetMapping("shared-with-me")
    public String sharedWithMe(HttpSession session, Model model) {

        try {
            User user = (User) session.getAttribute("user");

            // Add project to View
            ArrayList<Project> sharedProjects = ShareProjectService.getSharedProjects(user.getId());
            model.addAttribute("projectList", sharedProjects);


        } catch (SQLException s) {
            return ErrorHandler.setCurrentError("Could not get shared projects", session);
        }
        return "share-project/shared-with-me";
    }
}
