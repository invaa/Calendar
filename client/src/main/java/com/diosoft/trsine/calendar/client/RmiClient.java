package com.diosoft.trsine.calendar.client;

import com.diosoft.trsine.calendar.common.Event;
import com.diosoft.trsine.calendar.exceptions.DateIntervalIsIncorrectException;
import com.diosoft.trsine.calendar.exceptions.IdIsNullException;
import com.diosoft.trsine.calendar.service.CalendarService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.UUID;

/**
 * Rmi client for calendar service.
 *
 * @author  Alexander Zamkovyi
 * @version 1.0
 * @since 1.0
 */
public class RmiClient {
    private CalendarService calendarService;

    public void setService(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    public CalendarService getService() {
        return calendarService;
    }

    public static void main(String[] args) throws IdIsNullException, DateIntervalIsIncorrectException, RemoteException {
        ApplicationContext factory = new ClassPathXmlApplicationContext("ClientContext.xml");
        CalendarService service = ((RmiClient) factory.getBean("rmiClient")).getService();

        System.out.println(service.searchByDescription("Next daily scrum meeting").toString());

    }

}
