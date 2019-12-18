package com.fasterxml.jackson.databind.jsonFormatVisitors;

import com.fasterxml.jackson.databind.*;

public static class Base implements JsonFormatVisitorWrapper
{
    protected SerializerProvider _provider;
    
    public Base() {
        super();
    }
    
    public Base(final SerializerProvider provider) {
        super();
        this._provider = provider;
    }
    
    @Override
    public SerializerProvider getProvider() {
        return this._provider;
    }
    
    @Override
    public void setProvider(final SerializerProvider provider) {
        this._provider = provider;
    }
    
    @Override
    public JsonObjectFormatVisitor expectObjectFormat(final JavaType javaType) throws JsonMappingException {
        return null;
    }
    
    @Override
    public JsonArrayFormatVisitor expectArrayFormat(final JavaType javaType) throws JsonMappingException {
        return null;
    }
    
    @Override
    public JsonStringFormatVisitor expectStringFormat(final JavaType javaType) throws JsonMappingException {
        return null;
    }
    
    @Override
    public JsonNumberFormatVisitor expectNumberFormat(final JavaType javaType) throws JsonMappingException {
        return null;
    }
    
    @Override
    public JsonIntegerFormatVisitor expectIntegerFormat(final JavaType javaType) throws JsonMappingException {
        return null;
    }
    
    @Override
    public JsonBooleanFormatVisitor expectBooleanFormat(final JavaType javaType) throws JsonMappingException {
        return null;
    }
    
    @Override
    public JsonNullFormatVisitor expectNullFormat(final JavaType javaType) throws JsonMappingException {
        return null;
    }
    
    @Override
    public JsonAnyFormatVisitor expectAnyFormat(final JavaType javaType) throws JsonMappingException {
        return null;
    }
    
    @Override
    public JsonMapFormatVisitor expectMapFormat(final JavaType javaType) throws JsonMappingException {
        return null;
    }
}
