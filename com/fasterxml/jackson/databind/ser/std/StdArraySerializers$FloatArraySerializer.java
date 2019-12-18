package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.databind.annotation.*;
import com.fasterxml.jackson.core.*;
import java.io.*;
import java.lang.reflect.*;
import com.fasterxml.jackson.databind.jsonFormatVisitors.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.type.*;

@JacksonStdImpl
public static class FloatArraySerializer extends TypedPrimitiveArraySerializer<float[]>
{
    private static final JavaType VALUE_TYPE;
    
    public FloatArraySerializer() {
        super(float[].class);
    }
    
    public FloatArraySerializer(final FloatArraySerializer floatArraySerializer, final BeanProperty beanProperty, final Boolean b) {
        super(floatArraySerializer, beanProperty, b);
    }
    
    @Override
    public JsonSerializer<?> _withResolved(final BeanProperty beanProperty, final Boolean b) {
        return new FloatArraySerializer(this, beanProperty, b);
    }
    
    @Override
    public JavaType getContentType() {
        return FloatArraySerializer.VALUE_TYPE;
    }
    
    @Override
    public JsonSerializer<?> getContentSerializer() {
        return null;
    }
    
    @Override
    public boolean isEmpty(final SerializerProvider serializerProvider, final float[] array) {
        return array.length == 0;
    }
    
    @Override
    public boolean hasSingleElement(final float[] array) {
        return array.length == 1;
    }
    
    @Override
    public final void serialize(final float[] currentValue, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException {
        final int length = currentValue.length;
        if (length == 1 && this._shouldUnwrapSingle(serializerProvider)) {
            this.serializeContents(currentValue, jsonGenerator, serializerProvider);
            return;
        }
        jsonGenerator.writeStartArray(length);
        jsonGenerator.setCurrentValue(currentValue);
        this.serializeContents(currentValue, jsonGenerator, serializerProvider);
        jsonGenerator.writeEndArray();
    }
    
    public void serializeContents(final float[] array, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException {
        for (int i = 0; i < array.length; ++i) {
            jsonGenerator.writeNumber(array[i]);
        }
    }
    
    @Override
    public JsonNode getSchema(final SerializerProvider serializerProvider, final Type type) {
        return this.createSchemaNode("array", true).set("items", this.createSchemaNode("number"));
    }
    
    @Override
    public void acceptJsonFormatVisitor(final JsonFormatVisitorWrapper jsonFormatVisitorWrapper, final JavaType javaType) throws JsonMappingException {
        this.visitArrayFormat(jsonFormatVisitorWrapper, javaType, JsonFormatTypes.NUMBER);
    }
    
    public void serializeContents(final Object o, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException {
        this.serializeContents((float[])o, jsonGenerator, serializerProvider);
    }
    
    @Override
    public void serialize(final Object o, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException {
        this.serialize((float[])o, jsonGenerator, serializerProvider);
    }
    
    @Override
    public boolean hasSingleElement(final Object o) {
        return this.hasSingleElement((float[])o);
    }
    
    @Override
    public boolean isEmpty(final SerializerProvider serializerProvider, final Object o) {
        return this.isEmpty(serializerProvider, (float[])o);
    }
    
    static {
        VALUE_TYPE = TypeFactory.defaultInstance().uncheckedSimpleType(Float.TYPE);
    }
}
