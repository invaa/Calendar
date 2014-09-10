package com.diosoft.trsine.calendar.exceptions;

/**
 * Checked exception throwing when id is null
 *
 * @author  Alexander Zamkovyi
 * @version 1.0
 * @since 1.0
 */

public class IdIsNullException extends Exception {
    @Override
    public String getMessage() {
        return "Event id could not be null";
    }
}
