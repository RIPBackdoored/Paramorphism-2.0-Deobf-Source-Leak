package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.databind.ser.impl.*;
import com.fasterxml.jackson.core.*;
import java.io.*;
import com.fasterxml.jackson.databind.jsonFormatVisitors.*;
import com.fasterxml.jackson.databind.*;

public static class Dynamic extends StdSerializer<Object>
{
    protected transient PropertySerializerMap _dynamicSerializers;
    
    public Dynamic() {
        super(String.class, false);
        this._dynamicSerializers = PropertySerializerMap.emptyForProperties();
    }
    
    Object readResolve() {
        this._dynamicSerializers = PropertySerializerMap.emptyForProperties();
        return this;
    }
    
    @Override
    public void serialize(final Object o, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException {
        final Class<?> class1 = o.getClass();
        final PropertySerializerMap dynamicSerializers = this._dynamicSerializers;
        JsonSerializer<Object> jsonSerializer = dynamicSerializers.serializerFor(class1);
        if (jsonSerializer == null) {
            jsonSerializer = this._findAndAddDynamic(dynamicSerializers, class1, serializerProvider);
        }
        jsonSerializer.serialize(o, jsonGenerator, serializerProvider);
    }
    
    @Override
    public void acceptJsonFormatVisitor(final JsonFormatVisitorWrapper jsonFormatVisitorWrapper, final JavaType javaType) throws JsonMappingException {
        this.visitStringFormat(jsonFormatVisitorWrapper, javaType);
    }
    
    protected JsonSerializer<Object> _findAndAddDynamic(final PropertySerializerMap propertySerializerMap, final Class<?> clazz, final SerializerProvider serializerProvider) throws JsonMappingException {
        if (clazz == Object.class) {
            final Default default1 = new Default(8, clazz);
            this._dynamicSerializers = propertySerializerMap.newWith(clazz, default1);
            return default1;
        }
        final PropertySerializerMap.SerializerAndMapResult andAddKeySerializer = propertySerializerMap.findAndAddKeySerializer(clazz, serializerProvider, null);
        if (propertySerializerMap != andAddKeySerializer.map) {
            this._dynamicSerializers = andAddKeySerializer.map;
        }
        return andAddKeySerializer.serializer;
    }
}
