package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.databind.annotation.*;
import com.fasterxml.jackson.databind.jsontype.*;
import com.fasterxml.jackson.databind.ser.impl.*;
import com.fasterxml.jackson.databind.ser.*;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.introspect.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import java.io.*;
import com.fasterxml.jackson.databind.jsonFormatVisitors.*;

@JacksonStdImpl
public class ObjectArraySerializer extends ArraySerializerBase<Object[]> implements ContextualSerializer
{
    protected final boolean _staticTyping;
    protected final JavaType _elementType;
    protected final TypeSerializer _valueTypeSerializer;
    protected JsonSerializer<Object> _elementSerializer;
    protected PropertySerializerMap _dynamicSerializers;
    
    public ObjectArraySerializer(final JavaType elementType, final boolean staticTyping, final TypeSerializer valueTypeSerializer, final JsonSerializer<Object> elementSerializer) {
        super(Object[].class);
        this._elementType = elementType;
        this._staticTyping = staticTyping;
        this._valueTypeSerializer = valueTypeSerializer;
        this._dynamicSerializers = PropertySerializerMap.emptyForProperties();
        this._elementSerializer = elementSerializer;
    }
    
    public ObjectArraySerializer(final ObjectArraySerializer objectArraySerializer, final TypeSerializer valueTypeSerializer) {
        super(objectArraySerializer);
        this._elementType = objectArraySerializer._elementType;
        this._valueTypeSerializer = valueTypeSerializer;
        this._staticTyping = objectArraySerializer._staticTyping;
        this._dynamicSerializers = objectArraySerializer._dynamicSerializers;
        this._elementSerializer = objectArraySerializer._elementSerializer;
    }
    
    public ObjectArraySerializer(final ObjectArraySerializer objectArraySerializer, final BeanProperty beanProperty, final TypeSerializer valueTypeSerializer, final JsonSerializer<?> elementSerializer, final Boolean b) {
        super(objectArraySerializer, beanProperty, b);
        this._elementType = objectArraySerializer._elementType;
        this._valueTypeSerializer = valueTypeSerializer;
        this._staticTyping = objectArraySerializer._staticTyping;
        this._dynamicSerializers = objectArraySerializer._dynamicSerializers;
        this._elementSerializer = (JsonSerializer<Object>)elementSerializer;
    }
    
    @Override
    public JsonSerializer<?> _withResolved(final BeanProperty beanProperty, final Boolean b) {
        return new ObjectArraySerializer(this, beanProperty, this._valueTypeSerializer, this._elementSerializer, b);
    }
    
    public ContainerSerializer<?> _withValueTypeSerializer(final TypeSerializer typeSerializer) {
        return new ObjectArraySerializer(this._elementType, this._staticTyping, typeSerializer, this._elementSerializer);
    }
    
    public ObjectArraySerializer withResolved(final BeanProperty beanProperty, final TypeSerializer typeSerializer, final JsonSerializer<?> jsonSerializer, final Boolean b) {
        if (this._property == beanProperty && jsonSerializer == this._elementSerializer && this._valueTypeSerializer == typeSerializer && this._unwrapSingle == b) {
            return this;
        }
        return new ObjectArraySerializer(this, beanProperty, typeSerializer, jsonSerializer, b);
    }
    
