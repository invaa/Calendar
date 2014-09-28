package com.diosoft.trsine.calendar.utilities;

import com.diosoft.trsine.calendar.common.Event;
import com.diosoft.trsine.calendar.common.EventAdapter;
import com.diosoft.trsine.calendar.datastore.DataStore;
import com.diosoft.trsine.calendar.exceptions.IncorrectPeriodDates;
import com.diosoft.trsine.calendar.service.CalendarServiceImp;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.UUID;

/**
 * Created by VARTAN on 28.09.2014.
 */
public class ConvertData {

    public static DataStore dataStore;

    public static void convertFromEventToXML(EventAdapter event, String dataPath) {

        if (dataPath == null) {
            throw new IllegalArgumentException("The dataPath can't be null");
        }

        Path file = Paths.get(dataPath + "\\" + event.getId() + ".xml");
        SecurityManager security = System.getSecurityManager();
        if (security != null) {
            try {
                security.checkWrite(file.getName(file.getNameCount()-1).toString());
            } catch (SecurityException ex) {
                ex.getStackTrace();
                return;
            }

        }

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(event.getClass());
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(event, file.toFile());
        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }

    public static void convertFromEventsToXML(Set<EventAdapter> events, String dataPath) {

        for (EventAdapter event : events) {
            convertFromEventToXML(event, dataPath);
        }

    }

    public static void convertFromXMLToEvent(DataStore ds) throws IOException {

        if (ds == null) {
            throw new IllegalArgumentException("The data store can't be null");
        }

        if (ds.getDataPath() == null) {
            throw new IllegalArgumentException("The dataPath can't be null");
        }

        dataStore = ds;

        Path path = Paths.get(ds.getDataPath());
        FileSystemVisitor fileSystemVisitor = new FileSystemVisitor();
        Files.walkFileTree(path, fileSystemVisitor);

    }

    public static void createEventOfFile(Path file) throws JAXBException, IncorrectPeriodDates {

        JAXBContext jaxbContext = JAXBContext.newInstance(EventAdapter.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        EventAdapter unmarshal = (EventAdapter) unmarshaller.unmarshal(file.toFile());

        Event event = unmarshal.createEvent();
        dataStore.add(event);

    }

    public static void deleteDataFile(UUID id, String dataPath) {

        if (id == null) {
            return;
        }
        if (dataPath == null) {
            throw new IllegalArgumentException("The dataPath can't be null");
        }

        Path file = Paths.get(dataPath + "\\" + id.toString() + ".xml");

        if (!Files.exists(file)) {
            return;
        }
        SecurityManager security = System.getSecurityManager();
        if (security != null) {
            try {
                security.checkDelete(file.toString());
            } catch (SecurityException ex) {
                ex.getStackTrace();
                return;
            }

        }

        try {
            Files.delete(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
