package com.fasterxml.jackson.databind.ser.std;

import java.sql.*;
import com.fasterxml.jackson.databind.annotation.*;
import com.fasterxml.jackson.core.*;
import java.io.*;
import java.lang.reflect.*;
import com.fasterxml.jackson.databind.jsonFormatVisitors.*;
import com.fasterxml.jackson.databind.*;

@JacksonStdImpl
public class SqlTimeSerializer extends StdScalarSerializer<Time>
{
    public SqlTimeSerializer() {
        super(Time.class);
    }
    
    @Override
    public void serialize(final Time time, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(time.toString());
    }
    
    @Override
    public JsonNode getSchema(final SerializerProvider serializerProvider, final Type type) {
        return this.createSchemaNode("string", true);
    }
    
    @Override
    public void acceptJsonFormatVisitor(final JsonFormatVisitorWrapper jsonFormatVisitorWrapper, final JavaType javaType) throws JsonMappingException {
        this.visitStringFormat(jsonFormatVisitorWrapper, javaType, JsonValueFormat.DATE_TIME);
    }
    
    @Override
    public void serialize(final Object o, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException {
        this.serialize((Time)o, jsonGenerator, serializerProvider);
    }
}
