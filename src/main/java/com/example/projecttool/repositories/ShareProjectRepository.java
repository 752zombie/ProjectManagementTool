package com.example.projecttool.repositories;


import com.example.projecttool.models.project.Project;
import com.example.projecttool.services.DatabaseConnection;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;

public class ShareProjectRepository {


    public static ArrayList<Project> getSharedProjects(int userId) throws SQLException {

        Connection connection = DatabaseConnection.getConnection();
        ArrayList<Project> sharedProjects = new ArrayList<>();

        PreparedStatement statement = connection.prepareStatement("SELECT * \n" +
                "FROM project\n" +
                "INNER JOIN collaborators\n" +
                "ON  project.project_id = collaborators.project_id\n" +
                "WHERE collaborator_id = ?");
        statement.setInt(1, userId);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int project_id = resultSet.getInt("project_id");
            String project_name = resultSet.getString("name");
            String start_time = resultSet.getString("start_time");
            String end_time = resultSet.getString("end_time");

            sharedProjects.add(new Project(project_id, project_name, start_time, end_time));
        }
        return sharedProjects;
    }

    public static void shareProject(String receiverMail, String editOrRead, int projectId) throws SQLException {

        Connection connection = DatabaseConnection.getConnection();

        int receiverId = getReceiverId(receiverMail);

        editOrReadAccess(editOrRead, receiverId, projectId);

        PreparedStatement statement = connection.prepareStatement("INSERT INTO collaborators (project_id, collaborator_id) VALUES (?, ?)");
        statement.setInt(1, projectId);
        statement.setInt(2, receiverId);

        statement.execute();
    }

    private static void editOrReadAccess(String editOrRead, int receiverId, int projectId) throws SQLException {

        Connection connection = DatabaseConnection.getConnection();

        PreparedStatement statement = connection.prepareStatement("INSERT INTO edit_read (access_level, collaborator_id, project_id) VALUES (?, ?, ?)");
        statement.setString(1, editOrRead);
        statement.setInt(2, receiverId);
        statement.setInt(3, projectId);
        statement.execute();

    }


    private static int getReceiverId(String receiverMail) throws SQLException {

        Connection connection = DatabaseConnection.getConnection();

        int receiverId = 0;


        PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE email = ?");
        statement.setString(1, receiverMail);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getInt("user_id");
        }

        return receiverId;

    }


    public static boolean isReadOnly(int projectId, int userId) throws SQLException {

        Connection connection = DatabaseConnection.getConnection();
        String isReadOnly = "";

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM edit_read WHERE project_id = ?");
        statement.setInt(1, projectId);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            isReadOnly = resultSet.getString("access_level");
            return isReadOnly.equals("read-only");
        }
        return false;

    }

}







