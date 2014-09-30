package com.diosoft.trsine.calendar.datastore;

import com.diosoft.trsine.calendar.adapter.EventAdapter;
import com.diosoft.trsine.calendar.common.Event;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Data store, storing events in JPA-compatible db.
 */

@Component
@Transactional
public class PersistentDataStore implements DataStore {

    // Injected database connection:
    @PersistenceContext
    private EntityManager em;

    // Stores a new event
    public void persist(Event event) {
        EventAdapter ea = new EventAdapter();
        ea.setFieldsFromEvent(event);
        em.persist(ea);
    }

    /**
     * Adds <code>Event</code> to data store.
     *
     * @param event to be added
     */
    @Override
    public void add(Event event) {
        persist(event);
    }

    /**
     * Adds the <code>Event</code> collection to data store.
     *
     * @param events to be added
     */
    @Override
    public void addAll(Collection<Event> events) {
        events.forEach(this::persist);
    }

    /**
     * Removes the <code>Event</code> from data store.
     *
     * @param id of <code>Event</code>
     */
    @Override
    public void remove(UUID id) {
        EventAdapter ea = em.find(EventAdapter.class, id.toString());

        em.remove(ea);
    }

    /**
     * Search for all <code>Event</code> in data store by given description.
     *
     * @param description to search by
     * @return the list of <code>Event</code>s
     */
    @Override
    public List<Event> searchByDescription(String description) {
        TypedQuery<EventAdapter> query = em.createQuery(
                "SELECT e FROM EventAdapter e "
                        + "WHERE e.description = :description "
                        + "ORDER BY e.id", EventAdapter.class)
                .setParameter("description", description);

        return query.getResultList()
                .parallelStream()
                .map(EventAdapter::getEvent)
                .collect(Collectors.toList());
    }

    /**
     * Search for all <code>Event</code> in data store by given title.
     *
     * @param title to search by
     * @return the list of <code>Event</code>s
     */
    @Override
    public List<Event> searchByTitle(String title) {
        TypedQuery<EventAdapter> query = em.createQuery(
                "SELECT e FROM EventAdapter e "
                        + "WHERE e.title = :title "
                        + "ORDER BY e.id", EventAdapter.class)
                .setParameter("title", title);

        return query.getResultList()
                .parallelStream()
                .map(EventAdapter::getEvent)
                .collect(Collectors.toList());
    }

    /**
     * Search for all <code>Event</code> in data store by given title.
     *
     * @param id to search by
     * @return <code>Event</code>
     */
    @Override
    public Event getById(UUID id) {
        TypedQuery<EventAdapter> query = em.createQuery(
                "SELECT e FROM EventAdapter e "
                        + "WHERE e.id = :id "
                        + "ORDER BY e.id", EventAdapter.class)
                .setParameter("id", id.toString());

        EventAdapter ea = query.getSingleResult();
        if (ea != null) {
            return ea.getEvent();
        } else {
            return null;
        }
    }

    /**
     * Search for all <code>Event</code> in data store
     * by given date of begining.
     *
     * @param day date of begining to search by
     * @return the list of <code>Event</code>s
     */
    @Override
    public List<Event> searchByDay(Date day) {
        TypedQuery<EventAdapter> query = em.createQuery(
                "SELECT e FROM EventAdapter e "
                        + "WHERE e.dateBegin = :day "
                        + "ORDER BY e.id", EventAdapter.class)
                .setParameter("day", day);

        return query.getResultList()
                .parallelStream()
                .map(EventAdapter::getEvent)
                .collect(Collectors.toList());
    }

    /**
     * Search for all <code>Event</code> in data store
     * by given date of begining interval.
     *
     * @param leftDate  date of begining to search by
     * @param rightDate date of begining to search by
     * @return the list of <code>Event</code>s
     */
    @Override
    public List<Event> searchByInterval(Date leftDate, Date rightDate) {
        TypedQuery<EventAdapter> query = em.createQuery(
                "SELECT e FROM EventAdapter e "
                       + "WHERE e.dateBegin BETWEEN :leftDate and :rightDate "
                       + "ORDER BY e.id", EventAdapter.class)
                .setParameter("leftDate", leftDate)
                .setParameter("rightDate", rightDate);

        return query.getResultList()
                .parallelStream()
                .map(EventAdapter::getEvent)
                .collect(Collectors.toList());
    }

    /**
     * Search for all <code>Event</code> in data store by given title beginning.
     *
     * @param filter to search by
     * @return the list of <code>Event</code>s
     */
    @Override
    public List<Event> searchByTitleStartWith(String filter) {
        TypedQuery<EventAdapter> query = em.createQuery(
                "SELECT e FROM EventAdapter e "
                        + "WHERE e.title LIKE :filter "
                        + "ORDER BY e.id", EventAdapter.class)
                .setParameter("filter", filter +  "%");

        return query.getResultList()
                .parallelStream()
                .map(EventAdapter::getEvent)
                .collect(Collectors.toList());
    }

    /**
     * Initialization if needed.
     */
    @Override
    public void init() {
        //stub
    }
}
