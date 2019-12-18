package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.jsontype.*;
import com.fasterxml.jackson.databind.ser.*;

protected abstract static class TypedPrimitiveArraySerializer<T> extends ArraySerializerBase<T>
{
    protected TypedPrimitiveArraySerializer(final Class<T> clazz) {
        super(clazz);
    }
    
    protected TypedPrimitiveArraySerializer(final TypedPrimitiveArraySerializer<T> typedPrimitiveArraySerializer, final BeanProperty beanProperty, final Boolean b) {
        super(typedPrimitiveArraySerializer, beanProperty, b);
    }
    
    public final ContainerSerializer<?> _withValueTypeSerializer(final TypeSerializer typeSerializer) {
        return this;
    }
}
