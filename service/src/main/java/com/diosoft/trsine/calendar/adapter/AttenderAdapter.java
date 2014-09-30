package com.diosoft.trsine.calendar.adapter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Serializing Event with JAXB.
 *
 * @author  Alexander Zamkovyi
 * @version 1.0
 * @since 1.0
 */

@Entity
@XmlRootElement
public class AttenderAdapter implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id  @XmlElement
    private String email;
    @Id @ManyToOne(fetch = FetchType.LAZY)
    private EventAdapter event;

    public AttenderAdapter(String email, EventAdapter event) {
        this.email = email;
        this.event = event;
    }

    public AttenderAdapter() {
    }

    public EventAdapter getEvent() {
        return event;
    }

    public String getEmail() {

        return email;
    }
}
