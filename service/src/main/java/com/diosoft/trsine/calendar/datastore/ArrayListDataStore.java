package com.diosoft.trsine.calendar.datastore;

import com.diosoft.trsine.calendar.common.Event;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ArrayListDataStore implements DataStore {

    ArrayList<Event> events = new ArrayList<>();

    @Override
    public void add(Event event) {
        events.add(event);
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
        ArrayList<Event> eventsFound = new ArrayList<>();

        for (Event event: events) {
            if (event.getDescription().equals(description)) eventsFound.add(event);
        }

        return eventsFound;
    }

    @Override
    public List<Event> searchByTitle(String title) {
        ArrayList<Event> eventsFound = new ArrayList<>();

        for (Event event: events) {
            if (event.getTitle().equals(title)) eventsFound.add(event);
        }

        return eventsFound;
    }

    @Override
    public List<Event> searchByDay(Date day) {
        ArrayList<Event> eventsFound = new ArrayList<>();

        for (Event event: events) {
            if (event.getDateBegin().equals(day)) eventsFound.add(event);
        }

        return eventsFound;
    }
}
