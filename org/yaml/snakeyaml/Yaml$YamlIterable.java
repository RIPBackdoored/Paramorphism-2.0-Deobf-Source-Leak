package org.yaml.snakeyaml;

import java.util.*;

private static class YamlIterable implements Iterable<Object>
{
    private Iterator<Object> iterator;
    
    public YamlIterable(final Iterator<Object> iterator) {
        super();
        this.iterator = iterator;
    }
    
    @Override
    public Iterator<Object> iterator() {
        return this.iterator;
    }
}
