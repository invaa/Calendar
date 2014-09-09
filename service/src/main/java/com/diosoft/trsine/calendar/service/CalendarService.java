package com.diosoft.trsine.calendar.service;

import com.diosoft.trsine.calendar.common.Event;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Contract of Calendar service
 *
 * @author  Vasyl Tegza
 * @since 1.5
 */

public interface CalendarService {
    void add(Event event);
    void addAll(Collection<Event> events);
    void remove(UUID id);
    List<Event> searchByTitle(String title);
    List<Event> searchByDescription(String description);
    List<Event> searchByDay(Date day);
}
