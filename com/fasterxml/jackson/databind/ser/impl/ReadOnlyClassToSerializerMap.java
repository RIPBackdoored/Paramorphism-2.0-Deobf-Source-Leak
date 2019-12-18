package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.databind.util.*;
import java.util.*;
import com.fasterxml.jackson.databind.*;

public final class ReadOnlyClassToSerializerMap
{
    private final Bucket[] _buckets;
    private final int _size;
    private final int _mask;
    
    public ReadOnlyClassToSerializerMap(final Map<TypeKey, JsonSerializer<Object>> map) {
        super();
        final int size = findSize(map.size());
        this._size = size;
        this._mask = size - 1;
        final Bucket[] buckets = new Bucket[size];
        for (final Map.Entry<TypeKey, JsonSerializer<Object>> entry : map.entrySet()) {
            final TypeKey typeKey = entry.getKey();
            final int n = typeKey.hashCode() & this._mask;
            buckets[n] = new Bucket(buckets[n], typeKey, entry.getValue());
        }
        this._buckets = buckets;
    }
    
    private static final int findSize(final int n) {
        int n2;
        int i;
        for (n2 = ((n <= 64) ? (n + n) : (n + (n >> 2))), i = 8; i < n2; i += i) {}
        return i;
    }
    
    public static ReadOnlyClassToSerializerMap from(final HashMap<TypeKey, JsonSerializer<Object>> hashMap) {
        return new ReadOnlyClassToSerializerMap(hashMap);
    }
    
    public int size() {
        return this._size;
    }
    
    public JsonSerializer<Object> typedValueSerializer(final JavaType javaType) {
        Bucket next = this._buckets[TypeKey.typedHash(javaType) & this._mask];
        if (next == null) {
            return null;
        }
        if (next.matchesTyped(javaType)) {
            return next.value;
        }
        while ((next = next.next) != null) {
            if (next.matchesTyped(javaType)) {
                return next.value;
            }
        }
        return null;
    }
    
    public JsonSerializer<Object> typedValueSerializer(final Class<?> clazz) {
        Bucket next = this._buckets[TypeKey.typedHash(clazz) & this._mask];
        if (next == null) {
            return null;
        }
        if (next.matchesTyped(clazz)) {
            return next.value;
        }
        while ((next = next.next) != null) {
            if (next.matchesTyped(clazz)) {
                return next.value;
            }
        }
        return null;
    }
    
    public JsonSerializer<Object> untypedValueSerializer(final JavaType javaType) {
        Bucket next = this._buckets[TypeKey.untypedHash(javaType) & this._mask];
        if (next == null) {
            return null;
        }
        if (next.matchesUntyped(javaType)) {
            return next.value;
        }
        while ((next = next.next) != null) {
            if (next.matchesUntyped(javaType)) {
                return next.value;
            }
        }
        return null;
    }
    
    public JsonSerializer<Object> untypedValueSerializer(final Class<?> clazz) {
        Bucket next = this._buckets[TypeKey.untypedHash(clazz) & this._mask];
        if (next == null) {
            return null;
        }
        if (next.matchesUntyped(clazz)) {
            return next.value;
        }
        while ((next = next.next) != null) {
            if (next.matchesUntyped(clazz)) {
                return next.value;
            }
        }
        return null;
    }
    
    private static final class Bucket
    {
        public final JsonSerializer<Object> value;
        public final Bucket next;
        protected final Class<?> _class;
        protected final JavaType _type;
        protected final boolean _isTyped;
        
        public Bucket(final Bucket next, final TypeKey typeKey, final JsonSerializer<Object> value) {
            super();
            this.next = next;
            this.value = value;
            this._isTyped = typeKey.isTyped();
            this._class = typeKey.getRawType();
            this._type = typeKey.getType();
        }
        
        public boolean matchesTyped(final Class<?> clazz) {
            return this._class == clazz && this._isTyped;
        }
        
        public boolean matchesUntyped(final Class<?> clazz) {
            return this._class == clazz && !this._isTyped;
        }
        
        public boolean matchesTyped(final JavaType javaType) {
            return this._isTyped && javaType.equals(this._type);
        }
        
        public boolean matchesUntyped(final JavaType javaType) {
            return !this._isTyped && javaType.equals(this._type);
        }
    }
}
