package com.fasterxml.jackson.databind.util;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import java.io.*;
import com.fasterxml.jackson.databind.jsontype.*;

public class RawValue implements JsonSerializable
{
    protected Object _value;
    
    public RawValue(final String value) {
        super();
        this._value = value;
    }
    
    public RawValue(final SerializableString value) {
        super();
        this._value = value;
    }
    
    public RawValue(final JsonSerializable value) {
        super();
        this._value = value;
    }
    
    protected RawValue(final Object value, final boolean b) {
        super();
        this._value = value;
    }
    
    public Object rawValue() {
        return this._value;
    }
    
    @Override
    public void serialize(final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException {
        if (this._value instanceof JsonSerializable) {
            ((JsonSerializable)this._value).serialize(jsonGenerator, serializerProvider);
        }
        else {
            this._serialize(jsonGenerator);
        }
    }
    
    @Override
    public void serializeWithType(final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider, final TypeSerializer typeSerializer) throws IOException {
        if (this._value instanceof JsonSerializable) {
            ((JsonSerializable)this._value).serializeWithType(jsonGenerator, serializerProvider, typeSerializer);
        }
        else if (this._value instanceof SerializableString) {
            this.serialize(jsonGenerator, serializerProvider);
        }
    }
    
    public void serialize(final JsonGenerator jsonGenerator) throws IOException {
        if (this._value instanceof JsonSerializable) {
            jsonGenerator.writeObject(this._value);
        }
        else {
            this._serialize(jsonGenerator);
        }
    }
    
    protected void _serialize(final JsonGenerator jsonGenerator) throws IOException {
        if (this._value instanceof SerializableString) {
            jsonGenerator.writeRawValue((SerializableString)this._value);
        }
        else {
            jsonGenerator.writeRawValue(String.valueOf(this._value));
        }
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RawValue)) {
            return false;
        }
        final RawValue rawValue = (RawValue)o;
        return this._value == rawValue._value || (this._value != null && this._value.equals(rawValue._value));
    }
    
    @Override
    public int hashCode() {
        return (this._value == null) ? 0 : this._value.hashCode();
    }
    
    @Override
    public String toString() {
        return String.format("[RawValue of type %s]", ClassUtil.classNameOf(this._value));
    }
}
