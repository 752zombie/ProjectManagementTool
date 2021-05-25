package com.example.projecttool.controllers;

import javax.servlet.http.HttpSession;

public class ErrorHandlerController {
    public static String setCurrentError(String errorText, HttpSession session) {
        session.setAttribute("currentError", errorText);
        return "project/subtask-error-page";
    }
}
