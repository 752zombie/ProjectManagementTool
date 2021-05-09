package com.example.projecttool.repositories;


import com.example.projecttool.models.User;
import com.example.projecttool.models.project.Project;
import com.example.projecttool.models.project.Subtask;
import com.example.projecttool.services.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;

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

    public static void shareProject(String receiverMail, int projectId) throws SQLException {

        Connection connection = DatabaseConnection.getConnection();

        int receiverId = getReceiverId(receiverMail);

        PreparedStatement statement = connection.prepareStatement("INSERT INTO collaborators (project_id, collaborator_id) VALUES (?, ?)");
        statement.setInt(1, projectId);
        statement.setInt(2, receiverId);

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


}






