package com.diosoft.trsine.calendar.common;

import com.diosoft.trsine.calendar.exceptions.DateIntervalIsIncorrectException;
import com.diosoft.trsine.calendar.exceptions.IdIsNullException;
import com.rits.cloning.Cloner;

import java.text.SimpleDateFormat;
import java.util.Set;
import java.util.Date;
import java.util.UUID;
import java.util.Locale;
import java.util.HashSet;

/**
 * Calendar <code>Event</code> POJO.
 *
 * @author  Igor Vartanian
 * @version 1.0
 * @since 1.0
 */

public class Event implements Comparable<Event> {

    /**
     * Event description.
     */
    private final String description;
    /**
     * Collection of attender e-mails.
     */
    private final Set<String> attenders;
    /**
     * Date of the beginning.
     */
    private final Date dateBegin;
    /**
     * End date.
     */
    private final Date dateEnd;
    /**
     * Unique event identifier.
     */
    private final UUID id;
    /**
     * Event title.
     */
    private final String title;
    /**
     * Cloner object to clone fields.
     */
    private final Cloner cloner = new Cloner();

    /**
     * Date format in toString() method implementation.
     */
    private SimpleDateFormat df = new SimpleDateFormat(""
            + "E dd MMMM yyyy 'at' hh:mm", new Locale("en", "En"));

    /**
     * Private constructor according to the Builder Pattern.
     *
     * @param  builder  Event Builder object
     */
    private Event(final Builder builder) {
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
     * Equals implementation.
     *
     * @param o Event to check
     * @return true if reference class type and id value
     * are equal and false otherwise
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        } //end if
        if (o == null || getClass() != o.getClass()) {
            return false;
        } //end if

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
        return "Event{"
                + "id = " + id
                + ", title = '" + title
                + ", description = '" + description + '\''
                + ", attenders = " + attenders
                + ", dateBegin = " + df.format(dateBegin)
                + ", dateEnd = " + df.format(dateEnd) + '\''
                + '}';
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public final int compareTo(final Event event) {
        if (event == null || id == null) {
            throw new NullPointerException();
        } //end if

        final int isEqual = 0;

        if (this == event) {
            return isEqual;
        } else {
            return id.compareTo(event.id);
        } //end if
    }

    /**
     * <code>Event</code> Builder.
     * in order to use it you should implement newSet() method
     * to instantiate the attenders <code>Set</code>
     */
     public abstract static class Builder {

        /**
         * Event description.
         */
        private String description;
        /**
         * Collection of attender e-mails.
         */
        private Set<String> attenders;
        /**
         * Date of the beginning.
         */
        private Date dateBegin;
        /**
         * End date.
         */
        private Date dateEnd;
        /**
         * Unique event identifier.
         */
        private UUID id;
        /**
         * Event title.
         */
        private String title;

        /**
         * Builder constructor.
         */
        public Builder() {
        }

        /**
         * Сopy constructor.
         *
         * @param original event to clone
         *
         */
        public Builder(final Event original) {
            this.description = original.description;
            this.attenders = original.attenders;
            this.dateBegin = original.dateBegin;
            this.dateEnd = original.dateEnd;
            this.id = original.id;
            this.title = original.title;
        }

        /**
         * Set id.
         *
         * @param id to set.
         * @return Builder, method chaining
         */
        public final Builder setId(final UUID id) {
            this.id = id;
            return this;
        }

        /**
         * Set title.
         *
         * @param title to set.
         * @return Builder, method chaining
         */
        public final Builder setTitle(final String title) {
            this.title = title;
            return this;
        }

        /**
         * Set description.
         *
         * @param description to set.
         * @return Builder, method chaining
         */
         public final Builder setDescription(final String description) {
            this.description = description;
            return this;
        }

        /**
         * Get attenders.
         *
         * @return Set of attenders
         */
         public final Set<String> getAttenders() {
            return attenders;
        }

