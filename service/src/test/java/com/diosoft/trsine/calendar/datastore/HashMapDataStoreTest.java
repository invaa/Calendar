package com.diosoft.trsine.calendar.datastore;

import com.diosoft.trsine.calendar.common.Event;
import com.diosoft.trsine.calendar.exceptions.IncorrectPeriodDates;
import org.junit.*;

import java.util.*;

public class HashMapDataStoreTest {

    int indexTest = 2;

    OrganizerUtil util = new OrganizerUtil();
    DataStore dsAdd = new HashMapDataStoreImp();
    DataStore dsAddAll = new HashMapDataStoreImp();

    //expectedHelpMap
    Map<UUID, Event> resultHelpMapAdd = new HashMap<>();
    Map<UUID, Event> resultHelpMapAddAll = new HashMap<>();

    //this event will be remove, search
    Event rememberEvent;

    //current data in store
    Map<UUID, Event> currentDataStore;

    //collectionToAddAll
    HashSet<Event> hashEvents = new HashSet<>();

    //result searchByDescription
    List<Event> resultSearchByDescription;

    //result searchByTitle
    List<Event> resultSearchByTitle;

    //result searchByDate
    List<Event> resultSearchByDate;

    @Before
    public void fillData() throws IncorrectPeriodDates {

        Date date = new GregorianCalendar(2014, 10, 12, 10, 0, 0).getTime();

        Set<String> attenders = util.createAttenders(10, "User");

        Event eventAdd0 = util.createEvent(0, date, date, "Event", "DescriptionEvent", attenders);
        Event eventAdd1 = util.createEvent(1, date, date, "Event", "DescriptionEvent", attenders);
        Event eventAdd2 = util.createEvent(2, date, date, "Event", "DescriptionEvent", attenders);
        Event eventAdd3 = util.createEvent(1, date, date, "Event", "DescriptionEvent", attenders);
        Event eventAdd4 = util.createEvent(3, date, date, "Event", "DescriptionEvent", attenders);
        Event eventAdd5 = util.createEvent(4, date, date, "Event", "DescriptionEvent", attenders);

        //data for Add
        for (int i = 0; i < 2; i++) {
            Event eventAdd = util.createEvent(i, date, date, "Event", "DescriptionEvent", attenders);
            resultHelpMapAdd.put(eventAdd.getId(), eventAdd);
            dsAdd.add(eventAdd);
            if (i == indexTest) {
                rememberEvent = eventAdd;
            }
            addResultSearch(date, eventAdd);

        }

        //data for addAll
        for (int i = 0; i < 1; i++) {

            Event eventAddAll = util.createEvent(i, date, date, "Event", "DescriptionEvent", attenders);
            hashEvents.add(eventAddAll);
            resultHelpMapAddAll.put(eventAddAll.getId(), eventAddAll);

            addResultSearch(date, eventAddAll);

        }

    }

    private void addResultSearch(Date date, Event eventAdd) {

        //expected SearchByDescription
        if (eventAdd.getDescription() == String.valueOf(indexTest) + "_" + "DescriptionEvent") {
            if (resultSearchByDescription == null) {
                resultSearchByDescription = new ArrayList<>();
            }

            resultSearchByDescription.add(eventAdd);
        }
        //expected SearchByTitle
        if (eventAdd.getTitle() == String.valueOf(indexTest) + "_" + "Event") {
            if (resultSearchByTitle == null) {
                resultSearchByTitle = new ArrayList<>();
            }
            resultSearchByTitle.add(eventAdd);
        }
        //expected SearchByDate
        if (eventAdd.getDateBegin() == date) {
            if (resultSearchByDate == null) {
                resultSearchByDate = new ArrayList<>();
            }

            resultSearchByDate.add(eventAdd);
        }
    }

    @After
    public void clearData() {
        dsAdd = new HashMapDataStoreImp();
        dsAddAll = new HashMapDataStoreImp();
        resultHelpMapAdd = new HashMap<>();
        resultHelpMapAddAll = new HashMap<>();
    }

    @Test(timeout = 1000)
    public void testAddDataStore() {

        currentDataStore = dsAdd.getDataStore();
        Assert.assertEquals(resultHelpMapAdd, currentDataStore);

    }

    @Test(timeout = 5000)
    public void testAddAllDataStore() {

        dsAddAll.addAll(hashEvents);
        currentDataStore = dsAddAll.getDataStore();
        Assert.assertEquals(resultHelpMapAddAll, currentDataStore);

    }

    @Ignore
    @Test(timeout = 1000)
    public void testRemoveDataStore() {

        if (rememberEvent == null){
            Assert
        }

        dsAdd.remove(rememberEvent.getId());
        resultHelpMapAdd.remove(rememberEvent.getId());
        currentDataStore = dsAdd.getDataStore();
        Assert.assertEquals(resultHelpMapAdd, currentDataStore);

    }

    @Test(timeout = 1000)
    public void testSearchByDescriptionDataStore() {

        List<Event> searchByDescription = dsAdd.searchByDescription(String.valueOf(indexTest) + "_" + "DescriptionEvent");
        if (resultSearchByDescription == null) {
            Assert.assertTrue(resultSearchByDescription == searchByDescription);
        }else{
            Assert.assertEquals(resultSearchByDescription, searchByDescription);
        }


    }

    @Test(timeout = 1000)
    public void testSearchByTitleDataStore() {

        List<Event> resultSearchByTitle = dsAdd.searchByTitle(String.valueOf(indexTest) + "_" + "Event");
        Assert.assertEquals(resultSearchByTitle, resultSearchByTitle);

    }

    @Test(timeout = 1000)
    public void testSearchByDateDataStore() {

        Date dateSearch = new GregorianCalendar(2014, 10, 12, 10, 0, 0).getTime();
        List<Event> resultSearchByDate = dsAdd.searchByDay(dateSearch);
        Assert.assertEquals(resultSearchByDate, resultSearchByDate);

    }


}
