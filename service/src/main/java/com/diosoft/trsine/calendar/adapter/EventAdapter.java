package com.diosoft.trsine.calendar.adapter;

import com.diosoft.trsine.calendar.common.Event;
import com.diosoft.trsine.calendar.exceptions.DateIntervalIsIncorrectException;
import com.diosoft.trsine.calendar.exceptions.IdIsNullException;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Serializing Event with JAXB
 */

@Entity
@XmlRootElement
public class EventAdapter implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Event description.
     */
    @XmlElement
    private String description;

    /**
     * Date of the beginning.
     */
    @JsonProperty("start")
    @XmlElement
    private Date dateBegin;
    /**
     * End date.
     */
    @JsonProperty("end")
    @XmlElement
    private Date dateEnd;
    /**
     * Unique event identifier.
     */
    @Id
    @XmlElement
    private String id;
    /**
     * Event title.
     */
    @XmlElement
    private String title;

    @XmlElement
    @OneToMany(mappedBy="event")
    private List<AttenderAdapter> attenders = new ArrayList<>();

    public void setFieldsFromEvent(final Event original) {
        this.description = original.getDescription();
        if (this.attenders != null) {
            this.attenders.clear();
        }
        if (original.getAttenders() != null) {
            this.attenders.addAll(
                    original
                            .getAttenders()
                            .parallelStream()
                            .map(attender -> new AttenderAdapter(attender, this))
                            .collect(Collectors.toList()));
        }
        if (this.attenders == null) {
            this.attenders = new ArrayList<>();
        }
        this.dateBegin = original.getDateBegin();
        this.dateEnd = original.getDateEnd();
        this.id = original.getId().toString();
        this.title = original.getTitle();
    }

    public Event getEvent() {
        Event.Builder builder = new Event.HashSetBuilder()
                .setDateBegin(dateBegin)
                .setDateEnd(dateEnd)
                .setId(UUID.fromString(id))
                .setTitle(title)
                .setDescription(description);
                //.setAttenders(attenders)

        for (AttenderAdapter attender: attenders) {
            builder.addAttender(attender.getEmail());
        }

        try {
            return builder.build();
        } catch (IdIsNullException e) {
            e.printStackTrace();
        } catch (DateIntervalIsIncorrectException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void saveEvent(String fileName) throws JAXBException {
        File file = new File(fileName);
        JAXBContext jaxbContext = JAXBContext.newInstance(EventAdapter.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(this, file);
    }

    public EventAdapter readEvent(String fileName) throws JAXBException, IdIsNullException, DateIntervalIsIncorrectException {
        File file = new File(fileName);
        JAXBContext jaxbContext = JAXBContext.newInstance(EventAdapter.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return (EventAdapter)jaxbUnmarshaller.unmarshal(file);
    }

}
