package com.diosoft.trsine.calendar.init;

import com.diosoft.trsine.calendar.common.Event;
import com.diosoft.trsine.calendar.context.ApplicationContextProvider;
import com.diosoft.trsine.calendar.exceptions.DateIntervalIsIncorrectException;
import com.diosoft.trsine.calendar.exceptions.IdIsNullException;
import com.diosoft.trsine.calendar.service.CalendarServiceImpl;
import com.diosoft.trsine.calendar.util.DateHelper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.Date;
import java.util.UUID;

/**
 * Created by a.zamkovyi on 21.09.2014.
 */
public class MVCInitializer extends InternalResourceViewResolver {

    @Override
    public void initApplicationContext() {
        super.initApplicationContext();

        if (ApplicationContextProvider.getApplicationContext() == null) {

            System.out.println("Init. First run.");

            //Initialize data store with some events
            ApplicationContext appContext =
                    new ClassPathXmlApplicationContext("ApplicationContext.xml");

            CalendarServiceImpl service = (CalendarServiceImpl) appContext.getBean("calendarService", CalendarServiceImpl.class);

            Event event1 = null, event2 = null, event3 = null;

            try {
                event1 = new Event.HashSetBuilder()
                        .setDateBegin(DateHelper.getDateFromSimpleString("2014-09-26 10:40:01"))
                        .setDateEnd(DateHelper.getDateFromSimpleString("2014-09-26 11:40:01"))
                        .setId(UUID.randomUUID())
                        .setTitle("Daily Scrum")
                        .setDescription("Next daily scrum meeting")
                        .addAttender("alex@zamkovyi.name")
                        .addAttender("igor.vartanian@gmail.com")
                        .build();
                event2 = new Event.HashSetBuilder(event1)
                        .setTitle("Work")
                        .setDateBegin(DateHelper.getDateFromSimpleString("2014-09-25 10:40:01"))
                        .setDateEnd(DateHelper.getDateFromSimpleString("2014-09-25 11:40:01"))
                        .setId(UUID.randomUUID())
                        .build();
                event3 = new Event.HashSetBuilder(event1)
                        .setTitle("Holidays")
                        .setDateBegin(DateHelper.getDateFromSimpleString("2014-09-10 10:40:01"))
                        .setDateEnd(DateHelper.getDateFromSimpleString("2014-09-10 11:40:01"))
                        .setId(UUID.randomUUID())
                        .build();
            } catch (IdIsNullException e) {
                e.printStackTrace();
            } catch (DateIntervalIsIncorrectException e) {
                e.printStackTrace();
            }

            service.add(event1);
            service.add(event2);
            service.add(event3);
        }
    }

}
