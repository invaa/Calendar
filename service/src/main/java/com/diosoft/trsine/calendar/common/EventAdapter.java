package com.diosoft.trsine.calendar.common;

import com.diosoft.trsine.calendar.exceptions.IncorrectPeriodDates;
import com.rits.cloning.Cloner;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Calendar <code>Event</code> POJO
 *
 * @author Igor Vartanian
 * @since 1.5
 */

@XmlRootElement
public class EventAdapter implements Serializable{

    private String description;
    private Set<String> attenders;
    private Date dateBegin;
    private Date dateEnd;
    private String id;
    private String title;

    public EventAdapter() {
    }

    public EventAdapter(Event event) {
        this.description = event.getDescription();
        this.attenders = event.getAttenders();
        this.dateBegin = event.getDateBegin();
        this.dateEnd = event.getDateEnd();
        this.id = event.getId().toString();
        this.title = event.getTitle();
    }

    public String getDescription() {
        return description;
    }

    @XmlElement
    public void setDescription(String description) {
        this.description = description;
    }

    public Set<String> getAttenders() {
        return attenders;
    }

    @XmlElement
    public void setAttenders(Set<String> attenders) {
        this.attenders = attenders;
    }

    public Date getDateBegin() {
        return dateBegin;
    }

    @XmlElement
    public void setDateBegin(Date dateBegin) {
        this.dateBegin = dateBegin;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    @XmlElement
    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getId() {
        return id;
    }

    @XmlAttribute
    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    @XmlElement
    public void setTitle(String title) {
        this.title = title;
    }

    public Event createEvent() throws IncorrectPeriodDates {

        Event event = new Event.Builder()
                .setDateBegin(this.getDateBegin())
                .setDateEnd(this.getDateEnd())
                .setTitle(this.getTitle())
                .setDescription(this.getDescription())
                .setId(UUID.fromString(this.getId()))
                .setAttenders(this.getAttenders())
                .build();


        return event;
    }

}
