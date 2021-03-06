package com.diosoft.trsine.calendar.datastore;

import com.diosoft.trsine.calendar.common.Event;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Contract of data store of <code>Event</code>s.
 *
 * @author  Vasyl Tegza, Alexander Zamkovyi, Igor Vartanian
 * @version 1.0
 * @since 1.0
 */

public interface DataStore {
    /**
     * Adds <code>Event</code> to data store.
     * @param event to be added
     */
    void add(Event event);
    /**
     * Adds the <code>Event</code> collection to data store.
     * @param events to be added
     */
    void addAll(Collection<Event> events);
    /**
     * Removes the <code>Event</code> from data store.
     * @param id of <code>Event</code>
     */
    void remove(UUID id);
    /** Search for all <code>Event</code> in data store by given description.
     *
     * @param description to search by
     * @return the list of <code>Event</code>s
     */
    List<Event> searchByDescription(String description);
    /** Search for all <code>Event</code> in data store by given title.
     *
     * @param title to search by
     * @return the list of <code>Event</code>s
     */
    List<Event> searchByTitle(String title);
    /** Search for all <code>Event</code> in data store by given title.
     *
     * @param id to search by
     * @return <code>Event</code>
     */
    Event getById(UUID id);
    /** Search for all <code>Event</code> in data store
     * by given date of begining.
     *
     * @param day date of begining to search by
     * @return the list of <code>Event</code>s
     */
    List<Event> searchByDay(Date day);
    /** Search for all <code>Event</code> in data store
     * by given date of begining interval.
     *
     * @param leftDate date of begining to search by
     * @param rightDate date of begining to search by
     * @return the list of <code>Event</code>s
     */
    List<Event> searchByInterval(Date leftDate, Date rightDate);
    /** Search for all <code>Event</code> in data store by given title beginning.
     *
     * @param description to search by
     * @return the list of <code>Event</code>s
     */
    @SuppressWarnings("unused")
    List<Event> searchByTitleStartWith(String description);

    /**
     * Initialization if needed.
     */
    void init();
}
