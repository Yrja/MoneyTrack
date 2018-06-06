package com.example.vitalina.moneytrack.ui.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateDayUtils {

    public static List<Long> getDaysInterval(){
        List<Long> days = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,-1);

        while (!android.text.format.DateUtils.isToday(calendar.getTime().getTime())){
            days.add(DateUtils.getDay(calendar.getTime()));
            calendar.add(Calendar.DATE,1);
        }
        days.add(DateUtils.getDay(calendar.getTime()));
        return days;
    }
}
