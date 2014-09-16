package com.diosoft.trsine.calendar.client;

import com.diosoft.trsine.calendar.datastore.DataStore;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by a.zamkovyi on 16.09.2014.
 */
public class SpringMain {

    ApplicationContext appContext =
            new ClassPathXmlApplicationContext("ApplicationContext.xml");

    DataStore dataStore = (DataStore)appContext.getBean("dataStore");


}
