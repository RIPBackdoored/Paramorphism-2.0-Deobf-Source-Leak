package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.databind.annotation.*;
import com.fasterxml.jackson.core.*;
import java.io.*;
import com.fasterxml.jackson.databind.jsonFormatVisitors.*;
import java.lang.reflect.*;
import com.fasterxml.jackson.databind.*;

@JacksonStdImpl
public static final class FloatSerializer extends Base<Object>
{
    static final FloatSerializer instance;
    
    public FloatSerializer() {
        super(Float.class, JsonParser.NumberType.FLOAT, "number");
    }
    
    @Override
    public void serialize(final Object o, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeNumber((float)o);
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
    
    static {
        instance = new FloatSerializer();
    }
}
