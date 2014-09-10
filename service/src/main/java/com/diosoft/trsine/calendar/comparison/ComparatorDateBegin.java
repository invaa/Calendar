package com.diosoft.trsine.calendar.comparison;

import com.diosoft.trsine.calendar.common.Event;

import java.util.Comparator;
import java.util.Date;

/**
 * Created by i.vartanian on 10.09.2014.
 */
public class ComparatorDateBegin implements Comparator<Event> {

    @Override
    public int compare(Event o1, Event o2) {

        if (o1 == null | o2 == null) {
            throw new IllegalArgumentException("The compare event can't be equal Null");
        }

        Date o1DateBegin = o1.getDateBegin();
        Date o2DateBegin = o2.getDateBegin();

        if (o1DateBegin == o2DateBegin){
            return 0;
        }

        if (o1DateBegin == null | o2DateBegin == null) {
            throw new NullPointerException("Date begin can not be equal to Null");
        }

        return o1DateBegin.compareTo(o2DateBegin);

    }

}
