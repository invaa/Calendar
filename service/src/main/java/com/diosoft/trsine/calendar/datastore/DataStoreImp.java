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

public class DataStoreImp<T extends List<UUID>> implements DataStore {

    Map<UUID, Event> eventsMap;

    Map<String, T> titlesMap;
    Map<Date, T> daysMap;
    Map<String, T> descriptionsMap;

//    public DataStoreImp(Map T1) {
//        this.eventsMap = T1;
//        this.titlesMap = T1;
//        this.daysMap = T1;
//        this.descriptionsMap = T1;
//    }

    public Map<UUID, Event> getEventsMap() {
        return eventsMap;
    }

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
        valueUUID = checkedValue(valueUUID);
        valueUUID.add(event.getId());
    }

    private void addIndexDateBegin(Event event) {
        Date keyDate = event.getDateBegin();
        List<UUID> valueUUID = daysMap.get(keyDate);
        valueUUID = checkedValue(valueUUID);
        valueUUID.add(event.getId());
    }

    private void addIndexDescription(Event event) {
        String keyDescription = event.getDescription();
        List<UUID> valueUUID = descriptionsMap.get(keyDescription);
        valueUUID = checkedValue(valueUUID);
        valueUUID.add(event.getId());
    }

    private List<UUID> checkedValue(List<UUID> valueUUID) {
        if (valueUUID == null) {
            valueUUID = new ArrayList<UUID>();
        }
        return valueUUID;
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
