package org.yaml.snakeyaml;

import java.util.*;

class Yaml$1 implements Iterator<Object> {
    final Yaml this$0;
    
    Yaml$1(final Yaml this$0) {
        this.this$0 = this$0;
        super();
    }
    
    @Override
    public boolean hasNext() {
        return this.this$0.constructor.checkData();
    }
    
    @Override
    public Object next() {
        return this.this$0.constructor.getData();
    }
    
    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}