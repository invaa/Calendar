package com.diosoft.trsine.calendar.client;

import com.diosoft.trsine.calendar.common.Event;
import com.diosoft.trsine.calendar.exceptions.IncorrectPeriodDates;

import java.util.*;

/**
 * Created by i.vartanian on 12.09.2014.
 */
public class OrganizerUtil {

    public Event createEvent(int prefix, Date dateBegin, Date dateEnd, String title, String description, Set<String> attenders) throws IncorrectPeriodDates {

        Event event = new Event.Builder()
                .setDateBegin(dateBegin)
                .setDateEnd(dateEnd)
                .setTitle(String.valueOf(prefix) + "_" + title)
                .setDescription(String.valueOf(prefix) + "_" + description)
                .setId(UUID.randomUUID())
                .setAttenders(attenders)
                .build();

        return event;

    }


    public Set<String> createAttenders(int numberAttenders, String attender) {

        if (numberAttenders <= 0){
            return null;
        }

        HashSet<String> attenders = new HashSet<>();

        for (int i = 0; i <= numberAttenders; i++) {
            attenders.add(String.valueOf(i) + "_" + attender);
        }

        return attenders;

    }

    public Date createDate(int year, int month, int day, int hours, int minutes, int seconds){
        return new GregorianCalendar(year, month, day, hours, minutes, seconds).getTime();
    }

    public Date createDate(int year, int month, int day){
        return createDate(year, month, day, 0, 0, 0);
    }

}
