package com.example.projecttool.repositories;


import com.example.projecttool.models.User;
import com.example.projecttool.models.UserAttribute;
import com.example.projecttool.services.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;

public class UserRepository {

    public static void addUser(String name, String email, String password) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();


            String command = String.format("INSERT INTO users (user_name, email, user_password) VALUES ('%s', '%s', MD5('%s'))", name, email, password);
            PreparedStatement statement = connection.prepareStatement(command);
            statement.execute();


    }


    public static void removeUser(int userid) {
        Connection connection = DatabaseConnection.getConnection();

        try {
            //Should user projects på deleted?
            // ProjectRepository.deleteWishlist(userid);

            String command = String.format("DELETE FROM users WHERE user_id = %d", userid);
            PreparedStatement statement = connection.prepareStatement(command);
            statement.execute();
        } catch (SQLException e) {
            System.out.println("Error deleting user");
        }
    }

    public static void removeUser(User user) {
        removeUser(user.getId());
    }

    public static void updateUserInfo(int userId, UserAttribute attributeToUpdate, String newValueOfAttribute) {
        Connection connection = DatabaseConnection.getConnection();

        try {
            String command;
            String column = userAttributeToColumn(attributeToUpdate);

            if (column.equals("user_password")) {
                command = String.format("UPDATE users SET user_password = MD5('%s') WHERE user_id = %d", newValueOfAttribute, userId);
            } else {
                command = String.format("UPDATE users SET %s = '%s' WHERE user_id = %d", column, newValueOfAttribute, userId);
            }
            PreparedStatement statement = connection.prepareStatement(command);
            statement.execute();
        } catch (SQLException e) {
            System.out.println("Error updating user info");
        }
    }

    public static User attemptLogin(String email, String password) {
        Connection connection = DatabaseConnection.getConnection();


        try {
            String command = String.format("SELECT * FROM users WHERE email = '%s' AND user_password = MD5('%s')", email, password);
            PreparedStatement statement = connection.prepareStatement(command);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("user_id");
                String name = resultSet.getString("user_name");
                String userEmail = resultSet.getString("email");
                String userPassword = resultSet.getString("user_password");
                User user = new User(name, userEmail, userPassword);
                user.setId(id);

                return user;
            }

        } catch (SQLException e) {

            System.out.println("Something went wrong");
        }

        throw new NoSuchElementException();

    }


    private static String userAttributeToColumn(UserAttribute attribute) {
        switch (attribute) {
            case name:
                return "user_name";
            case email:
                return "email";
            case password:
                return "user_password";
            default:
                return "invalid";
        }
    }


}
