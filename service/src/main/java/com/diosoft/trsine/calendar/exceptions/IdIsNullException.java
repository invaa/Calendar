package com.diosoft.trsine.calendar.exceptions;

/**
 * Checked exception throwing when id is null.
 *
 * @author  Alexander Zamkovyi
 * @version 1.0
 * @since 1.0
 */

public class IdIsNullException extends Exception {
    /** Noargument constructor.
     *
     */
    public IdIsNullException() { }

    /** Constructor with message.
     * @param message Message to throw
     */
    public IdIsNullException(final String message) {
        super(message);
    }
}
