package com.fasterxml.jackson.databind.jsonFormatVisitors;

import com.fasterxml.jackson.databind.*;

public static class Base implements JsonArrayFormatVisitor
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
    public void itemsFormat(final JsonFormatVisitable jsonFormatVisitable, final JavaType javaType) throws JsonMappingException {
    }
    
    @Override
    public void itemsFormat(final JsonFormatTypes jsonFormatTypes) throws JsonMappingException {
    }
}
