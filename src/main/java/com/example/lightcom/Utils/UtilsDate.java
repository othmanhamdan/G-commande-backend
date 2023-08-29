package com.example.lightcom.Utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class UtilsDate {
    public static Date incDateByDays(Date date, int pas){

        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH)+pas);
        return cal.getTime();
    }
    public static int getYear(Date date ){
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }
    public static Date max(Date date1, Date date2){
        if (date1 == null)
            return date2;

        if (date2 == null)
            return date1;

        if (date1.after(date2))
            return date1;
        return date2;
    }
}
