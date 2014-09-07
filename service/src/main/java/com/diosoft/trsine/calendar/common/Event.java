package com.diosoft.trsine.calendar.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Event {

    private final String description;
    private final Set<String> attenders;
    private final Date date;

    //format date
    private SimpleDateFormat df = new SimpleDateFormat ("E dd MMMM yyyy 'at' hh:mm", new Locale("en","En"));

    public Event(String description, Set<String> attenders, Date data) {
        this.description = description;
        this.attenders = attenders;
        this.date = data;
    }

    public Event(Builder builder) {
        this.description = builder.description;
        this.attenders = builder.attenders;
        this.date = builder.date;
    }

    public String getDescription() {
        return description;
    }

    public Set<String> getAttenders() {
        return attenders;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Event{" +
                "description = '" + description + '\'' +
                ", attenders = " + attenders +
                ", date = " + df.format(date) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        if (!attenders.equals(event.attenders)) return false;
        if (!date.equals(event.date)) return false;
        if (!description.equals(event.description)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = description.hashCode();
        result = 31 * result + attenders.hashCode();
        result = 31 * result + date.hashCode();
        return result;
    }

    public static abstract class Builder {

        private String description;
        private Set<String> attenders;
        private Date date;

        public Builder() {
        }

        public Builder(Event original) {
            this.description = original.description;
            this.attenders = original.attenders;
            this.date = original.date;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setAttenders(Set<String> attenders) {
            this.attenders = attenders;
            return this;
        }

        public Builder setData(Date data) {
            this.date = data;
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
                attenders = newSet();
            }
        }

        abstract public Set newSet();

        public Event build(){
            return new Event(this);
        }

    }

}
