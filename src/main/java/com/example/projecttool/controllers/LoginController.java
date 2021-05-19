package com.example.projecttool.controllers;


import com.example.projecttool.models.User;
import com.example.projecttool.services.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;

@Controller
public class LoginController {

    @GetMapping("/sign-out")
    public String signOut(HttpSession session) {

        session.invalidate();
        return "redirect:/";
    }


    @GetMapping("/sign-in")
    public String signIn() {
        return "login/login";
    }

    @PostMapping("/sign-up")
    public String login(@RequestParam("name") String name, @RequestParam("email") String eMail,
                        @RequestParam("password") String password, HttpSession session) {

        try {
            //User created successfully and should now be logged in
            User user = LoginService.attemptLogin(name, eMail, password);
            session.setAttribute("user", user);

        } catch (SQLException e) {
            System.out.println("Error log in");
            return "login/signup-failed";
        }

        return "index";
    }

    @PostMapping("sign-in")
    public String signIn(@RequestParam("email") String email, @RequestParam("password") String password, HttpSession session) {



        try {
            User user = LoginService.attemptLogin(email, password);
            session.setAttribute("user", user);

            return "redirect:/";
        }

        catch (SQLException s) {
            System.out.println("incorrect email or password");
            return "login/login-failed";
        }
    }

    @GetMapping("/sign-up")
    public String signUp() {
        return "login/signup";
    }

}
