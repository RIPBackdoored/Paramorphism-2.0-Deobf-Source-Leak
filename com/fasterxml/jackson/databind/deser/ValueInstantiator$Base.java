package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.databind.*;

public static class Base extends ValueInstantiator
{
    protected final Class<?> _valueType;
    
    public Base(final Class<?> valueType) {
        super();
        this._valueType = valueType;
    }
    
    public Base(final JavaType javaType) {
        super();
        this._valueType = javaType.getRawClass();
    }
    
    @Override
    public String getValueTypeDesc() {
        return this._valueType.getName();
    }
    
    @Override
    public Class<?> getValueClass() {
        return this._valueType;
    }
}
