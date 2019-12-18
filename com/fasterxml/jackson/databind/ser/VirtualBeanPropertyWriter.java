package com.fasterxml.jackson.databind.ser;

import java.io.*;
import com.fasterxml.jackson.databind.util.*;
import com.fasterxml.jackson.databind.jsontype.*;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.cfg.*;
import com.fasterxml.jackson.databind.introspect.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.ser.impl.*;

public abstract class VirtualBeanPropertyWriter extends BeanPropertyWriter implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    protected VirtualBeanPropertyWriter(final BeanPropertyDefinition beanPropertyDefinition, final Annotations annotations, final JavaType javaType) {
        this(beanPropertyDefinition, annotations, javaType, null, null, null, beanPropertyDefinition.findInclusion());
    }
    
    protected VirtualBeanPropertyWriter() {
        super();
    }
    
    protected VirtualBeanPropertyWriter(final BeanPropertyDefinition beanPropertyDefinition, final Annotations annotations, final JavaType javaType, final JsonSerializer<?> jsonSerializer, final TypeSerializer typeSerializer, final JavaType javaType2, final JsonInclude.Value value, final Class<?>[] array) {
        super(beanPropertyDefinition, beanPropertyDefinition.getPrimaryMember(), annotations, javaType, jsonSerializer, typeSerializer, javaType2, _suppressNulls(value), _suppressableValue(value), array);
    }
    
    @Deprecated
    protected VirtualBeanPropertyWriter(final BeanPropertyDefinition beanPropertyDefinition, final Annotations annotations, final JavaType javaType, final JsonSerializer<?> jsonSerializer, final TypeSerializer typeSerializer, final JavaType javaType2, final JsonInclude.Value value) {
        this(beanPropertyDefinition, annotations, javaType, jsonSerializer, typeSerializer, javaType2, value, null);
    }
    
    protected VirtualBeanPropertyWriter(final VirtualBeanPropertyWriter virtualBeanPropertyWriter) {
        super(virtualBeanPropertyWriter);
    }
    
    protected VirtualBeanPropertyWriter(final VirtualBeanPropertyWriter virtualBeanPropertyWriter, final PropertyName propertyName) {
        super(virtualBeanPropertyWriter, propertyName);
    }
    
    protected static boolean _suppressNulls(final JsonInclude.Value value) {
        if (value == null) {
            return false;
        }
        final JsonInclude.Include valueInclusion = value.getValueInclusion();
        return valueInclusion != JsonInclude.Include.ALWAYS && valueInclusion != JsonInclude.Include.USE_DEFAULTS;
    }
    
    protected static Object _suppressableValue(final JsonInclude.Value value) {
        if (value == null) {
            return false;
        }
        final JsonInclude.Include valueInclusion = value.getValueInclusion();
        if (valueInclusion == JsonInclude.Include.ALWAYS || valueInclusion == JsonInclude.Include.NON_NULL || valueInclusion == JsonInclude.Include.USE_DEFAULTS) {
            return null;
        }
        return VirtualBeanPropertyWriter.MARKER_FOR_EMPTY;
    }
    
    @Override
    public boolean isVirtual() {
        return true;
    }
    
    protected abstract Object value(final Object p0, final JsonGenerator p1, final SerializerProvider p2) throws Exception;
    
    public abstract VirtualBeanPropertyWriter withConfig(final MapperConfig<?> p0, final AnnotatedClass p1, final BeanPropertyDefinition p2, final JavaType p3);
    
    @Override
    public void serializeAsField(final Object o, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws Exception {
        final Object value = this.value(o, jsonGenerator, serializerProvider);
        if (value == null) {
            if (this._nullSerializer != null) {
                jsonGenerator.writeFieldName(this._name);
                this._nullSerializer.serialize(null, jsonGenerator, serializerProvider);
            }
            return;
        }
        JsonSerializer<Object> jsonSerializer = this._serializer;
        if (jsonSerializer == null) {
            final Class<?> class1 = value.getClass();
            final PropertySerializerMap dynamicSerializers = this._dynamicSerializers;
            jsonSerializer = dynamicSerializers.serializerFor(class1);
            if (jsonSerializer == null) {
                jsonSerializer = this._findAndAddDynamic(dynamicSerializers, class1, serializerProvider);
            }
        }
        if (this._suppressableValue != null) {
            if (VirtualBeanPropertyWriter.MARKER_FOR_EMPTY == this._suppressableValue) {
                if (jsonSerializer.isEmpty(serializerProvider, value)) {
                    return;
                }
            }
            else if (this._suppressableValue.equals(value)) {
                return;
            }
        }
        if (value == o && this._handleSelfReference(o, jsonGenerator, serializerProvider, jsonSerializer)) {
            return;
        }
        jsonGenerator.writeFieldName(this._name);
        if (this._typeSerializer == null) {
            jsonSerializer.serialize(value, jsonGenerator, serializerProvider);
        }
        else {
            jsonSerializer.serializeWithType(value, jsonGenerator, serializerProvider, this._typeSerializer);
        }
    }
    
    @Override
    public void serializeAsElement(final Object o, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws Exception {
        final Object value = this.value(o, jsonGenerator, serializerProvider);
        if (value == null) {
            if (this._nullSerializer != null) {
                this._nullSerializer.serialize(null, jsonGenerator, serializerProvider);
            }
            else {
                jsonGenerator.writeNull();
            }
            return;
        }
        JsonSerializer<Object> jsonSerializer = this._serializer;
        if (jsonSerializer == null) {
            final Class<?> class1 = value.getClass();
            final PropertySerializerMap dynamicSerializers = this._dynamicSerializers;
            jsonSerializer = dynamicSerializers.serializerFor(class1);
            if (jsonSerializer == null) {
                jsonSerializer = this._findAndAddDynamic(dynamicSerializers, class1, serializerProvider);
            }
        }
        if (this._suppressableValue != null) {
            if (VirtualBeanPropertyWriter.MARKER_FOR_EMPTY == this._suppressableValue) {
                if (jsonSerializer.isEmpty(serializerProvider, value)) {
                    this.serializeAsPlaceholder(o, jsonGenerator, serializerProvider);
                    return;
                }
            }
            else if (this._suppressableValue.equals(value)) {
                this.serializeAsPlaceholder(o, jsonGenerator, serializerProvider);
                return;
            }
        }
        if (value == o && this._handleSelfReference(o, jsonGenerator, serializerProvider, jsonSerializer)) {
            return;
        }
        if (this._typeSerializer == null) {
            jsonSerializer.serialize(value, jsonGenerator, serializerProvider);
        }
        else {
            jsonSerializer.serializeWithType(value, jsonGenerator, serializerProvider, this._typeSerializer);
        }
    }
}
