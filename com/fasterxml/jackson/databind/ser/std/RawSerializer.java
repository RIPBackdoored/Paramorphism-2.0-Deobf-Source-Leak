package com.fasterxml.jackson.databind.ser.std;

import java.io.*;
import com.fasterxml.jackson.databind.jsontype.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.type.*;
import java.lang.reflect.*;
import com.fasterxml.jackson.databind.jsonFormatVisitors.*;
import com.fasterxml.jackson.databind.*;

public class RawSerializer<T> extends StdSerializer<T>
{
    public RawSerializer(final Class<?> clazz) {
        super(clazz, false);
    }
    
    @Override
    public void serialize(final T t, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeRawValue(t.toString());
    }
    
    @Override
    public void serializeWithType(final T t, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider, final TypeSerializer typeSerializer) throws IOException {
        final WritableTypeId writeTypePrefix = typeSerializer.writeTypePrefix(jsonGenerator, typeSerializer.typeId(t, JsonToken.VALUE_EMBEDDED_OBJECT));
        this.serialize(t, jsonGenerator, serializerProvider);
        typeSerializer.writeTypeSuffix(jsonGenerator, writeTypePrefix);
    }
    
    @Override
    public JsonNode getSchema(final SerializerProvider serializerProvider, final Type type) {
        return this.createSchemaNode("string", true);
    }
    
    @Override
    public void acceptJsonFormatVisitor(final JsonFormatVisitorWrapper jsonFormatVisitorWrapper, final JavaType javaType) throws JsonMappingException {
        this.visitStringFormat(jsonFormatVisitorWrapper, javaType);
    }
}
