package com.example.projecttool.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
@PropertySource("classpath:application.properties")
public class DatabaseConnection {
    private static Connection connection;


    @Value("${user}")
    public void setUserStatic(String value) {
        userStatic = value;
    }

    @Value("${password}")
    public void setPasswordStatic(String value) {
        passwordStatic = value;
    }

    @Value("${url}")
    public void setUrlStatic(String value) {
        urlStatic = value;
    }

    public static String userStatic;

    public static String passwordStatic;

    public static String urlStatic;




    public static Connection getConnection()  {



        try {
            if (connection != null && connection.isValid(10)) {
                return connection;
            }
            connection = DriverManager.getConnection(urlStatic, userStatic, passwordStatic);
        }


        catch (SQLException e) {
            System.out.println(e);
            System.out.println("Error: connection to database could not be established");
        }

        return connection;
    }

}