        /**
         * Set attenders.
         *
         * @param attenders to set.
         * @return Builder, method chaining
         */
        public final Builder setAttenders(final Set<String> attenders) {
            this.attenders = attenders;
            return this;
        }

        /**
         * Set date of beginning.
         *
         * @param dateBegin to set.
         * @return Builder, method chaining
         */
         public final Builder setDateBegin(final Date dateBegin) {
            this.dateBegin =
                    dateBegin == null ? null : new Date(dateBegin.getTime());
            return this;
        }

        /**
         * Set end date.
         *
         * @param dateEnd to set.
         * @return Builder, method chaining
         */
         public final Builder setDateEnd(final Date dateEnd) {
            this.dateEnd = dateEnd == null ? null : new Date(dateEnd.getTime());
            return this;
        }

        /**
         * Adds attender to attenders <code>Set</code>.
         *
         * @param attender attenders e-mail
         * @return Builder
         */
        public final Builder addAttender(final String attender) {
            checkAttenders();
            attenders.add(attender);
            return this;
        }

        /**
         * Removes attender from attenders <code>Set</code>.
         *
         * @param attender attenders e-mail
         * @return Builder
         */
         public final Builder removeAttender(final String attender) {
            checkAttenders();
            attenders.remove(attender);
            return this;
        }

        /**
         * Add attender to attenders <code>Set</code>.
         *
         * @param attender attenders e-mail
         * @param resultAdd class encapsulating operation result
         * @return Builder
         * @see OperationResult
         */
        @SuppressWarnings("unused")
         public final Builder addAttender(final String attender,
                                      final OperationResult resultAdd) {
            checkAttenders();
            resultAdd.setResult(attenders.add(attender));
            return this;
        }

        /**
         * Add attender to attenders <code>Set</code>.
         *
         * @param attender attenders e-mail
         * @param resultRemove class encapsulating operation result
         * @return Builder
         * @see OperationResult
         */
        @SuppressWarnings("unused")
         public final Builder removeAttender(final String attender,
                                       final OperationResult resultRemove) {
            checkAttenders();
            resultRemove.setResult(attenders.remove(attender));
            return this;
        }

        /**
         * Lazy instantiation of attenders set
         * using abstract method <code>newSet()</code>.
         */
        private synchronized void checkAttenders() {
               if (this.attenders == null) {
                    this.attenders = newSet();
               } //end if
        }

        /**
         * method to instantiate the attenders <code>Set</code>.
         *
         * @return <code>Set</code> of strings
         */
        public abstract Set<String> newSet();

        /** Builds the <code>Event</code> POJO object
         *
         * @return <code>Event</code> POJO object
         * @throws IdIsNullException when Event id is null
         * @throws DateIntervalIsIncorrectException when left date is null,
         * or left date is greater than right date
         */
        final public Event build() throws IdIsNullException,
                DateIntervalIsIncorrectException {

            if (id == null) {
                throw new IdIsNullException(""
                        + "Event id could not be null");
            } //end if

            if (dateBegin == null
                    || (dateEnd != null && dateBegin.compareTo(dateEnd) > 0)) {
                throw new DateIntervalIsIncorrectException(""
                        + "Either left date is null, "
                        + "or left date is greater than right date");
            } //end if

            return new Event(this);
        }

    }

    /**
     * <code>Event</code> Builder implementation
     * that encapsulates HashSet&lt;String&gt; of attenders.
     */
     public final static class HashSetBuilder extends Builder {
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
     * Helper that helps to return Set add() and remove()
     * operation results by reference.
     */
    public final static class OperationResult {

        /**
         * No argument constructor.
         */
        public OperationResult() { }

        /**
         * @return result
         */
        public boolean resultOk() {
            return result;
        }

        /**
         * Set result.
         *
         * @param result to set
         */
        public void setResult(final boolean result) {
            this.result = result;
        }

        /**
         * Field, stores result.
         */
        private boolean result = false;

    }
}
