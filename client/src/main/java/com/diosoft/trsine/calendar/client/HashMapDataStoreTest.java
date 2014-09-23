package com.diosoft.trsine.calendar.client;

import com.diosoft.trsine.calendar.common.Event;
import com.diosoft.trsine.calendar.datastore.HashMapDataStoreImp;
import com.diosoft.trsine.calendar.exceptions.IncorrectPeriodDates;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class HashMapDataStoreTest {

    //help util
    OrganizerUtil util = new OrganizerUtil();

    //dataStore
    HashMapDataStoreImp ds = new HashMapDataStoreImp();

    //events
    Event eventAdd0;
    Event eventAdd1;
    Event eventAdd2;
    Event eventAdd3;
    Event eventAdd4;
    Event eventAddNull;


    //expectedHelpMap
    Map<UUID, Event> resultHelpMap = new HashMap<>();

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

        Set<String> attenders = util.createAttenders(10, "User");

        eventAdd0 = util.createEvent(0, util.createDate(2014, 10, 1), util.createDate(2014, 10, 1), "Event", "DescriptionEvent", attenders);
        eventAdd1 = util.createEvent(1, util.createDate(2014, 10, 1), util.createDate(2014, 10, 1), "Event", "DescriptionEvent", attenders);
        eventAdd2 = util.createEvent(2, util.createDate(2014, 10, 1), util.createDate(2014, 10, 1), "Event", "DescriptionEvent", attenders);
        eventAdd3 = util.createEvent(1, util.createDate(2014, 10, 1), util.createDate(2014, 10, 1), "Event", "DescriptionEvent", attenders);
        eventAdd4 = util.createEvent(3, util.createDate(2014, 10, 1), util.createDate(2014, 10, 1), "Event", "DescriptionEvent", attenders);
        eventAddNull = null;

    }

    @After
    public void clearData() {
        ds = new HashMapDataStoreImp();
        resultHelpMap = new HashMap<>();
    }

    @Test
    public void testDataStoreAdd() {

        ds.add(eventAdd0);
        ds.add(eventAdd1);

        resultHelpMap.put(eventAdd0.getId(), eventAdd0);
        resultHelpMap.put(eventAdd1.getId(), eventAdd1);

        currentDataStore = ds.getDataStore();
        Assert.assertEquals(resultHelpMap, currentDataStore);

    }

    @Test
    public void testDataStoreAddAll() {

        hashEvents.add(eventAdd0);
        hashEvents.add(eventAdd1);
        hashEvents.add(eventAdd2);
        hashEvents.add(eventAdd3);
        hashEvents.add(eventAdd4);

        resultHelpMap.put(eventAdd0.getId(), eventAdd0);
        resultHelpMap.put(eventAdd1.getId(), eventAdd1);
        resultHelpMap.put(eventAdd2.getId(), eventAdd2);
        resultHelpMap.put(eventAdd3.getId(), eventAdd3);
        resultHelpMap.put(eventAdd4.getId(), eventAdd4);

        ds.addAll(hashEvents);
        currentDataStore = ds.getDataStore();
        Assert.assertEquals(resultHelpMap, currentDataStore);

    }

    @Test
    public void testDataStoreRemove() {

        ds.add(eventAdd0);
        ds.add(eventAdd1);
        ds.add(eventAdd2);
        ds.add(eventAdd3);

        resultHelpMap.put(eventAdd0.getId(), eventAdd0);
        resultHelpMap.put(eventAdd2.getId(), eventAdd2);
        resultHelpMap.put(eventAdd3.getId(), eventAdd3);

        ds.remove(eventAdd1.getId());
        currentDataStore = ds.getDataStore();
        Assert.assertEquals(resultHelpMap, currentDataStore);

    }

    @Test
    public void testDataStoreSearchByDescription() {

        ds.add(eventAdd0);
        ds.add(eventAdd1);
        ds.add(eventAdd2);
        ds.add(eventAdd3);
        ds.add(eventAdd4);

        resultSearchByDescription.add(eventAdd2);

        List<Event> currentResult = ds.searchByDescription(eventAdd2.getDescription());
        Assert.assertEquals(resultSearchByDescription, currentResult);

    }

    @Test
    public void testSearchByTitleDataStore() {

        ds.add(eventAdd0);
        ds.add(eventAdd1);
        ds.add(eventAdd2);
        ds.add(eventAdd3);
        ds.add(eventAdd4);

        resultSearchByTitle.add(eventAdd2);

        List<Event> currentResult = ds.searchByTitle(eventAdd2.getTitle());
        Assert.assertEquals(resultSearchByTitle, currentResult);

    }

    @Test
    public void testSearchByDateDataStore() {

        ds.add(eventAdd0);
        ds.add(eventAdd1);
        ds.add(eventAdd2);
        ds.add(eventAdd3);
        ds.add(eventAdd4);

        resultSearchByDate.add(eventAdd0);
        resultSearchByDate.add(eventAdd1);
        resultSearchByDate.add(eventAdd2);
        resultSearchByDate.add(eventAdd3);
        resultSearchByDate.add(eventAdd4);

        List<Event> currentResult = ds.searchByDay(eventAdd2.getDateBegin());
        Assert.assertEquals(resultSearchByDate, currentResult);

    }


}
