package com.diosoft.trsine.calendar.datastore;

import com.diosoft.trsine.calendar.common.Event;
import com.diosoft.trsine.calendar.service.CalendarServiceImp;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class ConcurrentHashMapDataStoreTest {

    @Test
    public void testAddReturnsListContainingEvent() throws Exception {

        Event testEvent = new Event.Builder() {
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

        ConcurrentHashMapDataStore store = new ConcurrentHashMapDataStore() {
            @Override
            public Set<UUID> newSet() {
               return new HashSet<>();
            }
        };
        store.add(testEvent);
        List<Event> eventsAfterAdding = store.searchByDescription("Next daily scrum meeting");

        List<Event> result = new ArrayList<>();
        result.add(testEvent);

        assertEquals(result, eventsAfterAdding);
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

        ConcurrentHashMapDataStore store = new ConcurrentHashMapDataStore() {
            @Override
            public Set<UUID> newSet() {
                return new HashSet<>();
            }
        };
        store.addAll(arrayToAdd);

        store
                .searchByTitle("Daily Scrum")
                .parallelStream()
                .forEach(p -> assertTrue(arrayToAdd.contains(p)));
        store
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

            ConcurrentHashMapDataStore store = new ConcurrentHashMapDataStore() {
                @Override
                public Set<UUID> newSet() {
                return new HashSet<>();
            }
            };
            store.add(testEvent1);
            store.add(testEvent2);

            store.searchByTitle("Daily Scrum")
                    .parallelStream()
                    .forEach(p -> store.remove(p.getId()));
            List<Event> eventsAfterRemoving = store.searchByTitle("Daily Scrum");
            List<Event> result = new ArrayList<>();
            assertEquals(result, eventsAfterRemoving);

            eventsAfterRemoving = store.searchByTitle("Annual meeting");
            result.add(testEvent2);
            assertEquals(result, eventsAfterRemoving);
        }

    @Test
    public void testSearchByDescription() throws Exception {

    }

    @Test
    public void testSearchByTitle() throws Exception {

    }

    @Test
    public void testSearchByDay() throws Exception {

    }
}