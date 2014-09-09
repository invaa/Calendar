package com.diosoft.trsine.calendar.datastore;

import com.diosoft.trsine.calendar.common.Event;
import com.diosoft.trsine.calendar.exceptions.DateIntervalIsIncorrectException;
import com.diosoft.trsine.calendar.exceptions.IdIsNullException;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class ConcurrentHashMapDataStoreTest {

    /**
     * Gets test event
     *
     * @return test <code>Event</code>
     * @throws IdIsNullException
     * @throws DateIntervalIsIncorrectException
     */
    private Event getDailyScrumTestEvent() throws IdIsNullException, DateIntervalIsIncorrectException {
        return new Event.HashSetBuilder()
                .setDateBegin(new Date())
                .setDateEnd(new Date())
                .setId(UUID.randomUUID())
                .setTitle("Daily Scrum")
                .setDescription("Next daily scrum meeting")
                .addAttender("alex@zamkovyi.name")
                .addAttender("igor.vartanian@gmail.com")
                .build();
    }

    /**
     * Gets test event
     *
     * @return test <code>Event</code>
     * @throws IdIsNullException
     * @throws DateIntervalIsIncorrectException
     */
    private Event getAnnualMeetingTestEvent() throws IdIsNullException, DateIntervalIsIncorrectException {
        return new Event.HashSetBuilder()
                .setDateBegin(new Date())
                .setDateEnd(new Date())
                .setId(UUID.randomUUID())
                .setTitle("Annual meeting")
                .setDescription("Just ordinary annual meeting")
                .addAttender("igor.vartanian@gmail.com")
                .addAttender("alex@zamkovyi.name")
                .build();
    }

    @Test
    public void testAddReturnsListContainingEvent() throws Exception {

        Event testEvent = getDailyScrumTestEvent();

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
        Event testEvent1 = getDailyScrumTestEvent();
        Event testEvent2 = getAnnualMeetingTestEvent();

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
            Event testEvent1 = getDailyScrumTestEvent();
            Event testEvent2 = getAnnualMeetingTestEvent();

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
    public void testSearchByDescriptionFindsDesiredEvent() throws Exception {
        Event testEvent1 = getDailyScrumTestEvent();
        Event testEvent2 = getAnnualMeetingTestEvent();

        ConcurrentHashMapDataStore store = new ConcurrentHashMapDataStore() {
            @Override
            public Set<UUID> newSet() {
                return new HashSet<>();
            }
        };
        ArrayList<Event> arrayToAdd = new ArrayList<>();
        arrayToAdd.add(testEvent1);
        arrayToAdd.add(testEvent2);
        store.addAll(arrayToAdd);

        assertTrue(store
                 .searchByDescription(testEvent1.getDescription())
                 .contains(testEvent1));

        assertTrue(store
                .searchByDescription(testEvent2.getDescription())
                .contains(testEvent2));
    }

    @Test
    public void testSearchByTitle() throws Exception {
        Event testEvent1 = getDailyScrumTestEvent();
        Event testEvent2 = getAnnualMeetingTestEvent();

        ConcurrentHashMapDataStore store = new ConcurrentHashMapDataStore() {
            @Override
            public Set<UUID> newSet() {
                return new HashSet<>();
            }
        };
        ArrayList<Event> arrayToAdd = new ArrayList<>();
        arrayToAdd.add(testEvent1);
        arrayToAdd.add(testEvent2);
        store.addAll(arrayToAdd);

        assertTrue(store
                .searchByTitle(testEvent1.getTitle())
                .contains(testEvent1));

        assertTrue(store
                .searchByTitle(testEvent2.getTitle())
                .contains(testEvent2));
    }

    @Test
    public void testSearchByDay() throws Exception {
        Event testEvent1 = getDailyScrumTestEvent();
        Event testEvent2 = getAnnualMeetingTestEvent();

        ConcurrentHashMapDataStore store = new ConcurrentHashMapDataStore() {
            @Override
            public Set<UUID> newSet() {
                return new HashSet<>();
            }
        };
        ArrayList<Event> arrayToAdd = new ArrayList<>();
        arrayToAdd.add(testEvent1);
        arrayToAdd.add(testEvent2);
        store.addAll(arrayToAdd);

        assertTrue(store
                .searchByDay(testEvent1.getDateBegin())
                .contains(testEvent1));

        assertTrue(store
                .searchByDay(testEvent2.getDateBegin())
                .contains(testEvent2));
    }
}