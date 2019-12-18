package com.fasterxml.jackson.databind.ser.std;

import java.util.*;
import com.fasterxml.jackson.databind.util.*;
import com.fasterxml.jackson.databind.cfg.*;
import com.fasterxml.jackson.core.*;
import java.io.*;
import com.fasterxml.jackson.databind.ser.impl.*;
import com.fasterxml.jackson.databind.jsonFormatVisitors.*;
import com.fasterxml.jackson.databind.*;

public abstract class StdKeySerializers
{
    protected static final JsonSerializer<Object> DEFAULT_KEY_SERIALIZER;
    protected static final JsonSerializer<Object> DEFAULT_STRING_SERIALIZER;
    
    public StdKeySerializers() {
        super();
    }
    
    public static JsonSerializer<Object> getStdKeySerializer(final SerializationConfig serializationConfig, Class<?> wrapperType, final boolean b) {
        if (wrapperType == null || wrapperType == Object.class) {
            return new Dynamic();
        }
        if (wrapperType == String.class) {
            return StdKeySerializers.DEFAULT_STRING_SERIALIZER;
        }
        if (wrapperType.isPrimitive()) {
            wrapperType = ClassUtil.wrapperType(wrapperType);
        }
        if (wrapperType == Integer.class) {
            return new Default(5, wrapperType);
        }
        if (wrapperType == Long.class) {
            return new Default(6, wrapperType);
        }
        if (wrapperType.isPrimitive() || Number.class.isAssignableFrom(wrapperType)) {
            return new Default(8, wrapperType);
        }
        if (wrapperType == Class.class) {
            return new Default(3, wrapperType);
        }
        if (Date.class.isAssignableFrom(wrapperType)) {
            return new Default(1, wrapperType);
        }
        if (Calendar.class.isAssignableFrom(wrapperType)) {
            return new Default(2, wrapperType);
        }
        if (wrapperType == UUID.class) {
            return new Default(8, wrapperType);
        }
        if (wrapperType == byte[].class) {
            return new Default(7, wrapperType);
        }
        if (b) {
            return new Default(8, wrapperType);
        }
        return null;
    }
    
    public static JsonSerializer<Object> getFallbackKeySerializer(final SerializationConfig serializationConfig, final Class<?> clazz) {
        if (clazz != null) {
            if (clazz == Enum.class) {
                return new Dynamic();
            }
            if (clazz.isEnum()) {
                return EnumKeySerializer.construct(clazz, EnumValues.constructFromName(serializationConfig, (Class<Enum<?>>)clazz));
            }
        }
        return new Default(8, clazz);
    }
    
    @Deprecated
    public static JsonSerializer<Object> getDefault() {
        return StdKeySerializers.DEFAULT_KEY_SERIALIZER;
    }
    
    static {
        DEFAULT_KEY_SERIALIZER = new StdKeySerializer();
        DEFAULT_STRING_SERIALIZER = new StringKeySerializer();
    }
    
    public static class Default extends StdSerializer<Object>
    {
        static final int TYPE_DATE = 1;
        static final int TYPE_CALENDAR = 2;
        static final int TYPE_CLASS = 3;
        static final int TYPE_ENUM = 4;
        static final int TYPE_INTEGER = 5;
        static final int TYPE_LONG = 6;
        static final int TYPE_BYTE_ARRAY = 7;
        static final int TYPE_TO_STRING = 8;
        protected final int _typeId;
        
        public Default(final int typeId, final Class<?> clazz) {
            super(clazz, false);
            this._typeId = typeId;
        }
        
