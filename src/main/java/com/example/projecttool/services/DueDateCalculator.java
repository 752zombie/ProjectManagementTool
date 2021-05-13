package com.example.projecttool.services;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;

public class DueDateCalculator {

    public String[] dueDate(int hoursDay, int hoursTotal, String startDate, String countWeekends) {

        String[] endTimeCalcAndWeekChoice = new String[2];


        int daysToFinish = (hoursTotal / hoursDay);

        // IMPROVED VERSION WITH AMOUNT OF EMPLOYEES //
               //  int totalWorkHoursDay = hoursDay * employees;
               // int daysToFinish = hoursTotal / totalWorkHoursDay;

    if (countWeekends.equals("weekend-true")) {

        String dueDate = dateAdderWithWeekends(daysToFinish, startDate);

        endTimeCalcAndWeekChoice[0] = dueDate;

    }
    else {
        LocalDate str = LocalDate.parse(startDate);

        LocalDate withoutWeekends = addDaysSkippingWeekends(str, daysToFinish);

        String dateWithoutWeekends = withoutWeekends.toString();

        endTimeCalcAndWeekChoice[0] = dateWithoutWeekends;


    }
        endTimeCalcAndWeekChoice[1] = countWeekends;
        return endTimeCalcAndWeekChoice;

    }

    public String dateAdderWithWeekends(int daysToFinish, String startDate) {

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


    public static LocalDate addDaysSkippingWeekends(LocalDate date, int days) {
        LocalDate result = date;
        int addedDays = 0;
        while (addedDays < days) {
            result = result.plusDays(1);
            if (!(result.getDayOfWeek() == DayOfWeek.SATURDAY || result.getDayOfWeek() == DayOfWeek.SUNDAY)) {
                ++addedDays;
            }
        }
        return result;
    }


}
