package com.diosoft.trsine.calendar.exceptions;

/**
 * Checked exception throwing when left date is null, or left date is greater than right date
 *
 * @author  Alexander Zamkovyi
 * @version 1.0
 * @since 1.0
 */

public class DateIntervalIsIncorrectException extends Exception {
    @Override
    public String getMessage() {
        return "Either left date is null, or left date is greater than right date";
    }
}
