package com.diosoft.trsine.calendar.comparison;

import com.diosoft.trsine.calendar.common.Event;

import java.util.Comparator;
import java.util.Date;

/**
 * Comparing <code>Event</code>s by date of beginning.
 *
 * @author  Igor Vartanian
 * @version 1.0
 * @since 1.0
 * @see Event
 */

@SuppressWarnings("unused")
public class ComparatorDateBegin implements Comparator<Event> {

    /** Returns the result of comparing two <code>Event</code>s.
     *
     * @param o1 first <code>Event</code>
     * @param o2 first <code>Event</code>
     * @return returns 0 is equals, 1 if greater and -1 otherwise
     */
    @Override
    public final int compare(final Event o1, final Event o2) {

        if (o1 == null || o2 == null) {
            throw new IllegalArgumentException(""
                    + "The compare event can't be equal Null");
        }

        Date o1DateBegin = o1.getDateBegin();
        Date o2DateBegin = o2.getDateBegin();

        if (o1DateBegin == o2DateBegin) {
            return 0;
        }

        if (o1DateBegin == null || o2DateBegin == null) {
            throw new NullPointerException(""
                    + "Date begin can not be equal to Null");
        }

        return o1DateBegin.compareTo(o2DateBegin);

    }

}
