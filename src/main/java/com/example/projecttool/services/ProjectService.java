package com.example.projecttool.services;

import com.example.projecttool.models.project.Project;
import com.example.projecttool.repositories.ProjectRepository;
import com.example.projecttool.repositories.ShareProjectRepository;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProjectService {


    public static Project nameYourProject(int userId, String projectName, String projectStart, String projectEnd) throws SQLException {


        // Creates a project
        int project_id = ProjectRepository.createProject(userId, projectName, projectStart, projectEnd);

        // Saves project in session and adds to view


        return new Project(project_id, projectName, projectStart, projectEnd);
    }

    public static ArrayList<Project> seeProjectList(int userId) throws SQLException {


        return ProjectRepository.getProjects(userId);
    }


    public static Project getProject(int projectId) throws SQLException {

        return ProjectRepository.getProject(projectId);
    }

    public static boolean isReadOnly(int projectId, int userId) throws SQLException {
        String accessLevel = ShareProjectRepository.getAccessLevel(projectId, userId);
        return accessLevel.equals("read-only");

    }

    public static void deleteProject(Integer projectId) throws SQLException {

            ProjectRepository.deleteProject(projectId);
    }

    public static boolean isOwner(int projectId, int userId) throws SQLException{
        int ownerId = ProjectRepository.getOwnerId(projectId, userId);
        return ownerId == userId;
    }

    public static String getAccessLevel(int projectId, int userId) throws SQLException{
        return ShareProjectRepository.getAccessLevel(projectId, userId);
    }

    public static boolean hasAccess(int projectId, int userId) throws SQLException{
        String accessLevel = ShareProjectRepository.getAccessLevel(projectId, userId);
        boolean isCollaborator = accessLevel.equals("read-and-edit") || accessLevel.equals("read-only");
        boolean isOwner = isOwner(projectId, userId);
        return isOwner || isCollaborator;
    }

    public static boolean canEdit(int projectId, int userId) throws SQLException{
        return hasAccess(projectId, userId) && !isReadOnly(projectId, userId);
    }

}

