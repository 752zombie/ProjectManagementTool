package com.example.projecttool.repositories;


import com.example.projecttool.models.project.Project;

import java.sql.*;
import java.util.ArrayList;

public class ShareProjectRepository {


    public static ArrayList<Project> getSharedProjects(int userId) throws SQLException {

        Connection connection = DatabaseConnection.getConnection();

        PreparedStatement statement = connection.prepareStatement("SELECT * \n" +
                "FROM project\n" +
                "INNER JOIN collaborators\n" +
                "ON  project.project_id = collaborators.project_id\n" +
                "WHERE collaborator_id = ?");
        statement.setInt(1, userId);
        return ProjectRepository.getProjects(statement);

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

    //flawed logic. What if the project was shared with multiple users with different access right. In that case the order
    //that elements of the ResultSet came back would determine whether or not a user could edit a shared project
    //TODO: improve (see above)
    public static String getAccessLevel(int projectId, int userId) throws SQLException {

        Connection connection = DatabaseConnection.getConnection();
        String accessLevel = "no-access";

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM edit_read WHERE project_id = ? AND collaborator_id = ?");
        statement.setInt(1, projectId);
        statement.setInt(2, userId);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            accessLevel = resultSet.getString("access_level");
        }

        return accessLevel;

    }

    public static void ignoreProject(Integer projectId, int userId) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();

        PreparedStatement statement = connection.prepareStatement("DELETE FROM collaborators WHERE collaborator_id = ? AND project_id = ?");
        statement.setInt(1, userId);
        statement.setInt(2, projectId);
        statement.execute();

    }
}







