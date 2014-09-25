package com.diosoft.trsine.calendar.client;

import com.diosoft.trsine.calendar.common.Event;
import com.diosoft.trsine.calendar.exceptions.DateIntervalIsIncorrectException;
import com.diosoft.trsine.calendar.exceptions.IdIsNullException;
import com.diosoft.trsine.calendar.filesystem.EventAdater;
import com.diosoft.trsine.calendar.service.CalendarServiceImpl;
import com.sun.xml.internal.ws.util.Pool;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.Date;
import java.util.UUID;

/**
 * Spring+JAXB main
 */
public class JaxbMain {

   public static void main(String ... args) throws IdIsNullException, DateIntervalIsIncorrectException, JAXBException {

         Event testEvent = new Event.HashSetBuilder()
               .setDateBegin(new Date())
               .setDateEnd(new Date())
               .setId(UUID.randomUUID())
               .setTitle("Daily Scrum")
               .setDescription("Next daily scrum meeting")
               .addAttender("alex@zamkovyi.name")
               .addAttender("igor.vartanian@gmail.com")
               .build();

       EventAdater ea = new EventAdater();
       ea.setFieldsFromEvent(testEvent);

       try {
           String fileName = "D:\\file.xml";
           ea.saveEvent(fileName);

           Event eventUn = ea.readEvent(fileName).getEvent();

           System.out.println(eventUn);

       } catch (JAXBException e) {
           e.printStackTrace();
       }


   }
}
