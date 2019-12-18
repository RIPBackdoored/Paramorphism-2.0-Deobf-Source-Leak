package com.fasterxml.jackson.annotation;

import java.io.*;

public static final class IdKey implements Serializable
{
    private static final long serialVersionUID = 1L;
    public final Class<?> type;
    public final Class<?> scope;
    public final Object key;
    private final int hashCode;
    
    public IdKey(final Class<?> type, final Class<?> scope, final Object key) {
        super();
        if (key == null) {
            throw new IllegalArgumentException("Can not construct IdKey for null key");
        }
        this.type = type;
        this.scope = scope;
        this.key = key;
        int hashCode = key.hashCode() + type.getName().hashCode();
        if (scope != null) {
            hashCode ^= scope.getName().hashCode();
        }
        this.hashCode = hashCode;
    }
    
    @Override
    public int hashCode() {
        return this.hashCode;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (o.getClass() != this.getClass()) {
            return false;
        }
        final IdKey idKey = (IdKey)o;
        return idKey.key.equals(this.key) && idKey.type == this.type && idKey.scope == this.scope;
    }
    
    @Override
    public String toString() {
        return String.format("[ObjectId: key=%s, type=%s, scope=%s]", this.key, (this.type == null) ? "NONE" : this.type.getName(), (this.scope == null) ? "NONE" : this.scope.getName());
    }
}