    @Override
    public JsonSerializer<?> createContextual(final SerializerProvider serializerProvider, final BeanProperty beanProperty) throws JsonMappingException {
        TypeSerializer typeSerializer = this._valueTypeSerializer;
        if (typeSerializer != null) {
            typeSerializer = typeSerializer.forProperty(beanProperty);
        }
        JsonSerializer<?> jsonSerializer = null;
        Boolean feature = null;
        if (beanProperty != null) {
            final AnnotatedMember member = beanProperty.getMember();
            final AnnotationIntrospector annotationIntrospector = serializerProvider.getAnnotationIntrospector();
            if (member != null) {
                final Object contentSerializer = annotationIntrospector.findContentSerializer(member);
                if (contentSerializer != null) {
                    jsonSerializer = serializerProvider.serializerInstance(member, contentSerializer);
                }
            }
        }
        final JsonFormat.Value formatOverrides = this.findFormatOverrides(serializerProvider, beanProperty, this.handledType());
        if (formatOverrides != null) {
            feature = formatOverrides.getFeature(JsonFormat.Feature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED);
        }
        if (jsonSerializer == null) {
            jsonSerializer = this._elementSerializer;
        }
        JsonSerializer<?> jsonSerializer2 = this.findContextualConvertingSerializer(serializerProvider, beanProperty, jsonSerializer);
        if (jsonSerializer2 == null && this._elementType != null && this._staticTyping && !this._elementType.isJavaLangObject()) {
            jsonSerializer2 = serializerProvider.findValueSerializer(this._elementType, beanProperty);
        }
        return this.withResolved(beanProperty, typeSerializer, jsonSerializer2, feature);
    }
    
    @Override
    public JavaType getContentType() {
        return this._elementType;
    }
    
    @Override
    public JsonSerializer<?> getContentSerializer() {
        return this._elementSerializer;
    }
    
    @Override
    public boolean isEmpty(final SerializerProvider serializerProvider, final Object[] array) {
        return array.length == 0;
    }
    
    @Override
    public boolean hasSingleElement(final Object[] array) {
        return array.length == 1;
    }
    
