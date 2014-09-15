package com.diosoft.trsine.calendar.util;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class DateHelperTest {

    @Test
    public void testGetOnlyDate() throws Exception {
        Date date = DateHelper.getDateFromSimpleString("2014-08-10 10:40:01");
        Date expectedResult = DateHelper.getDateFromSimpleString("2014-08-10 00:00:00");

        assertEquals(expectedResult, DateHelper.getOnlyDate(date));
    }

    @Test
    public void testDayIncrement() throws Exception {
        Date date = DateHelper.getDateFromSimpleString("2014-08-10 10:40:01");
        Date expectedResult = DateHelper.getDateFromSimpleString("2014-08-11 10:40:01");

        assertEquals(expectedResult, DateHelper.dayIncrement(date));
    }

    @Test
    public void testGetDateFromSimpleString() throws Exception {
        //test am
        Date date = DateHelper.getDateFromSimpleString("2014-08-10 10:40:01");

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(0);
        cal.set(2014, Calendar.AUGUST, 10, 10, 40, 1);
        Date expectedResult = cal.getTime();

        assertEquals(expectedResult, date);

        //test pm
        date = DateHelper.getDateFromSimpleString("2014-09-08 12:20:33");

        cal = Calendar.getInstance();
        cal.setTimeInMillis(0);
        cal.set(2014, Calendar.SEPTEMBER, 8, 12, 20, 33);
        expectedResult = cal.getTime();

        assertEquals(expectedResult, date);
    }
}