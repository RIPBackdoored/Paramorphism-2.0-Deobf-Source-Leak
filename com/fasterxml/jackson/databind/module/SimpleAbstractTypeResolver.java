package com.fasterxml.jackson.databind.module;

import java.io.*;
import java.util.*;
import com.fasterxml.jackson.databind.type.*;
import java.lang.reflect.*;
import com.fasterxml.jackson.databind.*;

public class SimpleAbstractTypeResolver extends AbstractTypeResolver implements Serializable
{
    private static final long serialVersionUID = 8635483102371490919L;
    protected final HashMap<ClassKey, Class<?>> _mappings;
    
    public SimpleAbstractTypeResolver() {
        super();
        this._mappings = new HashMap<ClassKey, Class<?>>();
    }
    
    public <T> SimpleAbstractTypeResolver addMapping(final Class<T> clazz, final Class<? extends T> clazz2) {
        if (clazz == clazz2) {
            throw new IllegalArgumentException("Cannot add mapping from class to itself");
        }
        if (!clazz.isAssignableFrom(clazz2)) {
            throw new IllegalArgumentException("Cannot add mapping from class " + clazz.getName() + " to " + clazz2.getName() + ", as latter is not a subtype of former");
        }
        if (!Modifier.isAbstract(clazz.getModifiers())) {
            throw new IllegalArgumentException("Cannot add mapping from class " + clazz.getName() + " since it is not abstract");
        }
        this._mappings.put(new ClassKey(clazz), clazz2);
        return this;
    }
    
    @Override
    public JavaType findTypeMapping(final DeserializationConfig deserializationConfig, final JavaType javaType) {
        final Class<?> clazz = this._mappings.get(new ClassKey(javaType.getRawClass()));
        if (clazz == null) {
            return null;
        }
        return deserializationConfig.getTypeFactory().constructSpecializedType(javaType, clazz);
    }
    
    @Deprecated
    @Override
    public JavaType resolveAbstractType(final DeserializationConfig deserializationConfig, final JavaType javaType) {
        return null;
    }
    
    @Override
    public JavaType resolveAbstractType(final DeserializationConfig deserializationConfig, final BeanDescription beanDescription) {
        return null;
    }
}
