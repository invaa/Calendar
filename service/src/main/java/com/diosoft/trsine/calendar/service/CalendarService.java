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
    /**
     * Adds <code>Event</code> to calendar service
     * @param event to be added
     */
    void add(Event event);
    /**
     * Adds the <code>Event</code> collection to calendar service
     * @param events to be added
     */
    void addAll(Collection<Event> events);
    /**
     * Removes the <code>Event</code> from calendar service
     * @param id of <code>Event</code>
     */
    void remove(UUID id);
    /** Search for all <code>Event</code> in calendar service by given description
     *
     * @param description to search by
     * @return the list of <code>Event</code>s
     */
    List<Event> searchByDescription(String description);
    /** Search for all <code>Event</code> in calendar service by given title
     *
     * @param title to search by
     * @return the list of <code>Event</code>s
     */
    List<Event> searchByTitle(String title);
    /** Search for all <code>Event</code> in calendar service by given date of begining
     *
     * @param day date of begining to search by
     * @return the list of <code>Event</code>s
     */
    List<Event> searchByDay(Date day);
}
