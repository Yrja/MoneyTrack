package com.example.vitalina.moneytrack.ui.utils;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class DateUtilsTest {

    @Test
    public void getDay() {
        assertEquals(DateUtils.getDay(new Date()),DateUtils.getDay(Calendar.getInstance().getTime()));
    }
}