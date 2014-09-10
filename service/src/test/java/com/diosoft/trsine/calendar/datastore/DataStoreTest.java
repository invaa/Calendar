package com.diosoft.trsine.calendar.datastore;

import com.diosoft.trsine.calendar.common.Event;
import com.diosoft.trsine.calendar.common.TestEventChield;
import com.diosoft.trsine.calendar.comparison.ComparatorDateBegin;
import com.diosoft.trsine.calendar.exceptions.IncorrectPeriodDates;
import com.diosoft.trsine.calendar.service.CalendarServiceImp;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class DataStoreTest {

    @Test
    public void testDataStore() throws IncorrectPeriodDates {

//        Event testEvent = new Event.Builder() {
//            @Override
//            public Set newSet() {
//                return new HashSet<String>();
//            }
//        }//end of Builder implementation
//                .setDateBegin(new Date())
//                .setDateEnd(new Date())
//                .setTitle("Daily Scrum")
//                .setDescription("Next daily scrum meeting")
//                .addAttender("alex@zamkovyi.name")
//                .addAttender("igor.vartanian@gmail.com")
//                .build();
//
//        DataStore dataStore = new DataStoreImp();
//        CalendarServiceImp service = new CalendarServiceImp(dataStore);
//        service.add(testEvent);


        Event.Builder builder = new Event.Builder() {
            @Override
            public Set newSet() {
                return new HashSet<String>();
            }
        }//end of Builder implementation
                .setDateBegin(new Date())
                .setDateEnd(new Date())
                .setTitle("Daily Scrum")
                .setDescription("Next daily scrum meeting")
                .addAttender("alex@zamkovyi.name")
                .addAttender("igor.vartanian@gmail.com");
        Event testEvent = builder.build();

        DataStore dataStore = new DataStoreImp();
        CalendarServiceImp service = new CalendarServiceImp(dataStore);
        service.add(testEvent);
        TestEventChield testEvent2 = new TestEventChield(builder);


        ComparatorDateBegin compar = new ComparatorDateBegin();
        compar.compare(testEvent, testEvent2);



    }

}
