package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.databind.ser.std.*;
import com.fasterxml.jackson.databind.annotation.*;
import com.fasterxml.jackson.databind.jsonFormatVisitors.*;
import com.fasterxml.jackson.databind.*;
import java.io.*;
import com.fasterxml.jackson.databind.jsontype.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.type.*;
import java.util.*;

@JacksonStdImpl
public final class IndexedStringListSerializer extends StaticListSerializerBase<List<String>>
{
    private static final long serialVersionUID = 1L;
    public static final IndexedStringListSerializer instance;
    
    protected IndexedStringListSerializer() {
        super(List.class);
    }
    
    public IndexedStringListSerializer(final IndexedStringListSerializer indexedStringListSerializer, final Boolean b) {
        super(indexedStringListSerializer, b);
    }
    
    @Override
    public JsonSerializer<?> _withResolved(final BeanProperty beanProperty, final Boolean b) {
        return new IndexedStringListSerializer(this, b);
    }
    
    @Override
    protected JsonNode contentSchema() {
        return this.createSchemaNode("string", true);
    }
    
    @Override
    protected void acceptContentVisitor(final JsonArrayFormatVisitor jsonArrayFormatVisitor) throws JsonMappingException {
        jsonArrayFormatVisitor.itemsFormat(JsonFormatTypes.STRING);
    }
    
    @Override
    public void serialize(final List<String> list, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException {
        final int size = list.size();
        if (size == 1 && ((this._unwrapSingle == null && serializerProvider.isEnabled(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED)) || this._unwrapSingle == Boolean.TRUE)) {
            this.serializeContents(list, jsonGenerator, serializerProvider, 1);
            return;
        }
        jsonGenerator.writeStartArray(size);
        this.serializeContents(list, jsonGenerator, serializerProvider, size);
        jsonGenerator.writeEndArray();
    }
    
    @Override
    public void serializeWithType(final List<String> list, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider, final TypeSerializer typeSerializer) throws IOException {
        final WritableTypeId writeTypePrefix = typeSerializer.writeTypePrefix(jsonGenerator, typeSerializer.typeId(list, JsonToken.START_ARRAY));
        this.serializeContents(list, jsonGenerator, serializerProvider, list.size());
        typeSerializer.writeTypeSuffix(jsonGenerator, writeTypePrefix);
    }
    
    private final void serializeContents(final List<String> currentValue, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider, final int n) throws IOException {
        jsonGenerator.setCurrentValue(currentValue);
        int i = 0;
        try {
            while (i < n) {
                final String s = currentValue.get(i);
                if (s == null) {
                    serializerProvider.defaultSerializeNull(jsonGenerator);
                }
                else {
                    jsonGenerator.writeString(s);
                }
                ++i;
            }
        }
        catch (Exception ex) {
            this.wrapAndThrow(serializerProvider, ex, currentValue, i);
        }
    }
    
    @Override
    public void serializeWithType(final Collection collection, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider, final TypeSerializer typeSerializer) throws IOException {
        this.serializeWithType((List<String>)collection, jsonGenerator, serializerProvider, typeSerializer);
    }
    
    @Override
    public void serialize(final Object o, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException {
        this.serialize((List<String>)o, jsonGenerator, serializerProvider);
    }
    
    @Override
    public void serializeWithType(final Object o, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider, final TypeSerializer typeSerializer) throws IOException {
        this.serializeWithType((List<String>)o, jsonGenerator, serializerProvider, typeSerializer);
    }
    
    static {
        instance = new IndexedStringListSerializer();
    }
}
