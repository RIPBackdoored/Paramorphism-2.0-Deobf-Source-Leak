package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.deser.*;
import com.fasterxml.jackson.databind.jsontype.*;
import com.fasterxml.jackson.databind.util.*;
import com.fasterxml.jackson.databind.introspect.*;
import com.fasterxml.jackson.databind.*;

public class JsonLocationInstantiator extends Base
{
    public JsonLocationInstantiator() {
        super(JsonLocation.class);
    }
    
    @Override
    public boolean canCreateFromObjectWith() {
        return true;
    }
    
    @Override
    public SettableBeanProperty[] getFromObjectArguments(final DeserializationConfig deserializationConfig) {
        final JavaType constructType = deserializationConfig.constructType(Integer.TYPE);
        final JavaType constructType2 = deserializationConfig.constructType(Long.TYPE);
        return new SettableBeanProperty[] { creatorProp("sourceRef", deserializationConfig.constructType(Object.class), 0), creatorProp("byteOffset", constructType2, 1), creatorProp("charOffset", constructType2, 2), creatorProp("lineNr", constructType, 3), creatorProp("columnNr", constructType, 4) };
    }
    
    private static CreatorProperty creatorProp(final String s, final JavaType javaType, final int n) {
        return new CreatorProperty(PropertyName.construct(s), javaType, null, null, null, null, n, null, PropertyMetadata.STD_REQUIRED);
    }
    
    @Override
    public Object createFromObjectWith(final DeserializationContext deserializationContext, final Object[] array) {
        return new JsonLocation(array[0], _long(array[1]), _long(array[2]), _int(array[3]), _int(array[4]));
    }
    
    private static final long _long(final Object o) {
        return (o == null) ? 0L : ((Number)o).longValue();
    }
    
    private static final int _int(final Object o) {
        return (o == null) ? 0 : ((Number)o).intValue();
    }
}