        @Override
        public void serialize(final Object o, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException {
            switch (this._typeId) {
                case 1: {
                    serializerProvider.defaultSerializeDateKey((Date)o, jsonGenerator);
                    break;
                }
                case 2: {
                    serializerProvider.defaultSerializeDateKey(((Calendar)o).getTimeInMillis(), jsonGenerator);
                    break;
                }
                case 3: {
                    jsonGenerator.writeFieldName(((Class)o).getName());
                    break;
                }
                case 4: {
                    String s;
                    if (serializerProvider.isEnabled(SerializationFeature.WRITE_ENUMS_USING_TO_STRING)) {
                        s = o.toString();
                    }
                    else {
                        final Enum enum1 = (Enum)o;
                        if (serializerProvider.isEnabled(SerializationFeature.WRITE_ENUMS_USING_INDEX)) {
                            s = String.valueOf(enum1.ordinal());
                        }
                        else {
                            s = enum1.name();
                        }
                    }
                    jsonGenerator.writeFieldName(s);
                    break;
                }
                case 5:
                case 6: {
                    jsonGenerator.writeFieldId(((Number)o).longValue());
                    break;
                }
                case 7: {
                    jsonGenerator.writeFieldName(serializerProvider.getConfig().getBase64Variant().encode((byte[])o));
                    break;
                }
                default: {
                    jsonGenerator.writeFieldName(o.toString());
                    break;
                }
            }
        }
    }
    
    public static class Dynamic extends StdSerializer<Object>
    {
        protected transient PropertySerializerMap _dynamicSerializers;
        
        public Dynamic() {
            super(String.class, false);
            this._dynamicSerializers = PropertySerializerMap.emptyForProperties();
        }
        
        Object readResolve() {
            this._dynamicSerializers = PropertySerializerMap.emptyForProperties();
            return this;
        }
        
        @Override
        public void serialize(final Object o, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException {
            final Class<?> class1 = o.getClass();
            final PropertySerializerMap dynamicSerializers = this._dynamicSerializers;
            JsonSerializer<Object> jsonSerializer = dynamicSerializers.serializerFor(class1);
            if (jsonSerializer == null) {
                jsonSerializer = this._findAndAddDynamic(dynamicSerializers, class1, serializerProvider);
            }
            jsonSerializer.serialize(o, jsonGenerator, serializerProvider);
        }
        
        @Override
        public void acceptJsonFormatVisitor(final JsonFormatVisitorWrapper jsonFormatVisitorWrapper, final JavaType javaType) throws JsonMappingException {
            this.visitStringFormat(jsonFormatVisitorWrapper, javaType);
        }
        
        protected JsonSerializer<Object> _findAndAddDynamic(final PropertySerializerMap propertySerializerMap, final Class<?> clazz, final SerializerProvider serializerProvider) throws JsonMappingException {
            if (clazz == Object.class) {
                final Default default1 = new Default(8, clazz);
                this._dynamicSerializers = propertySerializerMap.newWith(clazz, default1);
                return default1;
            }
            final PropertySerializerMap.SerializerAndMapResult andAddKeySerializer = propertySerializerMap.findAndAddKeySerializer(clazz, serializerProvider, null);
            if (propertySerializerMap != andAddKeySerializer.map) {
                this._dynamicSerializers = andAddKeySerializer.map;
            }
            return andAddKeySerializer.serializer;
        }
    }
    
    public static class StringKeySerializer extends StdSerializer<Object>
    {
        public StringKeySerializer() {
            super(String.class, false);
        }
        
        @Override
        public void serialize(final Object o, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeFieldName((String)o);
        }
    }
    
    public static class EnumKeySerializer extends StdSerializer<Object>
    {
        protected final EnumValues _values;
        
        protected EnumKeySerializer(final Class<?> clazz, final EnumValues values) {
            super(clazz, false);
            this._values = values;
        }
        
        public static EnumKeySerializer construct(final Class<?> clazz, final EnumValues enumValues) {
            return new EnumKeySerializer(clazz, enumValues);
        }
        
        @Override
        public void serialize(final Object o, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException {
            if (serializerProvider.isEnabled(SerializationFeature.WRITE_ENUMS_USING_TO_STRING)) {
                jsonGenerator.writeFieldName(o.toString());
                return;
            }
            final Enum enum1 = (Enum)o;
            if (serializerProvider.isEnabled(SerializationFeature.WRITE_ENUMS_USING_INDEX)) {
                jsonGenerator.writeFieldName(String.valueOf(enum1.ordinal()));
                return;
            }
            jsonGenerator.writeFieldName(this._values.serializedValueFor(enum1));
        }
    }
}
