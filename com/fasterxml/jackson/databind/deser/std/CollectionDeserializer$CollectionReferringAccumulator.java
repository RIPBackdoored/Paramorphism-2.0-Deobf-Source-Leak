package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.databind.deser.*;
import com.fasterxml.jackson.databind.deser.impl.*;
import java.util.*;
import java.io.*;

public static class CollectionReferringAccumulator
{
    private final Class<?> _elementType;
    private final Collection<Object> _result;
    private List<CollectionReferring> _accumulator;
    
    public CollectionReferringAccumulator(final Class<?> elementType, final Collection<Object> result) {
        super();
        this._accumulator = new ArrayList<CollectionReferring>();
        this._elementType = elementType;
        this._result = result;
    }
    
    public void add(final Object o) {
        if (this._accumulator.isEmpty()) {
            this._result.add(o);
        }
        else {
            this._accumulator.get(this._accumulator.size() - 1).next.add(o);
        }
    }
    
    public ReadableObjectId.Referring handleUnresolvedReference(final UnresolvedForwardReference unresolvedForwardReference) {
        final CollectionReferring collectionReferring = new CollectionReferring(this, unresolvedForwardReference, this._elementType);
        this._accumulator.add(collectionReferring);
        return collectionReferring;
    }
    
    public void resolveForwardReference(final Object o, final Object o2) throws IOException {
        final Iterator<CollectionReferring> iterator = this._accumulator.iterator();
        Collection<Object> collection = this._result;
        while (iterator.hasNext()) {
            final CollectionReferring collectionReferring = iterator.next();
            if (collectionReferring.hasId(o)) {
                iterator.remove();
                collection.add(o2);
                collection.addAll(collectionReferring.next);
                return;
            }
            collection = collectionReferring.next;
        }
        throw new IllegalArgumentException("Trying to resolve a forward reference with id [" + o + "] that wasn't previously seen as unresolved.");
    }
}
