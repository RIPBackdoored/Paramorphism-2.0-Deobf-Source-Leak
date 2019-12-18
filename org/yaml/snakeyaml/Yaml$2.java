package org.yaml.snakeyaml;

import java.util.*;
import org.yaml.snakeyaml.nodes.*;
import org.yaml.snakeyaml.composer.*;

class Yaml$2 implements Iterator<Node> {
    final Composer val$composer;
    final Yaml this$0;
    
    Yaml$2(final Yaml this$0, final Composer val$composer) {
        this.this$0 = this$0;
        this.val$composer = val$composer;
        super();
    }
    
    @Override
    public boolean hasNext() {
        return this.val$composer.checkNode();
    }
    
    @Override
    public Node next() {
        return this.val$composer.getNode();
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