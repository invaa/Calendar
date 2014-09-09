package com.diosoft.trsine.calendar.common;

import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.*;

public class EventBuilderTest {

    @Test
    public void testSetAttendersWithHashSet() throws Exception {
        //init
        Event.Builder builder1 = new Event.HashSetBuilder();

        builder1
                .addAttender("alex@zamkovyi.name")
                .addAttender("igor.vartanian@gmail.com");

        HashSet<String> set = new HashSet<>();
        set.add("alex@zamkovyi.name");
        set.add("igor.vartanian@gmail.com");

        Event.Builder builder2 = new Event.HashSetBuilder();
        builder2.setAttenders(set);

        //check
        assertEquals(builder1, builder2);
    }

    @Test
    public void testRemoveAttender() throws Exception {
        //init
        Event.Builder builder1 = new Event.HashSetBuilder();

        builder1
                .addAttender("alex@zamkovyi.name")
                .addAttender("igor.vartanian@gmail.com")
                .removeAttender("alex@zamkovyi.name");

        HashSet<String> set = new HashSet<>();
        set.add("igor.vartanian@gmail.com");

        Event.Builder builder2 = new Event.HashSetBuilder();
        builder2.setAttenders(set);

        //check
        assertEquals(builder1, builder2);
    }
}