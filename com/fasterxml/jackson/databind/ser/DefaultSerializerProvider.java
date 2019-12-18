package com.fasterxml.jackson.databind.ser;

import com.fasterxml.jackson.databind.ser.impl.*;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.util.*;
import com.fasterxml.jackson.databind.introspect.*;
import java.util.*;
import java.util.concurrent.atomic.*;
import com.fasterxml.jackson.databind.jsontype.*;
import com.fasterxml.jackson.databind.cfg.*;
import java.io.*;
import com.fasterxml.jackson.databind.jsonFormatVisitors.*;
import com.fasterxml.jackson.databind.jsonschema.*;
import java.lang.reflect.*;
import com.fasterxml.jackson.databind.node.*;
import com.fasterxml.jackson.databind.*;

public abstract class DefaultSerializerProvider extends SerializerProvider implements Serializable
{
    private static final long serialVersionUID = 1L;
    protected transient Map<Object, WritableObjectId> _seenObjectIds;
    protected transient ArrayList<ObjectIdGenerator<?>> _objectIdGenerators;
    protected transient JsonGenerator _generator;
    
    protected DefaultSerializerProvider() {
        super();
    }
    
    protected DefaultSerializerProvider(final SerializerProvider serializerProvider, final SerializationConfig serializationConfig, final SerializerFactory serializerFactory) {
        super(serializerProvider, serializationConfig, serializerFactory);
    }
    
    protected DefaultSerializerProvider(final DefaultSerializerProvider defaultSerializerProvider) {
        super(defaultSerializerProvider);
    }
    
    public abstract DefaultSerializerProvider createInstance(final SerializationConfig p0, final SerializerFactory p1);
    
    public DefaultSerializerProvider copy() {
        throw new IllegalStateException("DefaultSerializerProvider sub-class not overriding copy()");
    }
    
    @Override
    public JsonSerializer<Object> serializerInstance(final Annotated annotated, final Object o) throws JsonMappingException {
        if (o == null) {
            return null;
        }
        JsonSerializer<?> jsonSerializer;
        if (o instanceof JsonSerializer) {
            jsonSerializer = (JsonSerializer<?>)o;
        }
        else {
            if (!(o instanceof Class)) {
                this.reportBadDefinition(annotated.getType(), "AnnotationIntrospector returned serializer definition of type " + o.getClass().getName() + "; expected type JsonSerializer or Class<JsonSerializer> instead");
            }
            final Class clazz = (Class)o;
            if (clazz == JsonSerializer.None.class || ClassUtil.isBogusClass(clazz)) {
                return null;
            }
            if (!JsonSerializer.class.isAssignableFrom(clazz)) {
                this.reportBadDefinition(annotated.getType(), "AnnotationIntrospector returned Class " + clazz.getName() + "; expected Class<JsonSerializer>");
            }
            final HandlerInstantiator handlerInstantiator = this._config.getHandlerInstantiator();
            jsonSerializer = ((handlerInstantiator == null) ? null : handlerInstantiator.serializerInstance(this._config, annotated, clazz));
            if (jsonSerializer == null) {
                jsonSerializer = ClassUtil.createInstance((Class<JsonSerializer<?>>)clazz, this._config.canOverrideAccessModifiers());
            }
        }
        return this._handleResolvable(jsonSerializer);
    }
    
    @Override
    public Object includeFilterInstance(final BeanPropertyDefinition beanPropertyDefinition, final Class<?> clazz) {
        if (clazz == null) {
            return null;
        }
        final HandlerInstantiator handlerInstantiator = this._config.getHandlerInstantiator();
        Object instance = (handlerInstantiator == null) ? null : handlerInstantiator.includeFilterInstance(this._config, beanPropertyDefinition, clazz);
        if (instance == null) {
            instance = ClassUtil.createInstance(clazz, this._config.canOverrideAccessModifiers());
        }
        return instance;
    }
    
    @Override
    public boolean includeFilterSuppressNulls(final Object o) throws JsonMappingException {
        if (o == null) {
            return true;
        }
        try {
            return o.equals(null);
        }
        catch (Throwable t) {
            this.reportBadDefinition(o.getClass(), String.format("Problem determining whether filter of type '%s' should filter out `null` values: (%s) %s", o.getClass().getName(), t.getClass().getName(), t.getMessage()), t);
            return false;
        }
    }
    
    @Override
    public WritableObjectId findObjectId(final Object o, final ObjectIdGenerator<?> objectIdGenerator) {
        if (this._seenObjectIds == null) {
            this._seenObjectIds = this._createObjectIdMap();
        }
        else {
            final WritableObjectId writableObjectId = this._seenObjectIds.get(o);
            if (writableObjectId != null) {
                return writableObjectId;
            }
        }
        ObjectIdGenerator<?> forSerialization = null;
        if (this._objectIdGenerators == null) {
            this._objectIdGenerators = new ArrayList<ObjectIdGenerator<?>>(8);
        }
        else {
            for (int i = 0; i < this._objectIdGenerators.size(); ++i) {
                final ObjectIdGenerator<?> objectIdGenerator2 = this._objectIdGenerators.get(i);
                if (objectIdGenerator2.canUseFor(objectIdGenerator)) {
                    forSerialization = objectIdGenerator2;
                    break;
                }
            }
        }
        if (forSerialization == null) {
            forSerialization = objectIdGenerator.newForSerialization(this);
            this._objectIdGenerators.add(forSerialization);
        }
        final WritableObjectId writableObjectId2 = new WritableObjectId(forSerialization);
        this._seenObjectIds.put(o, writableObjectId2);
        return writableObjectId2;
    }
    
