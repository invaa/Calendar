package com.diosoft.trsine.calendar.datastore;

import com.diosoft.trsine.calendar.common.Event;

import java.util.*;

/**
 * Contract of data store of <code>Event</code>s
 *
 * @author  Vasyl Tegza, Alexander Zamkovyi
 * @since 1.5
 */

public interface DataStore {
//    Map<UUID,Event>
//    Map<String, List<UUID>>
//    Map<Date, List<UUID>>

    void add(Event event);
    void addAll(Collection<Event> events);
    void remove(UUID id);
    Map<UUID, Event> getEventsMap();
    List<Event> searchByDescription(String description);
    List<Event> searchByTitle(String title);
    List<Event> searchByDay(Date day);

}
