package com.diosoft.trsine.calendar.datastore;

import com.diosoft.trsine.calendar.common.Event;
import com.diosoft.trsine.calendar.exceptions.IncorrectPeriodDates;
import com.diosoft.trsine.calendar.service.CalendarServiceImp;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class DataStoreTest {

    @Test
    public void testDataStore() throws IncorrectPeriodDates {
        Event testEvent = new Event.Builder() {
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
                .addAttender("igor.vartanian@gmail.com")
                .build();

        DataStore dataStore = new DataStoreImp();
        CalendarServiceImp service = new CalendarServiceImp(dataStore);
        service.add(testEvent);
    }

}
