package com.example.projecttool.util;

import javax.servlet.http.HttpSession;

public class ErrorHandler {
    public static String setCurrentError(String errorText, HttpSession session) {
        session.setAttribute("currentError", errorText);
        return "project/subtask-error-page";
    }
}
