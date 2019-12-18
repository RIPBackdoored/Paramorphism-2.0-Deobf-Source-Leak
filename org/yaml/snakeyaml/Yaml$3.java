package org.yaml.snakeyaml;

import java.util.*;
import org.yaml.snakeyaml.events.*;
import org.yaml.snakeyaml.parser.*;

class Yaml$3 implements Iterator<Event> {
    final Parser val$parser;
    final Yaml this$0;
    
    Yaml$3(final Yaml this$0, final Parser val$parser) {
        this.this$0 = this$0;
        this.val$parser = val$parser;
        super();
    }
    
    @Override
    public boolean hasNext() {
        return this.val$parser.peekEvent() != null;
    }
    
    @Override
    public Event next() {
        return this.val$parser.getEvent();
    }
    
    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public Object next() {
        return this.next();
    }
}