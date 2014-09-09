package com.diosoft.trsine.calendar.common;

import com.diosoft.trsine.calendar.exceptions.DateIntervalIsIncorrectException;
import com.diosoft.trsine.calendar.exceptions.IdIsNullException;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.Assert.*;

public class EventTest {

    /**
     * Gets test event
     *
     * @param id <code>Event</code> id
     *
     * @return test <code>Event</code>
     * @throws IdIsNullException
     * @throws DateIntervalIsIncorrectException
     */
    private Event getUntitledTestEvent(UUID id) throws IdIsNullException, DateIntervalIsIncorrectException {
        return new Event.Builder() {
            @Override
            public Set newSet() {
                return new HashSet<String>();
            }
        }//end of Builder implementation
                .setDateBegin(new Date())
                .setDateEnd(new Date())
                .setId(id)
                .setTitle("")
                .setDescription("")
                .addAttender("igor.vartanian@gmail.com")
                .build();
    }

    /**
     * Gets test event
     *
     * @param leftDate begining of event
     * @param rightDate end of event
     * @param id of event
     *
     * @return test <code>Event</code>
     * @throws IdIsNullException
     * @throws DateIntervalIsIncorrectException
     */
    private Event getDailyScrumTestEvent(Date leftDate, Date rightDate, UUID id) throws IdIsNullException, DateIntervalIsIncorrectException {
        return new Event.HashSetBuilder()
                .setDateBegin(leftDate)
                .setDateEnd(rightDate)
                .setId(id)
                .setTitle("Daily Scrum")
                .setDescription("Next daily scrum meeting")
                .addAttender("alex@zamkovyi.name")
                .addAttender("igor.vartanian@gmail.com")
                .build();
    }

    @Test
    public void testEqualsIsTrueIfIdsAreEqual() {
        //init
        UUID id = UUID.randomUUID();

        Event testEvent1 = null;
        try {
            testEvent1 = getDailyScrumTestEvent(new Date(), new Date(), id);
        } catch (IdIsNullException e) {
            fail(e.getMessage());
        } catch (DateIntervalIsIncorrectException e) {
            fail(e.getMessage());
        }

        Event testEvent2 = null;
        try {
            testEvent2 = getUntitledTestEvent(id);
        } catch (IdIsNullException e) {
            fail(e.getMessage());
        } catch (DateIntervalIsIncorrectException e) {
            fail(e.getMessage());
        }

        //check
        assertTrue(testEvent1.equals(testEvent2));
        assertTrue(testEvent2.equals(testEvent1));
    }

    @Test
    public void testHashCodeReturnsZeroIfIdIsNull() {

        //init
        Event testEvent = null;
        try {
            testEvent = getDailyScrumTestEvent(new Date(), new Date(), UUID.randomUUID());
        } catch (IdIsNullException e) {
            fail(e.getMessage());
        } catch (DateIntervalIsIncorrectException e) {
            fail(e.getMessage());
        }

        Field field1 = null;
        try {
            field1 = testEvent.getClass().getDeclaredField("id");
        } catch (NoSuchFieldException e) {
            fail(e.getMessage());
        }

        //field1 is not null
        field1.setAccessible(true);
        try {
            field1.set(testEvent, null);
        } catch (IllegalAccessException e) {
            fail(e.getMessage());
        }

        assertEquals(0, testEvent.hashCode());
    }

    @Test
    public void testThrowsIdIsNullExceptionWhenIdIsNull() {
        //init
        try {
            Event testEvent = getDailyScrumTestEvent(new Date(), new Date(), null);
        } catch (IdIsNullException e) {
            //ok
            return;
        } catch (DateIntervalIsIncorrectException e) {
            //not the subject of the test
        }

        //failed if not returned
        fail();
    }

    @Test
    public void testThrowsDateIntervalIsIncorrectExceptionWhenLeftDateIsNull() {
        //init
        try {
            Event testEvent = getDailyScrumTestEvent(null, new Date(), UUID.randomUUID());
        } catch (IdIsNullException e) {
            //not the subject of the test
        } catch (DateIntervalIsIncorrectException e) {
            //ok
            return;
        }

        //failed if not returned
        fail();
    }

    @Test
    public void testThrowsDateIntervalIsIncorrectExceptionWhenRightDateIsGreaterThanLeftDate() {
        //init
        Date leftDate = new Date();
        Date rightDate = new Date(leftDate.getTime() - 1);

        try {
            Event testEvent = getDailyScrumTestEvent(leftDate, rightDate, UUID.randomUUID());
        } catch (IdIsNullException e) {
            //not the subject of the test
        } catch (DateIntervalIsIncorrectException e) {
            //ok
            return;
        }

        //failed if not returned
        fail();
    }

    @Test
    public void testHashCodesAreEqualsWhenIdsAreTheSame() throws Exception {
        //init
        UUID id = UUID.randomUUID();

        Event testEvent1 = null;
        try {
            testEvent1 = getDailyScrumTestEvent(new Date(), new Date(), id);
        } catch (IdIsNullException e) {
            fail(e.getMessage());
        } catch (DateIntervalIsIncorrectException e) {
            fail(e.getMessage());
        }

        Event testEvent2 = null;
        try {
            testEvent2 = getUntitledTestEvent(id);
        } catch (IdIsNullException e) {
            fail(e.getMessage());
        } catch (DateIntervalIsIncorrectException e) {
            fail(e.getMessage());
        }

        //check
        assertEquals(testEvent1.hashCode(), testEvent2.hashCode());
    }

    @Test
    public void testEventIsImmutable() {
        Event testEvent = null;
        try {
            testEvent = getDailyScrumTestEvent(new Date(), new Date(), UUID.randomUUID());
        } catch (IdIsNullException e) {
            fail(e.getMessage());
        } catch (DateIntervalIsIncorrectException e) {
            fail(e.getMessage());
        }

        //checking only object types dateBegin, dateEnd, Set

        //attenders
        Set<String> attendersBefore = testEvent.getAttenders();
        Set<String> attendersAfter = testEvent.getAttenders();
        attendersAfter.remove("igor.vartanian@gmail.com");
        assertEquals(attendersBefore, testEvent.getAttenders());

        //Date begin
        long dateBeginAsLongBefore = testEvent.getDateBegin().getTime();
        testEvent.getDateBegin().setTime(dateBeginAsLongBefore + 1000);
        assertEquals(dateBeginAsLongBefore, testEvent.getDateBegin().getTime());

        //Date end
        long dateEndAsLongBefore = testEvent.getDateEnd().getTime();
        testEvent.getDateEnd().setTime(dateEndAsLongBefore + 1000);
        assertEquals(dateEndAsLongBefore, testEvent.getDateEnd().getTime());

    }

}