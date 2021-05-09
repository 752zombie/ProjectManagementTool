package com.example.projecttool.repositories;


import com.example.projecttool.models.User;
import com.example.projecttool.models.project.Project;
import com.example.projecttool.services.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class ShareProjectRepository {


/*
    public static ArrayList<Project> getSharedProjects(int id) {
    }

 */



    public static void shareProject(String receiverMail, int projectId) {

        Connection connection = DatabaseConnection.getConnection();

        int receiverId = getReceiverId(receiverMail);

        try {

            PreparedStatement statement = connection.prepareStatement("INSERT INTO collaboratorTEST (project_id, collaborator_id) VALUES (?, ?)");
            statement.setInt(1, projectId);
            statement.setInt(2, receiverId);

            statement.execute();
        }catch (SQLException s){

            System.out.println("something went adding collaborator");
        }

    }

    private static int getReceiverId(String receiverMail) {

       Connection connection = DatabaseConnection.getConnection();

        try {

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE email = ?");
            statement.setString(1, receiverMail);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("user_id");
            }

        } catch (SQLException e) {

            System.out.println("Something went wrong getting receiver id");
        }

        throw new NoSuchElementException();

    }


}






