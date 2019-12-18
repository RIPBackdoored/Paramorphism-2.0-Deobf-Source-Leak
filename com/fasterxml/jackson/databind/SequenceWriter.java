package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.databind.ser.*;
import com.fasterxml.jackson.databind.jsontype.*;
import java.io.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.cfg.*;
import java.util.*;
import com.fasterxml.jackson.databind.ser.impl.*;

public class SequenceWriter implements Versioned, Closeable, Flushable
{
    protected final DefaultSerializerProvider _provider;
    protected final SerializationConfig _config;
    protected final JsonGenerator _generator;
    protected final JsonSerializer<Object> _rootSerializer;
    protected final TypeSerializer _typeSerializer;
    protected final boolean _closeGenerator;
    protected final boolean _cfgFlush;
    protected final boolean _cfgCloseCloseable;
    protected PropertySerializerMap _dynamicSerializers;
    protected boolean _openArray;
    protected boolean _closed;
    
    public SequenceWriter(final DefaultSerializerProvider provider, final JsonGenerator generator, final boolean closeGenerator, final ObjectWriter.Prefetch prefetch) throws IOException {
        super();
        this._provider = provider;
        this._generator = generator;
        this._closeGenerator = closeGenerator;
        this._rootSerializer = prefetch.getValueSerializer();
        this._typeSerializer = prefetch.getTypeSerializer();
        this._config = provider.getConfig();
        this._cfgFlush = this._config.isEnabled(SerializationFeature.FLUSH_AFTER_WRITE_VALUE);
        this._cfgCloseCloseable = this._config.isEnabled(SerializationFeature.CLOSE_CLOSEABLE);
        this._dynamicSerializers = PropertySerializerMap.emptyForRootValues();
    }
    
    public SequenceWriter init(final boolean b) throws IOException {
        if (b) {
            this._generator.writeStartArray();
            this._openArray = true;
        }
        return this;
    }
    
    @Override
    public Version version() {
        return PackageVersion.VERSION;
    }
    
    public SequenceWriter write(final Object o) throws IOException {
        if (o == null) {
            this._provider.serializeValue(this._generator, null);
            return this;
        }
        if (this._cfgCloseCloseable && o instanceof Closeable) {
            return this._writeCloseableValue(o);
        }
        JsonSerializer<Object> jsonSerializer = this._rootSerializer;
        if (jsonSerializer == null) {
            final Class<?> class1 = o.getClass();
            jsonSerializer = this._dynamicSerializers.serializerFor(class1);
            if (jsonSerializer == null) {
                jsonSerializer = this._findAndAddDynamic(class1);
            }
        }
        this._provider.serializeValue(this._generator, o, null, jsonSerializer);
        if (this._cfgFlush) {
            this._generator.flush();
        }
        return this;
    }
    
    public SequenceWriter write(final Object o, final JavaType javaType) throws IOException {
        if (o == null) {
            this._provider.serializeValue(this._generator, null);
            return this;
        }
        if (this._cfgCloseCloseable && o instanceof Closeable) {
            return this._writeCloseableValue(o, javaType);
        }
        JsonSerializer<Object> jsonSerializer = this._dynamicSerializers.serializerFor(javaType.getRawClass());
        if (jsonSerializer == null) {
            jsonSerializer = this._findAndAddDynamic(javaType);
        }
        this._provider.serializeValue(this._generator, o, javaType, jsonSerializer);
        if (this._cfgFlush) {
            this._generator.flush();
        }
        return this;
    }
    
    public SequenceWriter writeAll(final Object[] array) throws IOException {
        for (int i = 0; i < array.length; ++i) {
            this.write(array[i]);
        }
        return this;
    }
    
    public <C extends Collection<?>> SequenceWriter writeAll(final C c) throws IOException {
        final Iterator<Object> iterator = ((Collection<Object>)c).iterator();
        while (iterator.hasNext()) {
            this.write(iterator.next());
        }
        return this;
    }
    
    public SequenceWriter writeAll(final Iterable<?> iterable) throws IOException {
        final Iterator<?> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            this.write(iterator.next());
        }
        return this;
    }
    
    @Override
    public void flush() throws IOException {
        if (!this._closed) {
            this._generator.flush();
        }
    }
    
    @Override
    public void close() throws IOException {
        if (!this._closed) {
            this._closed = true;
            if (this._openArray) {
                this._openArray = false;
                this._generator.writeEndArray();
            }
            if (this._closeGenerator) {
                this._generator.close();
            }
        }
    }
    
    protected SequenceWriter _writeCloseableValue(final Object o) throws IOException {
        Closeable closeable = (Closeable)o;
        try {
            JsonSerializer<Object> jsonSerializer = this._rootSerializer;
            if (jsonSerializer == null) {
                final Class<?> class1 = o.getClass();
                jsonSerializer = this._dynamicSerializers.serializerFor(class1);
                if (jsonSerializer == null) {
                    jsonSerializer = this._findAndAddDynamic(class1);
                }
            }
            this._provider.serializeValue(this._generator, o, null, jsonSerializer);
            if (this._cfgFlush) {
                this._generator.flush();
            }
            final Closeable closeable2 = closeable;
            closeable = null;
            closeable2.close();
        }
        finally {
            if (closeable != null) {
                try {
                    closeable.close();
                }
                catch (IOException ex) {}
            }
        }
        return this;
    }
    
    protected SequenceWriter _writeCloseableValue(final Object o, final JavaType javaType) throws IOException {
        Closeable closeable = (Closeable)o;
        try {
            JsonSerializer<Object> jsonSerializer = this._dynamicSerializers.serializerFor(javaType.getRawClass());
            if (jsonSerializer == null) {
                jsonSerializer = this._findAndAddDynamic(javaType);
            }
            this._provider.serializeValue(this._generator, o, javaType, jsonSerializer);
            if (this._cfgFlush) {
                this._generator.flush();
            }
            final Closeable closeable2 = closeable;
            closeable = null;
            closeable2.close();
        }
        finally {
            if (closeable != null) {
                try {
                    closeable.close();
                }
                catch (IOException ex) {}
            }
        }
        return this;
    }
    
    private final JsonSerializer<Object> _findAndAddDynamic(final Class<?> clazz) throws JsonMappingException {
        PropertySerializerMap.SerializerAndMapResult serializerAndMapResult;
        if (this._typeSerializer == null) {
            serializerAndMapResult = this._dynamicSerializers.findAndAddRootValueSerializer(clazz, this._provider);
        }
        else {
            serializerAndMapResult = this._dynamicSerializers.addSerializer(clazz, new TypeWrappedSerializer(this._typeSerializer, this._provider.findValueSerializer(clazz, null)));
        }
        this._dynamicSerializers = serializerAndMapResult.map;
        return serializerAndMapResult.serializer;
    }
    
    private final JsonSerializer<Object> _findAndAddDynamic(final JavaType javaType) throws JsonMappingException {
        PropertySerializerMap.SerializerAndMapResult serializerAndMapResult;
        if (this._typeSerializer == null) {
            serializerAndMapResult = this._dynamicSerializers.findAndAddRootValueSerializer(javaType, this._provider);
        }
        else {
            serializerAndMapResult = this._dynamicSerializers.addSerializer(javaType, new TypeWrappedSerializer(this._typeSerializer, this._provider.findValueSerializer(javaType, null)));
        }
        this._dynamicSerializers = serializerAndMapResult.map;
        return serializerAndMapResult.serializer;
    }
}
