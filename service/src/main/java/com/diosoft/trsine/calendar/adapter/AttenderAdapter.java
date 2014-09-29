package com.diosoft.trsine.calendar.adapter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Serializing Event with JAXB
 */

@Entity
@XmlRootElement
public class AttenderAdapter implements Serializable {

    private static final long serialVersionUID = 1L;

    public EventAdapter getEvent() {
        return event;
    }

    public String getEmail() {

        return email;
    }

    @Id  @XmlElement
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    private transient EventAdapter event;

    public AttenderAdapter(String email, EventAdapter event) {
        this.email = email;
        this.event = event;
    }

    public AttenderAdapter() {
    }
}
