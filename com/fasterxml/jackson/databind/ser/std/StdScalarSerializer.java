package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.databind.jsontype.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.type.*;
import java.io.*;
import java.lang.reflect.*;
import com.fasterxml.jackson.databind.jsonFormatVisitors.*;
import com.fasterxml.jackson.databind.*;

public abstract class StdScalarSerializer<T> extends StdSerializer<T>
{
    protected StdScalarSerializer(final Class<T> clazz) {
        super(clazz);
    }
    
    protected StdScalarSerializer(final Class<?> clazz, final boolean b) {
        super(clazz);
    }
    
    @Override
    public void serializeWithType(final T t, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider, final TypeSerializer typeSerializer) throws IOException {
        final WritableTypeId writeTypePrefix = typeSerializer.writeTypePrefix(jsonGenerator, typeSerializer.typeId(t, JsonToken.VALUE_STRING));
        this.serialize(t, jsonGenerator, serializerProvider);
        typeSerializer.writeTypeSuffix(jsonGenerator, writeTypePrefix);
    }
    
    @Override
    public JsonNode getSchema(final SerializerProvider serializerProvider, final Type type) throws JsonMappingException {
        return this.createSchemaNode("string", true);
    }
    
    @Override
    public void acceptJsonFormatVisitor(final JsonFormatVisitorWrapper jsonFormatVisitorWrapper, final JavaType javaType) throws JsonMappingException {
        this.visitStringFormat(jsonFormatVisitorWrapper, javaType);
    }
}
