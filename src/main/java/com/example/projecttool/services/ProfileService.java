package com.example.projecttool.services;

import com.example.projecttool.models.UserAttribute;
import com.example.projecttool.repositories.UserRepository;

import java.sql.SQLException;

public class ProfileService {

    public void updateUserInfo(int userId, UserAttribute name, String username) throws SQLException {

        UserRepository.updateUserInfo(userId, name, username);

    }

}
