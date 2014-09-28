package com.diosoft.trsine.calendar.service;

import com.diosoft.trsine.calendar.common.Event;
import com.diosoft.trsine.calendar.common.EventAdapter;
import com.diosoft.trsine.calendar.datastore.DataStore;
import com.diosoft.trsine.calendar.utilities.ConvertData;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Implementation of Calendar service
 *
 * @author  Vasyl Tegza, Alexander Zamkovyi
 * @since 1.5
 */

public class CalendarServiceImp implements CalendarService {

    private final DataStore dataStore;

    public CalendarServiceImp(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    @Override
    public void add(Event event) {
        dataStore.add(event);
        ConvertData.convertFromEventToXML(new EventAdapter(event), dataStore.getDataPath());
    }

    @Override
    public void addAll(Collection<Event> events) {
        dataStore.addAll(events);
        for (Event event : events){
            ConvertData.convertFromEventToXML(new EventAdapter(event), dataStore.getDataPath());
        }
    }

    @Override
    public void remove(UUID id) {
        dataStore.remove(id);
        ConvertData.deleteDataFile(id, dataStore.getDataPath());
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

    @Override
    public List<Event> searchByAttender(String s) {
        return dataStore.searchByAttender(s);
    }
}
