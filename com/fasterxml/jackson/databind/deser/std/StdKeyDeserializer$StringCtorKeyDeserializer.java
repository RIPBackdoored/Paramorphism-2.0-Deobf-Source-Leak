package com.fasterxml.jackson.databind.deser.std;

import java.lang.reflect.*;
import com.fasterxml.jackson.databind.*;

static final class StringCtorKeyDeserializer extends StdKeyDeserializer
{
    private static final long serialVersionUID = 1L;
    protected final Constructor<?> _ctor;
    
    public StringCtorKeyDeserializer(final Constructor<?> ctor) {
        super(-1, ctor.getDeclaringClass());
        this._ctor = ctor;
    }
    
    public Object _parse(final String s, final DeserializationContext deserializationContext) throws Exception {
        return this._ctor.newInstance(s);
    }
}
