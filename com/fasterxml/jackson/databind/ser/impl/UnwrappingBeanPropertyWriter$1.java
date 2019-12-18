package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.databind.jsonFormatVisitors.*;
import com.fasterxml.jackson.databind.*;

class UnwrappingBeanPropertyWriter$1 extends JsonFormatVisitorWrapper.Base {
    final JsonObjectFormatVisitor val$visitor;
    final UnwrappingBeanPropertyWriter this$0;
    
    UnwrappingBeanPropertyWriter$1(final UnwrappingBeanPropertyWriter this$0, final SerializerProvider serializerProvider, final JsonObjectFormatVisitor val$visitor) {
        this.this$0 = this$0;
        this.val$visitor = val$visitor;
        super(serializerProvider);
    }
    
    @Override
    public JsonObjectFormatVisitor expectObjectFormat(final JavaType javaType) throws JsonMappingException {
        return this.val$visitor;
    }
}