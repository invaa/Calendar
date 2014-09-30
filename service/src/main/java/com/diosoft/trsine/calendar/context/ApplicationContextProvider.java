package com.diosoft.trsine.calendar.context;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Custom context for Spring MVC.
 *
 * @author  Alexander Zamkovyi
 * @version 1.0
 * @since 1.0
 */
public class ApplicationContextProvider implements ApplicationContextAware {
    private static ApplicationContext context;

    public static ApplicationContext getApplicationContext() {
        return context;
    }

    @Override
    public void setApplicationContext(ApplicationContext ctx) {
        context = ctx;
    }
}