package com.diosoft.trsine.calendar.datastore;

import com.diosoft.trsine.calendar.common.Event;

import java.util.*;

/**
 * Implements <code>DataStore</code> interface
 * Stores <code>Event</>s, event descriptions, titles and begin dates as HaspMaps to optimize access speed
 *
 * @author Alexander Zamkovyi
 * @since 1.8
 */

public class HashMapDataStoreImp implements DataStore<Map> {

    private Map<UUID, Event> eventsMap = new HashMap<>();

    private Map<String, List<UUID>> titlesMap = new HashMap<>();
    private Map<Date, List<UUID>> daysMap = new HashMap<>();
    private Map<String, List<UUID>> descriptionsMap = new HashMap<>();



    @Override
    public void add(Event event) {

        if (event == null) {
            return;
        }

        UUID currentId = event.getId();
        eventsMap.putIfAbsent(currentId, event);

        // add index title
        addIndexTitle(event);
        // add index dateBegin
        addIndexDateBegin(event);
        // add index dateBegin
        addIndexDescription(event);

    }

    private void addIndexTitle(Event event) {
        String keyTitle = event.getTitle();
        List<UUID> valueUUID = titlesMap.get(keyTitle);
        valueUUID = addValue(event, valueUUID);
        titlesMap.put(keyTitle, valueUUID);
    }

    private void addIndexDateBegin(Event event) {
        Date keyDate = event.getDateBegin();
        List<UUID> valueUUID = daysMap.get(keyDate);
        valueUUID = addValue(event, valueUUID);
        daysMap.put(keyDate, valueUUID);
    }

    private void addIndexDescription(Event event) {
        String keyDescription = event.getDescription();
        List<UUID> valueUUID = descriptionsMap.get(keyDescription);
        valueUUID = addValue(event, valueUUID);
        descriptionsMap.put(keyDescription, valueUUID);
    }

    private List<UUID> addValue(Event event, List<UUID> valueUUID) {
        valueUUID = checkedValue(valueUUID);
        valueUUID.add(event.getId());
        return valueUUID;
    }

    private List<UUID> checkedValue(List<UUID> valueUUID) {
        if (valueUUID == null) {
            valueUUID = new ArrayList<UUID>();
        }
        return valueUUID;
    }

    @Override
    public void addAll(Collection<Event> events) {

        if (events == null) {
            return;
        }

        for (Event event : events) {
            add(event);
        }

    }

    @Override
    public void remove(UUID id) {

        if (!eventsMap.containsKey(id)) {
            return;
        }

        Event event = eventsMap.remove(eventsMap.get(id));

        // remove index title
        removeIndexTitle(event);
        // remove index dateBegin
        removeIndexDateBegin(event);
        // remove index dateBegin
        removeIndexDescription(event);


    }

    @Override
    public Map getDataStore() {
        return eventsMap;
    }

    private void removeIndexDescription(Event event) {
        String keyDescription = event.getDescription();
        if (!descriptionsMap.containsKey(keyDescription)) {
            return;
        }
        descriptionsMap.remove(keyDescription);
    }

    private void removeIndexDateBegin(Event event) {
        Date keyDate = event.getDateBegin();
        if (!daysMap.containsKey(keyDate)) {
            return;
        }
        daysMap.remove(keyDate);
    }

    private void removeIndexTitle(Event event) {
        String keyTitle = event.getTitle();
        if (!titlesMap.containsKey(keyTitle)) {
            return;
        }
        titlesMap.remove(keyTitle);
    }

    @Override
    public List<Event> searchByDescription(String description) {
        if (!descriptionsMap.containsKey(description)) {
            return null;
        }
        List<UUID> valueUUID = descriptionsMap.get(description);
        return getEvents(valueUUID);
    }

    @Override
    public List<Event> searchByTitle(String title) {
        if (!titlesMap.containsKey(title)) {
            return null;
        }
        List<UUID> valueUUID = titlesMap.get(title);
        return getEvents(valueUUID);
    }

    @Override
    public List<Event> searchByDay(Date day) {
        if (!daysMap.containsKey(day)) {
            return null;
        }
        List<UUID> valueUUID = daysMap.get(day);
        return getEvents(valueUUID);
    }

    private List<Event> getEvents(List<UUID> valueUUID) {
        if (valueUUID == null) {
            return null;
        }
        List<Event> foundItems = new ArrayList<>();
        for (UUID id : valueUUID) {
            foundItems.add(eventsMap.get(id));
        }
        return foundItems;
    }
}
