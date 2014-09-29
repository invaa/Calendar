package com.diosoft.trsine.calendar.server;

import com.diosoft.trsine.calendar.service.CalendarService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.rmi.RemoteException;

/**
 * Rmi Spring Server implementation
 *
 * @author a.zamkovyi
 * @version 1.0
 * @since 1.0
 */
public class RmiServer {
    public static void main(String[] args) throws RemoteException {
        ApplicationContext factory = new ClassPathXmlApplicationContext("xml-beans.xml");
        //load from filepath
        CalendarService service = (CalendarService) factory.getBean("calendarService");
        service.init();
    }
}
