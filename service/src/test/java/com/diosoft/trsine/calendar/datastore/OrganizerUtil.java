package com.diosoft.trsine.calendar.datastore;

import com.diosoft.trsine.calendar.common.Event;
import com.diosoft.trsine.calendar.exceptions.IncorrectPeriodDates;

import java.util.*;

/**
 * Created by i.vartanian on 12.09.2014.
 */
public class OrganizerUtil {

    public Event createEvent(int numberEvents, Date dateBegin, Date dateEnd, String title, String description, List attenders) throws IncorrectPeriodDates {

        Event event = new Event.Builder() {
            @Override
            public Set newSet() {
                return new HashSet<String>();
            }
        }//end of Builder implementation
                .setDateBegin(new Date())
                .setDateEnd(new Date())
                .setTitle("Daily Scrum")
                .setDescription("Next daily scrum meeting")
                .addAttender("alex@zamkovyi.name")
                .addAttender("igor.vartanian@gmail.com")
                .build();

        return event;

    }


    public List createAttenders(int numberAttenders, String attender) throws IncorrectPeriodDates {

        if (numberAttenders <= 0){
            return null;
        }

        ArrayList attenders = new ArrayList();

        for (int i = 0; i <= numberAttenders; i++) {
            attenders.add(String.valueOf(i) + "_" + attender);
        }

        return attenders;

    }

}
