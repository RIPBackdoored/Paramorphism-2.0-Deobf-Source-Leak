package com.fasterxml.jackson.databind.jsontype.impl;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.jsontype.*;
import com.fasterxml.jackson.annotation.*;

public class AsExternalTypeDeserializer extends AsArrayTypeDeserializer
{
    private static final long serialVersionUID = 1L;
    
    public AsExternalTypeDeserializer(final JavaType javaType, final TypeIdResolver typeIdResolver, final String s, final boolean b, final JavaType javaType2) {
        super(javaType, typeIdResolver, s, b, javaType2);
    }
    
    public AsExternalTypeDeserializer(final AsExternalTypeDeserializer asExternalTypeDeserializer, final BeanProperty beanProperty) {
        super(asExternalTypeDeserializer, beanProperty);
    }
    
    @Override
    public TypeDeserializer forProperty(final BeanProperty beanProperty) {
        if (beanProperty == this._property) {
            return this;
        }
        return new AsExternalTypeDeserializer(this, beanProperty);
    }
    
    @Override
    public JsonTypeInfo.As getTypeInclusion() {
        return JsonTypeInfo.As.EXTERNAL_PROPERTY;
    }
    
    @Override
    protected boolean _usesExternalId() {
        return true;
    }
}
