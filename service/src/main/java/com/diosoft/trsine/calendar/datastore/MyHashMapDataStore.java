package com.diosoft.trsine.calendar.datastore;

import com.diosoft.trsine.calendar.common.Event;

import java.util.*;

/**
 * Implements <code>DataStore</code> interface
 * Stores <code>Event</>s, event descriptions, titles and begin dates as HaspMaps to optimize access speed
 *
 * @author  Alexander Zamkovyi
 * @since 1.8
*/

public abstract class MyHashMapDataStore implements DataStore {

    HashMap<UUID,Event> eventsMap = new HashMap<>();
    HashMap<String, Set<UUID>> titlesMap = new HashMap<>();
    HashMap<Date, Set<UUID>> daysMap = new HashMap<>();
    HashMap<String, Set<UUID>> descriptionsMap = new HashMap<>();

    abstract public Set<UUID> newSet();

    @Override
    public void add(Event event) {
        UUID eventId = event.getId();
        eventsMap.putIfAbsent(eventId, event);

        Set<UUID> currentSet = null;
        //rebuild indexes
        currentSet = titlesMap.get(event.getTitle());
        if (currentSet == null) {
            currentSet = newSet();
            currentSet.add(eventId);
            titlesMap.put(event.getTitle(), currentSet);
        } else {
            currentSet.add(eventId);
        }
        daysMap.get(event.getDateBegin()).add(eventId);
        descriptionsMap.get(event.getDescription()).add(eventId);
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
}
