package com.fasterxml.jackson.databind.jsontype.impl;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.util.*;
import com.fasterxml.jackson.core.*;
import java.io.*;
import com.fasterxml.jackson.databind.jsontype.*;

public class AsWrapperTypeSerializer extends TypeSerializerBase
{
    public AsWrapperTypeSerializer(final TypeIdResolver typeIdResolver, final BeanProperty beanProperty) {
        super(typeIdResolver, beanProperty);
    }
    
    @Override
    public AsWrapperTypeSerializer forProperty(final BeanProperty beanProperty) {
        return (this._property == beanProperty) ? this : new AsWrapperTypeSerializer(this._idResolver, beanProperty);
    }
    
    @Override
    public JsonTypeInfo.As getTypeInclusion() {
        return JsonTypeInfo.As.WRAPPER_OBJECT;
    }
    
    protected String _validTypeId(final String s) {
        return ClassUtil.nonNullString(s);
    }
    
    protected final void _writeTypeId(final JsonGenerator jsonGenerator, final String s) throws IOException {
        if (s != null) {
            jsonGenerator.writeTypeId(s);
        }
    }
    
    @Override
    public TypeSerializer forProperty(final BeanProperty beanProperty) {
        return this.forProperty(beanProperty);
    }
}
