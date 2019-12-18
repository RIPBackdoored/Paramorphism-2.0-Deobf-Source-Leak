package com.fasterxml.jackson.databind.jsontype.impl;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.jsontype.*;

public class AsArrayTypeSerializer extends TypeSerializerBase
{
    public AsArrayTypeSerializer(final TypeIdResolver typeIdResolver, final BeanProperty beanProperty) {
        super(typeIdResolver, beanProperty);
    }
    
    @Override
    public AsArrayTypeSerializer forProperty(final BeanProperty beanProperty) {
        return (this._property == beanProperty) ? this : new AsArrayTypeSerializer(this._idResolver, beanProperty);
    }
    
    @Override
    public JsonTypeInfo.As getTypeInclusion() {
        return JsonTypeInfo.As.WRAPPER_ARRAY;
    }
    
    @Override
    public TypeSerializer forProperty(final BeanProperty beanProperty) {
        return this.forProperty(beanProperty);
    }
}
