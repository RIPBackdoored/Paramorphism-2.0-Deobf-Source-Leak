package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.databind.ser.*;
import java.io.*;
import com.fasterxml.jackson.databind.util.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.jsonFormatVisitors.*;
import com.fasterxml.jackson.databind.*;

private static final class SingleView extends BeanPropertyWriter implements Serializable
{
    private static final long serialVersionUID = 1L;
    protected final BeanPropertyWriter _delegate;
    protected final Class<?> _view;
    
    protected SingleView(final BeanPropertyWriter delegate, final Class<?> view) {
        super(delegate);
        this._delegate = delegate;
        this._view = view;
    }
    
    @Override
    public SingleView rename(final NameTransformer nameTransformer) {
        return new SingleView(this._delegate.rename(nameTransformer), this._view);
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
        final Class<?> activeView = serializerProvider.getActiveView();
        if (activeView == null || this._view.isAssignableFrom(activeView)) {
            this._delegate.serializeAsField(o, jsonGenerator, serializerProvider);
        }
        else {
            this._delegate.serializeAsOmittedField(o, jsonGenerator, serializerProvider);
        }
    }
    
    @Override
    public void serializeAsElement(final Object o, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws Exception {
        final Class<?> activeView = serializerProvider.getActiveView();
        if (activeView == null || this._view.isAssignableFrom(activeView)) {
            this._delegate.serializeAsElement(o, jsonGenerator, serializerProvider);
        }
        else {
            this._delegate.serializeAsPlaceholder(o, jsonGenerator, serializerProvider);
        }
    }
    
    @Override
    public void depositSchemaProperty(final JsonObjectFormatVisitor jsonObjectFormatVisitor, final SerializerProvider serializerProvider) throws JsonMappingException {
        final Class<?> activeView = serializerProvider.getActiveView();
        if (activeView == null || this._view.isAssignableFrom(activeView)) {
            super.depositSchemaProperty(jsonObjectFormatVisitor, serializerProvider);
        }
    }
    
    @Override
    public BeanPropertyWriter rename(final NameTransformer nameTransformer) {
        return this.rename(nameTransformer);
    }
}
