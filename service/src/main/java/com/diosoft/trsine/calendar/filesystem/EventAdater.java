package com.diosoft.trsine.calendar.filesystem;

import com.diosoft.trsine.calendar.common.Event;
import com.diosoft.trsine.calendar.exceptions.DateIntervalIsIncorrectException;
import com.diosoft.trsine.calendar.exceptions.IdIsNullException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Serializing Event with JAXB
 */

@XmlRootElement
public class EventAdater implements Serializable {

    /**
     * Event description.
     */
    @XmlElement
    private String description;
    /**
     * Collection of attender e-mails.
     */
    @XmlElement
    private Set<String> attenders = new HashSet<String>();
    /**
     * Date of the beginning.
     */
    @XmlElement
    private Date dateBegin;
    /**
     * End date.
     */
    @XmlElement
    private Date dateEnd;
    /**
     * Unique event identifier.
     */
    @XmlElement
    private UUID id;
    /**
     * Event title.
     */
    @XmlElement
    private String title;

    public void setFieldsFromEvent(final Event original) {
        this.description = original.getDescription();
        if (this.attenders != null) {
            this.attenders.clear();
        }
        this.attenders.addAll(original.getAttenders());
        this.dateBegin = original.getDateBegin();
        this.dateEnd = original.getDateEnd();
        this.id = original.getId();
        this.title = original.getTitle();
    }

    /**
     * Private constructor according to the Builder Pattern.
     *
     * @return  Event  Event object
     */

    public Event getEvent() throws IdIsNullException, DateIntervalIsIncorrectException {
        return new Event.HashSetBuilder()
                .setDateBegin(dateBegin)
                .setDateEnd(dateEnd)
                .setId(id)
                .setTitle(title)
                .setDescription(description)
                .setAttenders(attenders)
                .build();
    }

    public void saveEvent(String fileName) throws JAXBException {
        File file = new File(fileName);
        JAXBContext jaxbContext = JAXBContext.newInstance(EventAdater.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(this, file);
    }

    public EventAdater readEvent(String fileName) throws JAXBException, IdIsNullException, DateIntervalIsIncorrectException {
        File file = new File(fileName);
        JAXBContext jaxbContext = JAXBContext.newInstance(EventAdater.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return (EventAdater)jaxbUnmarshaller.unmarshal(file);
    }
}
