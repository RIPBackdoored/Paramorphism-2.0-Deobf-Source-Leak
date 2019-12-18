package com.fasterxml.jackson.databind.ser.std;

import java.util.*;
import com.fasterxml.jackson.databind.annotation.*;
import java.text.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import java.io.*;

@JacksonStdImpl
public class DateSerializer extends DateTimeSerializerBase<Date>
{
    public static final DateSerializer instance;
    
    public DateSerializer() {
        this(null, null);
    }
    
    public DateSerializer(final Boolean b, final DateFormat dateFormat) {
        super(Date.class, b, dateFormat);
    }
    
    @Override
    public DateSerializer withFormat(final Boolean b, final DateFormat dateFormat) {
        return new DateSerializer(b, dateFormat);
    }
    
    @Override
    protected long _timestamp(final Date date) {
        return (date == null) ? 0L : date.getTime();
    }
    
    @Override
    public void serialize(final Date date, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException {
        if (this._asTimestamp(serializerProvider)) {
            jsonGenerator.writeNumber(this._timestamp(date));
            return;
        }
        this._serializeAsString(date, jsonGenerator, serializerProvider);
    }
    
    @Override
    public void serialize(final Object o, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException {
        this.serialize((Date)o, jsonGenerator, serializerProvider);
    }
    
    @Override
    protected long _timestamp(final Object o) {
        return this._timestamp((Date)o);
    }
    
    @Override
    public DateTimeSerializerBase withFormat(final Boolean b, final DateFormat dateFormat) {
        return this.withFormat(b, dateFormat);
    }
    
    static {
        instance = new DateSerializer();
    }
}
