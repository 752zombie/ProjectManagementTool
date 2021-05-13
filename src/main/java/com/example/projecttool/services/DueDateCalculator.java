package com.example.projecttool.services;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DueDateCalculator {

    public String dueDate(int hoursDay, int hoursTotal, String startDate) {

        String dueDate;
        int daysToFinish;

        // IMPROVED VERSION WITH AMOUNT OF EMPLOYEES //
               //  int totalWorkHoursDay = hoursDay * employees;
               // int daysToFinish = hoursTotal / totalWorkHoursDay;


        System.out.println("hoursday: " + hoursDay + "\n hoursTotal " + hoursTotal);


        daysToFinish = (hoursTotal/hoursDay);


        dueDate = dateAdder(daysToFinish, startDate);


        return dueDate;

    }

    public String dateAdder(int daysToFinish, String startDate) {

        //Specifying date format that matches the given date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            //Setting the date to the given date
            c.setTime(sdf.parse(startDate));
        } catch (
                ParseException e) {
            e.printStackTrace();
        }

        //Number of Days to add
        c.add(Calendar.DAY_OF_MONTH, daysToFinish);
        //Date after adding the days to the given date
        String dueDate = sdf.format(c.getTime());


        return dueDate;

    }


}
