package nonunittests;

import com.diosoft.trsine.calendar.common.Event;
import com.diosoft.trsine.calendar.exceptions.IncorrectPeriodDates;
import com.sun.xml.internal.fastinfoset.tools.FI_SAX_Or_XML_SAX_DOM_SAX_SAXEvent;

import java.io.FileInputStream;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class EventTest {
    public static void main(String ... args) throws IncorrectPeriodDates {

        Event testEvent = new Event.Builder()
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
        Set<String> attenders = (Set<String>) testEvent.getAttenders();
        attenders.remove("igor.vartanian@gmail.com");

        //Output
        System.out.println(testEvent.getAttenders().toString());

    }


}