    protected Map<Object, WritableObjectId> _createObjectIdMap() {
        if (this.isEnabled(SerializationFeature.USE_EQUALITY_FOR_OBJECT_ID)) {
            return new HashMap<Object, WritableObjectId>();
        }
        return new IdentityHashMap<Object, WritableObjectId>();
    }
    
    public boolean hasSerializerFor(final Class<?> clazz, final AtomicReference<Throwable> atomicReference) {
        if (clazz == Object.class && !this._config.isEnabled(SerializationFeature.FAIL_ON_EMPTY_BEANS)) {
            return true;
        }
        try {
            return this._findExplicitUntypedSerializer(clazz) != null;
        }
        catch (JsonMappingException ex) {
            if (atomicReference != null) {
                atomicReference.set(ex);
            }
        }
        catch (RuntimeException ex2) {
            if (atomicReference == null) {
                throw ex2;
            }
            atomicReference.set(ex2);
        }
        return false;
    }
    
    @Override
    public JsonGenerator getGenerator() {
        return this._generator;
    }
    
    public void serializeValue(final JsonGenerator generator, final Object o) throws IOException {
        this._generator = generator;
        if (o == null) {
            this._serializeNull(generator);
            return;
        }
        final Class<?> class1 = o.getClass();
        final JsonSerializer<Object> typedValueSerializer = this.findTypedValueSerializer(class1, true, null);
        final PropertyName fullRootName = this._config.getFullRootName();
        if (fullRootName == null) {
            if (this._config.isEnabled(SerializationFeature.WRAP_ROOT_VALUE)) {
                this._serialize(generator, o, typedValueSerializer, this._config.findRootName(class1));
                return;
            }
        }
        else if (!fullRootName.isEmpty()) {
            this._serialize(generator, o, typedValueSerializer, fullRootName);
            return;
        }
        this._serialize(generator, o, typedValueSerializer);
    }
    
    public void serializeValue(final JsonGenerator generator, final Object o, final JavaType javaType) throws IOException {
        this._generator = generator;
        if (o == null) {
            this._serializeNull(generator);
            return;
        }
        if (!javaType.getRawClass().isAssignableFrom(o.getClass())) {
            this._reportIncompatibleRootType(o, javaType);
        }
        final JsonSerializer<Object> typedValueSerializer = this.findTypedValueSerializer(javaType, true, null);
        final PropertyName fullRootName = this._config.getFullRootName();
        if (fullRootName == null) {
            if (this._config.isEnabled(SerializationFeature.WRAP_ROOT_VALUE)) {
                this._serialize(generator, o, typedValueSerializer, this._config.findRootName(javaType));
                return;
            }
        }
        else if (!fullRootName.isEmpty()) {
            this._serialize(generator, o, typedValueSerializer, fullRootName);
            return;
        }
        this._serialize(generator, o, typedValueSerializer);
    }
    
    public void serializeValue(final JsonGenerator generator, final Object o, final JavaType javaType, JsonSerializer<Object> typedValueSerializer) throws IOException {
        this._generator = generator;
        if (o == null) {
            this._serializeNull(generator);
            return;
        }
        if (javaType != null && !javaType.getRawClass().isAssignableFrom(o.getClass())) {
            this._reportIncompatibleRootType(o, javaType);
        }
        if (typedValueSerializer == null) {
            typedValueSerializer = this.findTypedValueSerializer(javaType, true, null);
        }
        final PropertyName fullRootName = this._config.getFullRootName();
        if (fullRootName == null) {
            if (this._config.isEnabled(SerializationFeature.WRAP_ROOT_VALUE)) {
                this._serialize(generator, o, typedValueSerializer, (javaType == null) ? this._config.findRootName(o.getClass()) : this._config.findRootName(javaType));
                return;
            }
        }
        else if (!fullRootName.isEmpty()) {
            this._serialize(generator, o, typedValueSerializer, fullRootName);
            return;
        }
        this._serialize(generator, o, typedValueSerializer);
    }
    
