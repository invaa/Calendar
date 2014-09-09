package com.diosoft.trsine.calendar.common;

import com.diosoft.trsine.calendar.exceptions.DateIntervalIsIncorrectException;
import com.diosoft.trsine.calendar.exceptions.IdIsNullException;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.Assert.*;

public class EventTest {

    @Test
    public void testEquals() {
        //init

        //check
    }

    @Test
    public void testToString() {

    }

    @Test
    public void testThrowsIdIsNullExceptionWhenIdIsNull() {
        //init
        try {
            Event testEvent = new Event.Builder() {
                @Override
                public Set newSet() {
                    return new HashSet<String>();
                }
            }//end of Builder implementation
                    .setDateBegin(new Date())
                    .setDateEnd(new Date())
                    .setId(null)
                    .setTitle("Daily Scrum")
                    .setDescription("Next daily scrum meeting")
                    .addAttender("alex@zamkovyi.name")
                    .addAttender("igor.vartanian@gmail.com")
                    .build();
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
            Event testEvent = new Event.Builder() {
                @Override
                public Set newSet() {
                    return new HashSet<String>();
                }
            }//end of Builder implementation
                    .setDateBegin(null)
                    .setDateEnd(new Date())
                    .setId(UUID.randomUUID())
                    .setTitle("Daily Scrum")
                    .setDescription("Next daily scrum meeting")
                    .addAttender("alex@zamkovyi.name")
                    .addAttender("igor.vartanian@gmail.com")
                    .build();
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
            Event testEvent = new Event.Builder() {
                @Override
                public Set newSet() {
                    return new HashSet<String>();
                }
            }//end of Builder implementation
                    .setDateBegin(leftDate)
                    .setDateEnd(rightDate)
                    .setId(UUID.randomUUID())
                    .setTitle("Daily Scrum")
                    .setDescription("Next daily scrum meeting")
                    .addAttender("alex@zamkovyi.name")
                    .addAttender("igor.vartanian@gmail.com")
                    .build();
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
    public void testHashCodeIsZeroWhenIdIsNull() throws Exception {
        //if equals then hash codes are equals
        Event testEvent1 = new Event.Builder() {
            @Override
            public Set newSet() {
                return new HashSet<String>();
            }
        }//end of Builder implementation
                .setDateBegin(new Date())
                .setDateEnd(new Date())
                .setId(UUID.randomUUID())
                .setTitle("Daily Scrum")
                .setDescription("Next daily scrum meeting")
                .addAttender("alex@zamkovyi.name")
                .addAttender("igor.vartanian@gmail.com")
                .build();

        Event testEvent2 = new Event.Builder() {
            @Override
            public Set newSet() {
                return new HashSet<String>();
            }
        }//end of Builder implementation
                .setDateBegin(new Date())
                .setDateEnd(new Date())
                .setId(UUID.randomUUID())
                .setTitle("Daily Scrum")
                .setDescription("Next daily scrum meeting")
                .addAttender("alex@zamkovyi.name")
                .addAttender("igor.vartanian@gmail.com")
                .build();
    }

    @Test
    public void testHashCodesAreEqualsWhenIdsAreTheSame() throws Exception {
        //if equals then hash codes are equals

    }

    @Test
    public void testEventIsImmutable() {
        Event testEvent = null;
        try {
            testEvent = new Event.Builder() {
                @Override
                public Set newSet() {
                    return new HashSet<String>();
                }
            }//end of Builder implementation
                    .setDateBegin(new Date())
                    .setDateEnd(new Date())
                    .setId(UUID.randomUUID())
                    .setTitle("Daily Scrum")
                    .setDescription("Next daily scrum meeting")
                    .addAttender("alex@zamkovyi.name")
                    .addAttender("igor.vartanian@gmail.com")
                    .build();
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

        //Date
        long dateBeginAsLongBefore = testEvent.getDateBegin().getTime();
        testEvent.getDateBegin().setTime(dateBeginAsLongBefore + 1000);

        assertEquals(dateBeginAsLongBefore, testEvent.getDateBegin().getTime());

    }



}