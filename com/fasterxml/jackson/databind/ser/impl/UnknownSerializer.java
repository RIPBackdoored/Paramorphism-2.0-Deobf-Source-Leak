package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.databind.ser.std.*;
import java.io.*;
import com.fasterxml.jackson.databind.jsontype.*;
import com.fasterxml.jackson.core.*;
import java.lang.reflect.*;
import com.fasterxml.jackson.databind.jsonFormatVisitors.*;
import com.fasterxml.jackson.databind.*;

public class UnknownSerializer extends StdSerializer<Object>
{
    public UnknownSerializer() {
        super(Object.class);
    }
    
    public UnknownSerializer(final Class<?> clazz) {
        super(clazz, false);
    }
    
    @Override
    public void serialize(final Object o, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException {
        if (serializerProvider.isEnabled(SerializationFeature.FAIL_ON_EMPTY_BEANS)) {
            this.failForEmpty(serializerProvider, o);
        }
        jsonGenerator.writeStartObject();
        jsonGenerator.writeEndObject();
    }
    
    @Override
    public final void serializeWithType(final Object o, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider, final TypeSerializer typeSerializer) throws IOException {
        if (serializerProvider.isEnabled(SerializationFeature.FAIL_ON_EMPTY_BEANS)) {
            this.failForEmpty(serializerProvider, o);
        }
        typeSerializer.writeTypeSuffix(jsonGenerator, typeSerializer.writeTypePrefix(jsonGenerator, typeSerializer.typeId(o, JsonToken.START_OBJECT)));
    }
    
    @Override
    public boolean isEmpty(final SerializerProvider serializerProvider, final Object o) {
        return true;
    }
    
    @Override
    public JsonNode getSchema(final SerializerProvider serializerProvider, final Type type) throws JsonMappingException {
        return null;
    }
    
    @Override
    public void acceptJsonFormatVisitor(final JsonFormatVisitorWrapper jsonFormatVisitorWrapper, final JavaType javaType) throws JsonMappingException {
        jsonFormatVisitorWrapper.expectAnyFormat(javaType);
    }
    
    protected void failForEmpty(final SerializerProvider serializerProvider, final Object o) throws JsonMappingException {
        serializerProvider.reportBadDefinition(this.handledType(), String.format("No serializer found for class %s and no properties discovered to create BeanSerializer (to avoid exception, disable SerializationFeature.FAIL_ON_EMPTY_BEANS)", o.getClass().getName()));
    }
}
