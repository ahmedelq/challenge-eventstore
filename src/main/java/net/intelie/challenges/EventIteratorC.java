package net.intelie.challenges;
import java.util.Iterator;
import java.util.List;

public class EventIteratorC implements EventIterator{
    
    private final Iterator<Event> evtItr;
    private Event currEvt = null;

    public EventIteratorC(Iterator evtItr) {
        this.evtItr = evtItr; 
    }


    @Override
    public void close() throws Exception {
        // TODO Auto-generated method stub
    }

    @Override
    public synchronized boolean moveNext() {
        // TODO Auto-generated method stub
        boolean movedNext = false;
        if(this.evtItr.hasNext()){ 
            this.currEvt = this.evtItr.next();
            movedNext = true;
        }
        return movedNext;
    }

  

    @Override
    public synchronized Event current() {
        if(this.currEvt == null)
        throw new IllegalStateException();
        return this.currEvt;
    }

    @Override
    public synchronized void remove() {
        if(this.currEvt == null)
        throw new IllegalStateException();
        this.evtItr.remove();
    }
    
}
