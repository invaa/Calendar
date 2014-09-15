package nonunittests;

import com.diosoft.trsine.calendar.common.Event;
import com.diosoft.trsine.calendar.exceptions.DateIntervalIsIncorrectException;
import com.diosoft.trsine.calendar.exceptions.IdIsNullException;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class EventTest {
    public static void main(String ... args) throws IdIsNullException, DateIntervalIsIncorrectException {

        Event testEvent = new Event.Builder() {
            @Override
            public Set<String> newSet() {
                return new HashSet<>();
            }
        }//end of Builder implementation
                .setDateBegin(new Date())
                .setDateEnd(new Date())
                .setId(UUID.randomUUID())
                .setTitle("Daily Scrum")
                .setDescription("Next daily scrum meeting")
                .addAttender("alex@zamkovyi.name")
                .addAttender("igor.vartanian@gmail.com")
                .build();

        //Output
        System.out.println(testEvent.getAttenders().toString());

        //assuming that testEvent is immutable let's try to change participants
        Set<String> attenders = testEvent.getAttenders();
        attenders.remove("igor.vartanian@gmail.com");

        //Output
        System.out.println(testEvent.getAttenders().toString());



    }


}
