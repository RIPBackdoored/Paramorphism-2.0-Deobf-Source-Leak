package com.fasterxml.jackson.annotation;

import java.util.*;

public static final class UUIDGenerator extends Base<UUID>
{
    private static final long serialVersionUID = 1L;
    
    public UUIDGenerator() {
        this(Object.class);
    }
    
    private UUIDGenerator(final Class<?> clazz) {
        super(Object.class);
    }
    
    @Override
    public ObjectIdGenerator<UUID> forScope(final Class<?> clazz) {
        return this;
    }
    
    @Override
    public ObjectIdGenerator<UUID> newForSerialization(final Object o) {
        return this;
    }
    
    @Override
    public UUID generateId(final Object o) {
        return UUID.randomUUID();
    }
    
    @Override
    public IdKey key(final Object o) {
        if (o == null) {
            return null;
        }
        return new IdKey(this.getClass(), null, o);
    }
    
    @Override
    public boolean canUseFor(final ObjectIdGenerator<?> objectIdGenerator) {
        return objectIdGenerator.getClass() == this.getClass();
    }
    
    @Override
    public Object generateId(final Object o) {
        return this.generateId(o);
    }
}
