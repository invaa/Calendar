package com.diosoft.trsine.calendar.client;

import com.diosoft.trsine.calendar.common.Event;
import com.diosoft.trsine.calendar.datastore.ConcurrentHashMapDataStore;
import com.diosoft.trsine.calendar.exceptions.DateIntervalIsIncorrectException;
import com.diosoft.trsine.calendar.exceptions.IdIsNullException;
import com.diosoft.trsine.calendar.service.CalendarServiceImp;
import com.diosoft.trsine.calendar.util.DateHelper;

import java.util.*;

/**
 * Basic demo of how to cope with the Calendar service and ConcurrentHashMap data store
 *
 * @author  Alexander Zamkovyi
 * @since 1.8
 */

public class ConcurrentHashMapDataStoreMain {

    public static void main(String ... args) throws IdIsNullException, DateIntervalIsIncorrectException {

        boolean flagInputParametersProvided = args.length > 0;

        ArrayList<Event> eventCollection = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);
        String input;

        int eventsToEnter;

        if (!flagInputParametersProvided) {
            System.out.print("Please enter the number of events to create: ");
            input = scanner.nextLine();
            eventsToEnter = Integer.valueOf(input);
        } else {
            eventsToEnter = args.length;
        }//end if

        for (int i = 0; i < eventsToEnter; i++) {
            Event.HashSetBuilder builder = new Event.HashSetBuilder();
            boolean flagDataEntered = false;
            while (!flagDataEntered) {
                System.out.println("Creating event №: " + (i + 1));

                System.out.println("Please enter the event title: ");
                input = scanner.nextLine();
                builder.setTitle(input);

                if (!flagInputParametersProvided) {
                    System.out.println("Please enter the event description: ");
                    input = scanner.nextLine();
                    builder.setDescription(input);
                } else {
                    builder.setDescription(args[i]);
                } //end if
                System.out.println("Please enter the beginning date (in \"yyyy-MM-dd HH:mm:ss\", 24h format): ");
                input = scanner.nextLine();
                builder.setDateBegin(DateHelper.getDateFromSimpleString(input));

                System.out.println("Please enter the end date (in \"yyyy-MM-dd HH:mm:ss\", 24h format): ");
                input = scanner.nextLine();
                builder.setDateEnd(DateHelper.getDateFromSimpleString(input));

                int emailsToEnter;
                System.out.print("Enter the number of attenders: ");
                input = scanner.nextLine();
                emailsToEnter = Integer.valueOf(input);

                for (int j = 0; j < emailsToEnter; j++) {
                    System.out.println("Entering email of attender №: " + (j + 1));
                    input = scanner.nextLine();
                    builder.addAttender(input);
                } //end for

                builder.setId(UUID.randomUUID());

                try {
                    eventCollection.add(builder.build());
                } catch (IdIsNullException | DateIntervalIsIncorrectException e) {
                    System.out.println("Error creating event " + e.getMessage());
                    continue;
                }
                flagDataEntered = true;
            }
        } //end for
        Event testEvent = new Event.HashSetBuilder()
                .setDateBegin(new Date())
                .setDateEnd(new Date())
                .setId(UUID.randomUUID())
                .setTitle("Daily Scrum")
                .setDescription("Next daily scrum meeting")
                .addAttender("alex@zamkovyi.name")
                .addAttender("igor.vartanian@gmail.com")
                .build();

        CalendarServiceImp service = new CalendarServiceImp(new ConcurrentHashMapDataStore() {
            @Override
            public Set<UUID> newUUIDSet()  {
                return new HashSet<>();
            }

            @Override
            public List<Event> newResultList()  { return new ArrayList<>(); }
        });
        service.add(testEvent);
        service.addAll(eventCollection);

        System.out.println(service.searchByDescription("Next daily scrum meeting").toString());

        //find  all occurencies by description and remove by ids
        service.searchByDescription("Next daily scrum meeting")
                .parallelStream()
                .forEach(p -> service.remove(p.getId()));

        System.out.println(service.searchByDescription("Next daily scrum meeting").toString());
    }

}
