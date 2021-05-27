package com.example.projecttool.services;

import com.example.projecttool.models.project.Project;
import com.example.projecttool.models.project.Task;
import com.example.projecttool.repositories.ProjectRepository;
import com.example.projecttool.repositories.ShareProjectRepository;
import com.example.projecttool.repositories.TaskRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProjectService {

    private final Map<Integer, Project> loadedProjects;
    private static ProjectService projectService;

    private ProjectService() {
        loadedProjects = new HashMap<>();
    }

    public static ProjectService getInstance() {
        if (projectService == null) {
            projectService = new ProjectService();
        }

        return projectService;
    }

    public boolean loadProjectWithTasks(int projectId) {
        if (!loadedProjects.containsKey(projectId)) {
            try {
                Project project = ProjectRepository.getProject(projectId);
                HashMap<Integer, Task> tasks = TaskRepository.getTasks(projectId);
                project.setTasks(tasks);
                System.out.println(project.getTasks());
                project.setCurrentlyAccessing(project.getCurrentlyAccessing() + 1);
                loadedProjects.put(projectId, project);
            }

            catch (Exception e) {
                return false;
            }
        }

        return true;
    }

    public Project getProject(int projectId) {
        return loadedProjects.get(projectId);
    }

    public Task getTask(int projectId, int taskId) {
        return loadedProjects.get(projectId).getTasks().get(taskId);
    }

    public void deleteTask(int projectId, int taskId) {
        loadedProjects.get(projectId).getTasks().remove(taskId);
    }

    public Map<Integer, Task> getTaskMap(int projectId) {
        System.out.println(loadedProjects.get(projectId));
        return loadedProjects.get(projectId).getTasks();
    }



    //used for memory cleanup
    //should be called when a user stops accessing a certain project or the client disconnects from the server(session is no longer valid)
    //does not delete a project from the database
    public void removeProject(int projectId) {
        if (loadedProjects.containsKey(projectId)) {
            Project project = loadedProjects.get(projectId);
            project.setCurrentlyAccessing(project.getCurrentlyAccessing() - 1);

            if (project.getCurrentlyAccessing() < 1) {
                loadedProjects.remove(projectId);
            }
        }
    }



    public static Project nameYourProject(int userId, String projectName, String projectStart, String projectEnd) throws SQLException {


        // Creates a project
        int project_id = ProjectRepository.createProject(userId, projectName, projectStart, projectEnd);

        // Saves project in session and adds to view


        return new Project(project_id, projectName, projectStart, projectEnd);
    }

    public static ArrayList<Project> seeProjectList(int userId) throws SQLException {


        return ProjectRepository.getProjects(userId);
    }


    public static boolean isReadOnly(int projectId, int userId) throws SQLException {
        String accessLevel = ShareProjectRepository.getAccessLevel(projectId, userId);
        return accessLevel.equals("read-only");

    }

    public static void deleteProject(Integer projectId) throws SQLException {
            ProjectService.getInstance().removeProject(projectId);
            ProjectRepository.deleteProject(projectId);
    }

    public static boolean isOwner(int projectId, int userId) throws SQLException{
        int ownerId = ProjectRepository.getOwnerId(projectId);
        return ownerId == userId;
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

