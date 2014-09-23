package com.diosoft.trsine.calendar.datastore;

import com.diosoft.trsine.calendar.common.Event;
import com.diosoft.trsine.calendar.exceptions.IncorrectPeriodDates;
import org.junit.*;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class HashMapDataStoreTest {

    //help util
    OrganizerUtil util = new OrganizerUtil();

    //dataStore
    HashMapDataStoreImp ds;

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


    @Before
    public void fillData() throws IncorrectPeriodDates {

        Date date = new GregorianCalendar(2014, 10, 12, 10, 0, 0).getTime();
        ds = new HashMapDataStoreImp();
        Set<String> attenders = util.createAttenders(10, "User");

        eventAdd0 = util.createEvent(0, util.createDate(2014, 10, 1), util.createDate(2014, 10, 1), "Event", "DescriptionEvent", attenders);
        eventAdd1 = util.createEvent(1, util.createDate(2014, 10, 1), util.createDate(2014, 10, 1), "Birthday", "DescriptionEvent", attenders);
        eventAdd2 = util.createEvent(2, util.createDate(2014, 10, 1), util.createDate(2014, 10, 1), "Meeting", "DescriptionEvent", attenders);
        eventAdd3 = util.createEvent(1, util.createDate(2014, 10, 1), util.createDate(2014, 10, 1), "Event", "DescriptionEvent", attenders);
        eventAdd4 = util.createEvent(3, util.createDate(2014, 10, 1), util.createDate(2014, 10, 1), "Meeting", "DescriptionEvent", attenders);
        eventAddNull = null;

    }

    @After
    public void clearData() {

        resultHelpMap = new HashMap<>();
    }

    @Test
    public void testDataStoreAdd() {

        ds.add(eventAdd0);
        ds.add(eventAdd1);

        resultHelpMap.put(eventAdd0.getId(), eventAdd0);
        resultHelpMap.put(eventAdd1.getId(), eventAdd1);

        currentDataStore = ds.getDataStore();
        assertEquals(resultHelpMap, currentDataStore);

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
        assertEquals(resultHelpMap, currentDataStore);

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
        assertEquals(resultHelpMap, currentDataStore);

    }

    @Test
    public void testDataStoreSearchByDescription() {

        ds.add(eventAdd0);
        ds.add(eventAdd1);
        ds.add(eventAdd2);
        ds.add(eventAdd3);
        ds.add(eventAdd4);

        //result searchByDescription
        List<Event> resultSearchByDescription = new ArrayList<>();

        resultSearchByDescription.add(eventAdd2);

        List<Event> currentResult = ds.searchByDescription(eventAdd2.getDescription());
        assertEquals(resultSearchByDescription, currentResult);

    }

    @Test
    public void testSearchByTitleDataStore() {

        ds.add(eventAdd0);
        ds.add(eventAdd1);
        ds.add(eventAdd2);
        ds.add(eventAdd3);
        ds.add(eventAdd4);

        //result searchByTitle
        List<Event> resultSearchByTitle = new ArrayList<>();

        resultSearchByTitle.add(eventAdd2);

        List<Event> currentResult = ds.searchByTitle(eventAdd2.getTitle());
        assertEquals(resultSearchByTitle, currentResult);

    }

    @Test
    public void testSearchByDateDataStore() {

        ds.add(eventAdd0);
        ds.add(eventAdd1);
        ds.add(eventAdd2);
        ds.add(eventAdd3);
        ds.add(eventAdd4);

        //result searchByDate
        List<Event> resultSearchByDate = new ArrayList<>();

        resultSearchByDate.add(eventAdd0);
        resultSearchByDate.add(eventAdd1);
        resultSearchByDate.add(eventAdd2);
        resultSearchByDate.add(eventAdd3);
        resultSearchByDate.add(eventAdd4);

        List<Event> currentResult = ds.searchByDay(eventAdd2.getDateBegin());
        assertEquals(resultSearchByDate, currentResult);

    }

    @Test
    public void testSearchWithStartTitle() {

        ds.add(eventAdd0);
        ds.add(eventAdd1);
        ds.add(eventAdd2);
        ds.add(eventAdd3);
        ds.add(eventAdd4);
        ds.add(eventAddNull);

        List<Event> resultSearchWithStartTitle = new ArrayList<>();

        resultSearchWithStartTitle.add(eventAdd1);
        resultSearchWithStartTitle.add(eventAdd3);

        List<Event> expectedResult = ds.searchWithStartTitle("1_");

        assertEquals(resultSearchWithStartTitle, expectedResult);

    }


    @Test
    public void testSearchWithStartTitleNull() {

        ds.add(eventAdd0);
        ds.add(eventAdd1);
        ds.add(eventAdd2);
        ds.add(eventAdd3);
        ds.add(eventAdd4);
        ds.add(eventAddNull);

        List<Event> resultSearchWithStartTitle = new ArrayList<>();


        List<Event> expectedResult = ds.searchWithStartTitle(null);

        assertEquals(resultSearchWithStartTitle, expectedResult);

    }

    @Test
    public void testSearchByAttender(String attender) {

        ds.add(eventAdd0);
        ds.add(eventAdd1);
        ds.add(eventAdd2);
        ds.add(eventAdd3);
        ds.add(eventAdd4);
        ds.add(eventAddNull);

        List<Event> resultSearchByAttender = new ArrayList<>();


        List<Event> expectedResult = ds.searchByAttender(null);

        assertEquals(resultSearchByAttender, expectedResult);

    }

}
