package com.diosoft.trsine.calendar.datastore;

import com.diosoft.trsine.calendar.common.Event;

import java.util.*;

/**
 * Implements <code>DataStore</code> interface
 * Stores <code>Event</>s, event descriptions, titles and begin dates as HaspMaps
 *
 * @author  Alexander Zamkovyi
 * @since 1.8
*/

@SuppressWarnings("unused")
public class HashMapDataStore implements DataStore {

//    HashMap<UUID,Event> eventsMap;
//    HashMap<String, List<UUID>> titlesMap;
//    HashMap<Date, List<UUID>> daysMap;
//    HashMap<Date, List<UUID>> descriptionsMap;

    @Override
    public void add(Event event) {
        //events.add(event);
    }

    @Override
    public void addAll(Collection<Event> events) {
        //events.addAll(events);
    }

    @Override
    public void remove(UUID id) {
        //
    }

    @Override
    public List<Event> searchByDescription(String description) {
        return null;
    }

    @Override
    public List<Event> searchByTitle(String title) {
        return null;
    }

    @Override
    public List<Event> searchByDay(Date day) {
        return null;
    }

    @Override
    public List<Event> searchByInterval(Date leftDate, Date rightDate) {
        return null;
    }
}
