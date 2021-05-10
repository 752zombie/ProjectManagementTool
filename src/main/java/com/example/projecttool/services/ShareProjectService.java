package com.example.projecttool.services;

import com.example.projecttool.models.project.Project;
import com.example.projecttool.repositories.ShareProjectRepository;

import java.sql.SQLException;
import java.util.ArrayList;

public class ShareProjectService {

    public void shareProject(String receiverMail, String editOrRead, int projectId) throws SQLException {

        ShareProjectRepository.shareProject(receiverMail, editOrRead, projectId);
    }

    public ArrayList<Project> getSharedProjects(int userId) throws SQLException {

        return ShareProjectRepository.getSharedProjects(userId);
    }
}
