package com.diosoft.trsine.calendar.datastore;

import com.diosoft.trsine.calendar.common.Event;
//import com.diosoft.trsine.calendar.common.TestEventChield;
import com.diosoft.trsine.calendar.comparison.ComparatorDateBegin;
import com.diosoft.trsine.calendar.exceptions.IncorrectPeriodDates;
import com.diosoft.trsine.calendar.service.CalendarServiceImp;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class DataStoreTest {

    OrganizerUtil util = new OrganizerUtil();

    @Test
    public void testDataStore() throws IncorrectPeriodDates {

//        DataStore mockedObject = mock(DataStore.class);
        DataStore ds = new DataStoreImp();

        Set<String> attenders = util.createAttenders(5, "user");
        Date date = new GregorianCalendar(2014, 10, 12, 10, 0, 0).getTime();

        Map<UUID, Event> currentDataStore = ds.getEventsMap();

        //resultHelpMap
        Map<UUID, Event> resultHelpMap = new HashMap<>();


        //add
        for (int i = 0; i < 1000; i++) {
            Event eventAdd = util.createEvent(i, date, date, String.valueOf(i) + "_" + "Event",
                    String.valueOf(i) + "_" + "DescriptionEvent", attenders);
            ds.add(eventAdd);
            resultHelpMap.put(eventAdd.getId(), eventAdd);
        }
        currentDataStore = ds.getEventsMap();
        Assert.assertEquals(resultHelpMap, currentDataStore);


        //addAll
        HashSet<Event> hashEvents = new HashSet<>();
        for (int i = 700; i < 10000; i++) {
            Event eventAddAll = util.createEvent(i, date, date, String.valueOf(i) + "_" + "Event",
                    String.valueOf(i) + "_" + "DescriptionEvent", attenders);
            hashEvents.add(eventAddAll);
            resultHelpMap.put(eventAddAll.getId(), eventAddAll);
        }
        ds.addAll(hashEvents);
        currentDataStore = ds.getEventsMap();
        Assert.assertEquals(resultHelpMap, currentDataStore);


        //remove
        Set<Map.Entry<UUID, Event>> setDataStore = ds.getEventsMap().entrySet();
        int countRemove = 0;
        for (Map.Entry<UUID, Event> keyValue: setDataStore) {
            if (countRemove > 100){
                break;
            }
            Event currentEvent = keyValue.getValue();
            ds.remove(currentEvent.getId());
            resultHelpMap.remove(currentEvent.getId());
            countRemove++;
        }
        currentDataStore = ds.getEventsMap();
        Assert.assertEquals(resultHelpMap, currentDataStore);


        //searchByDescription
        List<Event> resultSearchByDescription = new ArrayList<>();
        Collection<Event> eventsDataStoreSearch = ds.getEventsMap().values();
        int countSearch = 0;
//        for (Event event: eventsDataStoreSearch) {
//            if (countSearch > 600 && countSearch < 900){
//                Event currentEvent = keyValue.getValue();
//                ds.remove(currentEvent.getId());
//                resultHelpMap.remove(currentEvent.getId());
//            }
//
//            countRemove++;
//        }

        resultSearchByDescription.add(util.createEvent(1, date, date, String.valueOf(1) + "_" + "Event",
                String.valueOf(1) + "_" + "DescriptionEvent", attenders));
        resultSearchByDescription.add(util.createEvent(2, date, date, String.valueOf(2) + "_" + "Event",
                String.valueOf(2) + "_" + "DescriptionEvent", attenders));
        resultSearchByDescription.add(util.createEvent(3, date, date, String.valueOf(3) + "_" + "Event",
                String.valueOf(3) + "_" + "DescriptionEvent", attenders));
        resultSearchByDescription.add(util.createEvent(3, date, date, String.valueOf(3) + "_" + "Event",
                String.valueOf(3) + "_" + "DescriptionEvent", attenders));
        resultSearchByDescription.add(util.createEvent(4, date, date, String.valueOf(4) + "_" + "Event",
                String.valueOf(4) + "_" + "DescriptionEvent", attenders));

        List<Event> searchByDescription = ds.searchByDescription("3_DescriptionEvent");
        Assert.assertEquals(resultSearchByDescription, searchByDescription);


        //searchByTitle
        List<Event> resultSearchByTitle = new ArrayList<>();
        resultSearchByTitle.add(util.createEvent(2, date, date, String.valueOf(2) + "_" + "Event",
                String.valueOf(2) + "_" + "DescriptionEvent", attenders));

        List<Event> searchDescription = ds.searchByDescription("2_DescriptionEvent");
        Assert.assertEquals(resultSearchByDescription, searchDescription);




    }


}
