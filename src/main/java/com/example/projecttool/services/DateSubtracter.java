package com.example.projecttool.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DateSubtracter {

    public long subtractDates(Date firstDate, Date secondDate) {

        try {

            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);

            firstDate = sdf.parse("06/24/2017");
            secondDate = sdf.parse("06/30/2017");

            long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
            long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

            System.out.println(diff);

            return diff;


        } catch (ParseException parseException) {
            System.out.println("something went wrong parsing date");
        }
             return 0;
    }

}
