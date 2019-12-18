package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.databind.jsontype.*;
import com.fasterxml.jackson.databind.ser.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import java.io.*;
import java.util.*;
import com.fasterxml.jackson.databind.ser.impl.*;

public class CollectionSerializer extends AsArraySerializerBase<Collection<?>>
{
    private static final long serialVersionUID = 1L;
    
    public CollectionSerializer(final JavaType javaType, final boolean b, final TypeSerializer typeSerializer, final JsonSerializer<Object> jsonSerializer) {
        super(Collection.class, javaType, b, typeSerializer, jsonSerializer);
    }
    
    @Deprecated
    public CollectionSerializer(final JavaType javaType, final boolean b, final TypeSerializer typeSerializer, final BeanProperty beanProperty, final JsonSerializer<Object> jsonSerializer) {
        this(javaType, b, typeSerializer, jsonSerializer);
    }
    
    public CollectionSerializer(final CollectionSerializer collectionSerializer, final BeanProperty beanProperty, final TypeSerializer typeSerializer, final JsonSerializer<?> jsonSerializer, final Boolean b) {
        super(collectionSerializer, beanProperty, typeSerializer, jsonSerializer, b);
    }
    
    public ContainerSerializer<?> _withValueTypeSerializer(final TypeSerializer typeSerializer) {
        return new CollectionSerializer(this, this._property, typeSerializer, this._elementSerializer, this._unwrapSingle);
    }
    
    @Override
    public CollectionSerializer withResolved(final BeanProperty beanProperty, final TypeSerializer typeSerializer, final JsonSerializer<?> jsonSerializer, final Boolean b) {
        return new CollectionSerializer(this, beanProperty, typeSerializer, jsonSerializer, b);
    }
    
    @Override
    public boolean isEmpty(final SerializerProvider serializerProvider, final Collection<?> collection) {
        return collection.isEmpty();
    }
    
    @Override
    public boolean hasSingleElement(final Collection<?> collection) {
        return collection.size() == 1;
    }
    
    @Override
    public final void serialize(final Collection<?> collection, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException {
        final int size = collection.size();
        if (size == 1 && ((this._unwrapSingle == null && serializerProvider.isEnabled(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED)) || this._unwrapSingle == Boolean.TRUE)) {
            this.serializeContents(collection, jsonGenerator, serializerProvider);
            return;
        }
        jsonGenerator.writeStartArray(size);
        this.serializeContents(collection, jsonGenerator, serializerProvider);
        jsonGenerator.writeEndArray();
    }
    
    public void serializeContents(final Collection<?> currentValue, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.setCurrentValue(currentValue);
        if (this._elementSerializer != null) {
            this.serializeContentsUsing(currentValue, jsonGenerator, serializerProvider, this._elementSerializer);
            return;
        }
        final Iterator<?> iterator = currentValue.iterator();
        if (!iterator.hasNext()) {
            return;
        }
        PropertySerializerMap propertySerializerMap = this._dynamicSerializers;
        final TypeSerializer valueTypeSerializer = this._valueTypeSerializer;
        int n = 0;
        try {
            do {
                final Object next = iterator.next();
                if (next == null) {
                    serializerProvider.defaultSerializeNull(jsonGenerator);
                }
                else {
                    final Class<?> class1 = next.getClass();
                    JsonSerializer<Object> jsonSerializer = propertySerializerMap.serializerFor(class1);
                    if (jsonSerializer == null) {
                        if (this._elementType.hasGenericTypes()) {
                            jsonSerializer = this._findAndAddDynamic(propertySerializerMap, serializerProvider.constructSpecializedType(this._elementType, class1), serializerProvider);
                        }
                        else {
                            jsonSerializer = this._findAndAddDynamic(propertySerializerMap, class1, serializerProvider);
                        }
                        propertySerializerMap = this._dynamicSerializers;
                    }
                    if (valueTypeSerializer == null) {
                        jsonSerializer.serialize(next, jsonGenerator, serializerProvider);
                    }
                    else {
                        jsonSerializer.serializeWithType(next, jsonGenerator, serializerProvider, valueTypeSerializer);
                    }
                }
                ++n;
            } while (iterator.hasNext());
        }
        catch (Exception ex) {
            this.wrapAndThrow(serializerProvider, ex, currentValue, n);
        }
    }
    
    public void serializeContentsUsing(final Collection<?> collection, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider, final JsonSerializer<Object> jsonSerializer) throws IOException {
        final Iterator<?> iterator = collection.iterator();
        if (iterator.hasNext()) {
            final TypeSerializer valueTypeSerializer = this._valueTypeSerializer;
            int n = 0;
            do {
                final Object next = iterator.next();
                try {
                    if (next == null) {
                        serializerProvider.defaultSerializeNull(jsonGenerator);
                    }
                    else if (valueTypeSerializer == null) {
                        jsonSerializer.serialize(next, jsonGenerator, serializerProvider);
                    }
                    else {
                        jsonSerializer.serializeWithType(next, jsonGenerator, serializerProvider, valueTypeSerializer);
                    }
                    ++n;
                }
                catch (Exception ex) {
                    this.wrapAndThrow(serializerProvider, ex, collection, n);
                }
            } while (iterator.hasNext());
        }
    }
    
    public void serializeContents(final Object o, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException {
        this.serializeContents((Collection<?>)o, jsonGenerator, serializerProvider);
    }
    
    @Override
    public void serialize(final Object o, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException {
        this.serialize((Collection<?>)o, jsonGenerator, serializerProvider);
    }
    
    @Override
    public AsArraySerializerBase withResolved(final BeanProperty beanProperty, final TypeSerializer typeSerializer, final JsonSerializer jsonSerializer, final Boolean b) {
        return this.withResolved(beanProperty, typeSerializer, (JsonSerializer<?>)jsonSerializer, b);
    }
    
    @Override
    public boolean hasSingleElement(final Object o) {
        return this.hasSingleElement((Collection<?>)o);
    }
    
    @Override
    public boolean isEmpty(final SerializerProvider serializerProvider, final Object o) {
        return this.isEmpty(serializerProvider, (Collection<?>)o);
    }
}
