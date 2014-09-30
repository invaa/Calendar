package com.diosoft.trsine.calendar.datastore;

import com.diosoft.trsine.calendar.adapter.EventAdapter;
import com.diosoft.trsine.calendar.common.Event;
import com.diosoft.trsine.calendar.exceptions.DateIntervalIsIncorrectException;
import com.diosoft.trsine.calendar.exceptions.IdIsNullException;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.nio.file.FileVisitResult.*;

/**
 * All the same as SimpleConcurrentHashMapDataStore, but with file system.
 *
 * @author  Alexander Zamkovyi
 * @version 1.0
 * @since 1.0
 */
public class NioConcurrentHashMapDataStore extends SimpleConcurrentHashMapDataStore {

    /**
     * File path to save/load events.
     */
    //we'll inject it
    private String filePath;

    public NioConcurrentHashMapDataStore(ConcurrentHashMap<UUID, Event> eventsMap, ConcurrentHashMap<Object, Set<UUID>> titlesMap, ConcurrentHashMap<Object, Set<UUID>> daysMap, ConcurrentHashMap<Object, Set<UUID>> descriptionsMap, String filePath) {
        super(eventsMap, titlesMap, daysMap, descriptionsMap);
        this.filePath = filePath;
    }

    /**
     * Adds <code>Event</code> to data store.
     *
     * @param event to be added
     */
    @Override
    public void add(Event event) {
        callAddFromSuper(event);
        //persist with nio

        EventAdapter ea = new EventAdapter();
        ea.setFieldsFromEvent(event);

        try {
            String fileName = filePath + "\\" + event.getId().toString() + ".xml";
            ea.saveEvent(fileName);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    private void callAddFromSuper(Event event) {
        super.add(event);
    }

    /**
     * Adds the <code>Event</code> collection to data store.
     *
     * @param events to be added
     */
    @Override
    public void addAll(Collection<Event> events) {
        events.parallelStream().forEach(this::add);
    }

    /**
     * Removes the <code>Event</code> from data store.
     *
     * @param id of <code>Event</code>
     */
    @Override
    public void remove(UUID id) {
        super.remove(id);
        //remove with nio
        Path path = Paths.get(filePath + "\\" + id.toString() + ".xml");

        try {
            Files.delete(path);
        } catch (NoSuchFileException x) {
            System.err.format("%s: no such" + " file or directory%n", path);
        } catch (DirectoryNotEmptyException x) {
            System.err.format("%s not empty%n", path);
        } catch (IOException x) {
            // File permission problems are caught here.
            System.err.println(x.getMessage());
        }
    }

    @Override
    public void init() {
        //load data store from file path
        EnumSet<FileVisitOption> options = EnumSet.of(FileVisitOption.FOLLOW_LINKS);

        Path dir = Paths.get(filePath);

        try {
            Files.walkFileTree(dir, options, Integer.MAX_VALUE, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file,
                                                 BasicFileAttributes attr) {
                    if (attr.isRegularFile()) {
                        Pattern p = Pattern.compile("^[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}[/.][x][m][l]$", Pattern.CASE_INSENSITIVE);
                        Matcher m = p.matcher(file.getFileName().toString());
                        if (m.matches()) {
                            System.out.println(filePath + file.getFileName().toString());
                            //read
                            EventAdapter ea = new EventAdapter();
                            try {
                                Event event = ea.readEvent(filePath + file.getFileName().toString()).getEvent();
                                callAddFromSuper(event);
                            } catch (JAXBException | IdIsNullException | DateIntervalIsIncorrectException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    return CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
