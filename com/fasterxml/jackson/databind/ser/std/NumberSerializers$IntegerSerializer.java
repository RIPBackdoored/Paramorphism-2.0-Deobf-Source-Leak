package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.databind.annotation.*;
import com.fasterxml.jackson.core.*;
import java.io.*;
import com.fasterxml.jackson.databind.jsontype.*;
import com.fasterxml.jackson.databind.jsonFormatVisitors.*;
import java.lang.reflect.*;
import com.fasterxml.jackson.databind.*;

@JacksonStdImpl
public static final class IntegerSerializer extends Base<Object>
{
    public IntegerSerializer(final Class<?> clazz) {
        super(clazz, JsonParser.NumberType.INT, "integer");
    }
    
    @Override
    public void serialize(final Object o, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeNumber((int)o);
    }
    
    @Override
    public void serializeWithType(final Object o, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider, final TypeSerializer typeSerializer) throws IOException {
        this.serialize(o, jsonGenerator, serializerProvider);
    }
    
    @Override
    public JsonSerializer createContextual(final SerializerProvider serializerProvider, final BeanProperty beanProperty) throws JsonMappingException {
        return super.createContextual(serializerProvider, beanProperty);
    }
    
    @Override
    public void acceptJsonFormatVisitor(final JsonFormatVisitorWrapper jsonFormatVisitorWrapper, final JavaType javaType) throws JsonMappingException {
        super.acceptJsonFormatVisitor(jsonFormatVisitorWrapper, javaType);
    }
    
    @Override
    public JsonNode getSchema(final SerializerProvider serializerProvider, final Type type) {
        return super.getSchema(serializerProvider, type);
    }
}
