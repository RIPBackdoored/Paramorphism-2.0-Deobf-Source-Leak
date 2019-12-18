package com.fasterxml.jackson.databind.jsontype.impl;

import com.fasterxml.jackson.databind.jsontype.*;
import java.util.*;
import com.fasterxml.jackson.databind.util.*;
import java.util.concurrent.*;
import com.fasterxml.jackson.annotation.*;
import java.io.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.std.*;
import com.fasterxml.jackson.core.*;

public abstract class TypeDeserializerBase extends TypeDeserializer implements Serializable
{
    private static final long serialVersionUID = 1L;
    protected final TypeIdResolver _idResolver;
    protected final JavaType _baseType;
    protected final BeanProperty _property;
    protected final JavaType _defaultImpl;
    protected final String _typePropertyName;
    protected final boolean _typeIdVisible;
    protected final Map<String, JsonDeserializer<Object>> _deserializers;
    protected JsonDeserializer<Object> _defaultImplDeserializer;
    
    protected TypeDeserializerBase(final JavaType baseType, final TypeIdResolver idResolver, final String s, final boolean typeIdVisible, final JavaType defaultImpl) {
        super();
        this._baseType = baseType;
        this._idResolver = idResolver;
        this._typePropertyName = ClassUtil.nonNullString(s);
        this._typeIdVisible = typeIdVisible;
        this._deserializers = new ConcurrentHashMap<String, JsonDeserializer<Object>>(16, 0.75f, 2);
        this._defaultImpl = defaultImpl;
        this._property = null;
    }
    
    protected TypeDeserializerBase(final TypeDeserializerBase typeDeserializerBase, final BeanProperty property) {
        super();
        this._baseType = typeDeserializerBase._baseType;
        this._idResolver = typeDeserializerBase._idResolver;
        this._typePropertyName = typeDeserializerBase._typePropertyName;
        this._typeIdVisible = typeDeserializerBase._typeIdVisible;
        this._deserializers = typeDeserializerBase._deserializers;
        this._defaultImpl = typeDeserializerBase._defaultImpl;
        this._defaultImplDeserializer = typeDeserializerBase._defaultImplDeserializer;
        this._property = property;
    }
    
    @Override
    public abstract TypeDeserializer forProperty(final BeanProperty p0);
    
    @Override
    public abstract JsonTypeInfo.As getTypeInclusion();
    
    public String baseTypeName() {
        return this._baseType.getRawClass().getName();
    }
    
    @Override
    public final String getPropertyName() {
        return this._typePropertyName;
    }
    
    @Override
    public TypeIdResolver getTypeIdResolver() {
        return this._idResolver;
    }
    
    @Override
    public Class<?> getDefaultImpl() {
        return ClassUtil.rawClass(this._defaultImpl);
    }
    
    public JavaType baseType() {
        return this._baseType;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append('[').append(this.getClass().getName());
        sb.append("; base-type:").append(this._baseType);
        sb.append("; id-resolver: ").append(this._idResolver);
        sb.append(']');
        return sb.toString();
    }
    
    protected final JsonDeserializer<Object> _findDeserializer(final DeserializationContext deserializationContext, final String s) throws IOException {
        JsonDeserializer<Object> jsonDeserializer = this._deserializers.get(s);
        if (jsonDeserializer == null) {
            JavaType javaType = this._idResolver.typeFromId(deserializationContext, s);
            if (javaType == null) {
                jsonDeserializer = this._findDefaultImplDeserializer(deserializationContext);
                if (jsonDeserializer == null) {
                    final JavaType handleUnknownTypeId = this._handleUnknownTypeId(deserializationContext, s);
                    if (handleUnknownTypeId == null) {
                        return null;
                    }
                    jsonDeserializer = deserializationContext.findContextualValueDeserializer(handleUnknownTypeId, this._property);
                }
            }
            else {
                if (this._baseType != null && this._baseType.getClass() == javaType.getClass() && !javaType.hasGenericTypes()) {
                    javaType = deserializationContext.getTypeFactory().constructSpecializedType(this._baseType, javaType.getRawClass());
                }
                jsonDeserializer = deserializationContext.findContextualValueDeserializer(javaType, this._property);
            }
            this._deserializers.put(s, jsonDeserializer);
        }
        return jsonDeserializer;
    }
    
    protected final JsonDeserializer<Object> _findDefaultImplDeserializer(final DeserializationContext deserializationContext) throws IOException {
        if (this._defaultImpl == null) {
            if (!deserializationContext.isEnabled(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE)) {
                return NullifyingDeserializer.instance;
            }
            return null;
        }
        else {
            if (ClassUtil.isBogusClass(this._defaultImpl.getRawClass())) {
                return NullifyingDeserializer.instance;
            }
            synchronized (this._defaultImpl) {
                if (this._defaultImplDeserializer == null) {
                    this._defaultImplDeserializer = deserializationContext.findContextualValueDeserializer(this._defaultImpl, this._property);
                }
                return this._defaultImplDeserializer;
            }
        }
    }
    
    @Deprecated
    protected Object _deserializeWithNativeTypeId(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        return this._deserializeWithNativeTypeId(jsonParser, deserializationContext, jsonParser.getTypeId());
    }
    
    protected Object _deserializeWithNativeTypeId(final JsonParser jsonParser, final DeserializationContext deserializationContext, final Object o) throws IOException {
        JsonDeserializer<Object> jsonDeserializer;
        if (o == null) {
            jsonDeserializer = this._findDefaultImplDeserializer(deserializationContext);
            if (jsonDeserializer == null) {
                return deserializationContext.reportInputMismatch(this.baseType(), "No (native) type id found when one was expected for polymorphic type handling", new Object[0]);
            }
        }
        else {
            jsonDeserializer = this._findDeserializer(deserializationContext, (o instanceof String) ? ((String)o) : String.valueOf(o));
        }
        return jsonDeserializer.deserialize(jsonParser, deserializationContext);
    }
    
    protected JavaType _handleUnknownTypeId(final DeserializationContext deserializationContext, final String s) throws IOException {
        final String descForKnownTypeIds = this._idResolver.getDescForKnownTypeIds();
        String s2;
        if (descForKnownTypeIds == null) {
            s2 = "type ids are not statically known";
        }
        else {
            s2 = "known type ids = " + descForKnownTypeIds;
        }
        if (this._property != null) {
            s2 = String.format("%s (for POJO property '%s')", s2, this._property.getName());
        }
        return deserializationContext.handleUnknownTypeId(this._baseType, s, this._idResolver, s2);
    }
    
    protected JavaType _handleMissingTypeId(final DeserializationContext deserializationContext, final String s) throws IOException {
        return deserializationContext.handleMissingTypeId(this._baseType, this._idResolver, s);
    }
}
