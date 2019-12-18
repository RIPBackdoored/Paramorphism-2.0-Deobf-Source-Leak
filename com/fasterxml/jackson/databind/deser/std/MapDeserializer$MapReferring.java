package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.databind.deser.impl.*;
import com.fasterxml.jackson.databind.deser.*;
import java.util.*;
import java.io.*;

static class MapReferring extends ReadableObjectId.Referring
{
    private final MapReferringAccumulator _parent;
    public final Map<Object, Object> next;
    public final Object key;
    
    MapReferring(final MapReferringAccumulator parent, final UnresolvedForwardReference unresolvedForwardReference, final Class<?> clazz, final Object key) {
        super(unresolvedForwardReference, clazz);
        this.next = new LinkedHashMap<Object, Object>();
        this._parent = parent;
        this.key = key;
    }
    
    @Override
    public void handleResolvedForwardReference(final Object o, final Object o2) throws IOException {
        this._parent.resolveForwardReference(o, o2);
    }
}