    public void serializePolymorphic(final JsonGenerator generator, final Object o, final JavaType javaType, JsonSerializer<Object> jsonSerializer, final TypeSerializer typeSerializer) throws IOException {
        this._generator = generator;
        if (o == null) {
            this._serializeNull(generator);
            return;
        }
        if (javaType != null && !javaType.getRawClass().isAssignableFrom(o.getClass())) {
            this._reportIncompatibleRootType(o, javaType);
        }
        if (jsonSerializer == null) {
            if (javaType != null && javaType.isContainerType()) {
                jsonSerializer = this.findValueSerializer(javaType, null);
            }
            else {
                jsonSerializer = this.findValueSerializer(o.getClass(), null);
            }
        }
        final PropertyName fullRootName = this._config.getFullRootName();
        int enabled;
        if (fullRootName == null) {
            enabled = (this._config.isEnabled(SerializationFeature.WRAP_ROOT_VALUE) ? 1 : 0);
            if (enabled != 0) {
                generator.writeStartObject();
                generator.writeFieldName(this._config.findRootName(o.getClass()).simpleAsEncoded(this._config));
            }
        }
        else if (fullRootName.isEmpty()) {
            enabled = 0;
        }
        else {
            enabled = 1;
            generator.writeStartObject();
            generator.writeFieldName(fullRootName.getSimpleName());
        }
        try {
            jsonSerializer.serializeWithType(o, generator, this, typeSerializer);
            if (enabled != 0) {
                generator.writeEndObject();
            }
        }
        catch (Exception ex) {
            throw this._wrapAsIOE(generator, ex);
        }
    }
    
    private final void _serialize(final JsonGenerator jsonGenerator, final Object o, final JsonSerializer<Object> jsonSerializer, final PropertyName propertyName) throws IOException {
        try {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeFieldName(propertyName.simpleAsEncoded(this._config));
            jsonSerializer.serialize(o, jsonGenerator, this);
            jsonGenerator.writeEndObject();
        }
        catch (Exception ex) {
            throw this._wrapAsIOE(jsonGenerator, ex);
        }
    }
    
    private final void _serialize(final JsonGenerator jsonGenerator, final Object o, final JsonSerializer<Object> jsonSerializer) throws IOException {
        try {
            jsonSerializer.serialize(o, jsonGenerator, this);
        }
        catch (Exception ex) {
            throw this._wrapAsIOE(jsonGenerator, ex);
        }
    }
    
    protected void _serializeNull(final JsonGenerator jsonGenerator) throws IOException {
        final JsonSerializer<Object> defaultNullValueSerializer = this.getDefaultNullValueSerializer();
        try {
            defaultNullValueSerializer.serialize(null, jsonGenerator, this);
        }
        catch (Exception ex) {
            throw this._wrapAsIOE(jsonGenerator, ex);
        }
    }
    
    private IOException _wrapAsIOE(final JsonGenerator jsonGenerator, final Exception ex) {
        if (ex instanceof IOException) {
            return (IOException)ex;
        }
        String s = ex.getMessage();
        if (s == null) {
            s = "[no message for " + ex.getClass().getName() + "]";
        }
        return new JsonMappingException(jsonGenerator, s, ex);
    }
    
    public int cachedSerializersCount() {
        return this._serializerCache.size();
    }
    
    public void flushCachedSerializers() {
        this._serializerCache.flush();
    }
    
    public void acceptJsonFormatVisitor(final JavaType javaType, final JsonFormatVisitorWrapper jsonFormatVisitorWrapper) throws JsonMappingException {
        if (javaType == null) {
            throw new IllegalArgumentException("A class must be provided");
        }
        jsonFormatVisitorWrapper.setProvider(this);
        this.findValueSerializer(javaType, null).acceptJsonFormatVisitor(jsonFormatVisitorWrapper, javaType);
    }
    
    @Deprecated
    public JsonSchema generateJsonSchema(final Class<?> clazz) throws JsonMappingException {
        final JsonSerializer<Object> valueSerializer = this.findValueSerializer(clazz, null);
        final JsonNode jsonNode = (valueSerializer instanceof SchemaAware) ? ((SchemaAware)valueSerializer).getSchema(this, null) : JsonSchema.getDefaultSchemaNode();
        if (!(jsonNode instanceof ObjectNode)) {
            throw new IllegalArgumentException("Class " + clazz.getName() + " would not be serialized as a JSON object and therefore has no schema");
        }
        return new JsonSchema((ObjectNode)jsonNode);
    }
    
    public static final class Impl extends DefaultSerializerProvider
    {
        private static final long serialVersionUID = 1L;
        
        public Impl() {
            super();
        }
        
        public Impl(final Impl impl) {
            super(impl);
        }
        
        protected Impl(final SerializerProvider serializerProvider, final SerializationConfig serializationConfig, final SerializerFactory serializerFactory) {
            super(serializerProvider, serializationConfig, serializerFactory);
        }
        
        @Override
        public DefaultSerializerProvider copy() {
            if (this.getClass() != Impl.class) {
                return super.copy();
            }
            return new Impl(this);
        }
        
        @Override
        public Impl createInstance(final SerializationConfig serializationConfig, final SerializerFactory serializerFactory) {
            return new Impl(this, serializationConfig, serializerFactory);
        }
        
        @Override
        public DefaultSerializerProvider createInstance(final SerializationConfig serializationConfig, final SerializerFactory serializerFactory) {
            return this.createInstance(serializationConfig, serializerFactory);
        }
    }
}
