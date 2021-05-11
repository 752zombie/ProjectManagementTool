package com.example.projecttool.services;

import com.example.projecttool.models.project.Project;
import com.example.projecttool.repositories.ShareProjectRepository;

import java.sql.SQLException;
import java.util.ArrayList;

public class ShareProjectService {

    public boolean shareProject(String userEmail, String receiverMail, String editOrRead, int projectId) throws SQLException {


        if (userEmail.equals(receiverMail) && editOrRead.equals("read-only")){
            return true;
        }
            ShareProjectRepository.shareProject(receiverMail, editOrRead, projectId);
        return false;

    }

    public ArrayList<Project> getSharedProjects(int userId) throws SQLException {

        return ShareProjectRepository.getSharedProjects(userId);
    }
}
