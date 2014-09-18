package com.diosoft.trsine.calendar.service;

import com.diosoft.trsine.calendar.common.Event;
import com.diosoft.trsine.calendar.datastore.DataStore;

import java.rmi.Remote;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Implementation of Calendar service, using data store <code>DataStore</code>.
 *
 * @author  Vasyl Tegza, Alexander Zamkovyi
 * @version 1.0
 * @since 1.0
 */

public class CalendarServiceImpl implements CalendarService {

    /**
     * Data store resource.
     */
    private final DataStore dataStore;

    /**
     * Constructor.
     *
     * @param ds data store to inject
     */
    public CalendarServiceImpl(final DataStore ds) {
        this.dataStore = ds;
    }

    /**
     * Adds <code>Event</code> to calendar service.
     *
     * @param event to be added
     */
    @Override
    public final void add(final Event event) {
        dataStore.add(event);
    }

    /**
     * Adds the <code>Event</code> collection to calendar service.
     *
     * @param events to be added
     */
    @Override
    public final void addAll(final Collection<Event> events) {
        dataStore.addAll(events);
    }

    /**
     * Removes the <code>Event</code> from calendar service.
     *
     * @param id of <code>Event</code>
     */
    @Override
    public final void remove(final UUID id) {
        dataStore.remove(id);
    }

    /** Search for all <code>Event</code> in calendar service
     * by given title.
     *
     * @param title to search by
     * @return the list of <code>Event</code>s
     */
    @Override
    public final List<Event> searchByTitle(final String title) {
        return dataStore.searchByTitle(title);
    }

    /** Search for all <code>Event</code> in calendar service
     * by given description.
     *
     * @param description to search by
     * @return the list of <code>Event</code>s
     */
    @Override
    public final List<Event> searchByDescription(final String description) {
        return dataStore.searchByDescription(description);
    }

    /** Search for all <code>Event</code> in calendar service
     * by given date of begining.
     *
     * @param day date of begining to search by
     * @return the list of <code>Event</code>s
     */
    @Override
    public final List<Event> searchByDay(final Date day) {
        return dataStore.searchByDay(day);
    }

    /** Search for all <code>Event</code> in calendar service
     * by given date of begining interval.
     *
     * @param leftDate date of begining to search by
     * @param rightDate date of begining to search by
     * @return the list of <code>Event</code>s
     */
    @Override
    public final List<Event> searchByInterval(final Date leftDate,
                                              final Date rightDate) {
        return dataStore.searchByInterval(leftDate, rightDate);
    }


}
