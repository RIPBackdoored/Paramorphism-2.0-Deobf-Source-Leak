package com.fasterxml.jackson.databind.ser;

import java.util.*;
import com.fasterxml.jackson.databind.util.*;
import java.util.concurrent.atomic.*;
import com.fasterxml.jackson.databind.ser.impl.*;
import com.fasterxml.jackson.databind.*;

public final class SerializerCache
{
    private final HashMap<TypeKey, JsonSerializer<Object>> _sharedMap;
    private final AtomicReference<ReadOnlyClassToSerializerMap> _readOnlyMap;
    
    public SerializerCache() {
        super();
        this._sharedMap = new HashMap<TypeKey, JsonSerializer<Object>>(64);
        this._readOnlyMap = new AtomicReference<ReadOnlyClassToSerializerMap>();
    }
    
    public ReadOnlyClassToSerializerMap getReadOnlyLookupMap() {
        final ReadOnlyClassToSerializerMap readOnlyClassToSerializerMap = this._readOnlyMap.get();
        if (readOnlyClassToSerializerMap != null) {
            return readOnlyClassToSerializerMap;
        }
        return this._makeReadOnlyLookupMap();
    }
    
    private final synchronized ReadOnlyClassToSerializerMap _makeReadOnlyLookupMap() {
        ReadOnlyClassToSerializerMap from = this._readOnlyMap.get();
        if (from == null) {
            from = ReadOnlyClassToSerializerMap.from(this._sharedMap);
            this._readOnlyMap.set(from);
        }
        return from;
    }
    
    public synchronized int size() {
        return this._sharedMap.size();
    }
    
    public JsonSerializer<Object> untypedValueSerializer(final Class<?> clazz) {
        synchronized (this) {
            return this._sharedMap.get(new TypeKey(clazz, false));
        }
    }
    
    public JsonSerializer<Object> untypedValueSerializer(final JavaType javaType) {
        synchronized (this) {
            return this._sharedMap.get(new TypeKey(javaType, false));
        }
    }
    
    public JsonSerializer<Object> typedValueSerializer(final JavaType javaType) {
        synchronized (this) {
            return this._sharedMap.get(new TypeKey(javaType, true));
        }
    }
    
    public JsonSerializer<Object> typedValueSerializer(final Class<?> clazz) {
        synchronized (this) {
            return this._sharedMap.get(new TypeKey(clazz, true));
        }
    }
    
    public void addTypedSerializer(final JavaType javaType, final JsonSerializer<Object> jsonSerializer) {
        synchronized (this) {
            if (this._sharedMap.put(new TypeKey(javaType, true), jsonSerializer) == null) {
                this._readOnlyMap.set(null);
            }
        }
    }
    
    public void addTypedSerializer(final Class<?> clazz, final JsonSerializer<Object> jsonSerializer) {
        synchronized (this) {
            if (this._sharedMap.put(new TypeKey(clazz, true), jsonSerializer) == null) {
                this._readOnlyMap.set(null);
            }
        }
    }
    
    public void addAndResolveNonTypedSerializer(final Class<?> clazz, final JsonSerializer<Object> jsonSerializer, final SerializerProvider serializerProvider) throws JsonMappingException {
        synchronized (this) {
            if (this._sharedMap.put(new TypeKey(clazz, false), jsonSerializer) == null) {
                this._readOnlyMap.set(null);
            }
            if (jsonSerializer instanceof ResolvableSerializer) {
                ((ResolvableSerializer)jsonSerializer).resolve(serializerProvider);
            }
        }
    }
    
    public void addAndResolveNonTypedSerializer(final JavaType javaType, final JsonSerializer<Object> jsonSerializer, final SerializerProvider serializerProvider) throws JsonMappingException {
        synchronized (this) {
            if (this._sharedMap.put(new TypeKey(javaType, false), jsonSerializer) == null) {
                this._readOnlyMap.set(null);
            }
            if (jsonSerializer instanceof ResolvableSerializer) {
                ((ResolvableSerializer)jsonSerializer).resolve(serializerProvider);
            }
        }
    }
    
    public void addAndResolveNonTypedSerializer(final Class<?> clazz, final JavaType javaType, final JsonSerializer<Object> jsonSerializer, final SerializerProvider serializerProvider) throws JsonMappingException {
        synchronized (this) {
            final JsonSerializer<Object> put = this._sharedMap.put(new TypeKey(clazz, false), jsonSerializer);
            final JsonSerializer<Object> put2 = this._sharedMap.put(new TypeKey(javaType, false), jsonSerializer);
            if (put == null || put2 == null) {
                this._readOnlyMap.set(null);
            }
            if (jsonSerializer instanceof ResolvableSerializer) {
                ((ResolvableSerializer)jsonSerializer).resolve(serializerProvider);
            }
        }
    }
    
    public synchronized void flush() {
        this._sharedMap.clear();
    }
}
