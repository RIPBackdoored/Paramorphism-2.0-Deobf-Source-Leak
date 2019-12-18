package com.fasterxml.jackson.databind.ser;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.jsontype.*;
import com.fasterxml.jackson.databind.type.*;

public static class Base implements Serializers
{
    public Base() {
        super();
    }
    
    @Override
    public JsonSerializer<?> findSerializer(final SerializationConfig serializationConfig, final JavaType javaType, final BeanDescription beanDescription) {
        return null;
    }
    
    @Override
    public JsonSerializer<?> findReferenceSerializer(final SerializationConfig serializationConfig, final ReferenceType referenceType, final BeanDescription beanDescription, final TypeSerializer typeSerializer, final JsonSerializer<Object> jsonSerializer) {
        return this.findSerializer(serializationConfig, referenceType, beanDescription);
    }
    
    @Override
    public JsonSerializer<?> findArraySerializer(final SerializationConfig serializationConfig, final ArrayType arrayType, final BeanDescription beanDescription, final TypeSerializer typeSerializer, final JsonSerializer<Object> jsonSerializer) {
        return null;
    }
    
    @Override
    public JsonSerializer<?> findCollectionSerializer(final SerializationConfig serializationConfig, final CollectionType collectionType, final BeanDescription beanDescription, final TypeSerializer typeSerializer, final JsonSerializer<Object> jsonSerializer) {
        return null;
    }
    
    @Override
    public JsonSerializer<?> findCollectionLikeSerializer(final SerializationConfig serializationConfig, final CollectionLikeType collectionLikeType, final BeanDescription beanDescription, final TypeSerializer typeSerializer, final JsonSerializer<Object> jsonSerializer) {
        return null;
    }
    
    @Override
    public JsonSerializer<?> findMapSerializer(final SerializationConfig serializationConfig, final MapType mapType, final BeanDescription beanDescription, final JsonSerializer<Object> jsonSerializer, final TypeSerializer typeSerializer, final JsonSerializer<Object> jsonSerializer2) {
        return null;
    }
    
    @Override
    public JsonSerializer<?> findMapLikeSerializer(final SerializationConfig serializationConfig, final MapLikeType mapLikeType, final BeanDescription beanDescription, final JsonSerializer<Object> jsonSerializer, final TypeSerializer typeSerializer, final JsonSerializer<Object> jsonSerializer2) {
        return null;
    }
}
