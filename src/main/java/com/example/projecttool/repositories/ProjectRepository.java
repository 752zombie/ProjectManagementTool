package com.example.projecttool.repositories;

import com.example.projecttool.services.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProjectRepository {

    public static void editProject(int id, String name, String description) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();


        String command = String.format("UPDATE test_project SET project_name = '%s', project_description = '%s' WHERE id = '%d'", name, description, id);
        PreparedStatement statement = connection.prepareStatement(command);
        statement.execute();

    }


}
