package com.diosoft.trsine.calendar.common;

import org.junit.Test;

import java.util.HashSet;

import static com.diosoft.trsine.calendar.common.Event.Builder;
import static com.diosoft.trsine.calendar.common.Event.HashSetBuilder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EventBuilderTest {

    @Test
    public void testSetAttendersWithHashSet() throws Exception {
        //init
        Builder builder1 = new HashSetBuilder();

        builder1
                .addAttender("alex@zamkovyi.name")
                .addAttender("igor.vartanian@gmail.com");

        HashSet<String> set = new HashSet<>();
        set.add("alex@zamkovyi.name");
        set.add("igor.vartanian@gmail.com");

        Builder builder2 = new HashSetBuilder();
        builder2.setAttenders(set);

        //check
        assertEquals(builder1.getAttenders(), builder2.getAttenders());
    }

    @Test
    public void testRemoveAttender() throws Exception {
        //init
        Builder builder1 = new HashSetBuilder();

        builder1
                .addAttender("alex@zamkovyi.name")
                .addAttender("igor.vartanian@gmail.com")
                .removeAttender("alex@zamkovyi.name");

        HashSet<String> set = new HashSet<>();
        set.add("igor.vartanian@gmail.com");

        Builder builder2 = new HashSetBuilder();
        builder2.setAttenders(set);

        //check
        assertEquals(builder1.getAttenders(), builder2.getAttenders());
    }

    @Test
    public void testRemoveAttenderAndReturnTrue() throws Exception {
        //init
        Builder builder1 = new HashSetBuilder();

        Event.OperationResult operationResult = new Event.OperationResult();
        assertTrue(!operationResult.resultOk());

        builder1
                .addAttender("alex@zamkovyi.name")
                .addAttender("igor.vartanian@gmail.com")
                .removeAttender("alex@zamkovyi.name", operationResult);

        HashSet<String> set = new HashSet<>();
        set.add("igor.vartanian@gmail.com");

        Builder builder2 = new HashSetBuilder();
        builder2.setAttenders(set);

        //check
        assertEquals(builder1.getAttenders(), builder2.getAttenders());
        assertTrue(operationResult.resultOk());
    }
}