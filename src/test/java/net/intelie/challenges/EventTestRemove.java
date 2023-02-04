package net.intelie.challenges;

import org.junit.Test;
import org.junit.After;


import static org.junit.Assert.assertEquals;

import java.util.Date;

import java.util.stream.IntStream;

public class EventTestRemove {
    private final long MIN_TIME = 0;
    private final long MAX_TIME = Long.MAX_VALUE;
    private final int N_EVENT_PER_TYPE = 5; 
    private final EventStoreC eventstore = new EventStoreC();  
    private final String[] EVENT_TYPES =  IntStream.rangeClosed(0, 5).mapToObj(String::valueOf).map(s -> "TYPE_" + s).toArray(String[]::new);



    @Test
    public void InsertEvents() throws Exception {
        int totalEvents = 0; 


        for (int i = 0; i < N_EVENT_PER_TYPE; i++) {
            for (String evtType : EVENT_TYPES) {
                Event e = new Event(evtType, new Date().getTime());
                eventstore.insert(e);
            }
        }
        
        for (String evtType : EVENT_TYPES) {
           int n_evts = 0;
            EventIterator eItr =  eventstore.query(evtType, MIN_TIME, MAX_TIME);
            while(eItr.moveNext()) {
                assertEquals(eItr.current().type(), evtType);
                n_evts++;
            }
            totalEvents += n_evts;
            assertEquals(n_evts, N_EVENT_PER_TYPE);
           
        }
        

        assertEquals(totalEvents, EVENT_TYPES.length * N_EVENT_PER_TYPE);
   

    }

    @After
    @Test
    public void RemoveEvents() throws Exception {
        for (String evtType : EVENT_TYPES) {
            eventstore.removeAll(evtType);            
         }

         for (String evtType : EVENT_TYPES) {
            int n_evts = 0;
             EventIterator eItr =  eventstore.query(evtType, MIN_TIME, MAX_TIME);
             while(eItr.moveNext()) {
                 n_evts++;
             }
             assertEquals(n_evts, 0);
            
         }
         
    }


}