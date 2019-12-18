package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.databind.ser.*;
import java.io.*;
import com.fasterxml.jackson.databind.util.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.jsonFormatVisitors.*;
import com.fasterxml.jackson.databind.*;

private static final class MultiView extends BeanPropertyWriter implements Serializable
{
    private static final long serialVersionUID = 1L;
    protected final BeanPropertyWriter _delegate;
    protected final Class<?>[] _views;
    
    protected MultiView(final BeanPropertyWriter delegate, final Class<?>[] views) {
        super(delegate);
        this._delegate = delegate;
        this._views = views;
    }
    
    @Override
    public MultiView rename(final NameTransformer nameTransformer) {
        return new MultiView(this._delegate.rename(nameTransformer), this._views);
    }
    
    @Override
    public void assignSerializer(final JsonSerializer<Object> jsonSerializer) {
        this._delegate.assignSerializer(jsonSerializer);
    }
    
    @Override
    public void assignNullSerializer(final JsonSerializer<Object> jsonSerializer) {
        this._delegate.assignNullSerializer(jsonSerializer);
    }
    
    @Override
    public void serializeAsField(final Object o, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws Exception {
        if (this._inView(serializerProvider.getActiveView())) {
            this._delegate.serializeAsField(o, jsonGenerator, serializerProvider);
            return;
        }
        this._delegate.serializeAsOmittedField(o, jsonGenerator, serializerProvider);
    }
    
    @Override
    public void serializeAsElement(final Object o, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws Exception {
        if (this._inView(serializerProvider.getActiveView())) {
            this._delegate.serializeAsElement(o, jsonGenerator, serializerProvider);
            return;
        }
        this._delegate.serializeAsPlaceholder(o, jsonGenerator, serializerProvider);
    }
    
    @Override
    public void depositSchemaProperty(final JsonObjectFormatVisitor jsonObjectFormatVisitor, final SerializerProvider serializerProvider) throws JsonMappingException {
        if (this._inView(serializerProvider.getActiveView())) {
            super.depositSchemaProperty(jsonObjectFormatVisitor, serializerProvider);
        }
    }
    
    private final boolean _inView(final Class<?> clazz) {
        if (clazz == null) {
            return true;
        }
        for (int length = this._views.length, i = 0; i < length; ++i) {
            if (this._views[i].isAssignableFrom(clazz)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public BeanPropertyWriter rename(final NameTransformer nameTransformer) {
        return this.rename(nameTransformer);
    }
}
