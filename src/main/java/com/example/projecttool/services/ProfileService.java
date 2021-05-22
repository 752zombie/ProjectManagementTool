package com.example.projecttool.services;

import com.example.projecttool.models.UserAttribute;
import com.example.projecttool.repositories.UserRepository;

import java.sql.SQLException;

public class ProfileService {

    public static void updateUserInfo(int userId, UserAttribute attributeToUpdate, String newValue) throws SQLException {

        UserRepository.updateUserInfo(userId, attributeToUpdate, newValue);

    }

    public static void deleteAccount(int userId) throws SQLException{
        UserRepository.deleteUser(userId);
    }

}
