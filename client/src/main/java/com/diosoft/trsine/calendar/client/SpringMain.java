package com.diosoft.trsine.calendar.client;

import com.diosoft.trsine.calendar.common.Event;
import com.diosoft.trsine.calendar.exceptions.DateIntervalIsIncorrectException;
import com.diosoft.trsine.calendar.exceptions.IdIsNullException;
import com.diosoft.trsine.calendar.service.CalendarServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.*;

/**
 * Spring implementation of calendar service client
 */
public class SpringMain {

   public static void main(String ... args) throws IdIsNullException, DateIntervalIsIncorrectException {
       ApplicationContext appContext =
               new ClassPathXmlApplicationContext("src/ApplicationContext.xml");

       CalendarServiceImpl service = (CalendarServiceImpl) appContext.getBean("calendarService", CalendarServiceImpl.class);

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
