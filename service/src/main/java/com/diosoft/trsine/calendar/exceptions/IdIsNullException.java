package com.diosoft.trsine.calendar.exceptions;

/**
 * Checked exception throwing when id is null
 *
 * @author  Alexander Zamkovyi
 * @since 1.5
 */

public class IdIsNullException extends Exception {
    @Override
    public String getMessage() {
        return "Event id could not be null";
    }
}
