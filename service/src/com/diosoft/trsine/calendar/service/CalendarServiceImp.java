package com.diosoft.trsine.calendar.service;

import com.diosoft.trsine.calendar.common.Event;
import com.diosoft.trsine.calendar.datastore.DataStore;

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

    }

    @Override
    public void remove(UUID id) {

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
