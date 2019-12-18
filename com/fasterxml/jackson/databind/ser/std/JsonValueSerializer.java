package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.databind.ser.*;
import com.fasterxml.jackson.databind.annotation.*;
import com.fasterxml.jackson.databind.introspect.*;
import java.io.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.type.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.jsonschema.*;
import java.lang.reflect.*;
import com.fasterxml.jackson.databind.util.*;
import java.util.*;
import com.fasterxml.jackson.databind.jsonFormatVisitors.*;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.jsontype.*;

@JacksonStdImpl
public class JsonValueSerializer extends StdSerializer<Object> implements ContextualSerializer, JsonFormatVisitable, SchemaAware
{
    protected final AnnotatedMember _accessor;
    protected final JsonSerializer<Object> _valueSerializer;
    protected final BeanProperty _property;
    protected final boolean _forceTypeInformation;
    
    public JsonValueSerializer(final AnnotatedMember accessor, final JsonSerializer<?> valueSerializer) {
        super(accessor.getType());
        this._accessor = accessor;
        this._valueSerializer = (JsonSerializer<Object>)valueSerializer;
        this._property = null;
        this._forceTypeInformation = true;
    }
    
    public JsonValueSerializer(final JsonValueSerializer jsonValueSerializer, final BeanProperty property, final JsonSerializer<?> valueSerializer, final boolean forceTypeInformation) {
        super(_notNullClass(jsonValueSerializer.handledType()));
        this._accessor = jsonValueSerializer._accessor;
        this._valueSerializer = (JsonSerializer<Object>)valueSerializer;
        this._property = property;
        this._forceTypeInformation = forceTypeInformation;
    }
    
    private static final Class<Object> _notNullClass(final Class<?> clazz) {
        return (Class<Object>)((clazz == null) ? Object.class : clazz);
    }
    
    public JsonValueSerializer withResolved(final BeanProperty beanProperty, final JsonSerializer<?> jsonSerializer, final boolean b) {
        if (this._property == beanProperty && this._valueSerializer == jsonSerializer && b == this._forceTypeInformation) {
            return this;
        }
        return new JsonValueSerializer(this, beanProperty, jsonSerializer, b);
    }
    
    @Override
    public JsonSerializer<?> createContextual(final SerializerProvider serializerProvider, final BeanProperty beanProperty) throws JsonMappingException {
        final JsonSerializer<Object> valueSerializer = this._valueSerializer;
        if (valueSerializer != null) {
            return this.withResolved(beanProperty, serializerProvider.handlePrimaryContextualization(valueSerializer, beanProperty), this._forceTypeInformation);
        }
        final JavaType type = this._accessor.getType();
        if (serializerProvider.isEnabled(MapperFeature.USE_STATIC_TYPING) || type.isFinal()) {
            final JsonSerializer<Object> primaryPropertySerializer = serializerProvider.findPrimaryPropertySerializer(type, beanProperty);
            return this.withResolved(beanProperty, primaryPropertySerializer, this.isNaturalTypeWithStdHandling(type.getRawClass(), primaryPropertySerializer));
        }
        return this;
    }
    
