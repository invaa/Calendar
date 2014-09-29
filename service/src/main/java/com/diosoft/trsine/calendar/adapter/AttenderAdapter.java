package com.diosoft.trsine.calendar.adapter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Serializing Event with JAXB
 */

@Entity
public class AttenderAdapter {

    public EventAdapter getEvent() {
        return event;
    }

    public String getEmail() {

        return email;
    }

    @Id
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    private EventAdapter event;

    public AttenderAdapter(String email, EventAdapter event) {
        this.email = email;
        this.event = event;
    }

    public AttenderAdapter() {
    }
}
