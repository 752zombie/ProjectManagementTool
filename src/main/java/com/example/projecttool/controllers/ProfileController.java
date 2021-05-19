package com.example.projecttool.controllers;

import com.example.projecttool.models.User;
import com.example.projecttool.models.UserAttribute;
import com.example.projecttool.services.LoginService;
import com.example.projecttool.services.ProfileService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.NoSuchElementException;

@Controller
public class ProfileController {

    @GetMapping("/profile")
    public String profilePage() {
        return "profile/profile";
    }

    @GetMapping("/change-user-name")
    public String usernameChangeForm() {
        return "profile/username-form";
    }

    @PostMapping("/change-user-name")
    public String changeUserName(@RequestParam("name") String username, HttpSession session) {

        try {
            User user = (User) session.getAttribute("user");
            ProfileService.updateUserInfo(user.getId(), UserAttribute.name, username);

            return "profile/success";

        } catch (SQLException e) {
            System.out.println("Error updating user info");
            return "profile/failed-changing-user-info";
        }
    }

    @GetMapping("/change-email")
    public String emailChangeForm() {
        return "profile/email-form";
    }

    @PostMapping("/change-email")
    public String changeEmail(@RequestParam("email") String email, HttpSession session) {

        try {
            User user = (User) session.getAttribute("user");
            ProfileService.updateUserInfo(user.getId(), UserAttribute.email, email);
            return "profile/success";

        } catch (SQLException e) {
            System.out.println("Error updating user info");
            return "profile/failed-changing-user-info";
        }
    }

    @GetMapping("/change-password")
    public String passwordChangeForm() {
        return "profile/password-form";
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam("current-password") String currentPassword,
                                 @RequestParam("new-password") String newPassword, HttpSession session) {

        try {
            User user = (User) session.getAttribute("user");

            try {
                LoginService.attemptLogin(user.getEmail(), currentPassword);
            } catch (NoSuchElementException e) {
                return "profile/password-form";
            }

            ProfileService.updateUserInfo(user.getId(), UserAttribute.password, newPassword);
            return "profile/success";

        } catch (SQLException e) {
            System.out.println("Error updating user info");
            return "profile/failed-changing-user-info";
        }

    }
}

