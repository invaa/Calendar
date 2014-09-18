package com.diosoft.trsine.calendar.server;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Rmi Spring Server implementation
 *
 * @author a.zamkovyi
 * @version 1.0
 * @since 1.0
 */
public class RmiServer {
    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("xml-beans.xml");
    }
}
