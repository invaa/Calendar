package com.diosoft.trsine.calendar.datastore;

import com.diosoft.trsine.calendar.common.Event;

import java.util.*;

/**
 * Contract of data store of <code>Event</code>s
 *
 * @author  Vasyl Tegza, Alexander Zamkovyi
 * @since 1.5
 */

public interface DataStore<K extends Map> {

    void add(Event event);
    void addAll(Collection<Event> events);
    void remove(UUID id);
    K getDataStore();
    List<Event> searchByDescription(String description);
    List<Event> searchByTitle(String title);
    List<Event> searchByDay(Date day);

}
