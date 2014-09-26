package com.diosoft.trsine.calendar.datastore;

import com.diosoft.trsine.calendar.common.Event;
import com.diosoft.trsine.calendar.util.DateHelper;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Implements <code>DataStore</code> interface
 * Stores <code>Event</code>s, event descriptions, titles and begin dates
 * as <code>ConcurrentHashMap</code>s to optimize access speed
 * implement method <code>newSet()</code>
 * to instantiate <code>Set</code> of <code>UUID</code>s.
 *
 * @author  Alexander Zamkovyi, Igor Vartanian
 * @version 1.0
 * @since 1.0
*/

public abstract class ConcurrentHashMapDataStore implements DataStore {

    ConcurrentHashMap<UUID,Event> eventsMap = new ConcurrentHashMap<>();

    //index maps
    ConcurrentHashMap<Object, Set<UUID>> titlesMap = new ConcurrentHashMap<>();
    ConcurrentHashMap<Object, Set<UUID>> daysMap = new ConcurrentHashMap<>();
    ConcurrentHashMap<Object, Set<UUID>> descriptionsMap = new ConcurrentHashMap<>();

    /**
     * Constructor
     *
     */
    public ConcurrentHashMapDataStore() { }

    /**
     * Constructor
     *
     * @param eventsMap events
     * @param titlesMap index for titles
     * @param daysMap index for days
     * @param descriptionsMap index for descriptions
     */
    public ConcurrentHashMapDataStore(ConcurrentHashMap<UUID, Event> eventsMap,
                                            ConcurrentHashMap<Object, Set<UUID>> titlesMap,
                                            ConcurrentHashMap<Object, Set<UUID>> daysMap,
                                            ConcurrentHashMap<Object, Set<UUID>> descriptionsMap) {

        this.eventsMap = eventsMap;
        this.titlesMap = titlesMap;
        this.daysMap = daysMap;
        this.descriptionsMap = descriptionsMap;
    }
        /**
         * method to instantiate the <code>Set</code> of UUIDs.
         *
         * @return <code>Set</code> of <code>UUID</code>s
         */
    abstract public Set<UUID> newUUIDSet();

    /**
     * method to instantiate the <code>List</code> of Events.
     *
     * @return <code>Set</code> of <code>Event</code>s
     */
    abstract public List<Event> newResultList();

    /**
     * Adds <code>Event</code> to data store.
     * @param event to be added
     */
    @Override
    public void add(final Event event) {
        UUID eventId = event.getId();
        @SuppressWarnings("unused")
        Event actualEvent = eventsMap.putIfAbsent(eventId, event);

        //rebuild indexes
        rebuildIndexOnAdd(titlesMap, event.getTitle(), eventId);
        rebuildIndexOnAdd(daysMap, DateHelper.getOnlyDate(event.getDateBegin()), eventId); //we need only date without time
        rebuildIndexOnAdd(descriptionsMap, event.getDescription(), eventId);
    }

    /** Rebuilds HashMaps intended to optimize search operations
     * after new Event added.
     *
     * @param indexMap index map to be updated
     * @param key index map key that needs an update
     * @param eventId id to be added to index map
     */
    private synchronized void rebuildIndexOnAdd(ConcurrentHashMap<Object, Set<UUID>> indexMap, Object key, UUID eventId) {
        Set<UUID> uuidSet = indexMap.get(key);
        if (uuidSet == null) {
            final Set<UUID> value = newUUIDSet();

            uuidSet = indexMap.putIfAbsent(key, value);
            if (uuidSet == null) {
                uuidSet = value;
            } //end if
            uuidSet.add(eventId);
        } else {
            uuidSet.add(eventId);
        } //end if
    }

    /** Rebuilds HashMaps intended to optimize search operations
     * after Event was removed.
     *
     * @param indexMap index map to be updated
     * @param key index map key that needs an update
     * @param eventId id to be removed from the index map
     */
    private void rebuildIndexOnRemove(ConcurrentHashMap<Object, Set<UUID>> indexMap, Object key, UUID eventId) {
        if (!indexMap.containsKey(key)) {
            //nothing to do
            return;
        } //end if

        Set<UUID> uuidSet;
        uuidSet = indexMap.get(key);
        if (uuidSet != null) {
            uuidSet.remove(eventId);
                if (uuidSet.size() == 0) {
                    indexMap.remove(key);
                } //end if
        } //end if
    }

    /**
     * Adds the <code>Event</code> collection to data store.
     * @param events to be added
     */
    @Override
    public void addAll(Collection<Event> events) {
        events.parallelStream().forEach(this::add);
    }

