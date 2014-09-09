package com.diosoft.trsine.calendar.datastore;

import com.diosoft.trsine.calendar.common.Event;

import java.util.*;
import java.util.stream.Collectors;

public class ArrayListDataStore implements DataStore {

    ArrayList<Event> events = new ArrayList<>();

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
        ArrayList<Event> eventsToBeDeleted = new ArrayList<>();

        for (Event event: events) {
            if (event.getId().equals(id)) eventsToBeDeleted.add(event);
        }

        for (Event event: eventsToBeDeleted) {
            events.remove(event);
        }
    }

    @Override
    public List<Event> searchByDescription(String description) {
        return events.parallelStream()
                .filter(p -> p.getDescription().equals(description))
                .collect(Collectors.toList());
    }

    @Override
    public List<Event> searchByTitle(String title) {
        return events.parallelStream()
                .filter(p -> p.getTitle().equals(title))
                .collect(Collectors.toList());
    }

    @Override
    public List<Event> searchByDay(Date day) {
        return events.parallelStream()
                .filter(p -> p.getDateBegin().equals(day))
                .collect(Collectors.toList());
    }
}
