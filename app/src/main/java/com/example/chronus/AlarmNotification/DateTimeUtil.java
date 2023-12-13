package com.example.chronus.AlarmNotification;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateTimeUtil {
    public static String getNLaterDateTimeString(int nType, int n) {
        Date date = new Date();
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        c.add(nType, n);

        return CalendarToString(c);
    }


    public static long stringToMillis(String dateTime) {
        Calendar c = StringToGregorianCalendar(dateTime);

        return c.getTimeInMillis();
    }



    public static Date StringToDate(String dateTimeStr) {
        Date date = new Date();

        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date = fmt.parse(dateTimeStr);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String DateToString(Date date) {
        String dateTimeStr = null;
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateTimeStr = fmt.format(date);
        return dateTimeStr;
    }

    public static Calendar StringToGregorianCalendar(String dateTimeStr) {
        Date date = StringToDate(dateTimeStr);
        Calendar calendar = new GregorianCalendar();

        calendar.setTime(date);
        return calendar;
    }

    public static String CalendarToString(Calendar calendar) {
        Date date = ((GregorianCalendar) calendar).getTime();
        return DateToString(date);
    }


}