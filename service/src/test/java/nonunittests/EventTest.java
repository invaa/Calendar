package nonunittests;

import com.diosoft.trsine.calendar.common.Event;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by a.zamkovyi on 07.09.2014.
 */
public class EventTest {
    public static void main(String ... args) {

        Event testEvent = new Event.Builder() {
            @Override
            public Set newSet() {
                return new HashSet<String>();
            }
        }//end of Builder implementation
        .setDateBegin(new Date())
        .setDescription("Test event")
        .addParticipant("alex@zamkovyi.name")
        .addParticipant("igor.vartanian@gmail.com")
        .build();

        //Output
        System.out.println(testEvent.getAttenders().toString());

        //assuming that testEvent is immutable let's try to change participants
        HashSet<String> attenders = (HashSet<String>) testEvent.getAttenders();
        attenders.remove("igor.vartanian@gmail.com");

        //Output
        System.out.println(testEvent.getAttenders().toString());



    }


}
