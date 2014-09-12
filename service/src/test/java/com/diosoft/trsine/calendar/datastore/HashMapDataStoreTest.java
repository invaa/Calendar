package com.diosoft.trsine.calendar.datastore;

import com.diosoft.trsine.calendar.common.Event;
import com.diosoft.trsine.calendar.exceptions.IncorrectPeriodDates;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class HashMapDataStoreTest {

    int indexTest = 400000;

    OrganizerUtil util = new OrganizerUtil();
    DataStore ds = new HashMapDataStoreImp();

    //expectedHelpMap
    Map<UUID, Event> resultHelpMap = new HashMap<>();

    //this event will be remove, search
    Event rememberEvent;

    //current data in store
    Map<UUID, Event> currentDataStore;

    //collectionToAddAll
    HashSet<Event> hashEvents = new HashSet<>();

    //result searchByDescription
    List<Event> resultSearchByDescription = new ArrayList<>();

    //result searchByTitle
    List<Event> resultSearchByTitle = new ArrayList<>();

    //result searchByDate
    List<Event> resultSearchByDate = new ArrayList<>();

    @Before
    public void fillData() throws IncorrectPeriodDates {

        Date date = new GregorianCalendar(2014, 10, 12, 10, 0, 0).getTime();
        HashSet<String> attenders = new HashSet<>();

        for (int i = 0; i <= 10; i++) {
            attenders.add(String.valueOf(i) + "_" + "User");
        }
        for (int i = 0; i < 1000000; i++) {
            Event eventAdd = util.createEvent(i, date, date, String.valueOf(i) + "_" + "Event",
                    String.valueOf(i) + "_" + "DescriptionEvent", attenders);
            resultHelpMap.put(eventAdd.getId(), eventAdd);
            ds.add(eventAdd);
            if (i == indexTest) {
                rememberEvent = eventAdd;
            }
            //expected SearchByDescription
            if (eventAdd.getDescription() == String.valueOf(indexTest) + "_" + "DescriptionEvent") {
                resultSearchByDescription.add(eventAdd);
            }
            //expected SearchByTitle
            if (eventAdd.getTitle() == String.valueOf(indexTest) + "_" + "Event") {
                resultSearchByTitle.add(eventAdd);
            }
            //expected SearchByDate
            if (eventAdd.getDateBegin() == date) {
                resultSearchByDate.add(eventAdd);
            }
        }

        //data for addAll
        for (int i = 0; i < 500000; i++) {
            Event eventAddAll = util.createEvent(i, date, date, String.valueOf(i) + "_" + "Event",
                    String.valueOf(i) + "_" + "DescriptionEvent", attenders);
            hashEvents.add(eventAddAll);
            resultHelpMap.put(eventAddAll.getId(), eventAddAll);
            //expected SearchByDescription
            if (eventAddAll.getDescription() == String.valueOf(indexTest) + "_" + "DescriptionEvent") {
                resultSearchByDescription.add(eventAddAll);
            }
            //expected SearchByTitle
            if (eventAddAll.getTitle() == String.valueOf(indexTest) + "_" + "Event") {
                resultSearchByTitle.add(eventAddAll);
            }
            //expected SearchByDate
            if (eventAddAll.getDateBegin() == date) {
                resultSearchByDate.add(eventAddAll);
            }

        }

    }

    @After
    public void clearData() {
        ds = new HashMapDataStoreImp();
        resultHelpMap = new HashMap<>();
    }

    @Test(timeout = 10)
    public void testAddDataStore() {

        currentDataStore = ds.getDataStore();
        Assert.assertEquals(resultHelpMap, currentDataStore);

    }

    @Test(timeout = 500)
    public void testAddAllDataStore() {

        ds.addAll(hashEvents);
        currentDataStore = ds.getDataStore();
        Assert.assertEquals(resultHelpMap, currentDataStore);

    }

    @Test(timeout = 10)
    public void testRemoveDataStore() {

        ds.remove(rememberEvent.getId());
        resultHelpMap.remove(rememberEvent.getId());
        currentDataStore = ds.getDataStore();
        Assert.assertEquals(resultHelpMap, currentDataStore);

    }

    @Test(timeout = 10)
    public void testSearchByDescriptionDataStore() {

        List<Event> searchByDescription = ds.searchByDescription(String.valueOf(indexTest) + "_" + "DescriptionEvent");
        Assert.assertEquals(resultSearchByDescription, searchByDescription);

    }

    @Test(timeout = 10)
    public void testSearchByTitleDataStore() {

        List<Event> resultSearchByTitle = ds.searchByTitle(String.valueOf(indexTest) + "_" + "Event");
        Assert.assertEquals(resultSearchByTitle, resultSearchByTitle);

    }

    @Test(timeout = 10)
    public void testSearchByDateDataStore() {

        Date dateSearch = new GregorianCalendar(2014, 10, 12, 10, 0, 0).getTime();
        List<Event> resultSearchByDate = ds.searchByDay(dateSearch);
        Assert.assertEquals(resultSearchByDate, resultSearchByDate);

    }


}
