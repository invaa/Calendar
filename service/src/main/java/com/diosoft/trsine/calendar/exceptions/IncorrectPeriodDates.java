package com.diosoft.trsine.calendar.exceptions;

/**
 * Created by i.vartanian on 10.09.2014.
 */
public class IncorrectPeriodDates extends OrganizerException {

    public IncorrectPeriodDates(String message) {
        super(message);
    }

    public IncorrectPeriodDates(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectPeriodDates(Throwable cause) {
        super(cause);
    }

    public IncorrectPeriodDates(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public IncorrectPeriodDates() {
        super();
    }

}