package com.diosoft.trsine.calendar.datastore;

import com.diosoft.trsine.calendar.common.Event;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implements <code>DataStore</code> interface
 * Stores <code>Event</code>s, event descriptions, titles and begin dates
 * as <code>ConcurrentHashMap</code>s to optimize access speed
 * implement method <code>newSet()</code>
 * to instantiate <code>Set</code> of <code>UUID</code>s.
 *
 * @author  Alexander Zamkovyi, Igor Vartanian
 * @version 1.0
 * @since 1.0
*/

public class SimpleConcurrentHashMapDataStore extends ConcurrentHashMapDataStore {
    public SimpleConcurrentHashMapDataStore(ConcurrentHashMap<UUID, Event> eventsMap,
                                            ConcurrentHashMap<Object, Set<UUID>> titlesMap,
                                            ConcurrentHashMap<Object, Set<UUID>> daysMap,
                                            ConcurrentHashMap<Object, Set<UUID>> descriptionsMap) {
        super(eventsMap, titlesMap, daysMap, descriptionsMap);
    }

    @Override
    public Set<UUID> newUUIDSet()  {
        return new HashSet<>();
    }

    @Override
    public List<Event> newResultList()  { return new ArrayList<>(); }
}
