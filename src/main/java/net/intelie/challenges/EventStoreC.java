package net.intelie.challenges;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;



public class EventStoreC implements EventStore {
    public final ConcurrentHashMap<String, List<Event>> evtStore;
    
    public ConcurrentHashMap<String, List<Event>> getEvents() {
        return this.evtStore;
    }

    public EventStoreC() {
        this.evtStore = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized void insert(Event event) {
        // TODO Auto-generated method stub
        if(event == null || event.type() == null)
        return; 
        String evtType = event.type(); 
        
        if(!this.evtStore.containsKey(evtType)){

            evtStore.put(evtType, Collections.synchronizedList(new ArrayList<Event>()));
        } 

        evtStore.get(evtType).add(event);
    }

    @Override
    public synchronized void removeAll(String type) {
        // TODO Auto-generated method stub
        if(type == null)
        return; 
        this.evtStore.remove(type);
    }

    @Override
    public synchronized EventIterator query(String type, long startTime, long endTime) {
        // TODO Auto-generated method stub
        Iterator<Event> evtItr = Collections.emptyIterator();

        if(this.evtStore.containsKey(type)){
            evtItr = Collections.synchronizedList(this.evtStore.get(type)
            .stream()
            .filter(e -> e.timestamp() >= startTime && e.timestamp() < endTime )
            .collect(Collectors.toList()))
            .iterator();
        } 

        return new EventIteratorC(evtItr);
    }
    
}
