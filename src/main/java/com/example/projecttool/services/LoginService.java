package com.example.projecttool.services;

import com.example.projecttool.models.user.User;
import com.example.projecttool.repositories.UserRepository;

import java.sql.SQLException;

public class LoginService {

    public static User attemptLogin(String name, String eMail, String password) throws SQLException {

        UserRepository.addUser(name, eMail, password);
        return UserRepository.attemptLogin(eMail, password);
    }

    public static User attemptLogin(String email, String password) throws SQLException {


        User user = UserRepository.attemptLogin(email, password);
        if (user == null){
            System.out.println("user login failed");
         throw new SQLException();
        }
        return user;

    }

}
