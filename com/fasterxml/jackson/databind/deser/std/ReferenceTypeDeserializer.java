package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.databind.deser.*;
import com.fasterxml.jackson.databind.jsontype.*;
import com.fasterxml.jackson.databind.util.*;
import com.fasterxml.jackson.databind.*;
import java.io.*;
import com.fasterxml.jackson.core.*;

public abstract class ReferenceTypeDeserializer<T> extends StdDeserializer<T> implements ContextualDeserializer
{
    private static final long serialVersionUID = 2L;
    protected final JavaType _fullType;
    protected final ValueInstantiator _valueInstantiator;
    protected final TypeDeserializer _valueTypeDeserializer;
    protected final JsonDeserializer<Object> _valueDeserializer;
    
    public ReferenceTypeDeserializer(final JavaType fullType, final ValueInstantiator valueInstantiator, final TypeDeserializer valueTypeDeserializer, final JsonDeserializer<?> valueDeserializer) {
        super(fullType);
        this._valueInstantiator = valueInstantiator;
        this._fullType = fullType;
        this._valueDeserializer = (JsonDeserializer<Object>)valueDeserializer;
        this._valueTypeDeserializer = valueTypeDeserializer;
    }
    
    @Deprecated
    public ReferenceTypeDeserializer(final JavaType javaType, final TypeDeserializer typeDeserializer, final JsonDeserializer<?> jsonDeserializer) {
        this(javaType, null, typeDeserializer, jsonDeserializer);
    }
    
    @Override
    public JsonDeserializer<?> createContextual(final DeserializationContext deserializationContext, final BeanProperty beanProperty) throws JsonMappingException {
        final JsonDeserializer<Object> valueDeserializer = this._valueDeserializer;
        JsonDeserializer<?> jsonDeserializer;
        if (valueDeserializer == null) {
            jsonDeserializer = deserializationContext.findContextualValueDeserializer(this._fullType.getReferencedType(), beanProperty);
        }
        else {
            jsonDeserializer = deserializationContext.handleSecondaryContextualization(valueDeserializer, beanProperty, this._fullType.getReferencedType());
        }
        TypeDeserializer typeDeserializer = this._valueTypeDeserializer;
        if (typeDeserializer != null) {
            typeDeserializer = typeDeserializer.forProperty(beanProperty);
        }
        if (jsonDeserializer == this._valueDeserializer && typeDeserializer == this._valueTypeDeserializer) {
            return this;
        }
        return this.withResolved(typeDeserializer, jsonDeserializer);
    }
    
    @Override
    public AccessPattern getNullAccessPattern() {
        return AccessPattern.DYNAMIC;
    }
    
    @Override
    public AccessPattern getEmptyAccessPattern() {
        return AccessPattern.DYNAMIC;
    }
    
    protected abstract ReferenceTypeDeserializer<T> withResolved(final TypeDeserializer p0, final JsonDeserializer<?> p1);
    
    @Override
    public abstract T getNullValue(final DeserializationContext p0);
    
    @Override
    public Object getEmptyValue(final DeserializationContext deserializationContext) {
        return this.getNullValue(deserializationContext);
    }
    
    public abstract T referenceValue(final Object p0);
    
    public abstract T updateReference(final T p0, final Object p1);
    
    public abstract Object getReferenced(final T p0);
    
    @Override
    public JavaType getValueType() {
        return this._fullType;
    }
    
    @Override
    public Boolean supportsUpdate(final DeserializationConfig deserializationConfig) {
        return (this._valueDeserializer == null) ? null : this._valueDeserializer.supportsUpdate(deserializationConfig);
    }
    
    @Override
    public T deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        if (this._valueInstantiator != null) {
            return (T)this.deserialize(jsonParser, deserializationContext, this._valueInstantiator.createUsingDefault(deserializationContext));
        }
        return this.referenceValue((this._valueTypeDeserializer == null) ? this._valueDeserializer.deserialize(jsonParser, deserializationContext) : this._valueDeserializer.deserializeWithType(jsonParser, deserializationContext, this._valueTypeDeserializer));
    }
    
    @Override
    public T deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext, final T t) throws IOException {
        Object deserialize;
        if (this._valueDeserializer.supportsUpdate(deserializationContext.getConfig()).equals(Boolean.FALSE) || this._valueTypeDeserializer != null) {
            deserialize = ((this._valueTypeDeserializer == null) ? this._valueDeserializer.deserialize(jsonParser, deserializationContext) : this._valueDeserializer.deserializeWithType(jsonParser, deserializationContext, this._valueTypeDeserializer));
        }
        else {
            final Object referenced = this.getReferenced(t);
            if (referenced == null) {
                return this.referenceValue((this._valueTypeDeserializer == null) ? this._valueDeserializer.deserialize(jsonParser, deserializationContext) : this._valueDeserializer.deserializeWithType(jsonParser, deserializationContext, this._valueTypeDeserializer));
            }
            deserialize = this._valueDeserializer.deserialize(jsonParser, deserializationContext, referenced);
        }
        return this.updateReference(t, deserialize);
    }
    
    @Override
    public Object deserializeWithType(final JsonParser jsonParser, final DeserializationContext deserializationContext, final TypeDeserializer typeDeserializer) throws IOException {
        if (jsonParser.getCurrentToken() == JsonToken.VALUE_NULL) {
            return this.getNullValue(deserializationContext);
        }
        if (this._valueTypeDeserializer == null) {
            return this.deserialize(jsonParser, deserializationContext);
        }
        return this.referenceValue(this._valueTypeDeserializer.deserializeTypedFromAny(jsonParser, deserializationContext));
    }
}
