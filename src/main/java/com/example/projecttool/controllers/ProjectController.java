package com.example.projecttool.controllers;

import com.example.projecttool.models.User;
import com.example.projecttool.models.project.Project;
import com.example.projecttool.models.project.Task;
import com.example.projecttool.util.ErrorHandler;
import com.example.projecttool.services.ProjectService;
import com.example.projecttool.services.ShareProjectService;
import com.example.projecttool.services.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;

@Controller
public class ProjectController {


    @GetMapping("/create-project")
    public String createProject() {

        return "project/create-project";
    }

    @PostMapping("start-new-project")
    public String nameYourProject(@RequestParam("project_name") String projectName, @RequestParam("project_start") String projectStart,
                                  @RequestParam("project_end") String projectEnd, HttpSession session) {


        try {
            User user = (User) session.getAttribute("user");

            // Creates a project
           Project project = ProjectService.nameYourProject(user.getId(), projectName, projectStart, projectEnd);

           session.setAttribute("project", project);

        }

        catch (SQLException e) { ;
            return ErrorHandler.setCurrentError("Something went wrong editing project", session);
        }

        return "redirect:task-list";
    }

        @GetMapping("/see-all-projects")
    public String seeProjectList(HttpSession session, Model model) {

            try {
                User user = (User) session.getAttribute("user");
                ArrayList<Project> projectList = ProjectService.seeProjectList(user.getId());
                // WHY DOES THIS ONLY WORK WITH Model
                model.addAttribute("projectList", projectList);

                return "project/all-projects";

            } catch (SQLException e) {
                return ErrorHandler.setCurrentError("Something went wrong retrieving project list", session);
            }
        }


    @PostMapping("choose-project-to-edit")
    public String editProject(@RequestParam("id") Integer projectId, @RequestParam("action") String action, HttpSession session, Model model) {

        try {
            User user = (User) session.getAttribute("user");

            //check if user is allowed to access project
            if (!ProjectService.hasAccess(projectId, user.getId())) {
                return ErrorHandler.setCurrentError("You do not have access to that project", session);
            }
            // Add current project to session
            Project project = ProjectService.getProject(projectId);
            session.setAttribute("project", project);


            // Deletes row from project
            if (action.equals("Delete")) {
                ProjectService.deleteProject(projectId);
                // WHY DOES THIS ONLY WORK WITH Model
                ArrayList<Project> projectList = ProjectService.seeProjectList(user.getId());
                model.addAttribute("projectList", projectList);


                return "redirect:/see-all-projects";

            }
            // Ignores shared project
            else if (action.equals("Ignore")){
                ShareProjectService.ignoreProject(projectId, user.getId());
                // WHY DOES THIS ONLY WORK WITH Model
                ArrayList<Project> sharedProjects = ShareProjectService.getSharedProjects(user.getId());
                model.addAttribute("projectList", sharedProjects);

                return "redirect:/shared-with-me";

            }


        } catch (SQLException s) {
            return ErrorHandler.setCurrentError("Something went wrong editing project", session);
        }

        return "redirect:/task-list";

    }



}
