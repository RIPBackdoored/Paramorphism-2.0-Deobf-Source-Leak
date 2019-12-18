package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.databind.jsontype.*;
import com.fasterxml.jackson.databind.ser.impl.*;
import com.fasterxml.jackson.databind.ser.*;
import com.fasterxml.jackson.core.*;
import java.io.*;

public static final class Prefetch implements Serializable
{
    private static final long serialVersionUID = 1L;
    public static final Prefetch empty;
    private final JavaType rootType;
    private final JsonSerializer<Object> valueSerializer;
    private final TypeSerializer typeSerializer;
    
    private Prefetch(final JavaType rootType, final JsonSerializer<Object> valueSerializer, final TypeSerializer typeSerializer) {
        super();
        this.rootType = rootType;
        this.valueSerializer = valueSerializer;
        this.typeSerializer = typeSerializer;
    }
    
    public Prefetch forRootType(final ObjectWriter objectWriter, final JavaType javaType) {
        if (javaType == null || javaType.isJavaLangObject()) {
            if (this.rootType == null || this.valueSerializer == null) {
                return this;
            }
            return new Prefetch(null, null, this.typeSerializer);
        }
        else {
            if (javaType.equals(this.rootType)) {
                return this;
            }
            if (objectWriter.isEnabled(SerializationFeature.EAGER_SERIALIZER_FETCH)) {
                final DefaultSerializerProvider serializerProvider = objectWriter._serializerProvider();
                try {
                    final JsonSerializer<Object> typedValueSerializer = serializerProvider.findTypedValueSerializer(javaType, true, null);
                    if (typedValueSerializer instanceof TypeWrappedSerializer) {
                        return new Prefetch(javaType, null, ((TypeWrappedSerializer)typedValueSerializer).typeSerializer());
                    }
                    return new Prefetch(javaType, typedValueSerializer, null);
                }
                catch (JsonProcessingException ex) {}
            }
            return new Prefetch(javaType, null, this.typeSerializer);
        }
    }
    
    public final JsonSerializer<Object> getValueSerializer() {
        return this.valueSerializer;
    }
    
    public final TypeSerializer getTypeSerializer() {
        return this.typeSerializer;
    }
    
    public boolean hasSerializer() {
        return this.valueSerializer != null || this.typeSerializer != null;
    }
    
    public void serialize(final JsonGenerator jsonGenerator, final Object o, final DefaultSerializerProvider defaultSerializerProvider) throws IOException {
        if (this.typeSerializer != null) {
            defaultSerializerProvider.serializePolymorphic(jsonGenerator, o, this.rootType, this.valueSerializer, this.typeSerializer);
        }
        else if (this.valueSerializer != null) {
            defaultSerializerProvider.serializeValue(jsonGenerator, o, this.rootType, this.valueSerializer);
        }
        else if (this.rootType != null) {
            defaultSerializerProvider.serializeValue(jsonGenerator, o, this.rootType);
        }
        else {
            defaultSerializerProvider.serializeValue(jsonGenerator, o);
        }
    }
    
    static {
        empty = new Prefetch(null, null, null);
    }
}
