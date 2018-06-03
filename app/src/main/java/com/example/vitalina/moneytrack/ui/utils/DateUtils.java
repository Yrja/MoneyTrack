package com.example.vitalina.moneytrack.ui.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    public static long getDay(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MILLISECOND,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        return calendar.getTime().getTime();
    }
}
