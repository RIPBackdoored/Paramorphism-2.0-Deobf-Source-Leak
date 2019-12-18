package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.databind.ser.std.*;
import com.fasterxml.jackson.databind.annotation.*;
import com.fasterxml.jackson.databind.jsontype.*;
import com.fasterxml.jackson.databind.ser.*;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.introspect.*;
import com.fasterxml.jackson.core.*;
import java.io.*;
import java.lang.reflect.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.jsonFormatVisitors.*;
import com.fasterxml.jackson.databind.type.*;

@JacksonStdImpl
public class StringArraySerializer extends ArraySerializerBase<String[]> implements ContextualSerializer
{
    private static final JavaType VALUE_TYPE;
    public static final StringArraySerializer instance;
    protected final JsonSerializer<Object> _elementSerializer;
    
    protected StringArraySerializer() {
        super(String[].class);
        this._elementSerializer = null;
    }
    
    public StringArraySerializer(final StringArraySerializer stringArraySerializer, final BeanProperty beanProperty, final JsonSerializer<?> elementSerializer, final Boolean b) {
        super(stringArraySerializer, beanProperty, b);
        this._elementSerializer = (JsonSerializer<Object>)elementSerializer;
    }
    
    @Override
    public JsonSerializer<?> _withResolved(final BeanProperty beanProperty, final Boolean b) {
        return new StringArraySerializer(this, beanProperty, this._elementSerializer, b);
    }
    
    public ContainerSerializer<?> _withValueTypeSerializer(final TypeSerializer typeSerializer) {
        return this;
    }
    
    @Override
    public JsonSerializer<?> createContextual(final SerializerProvider serializerProvider, final BeanProperty beanProperty) throws JsonMappingException {
        JsonSerializer<?> jsonSerializer = null;
        if (beanProperty != null) {
            final AnnotationIntrospector annotationIntrospector = serializerProvider.getAnnotationIntrospector();
            final AnnotatedMember member = beanProperty.getMember();
            if (member != null) {
                final Object contentSerializer = annotationIntrospector.findContentSerializer(member);
                if (contentSerializer != null) {
                    jsonSerializer = serializerProvider.serializerInstance(member, contentSerializer);
                }
            }
        }
        final Boolean formatFeature = this.findFormatFeature(serializerProvider, beanProperty, String[].class, JsonFormat.Feature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED);
        if (jsonSerializer == null) {
            jsonSerializer = this._elementSerializer;
        }
        JsonSerializer<?> jsonSerializer2 = this.findContextualConvertingSerializer(serializerProvider, beanProperty, jsonSerializer);
        if (jsonSerializer2 == null) {
            jsonSerializer2 = serializerProvider.findValueSerializer(String.class, beanProperty);
        }
        if (this.isDefaultSerializer(jsonSerializer2)) {
            jsonSerializer2 = null;
        }
        if (jsonSerializer2 == this._elementSerializer && formatFeature == this._unwrapSingle) {
            return this;
        }
        return new StringArraySerializer(this, beanProperty, jsonSerializer2, formatFeature);
    }
    
    @Override
    public JavaType getContentType() {
        return StringArraySerializer.VALUE_TYPE;
    }
    
    @Override
    public JsonSerializer<?> getContentSerializer() {
        return this._elementSerializer;
    }
    
    @Override
    public boolean isEmpty(final SerializerProvider serializerProvider, final String[] array) {
        return array.length == 0;
    }
    
    @Override
    public boolean hasSingleElement(final String[] array) {
        return array.length == 1;
    }
    
    @Override
    public final void serialize(final String[] array, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException {
        final int length = array.length;
        if (length == 1 && ((this._unwrapSingle == null && serializerProvider.isEnabled(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED)) || this._unwrapSingle == Boolean.TRUE)) {
            this.serializeContents(array, jsonGenerator, serializerProvider);
            return;
        }
        jsonGenerator.writeStartArray(length);
        this.serializeContents(array, jsonGenerator, serializerProvider);
        jsonGenerator.writeEndArray();
    }
    
    public void serializeContents(final String[] array, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException {
        final int length = array.length;
        if (length == 0) {
            return;
        }
        if (this._elementSerializer != null) {
            this.serializeContentsSlow(array, jsonGenerator, serializerProvider, this._elementSerializer);
            return;
        }
        for (int i = 0; i < length; ++i) {
            if (array[i] == null) {
                jsonGenerator.writeNull();
            }
            else {
                jsonGenerator.writeString(array[i]);
            }
        }
    }
    
    private void serializeContentsSlow(final String[] array, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider, final JsonSerializer<Object> jsonSerializer) throws IOException {
        for (int i = 0; i < array.length; ++i) {
            if (array[i] == null) {
                serializerProvider.defaultSerializeNull(jsonGenerator);
            }
            else {
                jsonSerializer.serialize(array[i], jsonGenerator, serializerProvider);
            }
        }
    }
    
    @Override
    public JsonNode getSchema(final SerializerProvider serializerProvider, final Type type) {
        return this.createSchemaNode("array", true).set("items", this.createSchemaNode("string"));
    }
    
    @Override
    public void acceptJsonFormatVisitor(final JsonFormatVisitorWrapper jsonFormatVisitorWrapper, final JavaType javaType) throws JsonMappingException {
        this.visitArrayFormat(jsonFormatVisitorWrapper, javaType, JsonFormatTypes.STRING);
    }
    
    public void serializeContents(final Object o, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException {
        this.serializeContents((String[])o, jsonGenerator, serializerProvider);
    }
    
    @Override
    public void serialize(final Object o, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException {
        this.serialize((String[])o, jsonGenerator, serializerProvider);
    }
    
    @Override
    public boolean hasSingleElement(final Object o) {
        return this.hasSingleElement((String[])o);
    }
    
    @Override
    public boolean isEmpty(final SerializerProvider serializerProvider, final Object o) {
        return this.isEmpty(serializerProvider, (String[])o);
    }
    
    static {
        VALUE_TYPE = TypeFactory.defaultInstance().uncheckedSimpleType(String.class);
        instance = new StringArraySerializer();
    }
}
