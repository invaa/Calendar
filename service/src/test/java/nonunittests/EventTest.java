package nonunittests;

import com.diosoft.trsine.calendar.common.Event;
import com.diosoft.trsine.calendar.exeptions.IncorrectPeriodDates;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class EventTest {
    public static void main(String ... args) throws IncorrectPeriodDates {

        Event testEvent = new Event.Builder() {
            @Override
            public Set newSet() {
                return new HashSet<String>();
            }
        }//end of Builder implementation
                .setDateBegin(new Date())
                .setDateEnd(new Date())
                //.setId(UUID.randomUUID())
                .setTitle("Daily Scrum")
                .setDescription("Next daily scrum meeting")
                .addAttender("alex@zamkovyi.name")
                .addAttender("igor.vartanian@gmail.com")
                .build();

        //Output
        System.out.println(testEvent.getAttenders().toString());

        //assuming that testEvent is immutable let's try to change participants
        HashSet<String> attenders = (HashSet<String>) testEvent.getAttenders();
        attenders.remove("igor.vartanian@gmail.com");

        //Output
        System.out.println(testEvent.getAttenders().toString());

        System.out.println(testEvent);



    }


}
