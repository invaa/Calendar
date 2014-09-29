package com.diosoft.trsine.calendar.init;

import com.diosoft.trsine.calendar.context.ApplicationContextProvider;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * Spring MVC Custom initialization.
 */
public class MVCInitializer extends InternalResourceViewResolver {

    @Override
    public void initApplicationContext() {
        super.initApplicationContext();

        // persistence context should be inserted first

        if (ApplicationContextProvider.getApplicationContext() == null) {

            System.out.println("Init. First run.");

        }
    }

}
