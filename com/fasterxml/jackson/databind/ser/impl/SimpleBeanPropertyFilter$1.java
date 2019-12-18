package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.ser.*;
import com.fasterxml.jackson.databind.node.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.jsonFormatVisitors.*;

static final class SimpleBeanPropertyFilter$1 implements PropertyFilter {
    final BeanPropertyFilter val$src;
    
    SimpleBeanPropertyFilter$1(final BeanPropertyFilter val$src) {
        this.val$src = val$src;
        super();
    }
    
    @Override
    public void serializeAsField(final Object o, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider, final PropertyWriter propertyWriter) throws Exception {
        this.val$src.serializeAsField(o, jsonGenerator, serializerProvider, (BeanPropertyWriter)propertyWriter);
    }
    
    @Override
    public void depositSchemaProperty(final PropertyWriter propertyWriter, final ObjectNode objectNode, final SerializerProvider serializerProvider) throws JsonMappingException {
        this.val$src.depositSchemaProperty((BeanPropertyWriter)propertyWriter, objectNode, serializerProvider);
    }
    
    @Override
    public void depositSchemaProperty(final PropertyWriter propertyWriter, final JsonObjectFormatVisitor jsonObjectFormatVisitor, final SerializerProvider serializerProvider) throws JsonMappingException {
        this.val$src.depositSchemaProperty((BeanPropertyWriter)propertyWriter, jsonObjectFormatVisitor, serializerProvider);
    }
    
    @Override
    public void serializeAsElement(final Object o, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider, final PropertyWriter propertyWriter) throws Exception {
        throw new UnsupportedOperationException();
    }
}