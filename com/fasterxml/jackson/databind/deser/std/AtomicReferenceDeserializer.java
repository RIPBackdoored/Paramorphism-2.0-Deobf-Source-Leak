package com.fasterxml.jackson.databind.deser.std;

import java.util.concurrent.atomic.*;
import com.fasterxml.jackson.databind.deser.*;
import com.fasterxml.jackson.databind.jsontype.*;
import com.fasterxml.jackson.databind.*;

public class AtomicReferenceDeserializer extends ReferenceTypeDeserializer<AtomicReference<Object>>
{
    private static final long serialVersionUID = 1L;
    
    public AtomicReferenceDeserializer(final JavaType javaType, final ValueInstantiator valueInstantiator, final TypeDeserializer typeDeserializer, final JsonDeserializer<?> jsonDeserializer) {
        super(javaType, valueInstantiator, typeDeserializer, jsonDeserializer);
    }
    
    public AtomicReferenceDeserializer withResolved(final TypeDeserializer typeDeserializer, final JsonDeserializer<?> jsonDeserializer) {
        return new AtomicReferenceDeserializer(this._fullType, this._valueInstantiator, typeDeserializer, jsonDeserializer);
    }
    
    @Override
    public AtomicReference<Object> getNullValue(final DeserializationContext deserializationContext) {
        return new AtomicReference<Object>();
    }
    
    @Override
    public Object getEmptyValue(final DeserializationContext deserializationContext) {
        return new AtomicReference();
    }
    
    @Override
    public AtomicReference<Object> referenceValue(final Object o) {
        return new AtomicReference<Object>(o);
    }
    
    @Override
    public Object getReferenced(final AtomicReference<Object> atomicReference) {
        return atomicReference.get();
    }
    
    @Override
    public AtomicReference<Object> updateReference(final AtomicReference<Object> atomicReference, final Object o) {
        atomicReference.set(o);
        return atomicReference;
    }
    
    @Override
    public Boolean supportsUpdate(final DeserializationConfig deserializationConfig) {
        return Boolean.TRUE;
    }
    
    @Override
    public Object getReferenced(final Object o) {
        return this.getReferenced((AtomicReference<Object>)o);
    }
    
    @Override
    public Object updateReference(final Object o, final Object o2) {
        return this.updateReference((AtomicReference<Object>)o, o2);
    }
    
    @Override
    public Object referenceValue(final Object o) {
        return this.referenceValue(o);
    }
    
    @Override
    public Object getNullValue(final DeserializationContext deserializationContext) {
        return this.getNullValue(deserializationContext);
    }
    
    public ReferenceTypeDeserializer withResolved(final TypeDeserializer typeDeserializer, final JsonDeserializer jsonDeserializer) {
        return this.withResolved(typeDeserializer, (JsonDeserializer<?>)jsonDeserializer);
    }
}
