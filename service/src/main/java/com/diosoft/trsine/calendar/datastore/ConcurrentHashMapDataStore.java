package com.diosoft.trsine.calendar.datastore;

import com.diosoft.trsine.calendar.common.Event;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implements <code>DataStore</code> interface
 * Stores <code>Event</>s, event descriptions, titles and begin dates
 * as <code>ConcurrentHashMap</code>s to optimize access speed
 *
 * @author  Alexander Zamkovyi
 * @since 1.8
*/

public abstract class ConcurrentHashMapDataStore implements DataStore {

    ConcurrentHashMap<UUID,Event> eventsMap = new ConcurrentHashMap<>();
    ConcurrentHashMap<Object, Set<UUID>> titlesMap = new ConcurrentHashMap<>();
    ConcurrentHashMap<Object, Set<UUID>> daysMap = new ConcurrentHashMap<>();
    ConcurrentHashMap<Object, Set<UUID>> descriptionsMap = new ConcurrentHashMap<>();

    abstract public Set<UUID> newSet();

    @Override
    public void add(Event event) {
        UUID eventId = event.getId();
        eventsMap.putIfAbsent(eventId, event);

        //rebuild indexes
        rebuildIndexOnAdd(titlesMap, event.getTitle(), eventId);
        rebuildIndexOnAdd(daysMap, event.getDateBegin(), eventId);
        rebuildIndexOnAdd(descriptionsMap, event.getDescription(), eventId);
    }

    private void rebuildIndexOnAdd(ConcurrentHashMap<Object, Set<UUID>> indexMap, Object key, UUID eventId) {
        Set<UUID> currentSet;
        currentSet = indexMap.get(key);
        if (currentSet == null) {
            currentSet = newSet();
            currentSet.add(eventId);
            indexMap.put(key, currentSet);
        } else {
            currentSet.add(eventId);
        } //end if
    }

    private void rebuildIndexOnRemove(ConcurrentHashMap<Object, Set<UUID>> indexMap, Object key, UUID eventId) {
        Set<UUID> currentSet;
        currentSet = indexMap.get(key);
        if (currentSet != null) {
            indexMap.remove(eventId);
            currentSet.remove(eventId);
        } //end if
    }

    @Override
    public void addAll(Collection<Event> events) {
        events.parallelStream().forEach(this::add);
    }

    @Override
    public void remove(UUID id) {
        Event event = eventsMap.get(id);
        if (event == null) {
            //Nothing to do here
            return;
        } //end if

        eventsMap.remove(id);

        //rebuild indexes
        rebuildIndexOnRemove(titlesMap, event.getTitle(), id);
        rebuildIndexOnRemove(daysMap, event.getDateBegin(), id);
        rebuildIndexOnRemove(descriptionsMap, event.getDescription(), id);
    }

    @Override
    public List<Event> searchByDescription(String description) {
        List<Event> eventsFound = new ArrayList<>();

        descriptionsMap.get(description)
                .parallelStream()
                .forEach(p -> {
                    Event event = eventsMap.get(p);
                    if (event != null) eventsFound.add(event);
                });

        return eventsFound;
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
