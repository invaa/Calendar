package com.diosoft.trsine.calendar.datastore;

import com.diosoft.trsine.calendar.common.Event;

import java.util.*;
import java.util.stream.Collectors;

public class HashSetDataStore implements DataStore {

//    Map<UUID,Event>
//    Map<String, List<UUID>>
//    Map<Date, List<UUID>>
    ArrayList<Event> events = new ArrayList<>();

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
}