    /**
     * Removes the <code>Event</code> from data store.
     * @param id of <code>Event</code>
     */
    @Override
    public void remove(UUID id) {
        if (!eventsMap.containsKey(id)) {
            //nothing to remove
            return;
        }  //end if

        Event event = eventsMap.get(id);
        if (event == null) {
            //Nothing to do here
            return;
        } //end if

        eventsMap.remove(id);

        //rebuild indexes
        rebuildIndexOnRemove(titlesMap, event.getTitle(), id);
        rebuildIndexOnRemove(daysMap, DateHelper.getOnlyDate(event.getDateBegin()), id); //we need only date without time
        rebuildIndexOnRemove(descriptionsMap, event.getDescription(), id);
    }

    /**
     * Search events by key using index map.
     *
     * @param indexMap index map to use
     * @param key search criteria
     * @return collection of events found
     */
    private List<Event> searchByField(ConcurrentHashMap<Object, Set<UUID>> indexMap, Object key) {
        List<Event> eventsFound = newResultList();

        Set<UUID> value = indexMap.get(key);
        if (value != null){
            value.parallelStream()
                    .forEach(p -> {
                        Event event = eventsMap.get(p);
                        if (event != null) eventsFound.add(event);
                    });
        } //end if
        return eventsFound;
    }

    /** Search for all <code>Event</code> in data store
     * by given description.
     *
     * @param description to search by
     * @return the list of <code>Event</code>s
     */
    @Override
    public List<Event> searchByDescription(String description) {
        return searchByField(descriptionsMap, description);
    }

    /** Search for all <code>Event</code> in data store
     * by given title.
     *
     * @param title to search by
     * @return the list of <code>Event</code>s
     */
    @Override
    public List<Event> searchByTitle(String title) {
        return searchByField(titlesMap, title);
    }

    @Override
    public Event getById(UUID id) {
       return eventsMap.get(id);
    }

    /** Search for all <code>Event</code> in data store
     * by given date of begining.
     *
     * @param day date of begining to search by
     * @return the list of <code>Event</code>s
     */
    @Override
    public List<Event> searchByDay(Date day) {
        List<Event> eventsFound = newResultList();

        Set<UUID> value = daysMap.get(DateHelper.getOnlyDate(day));
        if (value != null) {
            value
                    .parallelStream()
                    .forEach(p -> {
                        Event event = eventsMap.get(p);
                        if (event != null && event.getDateBegin().equals(day)) eventsFound.add(event);
                    });
        } //end if
        return eventsFound;
    }

    /** Search for all <code>Event</code> in data store
     * by given date of begining interval.
     *
     * @param leftDate date of begining to search by
     * @param rightDate date of begining to search by
     * @return the list of <code>Event</code>s
     */
    public List<Event> searchByInterval(Date leftDate, Date rightDate) {

        List<Event> eventsFound = newResultList();
        Date leftDateOnlyDate = DateHelper.getOnlyDate(leftDate);
        Date rightDateOnlyDate = DateHelper.getOnlyDate(rightDate);
        Date currentDate = leftDateOnlyDate;

        while (currentDate.compareTo(rightDateOnlyDate) <= 0) {
            Date dateToSearch = currentDate;
            Set<UUID> value = daysMap.get(DateHelper.getOnlyDate(dateToSearch));
            if (value != null) {
                value
                        .parallelStream()
                        .forEach(p -> {
                            Event event = eventsMap.get(p);
                            if (event != null && event.getDateBegin().compareTo(leftDate) >= 0 && event.getDateBegin().compareTo(rightDate) <= 0)
                                eventsFound.add(event);
                        });
            } //end if
            currentDate = DateHelper.dayIncrement(currentDate);
        } //end while

        return eventsFound;
    }

    public List<Event> searchLambdaByTitleStartWith(String title) {
        return eventsMap.entrySet()
                .parallelStream()
                .filter( p ->
                                titlesMap.entrySet()
                                        .parallelStream()
                                        .filter(  b -> ((String)b.getKey()).startsWith(title))
                                        .map(e -> e.getValue()).flatMap((uuidList) -> uuidList.parallelStream())
                                        .collect(Collectors.toList()).contains(p.getKey())
                )
                .map(e -> e.getValue())
                .collect(Collectors.toList());
    }

    @Override
    public List<Event> searchByTitleStartWith(String title) {
        List resultList = new ArrayList<Event>();

        for (Map.Entry<Object, Set<UUID>> entry: titlesMap.entrySet()) {
            if (((String) entry.getKey()).startsWith(title)) {
                for (UUID uuid : entry.getValue()) {
                    Event event = eventsMap.get(uuid);

                    if (event != null) {
                        resultList.add(event);
                    }
                }
            }
        }

        return resultList;
    }
}
