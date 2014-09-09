package com.diosoft.trsine.calendar.client;

import com.diosoft.trsine.calendar.common.Event;
import com.diosoft.trsine.calendar.datastore.ArrayListDataStore;
import com.diosoft.trsine.calendar.exceptions.DateIntervalIsIncorrectException;
import com.diosoft.trsine.calendar.exceptions.IdIsNullException;
import com.diosoft.trsine.calendar.service.CalendarServiceImp;

import java.util.Date;
import java.util.UUID;

/**
 * Basic demo of how to cope with the Calendar service and ArrayList data store
 *
 * @author  Alexander Zamkovyi
 * @since 1.8
 */

public class ArrayListDataStoreMain {

    public static void main(String ... args) throws IdIsNullException, DateIntervalIsIncorrectException {
        Event testEvent1 = new Event.HashSetBuilder()
                .setDateBegin(new Date())
                .setDateEnd(new Date())
                .setId(UUID.randomUUID())
                .setTitle("Daily Scrum")
                .setDescription("Next daily scrum meeting")
                .addAttender("alex@zamkovyi.name")
                .addAttender("igor.vartanian@gmail.com")
                .build();

        Event testEvent2 = new Event.HashSetBuilder(testEvent1)
                .setDateBegin(new Date())
                .setDateEnd(new Date())
                .setId(UUID.randomUUID())
                .setTitle("New event")
                .setDescription("Next scheduled event")
                .build();

        CalendarServiceImp service = new CalendarServiceImp(new ArrayListDataStore());
        service.add(testEvent1);
        service.add(testEvent2);

        //find  all occurencies by description and remove by ids
        service.searchByDescription("Next daily scrum meeting")
                .parallelStream()
                .forEach(p -> service.remove(p.getId()));

        System.out.println(service.searchByDescription("Next daily scrum meeting").toString());
    }

}
