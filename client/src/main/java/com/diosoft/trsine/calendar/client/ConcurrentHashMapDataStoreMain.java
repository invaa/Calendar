package com.diosoft.trsine.calendar.client;

import com.diosoft.trsine.calendar.common.Event;
import com.diosoft.trsine.calendar.datastore.ConcurrentHashMapDataStore;
import com.diosoft.trsine.calendar.service.CalendarServiceImp;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Basic demo of how to cope with the Calendar service and ArrayList data store
 *
 * @author  Alexander Zamkovyi
 * @since 1.8
 */

public class ConcurrentHashMapDataStoreMain {

    public static void main(String ... args) {
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
                .addParticipant("alex@zamkovyi.name")
                .addParticipant("igor.vartanian@gmail.com")
                .build();

        CalendarServiceImp service = new CalendarServiceImp(new ConcurrentHashMapDataStore() {
            @Override
            public Set<UUID> newSet() {
                return new HashSet<>();
            }
        });
        service.add(testEvent);

        //find  all occurencies by description and remove by ids
        service.searchByDescription("Next daily scrum meeting")
                .parallelStream()
                .forEach(p -> service.remove(p.getId()));

        System.out.println(service.searchByDescription("Next daily scrum meeting").toString());
    }

}
