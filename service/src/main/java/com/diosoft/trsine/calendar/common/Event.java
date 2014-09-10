package com.diosoft.trsine.calendar.common;

import com.diosoft.trsine.calendar.exceptions.IncorrectPeriodDates;
import com.rits.cloning.Cloner;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Calendar <code>Event</code> POJO
 *
 * @author  Igor Vartanian
 * @since 1.5
 */

public class Event {

    private final String description;
    private final Set<String> attenders;
    private final Date dateBegin;
    private final Date dateEnd;
    private final UUID id;
    private final String title;

    //format dateBegin, dateEnd
    protected SimpleDateFormat df = new SimpleDateFormat("E dd MMMM yyyy 'at' hh:mm", new Locale("en", "En"));

    //clone objects
    protected final Cloner cloner = new Cloner();

    protected Event(Builder builder) {
        this.description = builder.description;
        this.attenders = builder.attenders;
        this.dateBegin = builder.dateBegin;
        this.dateEnd = builder.dateEnd;
        this.title = builder.title;
        this.id = UUID.randomUUID();
    }

    public String getDescription() {
        return description;
    }

    public Set<String> getAttenders() {
        return cloner.deepClone(attenders);
    }

    public Date getDateBegin() {
        return cloner.deepClone(dateBegin);
    }

    public Date getDateEnd() {
        return cloner.deepClone(dateEnd);
    }

    public String getTitle() {
        return title;
    }

    public UUID getId() {
        return cloner.deepClone(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        if (id != null ? !id.equals(event.id) : event.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Event{" +
                "title = '" + title +
                ", description = '" + description + '\'' +
                ", attenders = " + attenders +
                ", dateBegin = " + df.format(dateBegin) +
                ", dateEnd = " + df.format(dateEnd) +
                '}';
    }

    public static abstract class Builder {

        private String description;
        private Set<String> attenders;
        private Date dateBegin;
        private Date dateEnd;
        private String title;

        public Builder() {
        }

        public Builder(Event original) {
            this.description = original.description;
            this.attenders = original.attenders;
            this.dateBegin = original.dateBegin;
            this.dateEnd = original.dateEnd;
            this.title = original.title;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
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

        public Builder addAttender(String attender) {
            checkAttenders();
            attenders.add(attender);
            return this;
        }

        public Builder removeAttender(String attender) {
            checkAttenders();
            attenders.remove(attender);
            return this;
        }

        public Builder addAttender(String attender, OperationResult resultAdd) {
            checkAttenders();
            resultAdd.setResult(attenders.add(attender));
            return this;
        }

        public Builder removeAttender(String attender, OperationResult resultRemove) {
            checkAttenders();
            resultRemove.setResult(attenders.remove(attender));
            return this;
        }

        private void checkAttenders() {
            if (attenders == null) {
                synchronized (this) {
                    if (attenders == null)
                        attenders = newSet();
                }
            }
        }

        abstract public Set newSet();

        public Event build() throws IncorrectPeriodDates {

            if ((dateBegin == null | dateEnd == null) || (dateBegin.compareTo(dateEnd) > 0)) {
                 throw new IncorrectPeriodDates("Date begin can't be greater than date end");
            }

            return new Event(this);
        }

    }

    public class OperationResult {

        public boolean isResult() {
            return result;
        }

        public void setResult(boolean result) {
            this.result = result;
        }

        boolean result = false;

    }
}