    @Override
    public void serialize(final Object o, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException {
        try {
            final Object value = this._accessor.getValue(o);
            if (value == null) {
                serializerProvider.defaultSerializeNull(jsonGenerator);
                return;
            }
            JsonSerializer<Object> jsonSerializer = this._valueSerializer;
            if (jsonSerializer == null) {
                jsonSerializer = serializerProvider.findTypedValueSerializer(value.getClass(), true, this._property);
            }
            jsonSerializer.serialize(value, jsonGenerator, serializerProvider);
        }
        catch (Exception ex) {
            this.wrapAndThrow(serializerProvider, ex, o, this._accessor.getName() + "()");
        }
    }
    
    @Override
    public void serializeWithType(final Object o, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider, final TypeSerializer typeSerializer) throws IOException {
        try {
            final Object value = this._accessor.getValue(o);
            if (value == null) {
                serializerProvider.defaultSerializeNull(jsonGenerator);
                return;
            }
            JsonSerializer<Object> jsonSerializer = this._valueSerializer;
            if (jsonSerializer == null) {
                jsonSerializer = serializerProvider.findValueSerializer(value.getClass(), this._property);
            }
            else if (this._forceTypeInformation) {
                final WritableTypeId writeTypePrefix = typeSerializer.writeTypePrefix(jsonGenerator, typeSerializer.typeId(o, JsonToken.VALUE_STRING));
                jsonSerializer.serialize(value, jsonGenerator, serializerProvider);
                typeSerializer.writeTypeSuffix(jsonGenerator, writeTypePrefix);
                return;
            }
            jsonSerializer.serializeWithType(value, jsonGenerator, serializerProvider, new TypeSerializerRerouter(typeSerializer, o));
        }
        catch (Exception ex) {
            this.wrapAndThrow(serializerProvider, ex, o, this._accessor.getName() + "()");
        }
    }
    
    @Override
    public JsonNode getSchema(final SerializerProvider serializerProvider, final Type type) throws JsonMappingException {
        if (this._valueSerializer instanceof SchemaAware) {
            return ((SchemaAware)this._valueSerializer).getSchema(serializerProvider, null);
        }
        return JsonSchema.getDefaultSchemaNode();
    }
    
    @Override
    public void acceptJsonFormatVisitor(final JsonFormatVisitorWrapper jsonFormatVisitorWrapper, final JavaType javaType) throws JsonMappingException {
        final JavaType type = this._accessor.getType();
        final Class<?> declaringClass = this._accessor.getDeclaringClass();
        if (declaringClass != null && declaringClass.isEnum() && this._acceptJsonFormatVisitorForEnum(jsonFormatVisitorWrapper, javaType, declaringClass)) {
            return;
        }
        JsonSerializer<Object> jsonSerializer = this._valueSerializer;
        if (jsonSerializer == null) {
            jsonSerializer = jsonFormatVisitorWrapper.getProvider().findTypedValueSerializer(type, false, this._property);
            if (jsonSerializer == null) {
                jsonFormatVisitorWrapper.expectAnyFormat(javaType);
                return;
            }
        }
        jsonSerializer.acceptJsonFormatVisitor(jsonFormatVisitorWrapper, type);
    }
    
    protected boolean _acceptJsonFormatVisitorForEnum(final JsonFormatVisitorWrapper jsonFormatVisitorWrapper, final JavaType javaType, final Class<?> clazz) throws JsonMappingException {
        final JsonStringFormatVisitor expectStringFormat = jsonFormatVisitorWrapper.expectStringFormat(javaType);
        if (expectStringFormat != null) {
            final LinkedHashSet<String> set = new LinkedHashSet<String>();
            for (final Object o : clazz.getEnumConstants()) {
                try {
                    set.add(String.valueOf(this._accessor.getValue(o)));
                }
                catch (Exception ex) {
                    Throwable cause;
                    for (cause = ex; cause instanceof InvocationTargetException && cause.getCause() != null; cause = cause.getCause()) {}
                    ClassUtil.throwIfError(cause);
                    throw JsonMappingException.wrapWithPath(cause, o, this._accessor.getName() + "()");
                }
            }
            expectStringFormat.enumTypes(set);
        }
        return true;
    }
    
    protected boolean isNaturalTypeWithStdHandling(final Class<?> clazz, final JsonSerializer<?> jsonSerializer) {
        if (clazz.isPrimitive()) {
            if (clazz != Integer.TYPE && clazz != Boolean.TYPE && clazz != Double.TYPE) {
                return false;
            }
        }
        else if (clazz != String.class && clazz != Integer.class && clazz != Boolean.class && clazz != Double.class) {
            return false;
        }
        return this.isDefaultSerializer(jsonSerializer);
    }
    
    @Override
    public String toString() {
        return "(@JsonValue serializer for method " + this._accessor.getDeclaringClass() + "#" + this._accessor.getName() + ")";
    }
    
    static class TypeSerializerRerouter extends TypeSerializer
    {
        protected final TypeSerializer _typeSerializer;
        protected final Object _forObject;
        
        public TypeSerializerRerouter(final TypeSerializer typeSerializer, final Object forObject) {
            super();
            this._typeSerializer = typeSerializer;
            this._forObject = forObject;
        }
        
        @Override
        public TypeSerializer forProperty(final BeanProperty beanProperty) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public JsonTypeInfo.As getTypeInclusion() {
            return this._typeSerializer.getTypeInclusion();
        }
        
        @Override
        public String getPropertyName() {
            return this._typeSerializer.getPropertyName();
        }
        
        @Override
        public TypeIdResolver getTypeIdResolver() {
            return this._typeSerializer.getTypeIdResolver();
        }
        
        @Override
        public WritableTypeId writeTypePrefix(final JsonGenerator jsonGenerator, final WritableTypeId writableTypeId) throws IOException {
            writableTypeId.forValue = this._forObject;
            return this._typeSerializer.writeTypePrefix(jsonGenerator, writableTypeId);
        }
        
        @Override
        public WritableTypeId writeTypeSuffix(final JsonGenerator jsonGenerator, final WritableTypeId writableTypeId) throws IOException {
            return this._typeSerializer.writeTypeSuffix(jsonGenerator, writableTypeId);
        }
        
        @Deprecated
        @Override
        public void writeTypePrefixForScalar(final Object o, final JsonGenerator jsonGenerator) throws IOException {
            this._typeSerializer.writeTypePrefixForScalar(this._forObject, jsonGenerator);
        }
        
        @Deprecated
        @Override
        public void writeTypePrefixForObject(final Object o, final JsonGenerator jsonGenerator) throws IOException {
            this._typeSerializer.writeTypePrefixForObject(this._forObject, jsonGenerator);
        }
        
        @Deprecated
        @Override
        public void writeTypePrefixForArray(final Object o, final JsonGenerator jsonGenerator) throws IOException {
            this._typeSerializer.writeTypePrefixForArray(this._forObject, jsonGenerator);
        }
        
        @Deprecated
        @Override
        public void writeTypeSuffixForScalar(final Object o, final JsonGenerator jsonGenerator) throws IOException {
            this._typeSerializer.writeTypeSuffixForScalar(this._forObject, jsonGenerator);
        }
        
        @Deprecated
        @Override
        public void writeTypeSuffixForObject(final Object o, final JsonGenerator jsonGenerator) throws IOException {
            this._typeSerializer.writeTypeSuffixForObject(this._forObject, jsonGenerator);
        }
        
        @Deprecated
        @Override
        public void writeTypeSuffixForArray(final Object o, final JsonGenerator jsonGenerator) throws IOException {
            this._typeSerializer.writeTypeSuffixForArray(this._forObject, jsonGenerator);
        }
        
        @Deprecated
        @Override
        public void writeTypePrefixForScalar(final Object o, final JsonGenerator jsonGenerator, final Class<?> clazz) throws IOException {
            this._typeSerializer.writeTypePrefixForScalar(this._forObject, jsonGenerator, clazz);
        }
        
        @Deprecated
        @Override
        public void writeTypePrefixForObject(final Object o, final JsonGenerator jsonGenerator, final Class<?> clazz) throws IOException {
            this._typeSerializer.writeTypePrefixForObject(this._forObject, jsonGenerator, clazz);
        }
        
        @Deprecated
        @Override
        public void writeTypePrefixForArray(final Object o, final JsonGenerator jsonGenerator, final Class<?> clazz) throws IOException {
            this._typeSerializer.writeTypePrefixForArray(this._forObject, jsonGenerator, clazz);
        }
        
        @Deprecated
        @Override
        public void writeCustomTypePrefixForScalar(final Object o, final JsonGenerator jsonGenerator, final String s) throws IOException {
            this._typeSerializer.writeCustomTypePrefixForScalar(this._forObject, jsonGenerator, s);
        }
        
        @Deprecated
        @Override
        public void writeCustomTypePrefixForObject(final Object o, final JsonGenerator jsonGenerator, final String s) throws IOException {
            this._typeSerializer.writeCustomTypePrefixForObject(this._forObject, jsonGenerator, s);
        }
        
        @Deprecated
        @Override
        public void writeCustomTypePrefixForArray(final Object o, final JsonGenerator jsonGenerator, final String s) throws IOException {
            this._typeSerializer.writeCustomTypePrefixForArray(this._forObject, jsonGenerator, s);
        }
        
        @Deprecated
        @Override
        public void writeCustomTypeSuffixForScalar(final Object o, final JsonGenerator jsonGenerator, final String s) throws IOException {
            this._typeSerializer.writeCustomTypeSuffixForScalar(this._forObject, jsonGenerator, s);
        }
        
        @Deprecated
        @Override
        public void writeCustomTypeSuffixForObject(final Object o, final JsonGenerator jsonGenerator, final String s) throws IOException {
            this._typeSerializer.writeCustomTypeSuffixForObject(this._forObject, jsonGenerator, s);
        }
        
        @Deprecated
        @Override
        public void writeCustomTypeSuffixForArray(final Object o, final JsonGenerator jsonGenerator, final String s) throws IOException {
            this._typeSerializer.writeCustomTypeSuffixForArray(this._forObject, jsonGenerator, s);
        }
    }
}
