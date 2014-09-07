package com.diosoft.trsine.calendar.common;

import com.rits.cloning.Cloner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

public class Event {

    private final String description;
    private final Set<String> attenders;
    private final Date dateBegin;
    private final Date dateEnd;

    //format dateBegin, dateEnd
    private SimpleDateFormat df = new SimpleDateFormat ("E dd MMMM yyyy 'at' hh:mm", new Locale("en","En"));

    private Event(Builder builder) {
        this.description = builder.description;
        this.attenders = builder.attenders;
        this.dateBegin = builder.dateBegin;
        this.dateEnd = builder.dateEnd;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        if (!attenders.equals(event.attenders)) return false;
        if (!dateBegin.equals(event.dateBegin)) return false;
        if (!dateEnd.equals(event.dateEnd)) return false;
        if (!description.equals(event.description)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = description.hashCode();
        result = 31 * result + attenders.hashCode();
        result = 31 * result + dateBegin.hashCode();
        result = 31 * result + dateEnd.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Event{" +
                "description = '" + description + '\'' +
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

        public Builder() {
        }

        public Builder(Event original) {
            this.description = original.description;
            this.attenders = original.attenders;
            this.dateBegin = original.dateBegin;
            this.dateEnd = original.dateEnd;
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

        public Builder addParticipant(String participant){
            checkAttenders();
            attenders.add(participant);
            return this;
        }

        public Builder removeParticipant(String participant){
            checkAttenders();
            attenders.remove(participant);
            return this;
        }
        public Builder addParticipant(String participant, boolean resultAdd){
            checkAttenders();
            resultAdd = attenders.add(participant);
            return this;
        }

        public Builder removeParticipant(String participant, boolean resultRemove){
            checkAttenders();
            resultRemove = attenders.remove(participant);
            return this;
        }

        private void checkAttenders() {
            if (attenders == null){
                synchronized(this) {
                    if (attenders == null)
                        attenders = newSet();
                }
            }
        }

        abstract public Set newSet();

        public Event build(){
            return new Event(this);
        }

    }

}
