package com.diosoft.trsine.calendar.service;

import com.diosoft.trsine.calendar.common.Event;
import com.diosoft.trsine.calendar.datastore.DataStore;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class CalendarServiceImp implements CalendarService {

    private final DataStore dataStore;

    public CalendarServiceImp(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    @Override
    public void add(Event event) {
        dataStore.add(event);
    }

    @Override
    public void addAll(Collection<Event> events) {
        dataStore.addAll(events);
    }

    @Override
    public void remove(UUID id) {
        dataStore.remove(id);
    }

    @Override
    public List<Event> searchByTitle(String title) {
        return dataStore.searchByTitle(title);
    }

    @Override
    public List<Event> searchByDescription(String description) {
        return dataStore.searchByDescription(description);
    }

    @Override
    public List<Event> searchByDay(Date day) {
        return dataStore.searchByDay(day);
    }
}
