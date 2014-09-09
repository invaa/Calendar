package com.diosoft.trsine.calendar.common;

import com.rits.cloning.Cloner;

import java.text.SimpleDateFormat;
import java.util.*;

public class Event {

    private final String description;
    private final Set<String> attenders;
    private final Date dateBegin;
    private final Date dateEnd;
    private final UUID id;
    private final String title;

    //format dateBegin, dateEnd
    private SimpleDateFormat df = new SimpleDateFormat("E dd MMMM yyyy 'at' hh:mm", new Locale("en", "En"));

    private Event(Builder builder) {
        this.description = builder.description;
        this.attenders = builder.attenders;
        this.dateBegin = builder.dateBegin;
        this.dateEnd = builder.dateEnd;
        this.title = builder.title;
        this.id = builder.id;
    }

    public String getDescription() {
        return description;
    }

    public Set<String> getAttenders() {
        Cloner cloner = new Cloner();
        Set<String> clone = cloner.deepClone(attenders);
        return clone;
    }

    public Date getDateBegin() {
        return dateBegin;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public String getTitle() {
        return title;
    }

    public UUID getId() {
        return id;
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
                "id = " + id +
                ", title = '" + title +
                ", description = '" + description + '\'' +
                ", attenders = " + attenders +
                ", dateBegin = " + dateBegin +
                ", dateEnd = " + dateEnd + '\'' +
                '}';
    }

    public static abstract class Builder {

        private String description;
        private Set<String> attenders;
        private Date dateBegin;
        private Date dateEnd;
        private UUID id;
        private String title;

        public Builder() {
        }

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

        public Builder addParticipant(String participant) {
            checkAttenders();
            attenders.add(participant);
            return this;
        }

        public Builder removeParticipant(String participant) {
            checkAttenders();
            attenders.remove(participant);
            return this;
        }

        public Builder addParticipant(String participant, OperationResult resultAdd) {
            checkAttenders();
            resultAdd.setResult(attenders.add(participant));
            return this;
        }

        public Builder removeParticipant(String participant, OperationResult resultRemove) {
            checkAttenders();
            resultRemove.setResult(attenders.remove(participant));
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

        public Event build() {

            if ((dateBegin == null | dateEnd == null) || (dateBegin.compareTo(dateEnd) > 0)) {
                //TODO throw exception;
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
