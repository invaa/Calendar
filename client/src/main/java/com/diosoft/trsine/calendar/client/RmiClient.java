package com.diosoft.trsine.calendar.client;

import com.diosoft.trsine.calendar.common.Event;
import com.diosoft.trsine.calendar.exceptions.DateIntervalIsIncorrectException;
import com.diosoft.trsine.calendar.exceptions.IdIsNullException;
import com.diosoft.trsine.calendar.service.CalendarService;
import com.diosoft.trsine.calendar.service.CalendarServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;
import java.util.UUID;

/**
 * Rmi client for calendar service
 */
public class RmiClient {
    private CalendarService calendarService;

    public void setService(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    public CalendarService getService() {
        return calendarService;
    }

    public static void main(String[] args) throws IdIsNullException, DateIntervalIsIncorrectException {
        ApplicationContext factory = new ClassPathXmlApplicationContext("ApplicationContext.xml");
        CalendarService service = ((RmiClient) factory.getBean("rmiClient")).getService();

         Event testEvent = new Event.HashSetBuilder()
                .setDateBegin(new Date())
                .setDateEnd(new Date())
                .setId(UUID.randomUUID())
                .setTitle("Daily Scrum")
                .setDescription("Next daily scrum meeting")
                .addAttender("alex@zamkovyi.name")
                .addAttender("igor.vartanian@gmail.com")
                .build();

        service.add(testEvent);

        System.out.println(service.searchByDescription("Next daily scrum meeting").toString());

        //find  all occurencies by description and remove by ids
        service.searchByDescription("Next daily scrum meeting")
                .parallelStream()
                .forEach(p -> service.remove(p.getId()));

        System.out.println(service.searchByDescription("Next daily scrum meeting").toString());

    }

}
