package com.diosoft.trsine.calendar.service;

import java.awt.*;
import java.text.ParseException;
import java.util.ArrayList;

//я извиняюсь, но оно не очень хочет работать

public class ForAddEvent {
    static ArrayList<Event> eventList = new ArrayList<Event>();

    enum eventKeys {description, attenders, dateBegin, dateEnd, UID}

        public static void main(String[] args) throws ParseException {
        eventList.add(new Event.Builder()
                                .setUid()
                                .setDescription()
                                .setAttenders()
                                .setDateBegin()
                                .setdateEnd()
                                .build()
                        );
    }
}
