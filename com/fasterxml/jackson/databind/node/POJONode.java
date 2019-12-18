package com.fasterxml.jackson.databind.node;

import java.io.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.util.*;

public class POJONode extends ValueNode
{
    protected final Object _value;
    
    public POJONode(final Object value) {
        super();
        this._value = value;
    }
    
    @Override
    public JsonNodeType getNodeType() {
        return JsonNodeType.POJO;
    }
    
    @Override
    public JsonToken asToken() {
        return JsonToken.VALUE_EMBEDDED_OBJECT;
    }
    
    @Override
    public byte[] binaryValue() throws IOException {
        if (this._value instanceof byte[]) {
            return (byte[])this._value;
        }
        return super.binaryValue();
    }
    
    @Override
    public String asText() {
        return (this._value == null) ? "null" : this._value.toString();
    }
    
    @Override
    public String asText(final String s) {
        return (this._value == null) ? s : this._value.toString();
    }
    
    @Override
    public boolean asBoolean(final boolean b) {
        if (this._value != null && this._value instanceof Boolean) {
            return (boolean)this._value;
        }
        return b;
    }
    
    @Override
    public int asInt(final int n) {
        if (this._value instanceof Number) {
            return ((Number)this._value).intValue();
        }
        return n;
    }
    
    @Override
    public long asLong(final long n) {
        if (this._value instanceof Number) {
            return ((Number)this._value).longValue();
        }
        return n;
    }
    
    @Override
    public double asDouble(final double n) {
        if (this._value instanceof Number) {
            return ((Number)this._value).doubleValue();
        }
        return n;
    }
    
    @Override
    public final void serialize(final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException {
        if (this._value == null) {
            serializerProvider.defaultSerializeNull(jsonGenerator);
        }
        else if (this._value instanceof JsonSerializable) {
            ((JsonSerializable)this._value).serialize(jsonGenerator, serializerProvider);
        }
        else {
            serializerProvider.defaultSerializeValue(this._value, jsonGenerator);
        }
    }
    
    public Object getPojo() {
        return this._value;
    }
    
    @Override
    public boolean equals(final Object o) {
        return o == this || (o != null && o instanceof POJONode && this._pojoEquals((POJONode)o));
    }
    
    protected boolean _pojoEquals(final POJONode pojoNode) {
        if (this._value == null) {
            return pojoNode._value == null;
        }
        return this._value.equals(pojoNode._value);
    }
    
    @Override
    public int hashCode() {
        return this._value.hashCode();
    }
    
    @Override
    public String toString() {
        if (this._value instanceof byte[]) {
            return String.format("(binary value of %d bytes)", ((byte[])this._value).length);
        }
        if (this._value instanceof RawValue) {
            return String.format("(raw value '%s')", ((RawValue)this._value).toString());
        }
        return String.valueOf(this._value);
    }
}
