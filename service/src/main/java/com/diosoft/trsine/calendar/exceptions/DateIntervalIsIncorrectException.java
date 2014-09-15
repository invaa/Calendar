package com.diosoft.trsine.calendar.exceptions;

/**
 * Checked exception throwing when left date is null,
 * or left date is greater than right date.
 *
 * @author  Alexander Zamkovyi
 * @version 1.0
 * @since 1.0
 */

public class DateIntervalIsIncorrectException extends Exception {

    /** Noargument constructor.
     *
     */
     public DateIntervalIsIncorrectException() { }

    /** Constructor with message.
     * @param message Message to throw
     */
    public DateIntervalIsIncorrectException(final String message) {
            super(message);
        }
}
