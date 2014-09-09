package com.diosoft.trsine.calendar.datastore;

import com.diosoft.trsine.calendar.common.Event;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implements <code>DataStore</code> interface
 * Stores <code>Event</>s, event descriptions, titles and begin dates
 * as <code>ConcurrentHashMap</code>s to optimize access speed
 * implement method <code>newSet()</code> to instantiate <code>Set</code> of <code>UUID</code>s
 *
 * @author  Alexander Zamkovyi
 * @since 1.8
*/

public abstract class ConcurrentHashMapDataStore implements DataStore {

    ConcurrentHashMap<UUID,Event> eventsMap = new ConcurrentHashMap<>();

    //index maps
    ConcurrentHashMap<Object, Set<UUID>> titlesMap = new ConcurrentHashMap<>();
    ConcurrentHashMap<Object, Set<UUID>> daysMap = new ConcurrentHashMap<>();
    ConcurrentHashMap<Object, Set<UUID>> descriptionsMap = new ConcurrentHashMap<>();

    /**
     * method to instantiate the attenders <code>Set</code>
     */
    abstract public Set<UUID> newSet();

    /**
     * Adds <code>Event</code> to data store
     * @param event to be added
     */
    @Override
    public void add(Event event) {
        UUID eventId = event.getId();
        eventsMap.putIfAbsent(eventId, event);

        //rebuild indexes
        rebuildIndexOnAdd(titlesMap, event.getTitle(), eventId);
        rebuildIndexOnAdd(daysMap, event.getDateBegin(), eventId);
        rebuildIndexOnAdd(descriptionsMap, event.getDescription(), eventId);
    }

    /** Rebuilds HashMaps intended to optimize search operations after new Event added
     *
     * @param indexMap index map to be updated
     * @param key index map key that needs an update
     * @param eventId id to be added to index map
     */
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

    /** Rebuilds HashMaps intended to optimize search operations after Event was removed
     *
     * @param indexMap index map to be updated
     * @param key index map key that needs an update
     * @param eventId id to be removed from the index map
     */
    private void rebuildIndexOnRemove(ConcurrentHashMap<Object, Set<UUID>> indexMap, Object key, UUID eventId) {
        Set<UUID> currentSet;
        currentSet = indexMap.get(key);
        if (currentSet != null) {
            indexMap.remove(eventId);
            currentSet.remove(eventId);
        } //end if
    }

    /**
     * Adds the <code>Event</code> collection to data store
     * @param events to be added
     */
    @Override
    public void addAll(Collection<Event> events) {
        events.parallelStream().forEach(this::add);
    }

    /**
     * Removes the <code>Event</code> from data store
     * @param id of <code>Event</code>
     */
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

    /**
     * Search events by key using index map
     *
     * @param indexMap index map to use
     * @param key search criteria
     * @return collection of events found
     */
    private List<Event> searchByField(ConcurrentHashMap<Object, Set<UUID>> indexMap, Object key) {
        List<Event> eventsFound = new ArrayList<>();

        indexMap.get(key)
                .parallelStream()
                .forEach(p -> {
                    Event event = eventsMap.get(p);
                    if (event != null) eventsFound.add(event);
                });

        return eventsFound;
    }

    /** Search for all <code>Event</code> in data store by given description
     *
     * @param description to search by
     * @return the list of <code>Event</code>s
     */
    @Override
    public List<Event> searchByDescription(String description) {
        return searchByField(descriptionsMap, description);
    }

    /** Search for all <code>Event</code> in data store by given title
     *
     * @param title to search by
     * @return the list of <code>Event</code>s
     */
    @Override
    public List<Event> searchByTitle(String title) {
        return searchByField(titlesMap, title);
    }

    /** Search for all <code>Event</code> in data store by given date of begining
     *
     * @param day date of begining to search by
     * @return the list of <code>Event</code>s
     */
    @Override
    public List<Event> searchByDay(Date day) {
        return searchByField(daysMap, day);
    }
}