    @Override
    public final void serialize(final Object[] array, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException {
        final int length = array.length;
        if (length == 1 && ((this._unwrapSingle == null && serializerProvider.isEnabled(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED)) || this._unwrapSingle == Boolean.TRUE)) {
            this.serializeContents(array, jsonGenerator, serializerProvider);
            return;
        }
        jsonGenerator.writeStartArray(length);
        this.serializeContents(array, jsonGenerator, serializerProvider);
        jsonGenerator.writeEndArray();
    }
    
    public void serializeContents(final Object[] array, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException {
        final int length = array.length;
        if (length == 0) {
            return;
        }
        if (this._elementSerializer != null) {
            this.serializeContentsUsing(array, jsonGenerator, serializerProvider, this._elementSerializer);
            return;
        }
        if (this._valueTypeSerializer != null) {
            this.serializeTypedContents(array, jsonGenerator, serializerProvider);
            return;
        }
        int i = 0;
        Object o = null;
        try {
            final PropertySerializerMap dynamicSerializers = this._dynamicSerializers;
            while (i < length) {
                o = array[i];
                if (o == null) {
                    serializerProvider.defaultSerializeNull(jsonGenerator);
                }
                else {
                    final Class<?> class1 = o.getClass();
                    JsonSerializer<Object> jsonSerializer = dynamicSerializers.serializerFor(class1);
                    if (jsonSerializer == null) {
                        if (this._elementType.hasGenericTypes()) {
                            jsonSerializer = this._findAndAddDynamic(dynamicSerializers, serializerProvider.constructSpecializedType(this._elementType, class1), serializerProvider);
                        }
                        else {
                            jsonSerializer = this._findAndAddDynamic(dynamicSerializers, class1, serializerProvider);
                        }
                    }
                    jsonSerializer.serialize(o, jsonGenerator, serializerProvider);
                }
                ++i;
            }
        }
        catch (Exception ex) {
            this.wrapAndThrow(serializerProvider, ex, o, i);
        }
    }
    
    public void serializeContentsUsing(final Object[] array, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider, final JsonSerializer<Object> jsonSerializer) throws IOException {
        final int length = array.length;
        final TypeSerializer valueTypeSerializer = this._valueTypeSerializer;
        int i = 0;
        Object o = null;
        try {
            while (i < length) {
                o = array[i];
                if (o == null) {
                    serializerProvider.defaultSerializeNull(jsonGenerator);
                }
                else if (valueTypeSerializer == null) {
                    jsonSerializer.serialize(o, jsonGenerator, serializerProvider);
                }
                else {
                    jsonSerializer.serializeWithType(o, jsonGenerator, serializerProvider, valueTypeSerializer);
                }
                ++i;
            }
        }
        catch (Exception ex) {
            this.wrapAndThrow(serializerProvider, ex, o, i);
        }
    }
    
    public void serializeTypedContents(final Object[] array, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException {
        final int length = array.length;
        final TypeSerializer valueTypeSerializer = this._valueTypeSerializer;
        int i = 0;
        Object o = null;
        try {
            final PropertySerializerMap dynamicSerializers = this._dynamicSerializers;
            while (i < length) {
                o = array[i];
                if (o == null) {
                    serializerProvider.defaultSerializeNull(jsonGenerator);
                }
                else {
                    final Class<?> class1 = o.getClass();
                    JsonSerializer<Object> jsonSerializer = dynamicSerializers.serializerFor(class1);
                    if (jsonSerializer == null) {
                        jsonSerializer = this._findAndAddDynamic(dynamicSerializers, class1, serializerProvider);
                    }
                    jsonSerializer.serializeWithType(o, jsonGenerator, serializerProvider, valueTypeSerializer);
                }
                ++i;
            }
        }
        catch (Exception ex) {
            this.wrapAndThrow(serializerProvider, ex, o, i);
        }
    }
    
    @Override
    public void acceptJsonFormatVisitor(final JsonFormatVisitorWrapper jsonFormatVisitorWrapper, final JavaType javaType) throws JsonMappingException {
        final JsonArrayFormatVisitor expectArrayFormat = jsonFormatVisitorWrapper.expectArrayFormat(javaType);
        if (expectArrayFormat != null) {
            final JavaType elementType = this._elementType;
            JsonSerializer<Object> jsonSerializer = this._elementSerializer;
            if (jsonSerializer == null) {
                jsonSerializer = jsonFormatVisitorWrapper.getProvider().findValueSerializer(elementType, this._property);
            }
            expectArrayFormat.itemsFormat(jsonSerializer, elementType);
        }
    }
    
    protected final JsonSerializer<Object> _findAndAddDynamic(final PropertySerializerMap propertySerializerMap, final Class<?> clazz, final SerializerProvider serializerProvider) throws JsonMappingException {
        final PropertySerializerMap.SerializerAndMapResult andAddSecondarySerializer = propertySerializerMap.findAndAddSecondarySerializer(clazz, serializerProvider, this._property);
        if (propertySerializerMap != andAddSecondarySerializer.map) {
            this._dynamicSerializers = andAddSecondarySerializer.map;
        }
        return andAddSecondarySerializer.serializer;
    }
    
    protected final JsonSerializer<Object> _findAndAddDynamic(final PropertySerializerMap propertySerializerMap, final JavaType javaType, final SerializerProvider serializerProvider) throws JsonMappingException {
        final PropertySerializerMap.SerializerAndMapResult andAddSecondarySerializer = propertySerializerMap.findAndAddSecondarySerializer(javaType, serializerProvider, this._property);
        if (propertySerializerMap != andAddSecondarySerializer.map) {
            this._dynamicSerializers = andAddSecondarySerializer.map;
        }
        return andAddSecondarySerializer.serializer;
    }
    
    public void serializeContents(final Object o, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException {
        this.serializeContents((Object[])o, jsonGenerator, serializerProvider);
    }
    
    @Override
    public void serialize(final Object o, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException {
        this.serialize((Object[])o, jsonGenerator, serializerProvider);
    }
    
    @Override
    public boolean hasSingleElement(final Object o) {
        return this.hasSingleElement((Object[])o);
    }
    
    @Override
    public boolean isEmpty(final SerializerProvider serializerProvider, final Object o) {
        return this.isEmpty(serializerProvider, (Object[])o);
    }
}
