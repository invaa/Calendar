package com.diosoft.trsine.calendar.exeptions;

/**
 * Created by i.vartanian on 10.09.2014.
 */
public class OrganizerException extends Exception {

    public OrganizerException(String message) {
        super(message);
    }

    public OrganizerException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrganizerException(Throwable cause) {
        super(cause);
    }

    public OrganizerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public OrganizerException() {
        super();
    }
}
