package com.diosoft.trsine.calendar.datastore;

import com.diosoft.trsine.calendar.common.Event;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Implements <code>DataStore</code> interface
 * Stores <code>Event</>s, event descriptions, titles and begin dates as HaspMaps to optimize access speed
 *
 * @author  Igor Vartanian
 * @since 1.8
*/

public abstract class HashMapDataStore implements DataStore {

    Map<UUID, Event> eventsMap = new HashMap<>();

    Map<String, List<UUID>> titlesMap = new HashMap<>();
    Map<Date, List<UUID>> daysMap = new HashMap<>();
    Map<String, List<UUID>> descriptionsMap = new HashMap<>();

    Set<Event> events;

    @Override
    public void add(Event event) {

        events.add(event);
    }

    @Override
    public void addAll(Collection<Event> events) {
        events.addAll(events);
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
}
