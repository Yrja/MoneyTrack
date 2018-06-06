package com.example.vitalina.moneytrack.ui.utils;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class DateDayUtilsTest {
    @Test
    public void getDaysInterval(){
        List<Long> days = DateDayUtils.getDaysInterval();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,-1);
        assertEquals(days.get(0), DateUtils.getDay(calendar.getTime()));
        assertEquals(days.get(days.size()-1),DateUtils.getDay(new Date()));
    }
}