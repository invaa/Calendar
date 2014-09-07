package com.diosoft.trsine.calendar.service;

import com.diosoft.trsine.calendar.common.Event;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface CalendarService {
    void add(Event event);
    void remove(UUID id);
    List<Event> searchByTitle(String title);
    List<Event> searchByDay(Date day);
}
