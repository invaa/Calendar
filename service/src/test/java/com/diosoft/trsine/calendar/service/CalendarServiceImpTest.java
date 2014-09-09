package com.diosoft.trsine.calendar.service;

import com.diosoft.trsine.calendar.common.Event;
import com.diosoft.trsine.calendar.datastore.DataStore;
import org.junit.Test;

import java.util.*;

import static org.mockito.Mockito.*;

public class CalendarServiceImpTest {

    @Test
    public void testVerifyServiceCallDataStoreAddOnAdding() throws Exception {
        //Mocks
        Event eventMock = mock(Event.class);
        DataStore storeMock = mock(DataStore.class);
        CalendarService service = new CalendarServiceImp(storeMock);

        //checks
        service.add(eventMock);
        verify(storeMock, times(1)).add(eventMock);
        service.add(eventMock);
        verify(storeMock, times(2)).add(eventMock);
    }

    @Test
    public void testVerifyServiceCallDataStoreAddAllOnAddingAll() throws Exception {
        //Mocks
        @SuppressWarnings("unchecked")
        Collection<Event> collectionMock = mock(Collection.class);

        DataStore storeMock = mock(DataStore.class);
        CalendarService service = new CalendarServiceImp(storeMock);

        //checks
        service.addAll(collectionMock);
        verify(storeMock, times(1)).addAll(collectionMock);
        service.addAll(collectionMock);
        verify(storeMock, times(2)).addAll(collectionMock);
    }

    @Test
    public void testVerifyServiceCallDataStoreRemoveOnRemoving() throws Exception {
        //Mocks
        UUID id = UUID.randomUUID();
        DataStore storeMock = mock(DataStore.class);
        CalendarService service = new CalendarServiceImp(storeMock);

        //checks
        service.remove(id);
        verify(storeMock, times(1)).remove(id);
        service.remove(id);
        verify(storeMock, times(2)).remove(id);
    }

    @Test
    public void testVerifyServiceCallDataStoreSearchByTitleOnSearchingByTitle() throws Exception {
        //Mocks
        DataStore storeMock = mock(DataStore.class);
        CalendarService service = new CalendarServiceImp(storeMock);

        //checks
        service.searchByTitle("Some title");
        verify(storeMock, times(1)).searchByTitle("Some title");
        service.searchByTitle("Some title");
        verify(storeMock, times(2)).searchByTitle("Some title");
    }

    @Test
    public void testVerifyServiceCallDataStoreSearchByDescriptionOnSearchingByDescription() throws Exception {
        //Mocks
        DataStore storeMock = mock(DataStore.class);
        CalendarService service = new CalendarServiceImp(storeMock);

        //checks
        service.searchByDescription("Some description");
        verify(storeMock, times(1)).searchByDescription("Some description");
        service.searchByDescription("Some description");
        verify(storeMock, times(2)).searchByDescription("Some description");
    }

    @Test
    public void testVerifyServiceCallDataStoreSearchByDayOnSearchingByDay() throws Exception {
        //Mocks
        DataStore storeMock = mock(DataStore.class);
        CalendarService service = new CalendarServiceImp(storeMock);

        Date date = new Date();

        //checks
        service.searchByDay(date);
        verify(storeMock, times(1)).searchByDay(date);
        service.searchByDay(date);
        verify(storeMock, times(2)).searchByDay(date);
    }
}