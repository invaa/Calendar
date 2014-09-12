package com.diosoft.trsine.calendar.datastore;

import com.diosoft.trsine.calendar.common.Event;
import com.diosoft.trsine.calendar.exceptions.IncorrectPeriodDates;

import java.util.*;

/**
 * Created by i.vartanian on 12.09.2014.
 */
public class OrganizerUtil {

    public Event createEvent(int prefix, Date dateBegin, Date dateEnd, String title, String description, Set<String> attenders) throws IncorrectPeriodDates {

        Event event = new Event.Builder() {
            @Override
            public Set newSet() {
                return new HashSet<String>();
            }
        }//end of Builder implementation
                .setDateBegin(dateBegin)
                .setDateEnd(dateEnd)
                .setTitle(String.valueOf(prefix) + "_" + title)
                .setDescription(String.valueOf(prefix) + "_" + description)
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

}
