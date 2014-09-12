package com.diosoft.trsine.calendar.service;

import com.diosoft.trsine.calendar.common.Event;

import java.util.*;

//private Scanner console;
public CalendarService();

public interface CalendarService {
    static ArrayList<Event> eventList = new ArrayList<Event>();
    enum eventKeys {description, attenders, dateBegin, dateEnd}
    public static void main(String[] args) throws ParseException {
        System.out.println('Start');
        ArrayList<String>listOfAttenders = new ArrayList<String>();
        listOfAttenders.add('1@gmail.com');
        listOfAttenders.add('2@gmail.com');
        listOfAttenders.add('3@gmail.com');
        listOfAttenders.add('4@gmail.com');

        eventList.add(new Event.Builder() {
            @Override
            public Set newSet() {
                return null;
            }

            @Override
            public Event.Builder setUid(UUID uuid) {
                return null;
            }
        }
                .setUid(UUID.randomUUID())
                .setDescription("music festival")
                .setDateBegin(10 / 10 / 14)
                .setDateEnd(10 / 10 / 14)
                .setListOfAttenders(listOfAttenders)
                .build();

         ArrayList<String> listOfAttenders2 =  new ArrayList<String>();
        listOfAttenders2.add("Ben");
        listOfAttenders2.add("John");
        listOfAttenders2.add("Mary");
        listOfAttenders2.add("Joane");

        eventList.add(new Event.Builder() {
                    @Override
                    public Set newSet() {
                        return null;
                    }
                }
                        .setUid(UUID.randomUUID())
                        .setDescription("film festival")
                        .setDateBegin(20 / 10 / 14)
                        .setDateEnd(20 / 10 / 14)
                        .setListOfAttenders(listOfAttenders2)
                        .build()
        );
        System.out.println(eventList);





/*public double getCost();
    CalendarService iServiceImp = new CalendarServiceImp();
   /*
    add.
    iServiceImp. getcost()
    */
 /* private Map<cinema, Korean week>hashmap = new Hashmap<cinema, Korean week>();
   Map<music, concert OE>hashmap = new Hashmap<music, concert OE>();
   Map<drink, Oktoberfest>hashmap = new Hashmap<drink, Oktoberfest>();
   Map<culture, Gogolfest>hashmap = new Hashmap<culture, Gogolfest>();

    public Event getEvent(String id) {
        return (Event)getEvent().get(id);

    void add(Event event);
    void remove(UUID id);
    List<Event> searchByTitle(String title);
    List<Event> searchByDay(Date day);*/
}
/*public class CustomerEntity extends TableServiceEntity {
    public CustomerEntity(String lastName, String firstName) {
        this.partitionKey = lastName;
        this.rowKey = firstName;
    }

    public CustomerEntity() { }

    String email;
    String phoneNumber;

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;*/
    }
}