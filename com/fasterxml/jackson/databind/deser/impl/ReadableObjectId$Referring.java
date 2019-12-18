package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.databind.deser.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.core.*;
import java.io.*;

public abstract static class Referring
{
    private final UnresolvedForwardReference _reference;
    private final Class<?> _beanType;
    
    public Referring(final UnresolvedForwardReference reference, final Class<?> beanType) {
        super();
        this._reference = reference;
        this._beanType = beanType;
    }
    
    public Referring(final UnresolvedForwardReference reference, final JavaType javaType) {
        super();
        this._reference = reference;
        this._beanType = javaType.getRawClass();
    }
    
    public JsonLocation getLocation() {
        return this._reference.getLocation();
    }
    
    public Class<?> getBeanType() {
        return this._beanType;
    }
    
    public abstract void handleResolvedForwardReference(final Object p0, final Object p1) throws IOException;
    
    public boolean hasId(final Object o) {
        return o.equals(this._reference.getUnresolvedId());
    }
}
