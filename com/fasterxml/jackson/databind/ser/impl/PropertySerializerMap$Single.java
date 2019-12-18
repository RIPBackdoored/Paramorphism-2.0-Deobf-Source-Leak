package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.databind.*;

private static final class Single extends PropertySerializerMap
{
    private final Class<?> _type;
    private final JsonSerializer<Object> _serializer;
    
    public Single(final PropertySerializerMap propertySerializerMap, final Class<?> type, final JsonSerializer<Object> serializer) {
        super(propertySerializerMap);
        this._type = type;
        this._serializer = serializer;
    }
    
    @Override
    public JsonSerializer<Object> serializerFor(final Class<?> clazz) {
        if (clazz == this._type) {
            return this._serializer;
        }
        return null;
    }
    
    @Override
    public PropertySerializerMap newWith(final Class<?> clazz, final JsonSerializer<Object> jsonSerializer) {
        return new Double(this, this._type, this._serializer, clazz, jsonSerializer);
    }
}
