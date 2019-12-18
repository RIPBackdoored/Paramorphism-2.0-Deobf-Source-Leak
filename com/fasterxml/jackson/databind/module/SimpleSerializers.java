package com.fasterxml.jackson.databind.module;

import com.fasterxml.jackson.databind.ser.*;
import java.io.*;
import java.util.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.jsontype.*;
import com.fasterxml.jackson.databind.type.*;

public class SimpleSerializers extends Serializers.Base implements Serializable
{
    private static final long serialVersionUID = 8531646511998456779L;
    protected HashMap<ClassKey, JsonSerializer<?>> _classMappings;
    protected HashMap<ClassKey, JsonSerializer<?>> _interfaceMappings;
    protected boolean _hasEnumSerializer;
    
    public SimpleSerializers() {
        super();
        this._classMappings = null;
        this._interfaceMappings = null;
        this._hasEnumSerializer = false;
    }
    
    public SimpleSerializers(final List<JsonSerializer<?>> list) {
        super();
        this._classMappings = null;
        this._interfaceMappings = null;
        this._hasEnumSerializer = false;
        this.addSerializers(list);
    }
    
    public void addSerializer(final JsonSerializer<?> jsonSerializer) {
        final Class<?> handledType = jsonSerializer.handledType();
        if (handledType == null || handledType == Object.class) {
            throw new IllegalArgumentException("JsonSerializer of type " + jsonSerializer.getClass().getName() + " does not define valid handledType() -- must either register with method that takes type argument " + " or make serializer extend 'com.fasterxml.jackson.databind.ser.std.StdSerializer'");
        }
        this._addSerializer(handledType, jsonSerializer);
    }
    
    public <T> void addSerializer(final Class<? extends T> clazz, final JsonSerializer<T> jsonSerializer) {
        this._addSerializer(clazz, jsonSerializer);
    }
    
    public void addSerializers(final List<JsonSerializer<?>> list) {
        final Iterator<JsonSerializer<?>> iterator = list.iterator();
        while (iterator.hasNext()) {
            this.addSerializer(iterator.next());
        }
    }
    
    @Override
    public JsonSerializer<?> findSerializer(final SerializationConfig serializationConfig, final JavaType javaType, final BeanDescription beanDescription) {
        Class<?> clazz = javaType.getRawClass();
        final ClassKey classKey = new ClassKey(clazz);
        if (clazz.isInterface()) {
            if (this._interfaceMappings != null) {
                final JsonSerializer<?> jsonSerializer = this._interfaceMappings.get(classKey);
                if (jsonSerializer != null) {
                    return jsonSerializer;
                }
            }
        }
        else if (this._classMappings != null) {
            final JsonSerializer<?> jsonSerializer2 = this._classMappings.get(classKey);
            if (jsonSerializer2 != null) {
                return jsonSerializer2;
            }
            if (this._hasEnumSerializer && javaType.isEnumType()) {
                classKey.reset(Enum.class);
                final JsonSerializer<?> jsonSerializer3 = this._classMappings.get(classKey);
                if (jsonSerializer3 != null) {
                    return jsonSerializer3;
                }
            }
            for (Class<?> superclass = clazz; superclass != null; superclass = superclass.getSuperclass()) {
                classKey.reset(superclass);
                final JsonSerializer<?> jsonSerializer4 = this._classMappings.get(classKey);
                if (jsonSerializer4 != null) {
                    return jsonSerializer4;
                }
            }
        }
        if (this._interfaceMappings != null) {
            final JsonSerializer<?> findInterfaceMapping = this._findInterfaceMapping(clazz, classKey);
            if (findInterfaceMapping != null) {
                return findInterfaceMapping;
            }
            if (!clazz.isInterface()) {
                while ((clazz = clazz.getSuperclass()) != null) {
                    final JsonSerializer<?> findInterfaceMapping2 = this._findInterfaceMapping(clazz, classKey);
                    if (findInterfaceMapping2 != null) {
                        return findInterfaceMapping2;
                    }
                }
            }
        }
        return null;
    }
    
    @Override
    public JsonSerializer<?> findArraySerializer(final SerializationConfig serializationConfig, final ArrayType arrayType, final BeanDescription beanDescription, final TypeSerializer typeSerializer, final JsonSerializer<Object> jsonSerializer) {
        return this.findSerializer(serializationConfig, arrayType, beanDescription);
    }
    
    @Override
    public JsonSerializer<?> findCollectionSerializer(final SerializationConfig serializationConfig, final CollectionType collectionType, final BeanDescription beanDescription, final TypeSerializer typeSerializer, final JsonSerializer<Object> jsonSerializer) {
        return this.findSerializer(serializationConfig, collectionType, beanDescription);
    }
    
    @Override
    public JsonSerializer<?> findCollectionLikeSerializer(final SerializationConfig serializationConfig, final CollectionLikeType collectionLikeType, final BeanDescription beanDescription, final TypeSerializer typeSerializer, final JsonSerializer<Object> jsonSerializer) {
        return this.findSerializer(serializationConfig, collectionLikeType, beanDescription);
    }
    
    @Override
    public JsonSerializer<?> findMapSerializer(final SerializationConfig serializationConfig, final MapType mapType, final BeanDescription beanDescription, final JsonSerializer<Object> jsonSerializer, final TypeSerializer typeSerializer, final JsonSerializer<Object> jsonSerializer2) {
        return this.findSerializer(serializationConfig, mapType, beanDescription);
    }
    
    @Override
    public JsonSerializer<?> findMapLikeSerializer(final SerializationConfig serializationConfig, final MapLikeType mapLikeType, final BeanDescription beanDescription, final JsonSerializer<Object> jsonSerializer, final TypeSerializer typeSerializer, final JsonSerializer<Object> jsonSerializer2) {
        return this.findSerializer(serializationConfig, mapLikeType, beanDescription);
    }
    
    protected JsonSerializer<?> _findInterfaceMapping(final Class<?> clazz, final ClassKey classKey) {
        for (final Class clazz2 : clazz.getInterfaces()) {
            classKey.reset(clazz2);
            final JsonSerializer<?> jsonSerializer = this._interfaceMappings.get(classKey);
            if (jsonSerializer != null) {
                return jsonSerializer;
            }
            final JsonSerializer<?> findInterfaceMapping = this._findInterfaceMapping(clazz2, classKey);
            if (findInterfaceMapping != null) {
                return findInterfaceMapping;
            }
        }
        return null;
    }
    
    protected void _addSerializer(final Class<?> clazz, final JsonSerializer<?> jsonSerializer) {
        final ClassKey classKey = new ClassKey(clazz);
        if (clazz.isInterface()) {
            if (this._interfaceMappings == null) {
                this._interfaceMappings = new HashMap<ClassKey, JsonSerializer<?>>();
            }
            this._interfaceMappings.put(classKey, jsonSerializer);
        }
        else {
            if (this._classMappings == null) {
                this._classMappings = new HashMap<ClassKey, JsonSerializer<?>>();
            }
            this._classMappings.put(classKey, jsonSerializer);
            if (clazz == Enum.class) {
                this._hasEnumSerializer = true;
            }
        }
    }
}
