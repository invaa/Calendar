package com.diosoft.trsine.calendar.common;

import com.diosoft.trsine.calendar.exceptions.DateIntervalIsIncorrectException;
import com.diosoft.trsine.calendar.exceptions.IdIsNullException;
import com.rits.cloning.Cloner;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Calendar <code>Event</code> POJO
 *
 * @author  Igor Vartanian
 * @version 1.0
 * @since 1.0
 */

public class Event implements Comparable<Event> {

    private final String description;
    private final Set<String> attenders;
    private final Date dateBegin;
    private final Date dateEnd;
    private final UUID id;
    private final String title;

    private final Cloner cloner = new Cloner();

    //format for dateBegin, dateEnd
    private SimpleDateFormat df = new SimpleDateFormat("E dd MMMM yyyy 'at' hh:mm", new Locale("en", "En"));

    /**
     * Private constructor according to the Builder Pattern
     *
     * @param  builder  Event Builder object
     */
    private Event(Builder builder) {
        this.description = builder.description;
        this.attenders = builder.attenders;
        this.dateBegin = builder.dateBegin;
        this.dateEnd = builder.dateEnd;
        this.title = builder.title;
        this.id = builder.id;
    }

    /**
     *
     * @return Event description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @return Set&lt;String&gt; of Event attenders
     */
    public Set<String> getAttenders() {
        return cloner.deepClone(attenders);
    }

    /**
     *
     * @return Date when Event begins
     */
    public Date getDateBegin() {
        return cloner.deepClone(dateBegin);
    }

    /**
     *
     * @return Date when Event ends
     */
    public Date getDateEnd() {
        return cloner.deepClone(dateEnd);
    }

    /**
     *
     * @return Event title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @return Event id
     */
    public UUID getId() {
        return cloner.deepClone(id);
    }

    /**
     *
     * @return true if reference class type and id value are equal and false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        return !(id != null ? !id.equals(event.id) : event.id != null);
    }

    /**
     *
     * @return 0 when id is null and id.hashCode() otherwise
     */
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id = " + id +
                ", title = '" + title +
                ", description = '" + description + '\'' +
                ", attenders = " + attenders +
                ", dateBegin = " + df.format(dateBegin) +
                ", dateEnd = " + df.format(dateEnd) + '\'' +
                '}';
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public int compareTo(Event event) {
        if (event == null || id == null) {
            throw new NullPointerException();
        } //end if

        final int EQUAL = 0;

        if (this == event) {
            return EQUAL;
        } else {
            return id.compareTo(event.id);
        } //end if
    }

    /**
     * <code>Event</code> Builder
     * in order to use it you should implement newSet() method to instantiate the attenders <code>Set</code>
     */
    public static abstract class Builder {

        private String description;

        private Set<String> attenders;
        private Date dateBegin;
        private Date dateEnd;
        private UUID id;
        private String title;

        public Builder() {
        }

        /**
         * Сopy constructor
         *
         * @param original event to clone
         *
         */
        public Builder(Event original) {
            this.description = original.description;
            this.attenders = original.attenders;
            this.dateBegin = original.dateBegin;
            this.dateEnd = original.dateEnd;
            this.id = original.id;
            this.title = original.title;
        }

        public Builder setId(UUID id) {
            this.id = id;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Set<String> getAttenders() {
            return attenders;
        }

        public Builder setAttenders(Set<String> attenders) {
            this.attenders = attenders;
            return this;
        }

        public Builder setDateBegin(Date dateBegin) {
            this.dateBegin = dateBegin;
            return this;
        }

        public Builder setDateEnd(Date dateEnd) {
            this.dateEnd = dateEnd;
            return this;
        }

        /**
         * Adds attender to attenders <code>Set</code>
         *
         * @param attender attenders e-mail
         * @return Builder
         */
        public Builder addAttender(String attender) {
            checkAttenders();
            attenders.add(attender);
            return this;
        }

        /**
         * Removes attender from attenders <code>Set</code>
         *
         * @param attender attenders e-mail
         * @return Builder
         */
        public Builder removeAttender(String attender) {
            checkAttenders();
            attenders.remove(attender);
            return this;
        }

        /**
         * Add attender to attenders <code>Set</code>
         *
         * @param attender attenders e-mail
         * @param resultAdd class encapsulating operation result
         * @return Builder
         * @see OperationResult
         */
        @SuppressWarnings("unused")
        public Builder addAttender(String attender, OperationResult resultAdd) {
            checkAttenders();
            resultAdd.setResult(attenders.add(attender));
            return this;
        }

        /**
         * Add attender to attenders <code>Set</code>
         *
         * @param attender attenders e-mail
         * @param resultRemove class encapsulating operation result
         * @return Builder
         * @see OperationResult
         */
        @SuppressWarnings("unused")
        public Builder removeAttender(String attender, OperationResult resultRemove) {
            checkAttenders();
            resultRemove.setResult(attenders.remove(attender));
            return this;
        }

        /**
         * Lazy instantiation of attenders set using abstract method <code>newSet()</code>
         */
        private void checkAttenders() {
            if (this.attenders == null) {
                synchronized (this) {
                    if (this.attenders == null) {
                        this.attenders = newSet();
                    }
                } //end if
            }
            //end if
        }

        /**
         * method to instantiate the attenders <code>Set</code>
         *
         * @return <code>Set</code> of strings
         */
        abstract public Set<String> newSet();

        /** Builds the <code>Event</code> POJO object
         *
         * @return <code>Event</code> POJO object
         * @throws IdIsNullException when Event id is null
         * @throws DateIntervalIsIncorrectException when left date is null, or left date is greater than right date
         */
        public Event build() throws IdIsNullException, DateIntervalIsIncorrectException {

            if (id == null) {
                throw new IdIsNullException();
            } //end if

            if (dateBegin == null || (dateEnd != null && dateBegin.compareTo(dateEnd) > 0)) {
                throw new DateIntervalIsIncorrectException();
            } //end if

            return new Event(this);
        }

    }

    /**
     * <code>Event</code> Builder implementation that encapsulates HashSet&lt;String&gt; of attenders
     */
    final public static class HashSetBuilder extends Builder {
        public HashSetBuilder() {
        }

        @SuppressWarnings("unused")
        public HashSetBuilder(Event original) {
            super(original);
        }

        @Override
        public Set<String> newSet() {
            return new HashSet<>();
        }
    }

    /**
     * Helper that helps to return Set add() and remove() operation results by reference
     */
    public class OperationResult {

        @SuppressWarnings("unused")
        public boolean resultOk() {
            return result;
        }

        public void setResult(boolean result) {
            this.result = result;
        }

        boolean result = false;

    }
}
