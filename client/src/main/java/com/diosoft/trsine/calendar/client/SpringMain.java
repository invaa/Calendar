package com.diosoft.trsine.calendar.client;

import com.diosoft.trsine.calendar.datastore.DataStore;
import com.diosoft.trsine.calendar.datastore.SimpleConcurrentHashMapDataStore;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by a.zamkovyi on 16.09.2014.
 */
public class SpringMain {

   public static void main(String ... args) {
       ApplicationContext appContext =
               new ClassPathXmlApplicationContext("ApplicationContext.xml");

       SimpleConcurrentHashMapDataStore dataStore = (SimpleConcurrentHashMapDataStore) appContext.getBean("dataStore", SimpleConcurrentHashMapDataStore.class);
   }
}
