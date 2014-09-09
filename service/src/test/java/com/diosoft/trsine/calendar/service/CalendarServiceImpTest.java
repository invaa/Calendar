package com.diosoft.trsine.calendar.service;

import com.diosoft.trsine.calendar.common.Event;
import com.diosoft.trsine.calendar.datastore.ConcurrentHashMapDataStore;
import com.diosoft.trsine.calendar.datastore.DataStore;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.*;

public class CalendarServiceImpTest {

    @Test
    public void testVerifyServiceCallDataStoreAddOnAdding() throws Exception {

        //Mocks
        Event eventMock = mock(Event.class);

        DataStore storeMock = mock(DataStore.class);
        //when(storeMock.add(eventMock)).thenReturn();

        CalendarService service = new CalendarServiceImp(storeMock);
        service.add(eventMock);

        //add(eventMock)
        verify(storeMock).remove(UUID.randomUUID());



//        Event testEvent = new Event.Builder() {
//            @Override
//            public Set newSet() {
//                return new HashSet<String>();
//            }
//        }//end of Builder implementation
//                .setDateBegin(new Date())
//                .setDateEnd(new Date())
//                .setId(UUID.randomUUID())
//                .setTitle("Daily Scrum")
//                .setDescription("Next daily scrum meeting")
//                .addAttender("alex@zamkovyi.name")
//                .addAttender("igor.vartanian@gmail.com")
//                .build();
//
//        CalendarServiceImp service = new CalendarServiceImp(new ConcurrentHashMapDataStore() {
//            @Override
//            public Set<UUID> newSet() {
//                return new HashSet<>();
//            }
//        });
//        service.add(testEvent);
//        List<Event> eventsAfterAdding = service.searchByDescription("Next daily scrum meeting");
//
//        List<Event> result = new ArrayList<>();
//        result.add(testEvent);
//
//        assertEquals(result, eventsAfterAdding);
    }

    @Test
    public void testAddAllOfTwoEventsReturnsListContainingTwoEvents() throws Exception {
        //init
        Event testEvent1 = new Event.Builder() {
            @Override
            public Set newSet() {
                return new HashSet<String>();
            }
        }//end of Builder implementation
                .setDateBegin(new Date())
                .setDateEnd(new Date())
                .setId(UUID.randomUUID())
                .setTitle("Daily Scrum")
                .setDescription("Next daily scrum meeting")
                .addAttender("alex@zamkovyi.name")
                .addAttender("igor.vartanian@gmail.com")
                .build();
        Event testEvent2 = new Event.Builder() {
            @Override
            public Set newSet() {
                return new HashSet<String>();
            }
        }//end of Builder implementation
                .setDateBegin(new Date())
                .setDateEnd(new Date())
                .setId(UUID.randomUUID())
                .setTitle("Annual meeting")
                .setDescription("Just ordinary annual meeting")
                .addAttender("igor.vartanian@gmail.com")
                .addAttender("alex@zamkovyi.name")
                .build();

        ArrayList<Event> arrayToAdd = new ArrayList<>();
        arrayToAdd.add(testEvent1);
        arrayToAdd.add(testEvent2);

        CalendarServiceImp service = new CalendarServiceImp(new ConcurrentHashMapDataStore() {
            @Override
            public Set<UUID> newSet() {
                return new HashSet<>();
            }
        });
        service.addAll(arrayToAdd);

        service
                .searchByTitle("Daily Scrum")
                .parallelStream()
                .forEach(p -> assertTrue(arrayToAdd.contains(p)));
        service
                .searchByTitle("Annual meeting")
                .parallelStream()
                .forEach(p -> assertTrue(arrayToAdd.contains(p)));
    }

    @Test
    public void testRemoveEventReturnsListNotContainingEvent() throws Exception {
        //init
        Event testEvent1 = new Event.Builder() {
            @Override
            public Set newSet() {
                return new HashSet<String>();
            }
        }//end of Builder implementation
                .setDateBegin(new Date())
                .setDateEnd(new Date())
                .setId(UUID.randomUUID())
                .setTitle("Daily Scrum")
                .setDescription("Next daily scrum meeting")
                .addAttender("alex@zamkovyi.name")
                .addAttender("igor.vartanian@gmail.com")
                .build();
        Event testEvent2 = new Event.Builder() {
            @Override
            public Set newSet() {
                return new HashSet<String>();
            }
        }//end of Builder implementation
                .setDateBegin(new Date())
                .setDateEnd(new Date())
                .setId(UUID.randomUUID())
                .setTitle("Annual meeting")
                .setDescription("Just ordinary annual meeting")
                .addAttender("igor.vartanian@gmail.com")
                .addAttender("alex@zamkovyi.name")
                .build();

        CalendarServiceImp service = new CalendarServiceImp(new ConcurrentHashMapDataStore() {
            @Override
            public Set<UUID> newSet() {
                return new HashSet<>();
            }
        });
        service.add(testEvent1);
        service.add(testEvent2);

        service.searchByTitle("Daily Scrum")
                .parallelStream()
                .forEach(p -> service.remove(p.getId()));
        List<Event> eventsAfterRemoving = service.searchByTitle("Daily Scrum");
        List<Event> result = new ArrayList<>();
        assertEquals(result, eventsAfterRemoving);

        eventsAfterRemoving = service.searchByTitle("Annual meeting");
        result.add(testEvent2);
        assertEquals(result, eventsAfterRemoving);
    }


    @Test
    public void testSearchByTitle() throws Exception {

    }

    @Test
    public void testSearchByDescription() throws Exception {

    }

    @Test
    public void testSearchByDay() throws Exception {

    }
}