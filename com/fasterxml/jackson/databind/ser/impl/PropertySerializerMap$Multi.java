package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.databind.*;
import java.util.*;

private static final class Multi extends PropertySerializerMap
{
    private static final int MAX_ENTRIES = 8;
    private final TypeAndSerializer[] _entries;
    
    public Multi(final PropertySerializerMap propertySerializerMap, final TypeAndSerializer[] entries) {
        super(propertySerializerMap);
        this._entries = entries;
    }
    
    @Override
    public JsonSerializer<Object> serializerFor(final Class<?> clazz) {
        for (int i = 0; i < this._entries.length; ++i) {
            final TypeAndSerializer typeAndSerializer = this._entries[i];
            if (typeAndSerializer.type == clazz) {
                return typeAndSerializer.serializer;
            }
        }
        return null;
    }
    
    @Override
    public PropertySerializerMap newWith(final Class<?> clazz, final JsonSerializer<Object> jsonSerializer) {
        final int length = this._entries.length;
        if (length != 8) {
            final TypeAndSerializer[] array = Arrays.copyOf(this._entries, length + 1);
            array[length] = new TypeAndSerializer(clazz, jsonSerializer);
            return new Multi(this, array);
        }
        if (this._resetWhenFull) {
            return new Single(this, clazz, jsonSerializer);
        }
        return this;
    }
}
