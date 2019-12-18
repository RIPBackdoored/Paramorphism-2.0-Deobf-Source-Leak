package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.jsontype.*;
import java.io.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.util.*;

public abstract class StdScalarDeserializer<T> extends StdDeserializer<T>
{
    private static final long serialVersionUID = 1L;
    
    protected StdScalarDeserializer(final Class<?> clazz) {
        super(clazz);
    }
    
    protected StdScalarDeserializer(final JavaType javaType) {
        super(javaType);
    }
    
    protected StdScalarDeserializer(final StdScalarDeserializer<?> stdScalarDeserializer) {
        super(stdScalarDeserializer);
    }
    
    @Override
    public Object deserializeWithType(final JsonParser jsonParser, final DeserializationContext deserializationContext, final TypeDeserializer typeDeserializer) throws IOException {
        return typeDeserializer.deserializeTypedFromScalar(jsonParser, deserializationContext);
    }
    
    @Override
    public T deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext, final T t) throws IOException {
        deserializationContext.reportBadMerge(this);
        return this.deserialize(jsonParser, deserializationContext);
    }
    
    @Override
    public Boolean supportsUpdate(final DeserializationConfig deserializationConfig) {
        return Boolean.FALSE;
    }
    
    @Override
    public AccessPattern getNullAccessPattern() {
        return AccessPattern.ALWAYS_NULL;
    }
    
    @Override
    public AccessPattern getEmptyAccessPattern() {
        return AccessPattern.CONSTANT;
    }
}
