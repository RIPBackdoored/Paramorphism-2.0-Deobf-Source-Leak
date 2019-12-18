package com.fasterxml.jackson.databind.jsontype.impl;

import com.fasterxml.jackson.annotation.*;
import java.util.*;
import com.fasterxml.jackson.databind.cfg.*;
import com.fasterxml.jackson.databind.jsontype.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.*;
import java.lang.reflect.*;

public class StdTypeResolverBuilder implements TypeResolverBuilder<StdTypeResolverBuilder>
{
    protected JsonTypeInfo.Id _idType;
    protected JsonTypeInfo.As _includeAs;
    protected String _typeProperty;
    protected boolean _typeIdVisible;
    protected Class<?> _defaultImpl;
    protected TypeIdResolver _customIdResolver;
    
    public StdTypeResolverBuilder() {
        super();
        this._typeIdVisible = false;
    }
    
    protected StdTypeResolverBuilder(final JsonTypeInfo.Id idType, final JsonTypeInfo.As includeAs, final String typeProperty) {
        super();
        this._typeIdVisible = false;
        this._idType = idType;
        this._includeAs = includeAs;
        this._typeProperty = typeProperty;
    }
    
    public static StdTypeResolverBuilder noTypeInfoBuilder() {
        return new StdTypeResolverBuilder().init(JsonTypeInfo.Id.NONE, (TypeIdResolver)null);
    }
    
    @Override
    public StdTypeResolverBuilder init(final JsonTypeInfo.Id idType, final TypeIdResolver customIdResolver) {
        if (idType == null) {
            throw new IllegalArgumentException("idType cannot be null");
        }
        this._idType = idType;
        this._customIdResolver = customIdResolver;
        this._typeProperty = idType.getDefaultPropertyName();
        return this;
    }
    
    @Override
    public TypeSerializer buildTypeSerializer(final SerializationConfig serializationConfig, final JavaType javaType, final Collection<NamedType> collection) {
        if (this._idType == JsonTypeInfo.Id.NONE) {
            return null;
        }
        if (javaType.isPrimitive()) {
            return null;
        }
        final TypeIdResolver idResolver = this.idResolver(serializationConfig, javaType, collection, true, false);
        switch (this._includeAs) {
            case WRAPPER_ARRAY: {
                return new AsArrayTypeSerializer(idResolver, null);
            }
            case PROPERTY: {
                return new AsPropertyTypeSerializer(idResolver, null, this._typeProperty);
            }
            case WRAPPER_OBJECT: {
                return new AsWrapperTypeSerializer(idResolver, null);
            }
            case EXTERNAL_PROPERTY: {
                return new AsExternalTypeSerializer(idResolver, null, this._typeProperty);
            }
            case EXISTING_PROPERTY: {
                return new AsExistingPropertyTypeSerializer(idResolver, null, this._typeProperty);
            }
            default: {
                throw new IllegalStateException("Do not know how to construct standard type serializer for inclusion type: " + this._includeAs);
            }
        }
    }
    
    @Override
    public TypeDeserializer buildTypeDeserializer(final DeserializationConfig deserializationConfig, final JavaType javaType, final Collection<NamedType> collection) {
        if (this._idType == JsonTypeInfo.Id.NONE) {
            return null;
        }
        if (javaType.isPrimitive()) {
            return null;
        }
        final TypeIdResolver idResolver = this.idResolver(deserializationConfig, javaType, collection, false, true);
        final JavaType defineDefaultImpl = this.defineDefaultImpl(deserializationConfig, javaType);
        switch (this._includeAs) {
            case WRAPPER_ARRAY: {
                return new AsArrayTypeDeserializer(javaType, idResolver, this._typeProperty, this._typeIdVisible, defineDefaultImpl);
            }
            case PROPERTY:
            case EXISTING_PROPERTY: {
                return new AsPropertyTypeDeserializer(javaType, idResolver, this._typeProperty, this._typeIdVisible, defineDefaultImpl, this._includeAs);
            }
            case WRAPPER_OBJECT: {
                return new AsWrapperTypeDeserializer(javaType, idResolver, this._typeProperty, this._typeIdVisible, defineDefaultImpl);
            }
            case EXTERNAL_PROPERTY: {
                return new AsExternalTypeDeserializer(javaType, idResolver, this._typeProperty, this._typeIdVisible, defineDefaultImpl);
            }
            default: {
                throw new IllegalStateException("Do not know how to construct standard type serializer for inclusion type: " + this._includeAs);
            }
        }
    }
    
    protected JavaType defineDefaultImpl(final DeserializationConfig deserializationConfig, final JavaType javaType) {
        JavaType javaType2;
        if (this._defaultImpl == null) {
            if (deserializationConfig.isEnabled(MapperFeature.USE_BASE_TYPE_AS_DEFAULT_IMPL) && !javaType.isAbstract()) {
                javaType2 = javaType;
            }
            else {
                javaType2 = null;
            }
        }
        else if (this._defaultImpl == Void.class || this._defaultImpl == NoClass.class) {
            javaType2 = deserializationConfig.getTypeFactory().constructType(this._defaultImpl);
        }
        else if (javaType.hasRawClass(this._defaultImpl)) {
            javaType2 = javaType;
        }
        else if (javaType.isTypeOrSuperTypeOf(this._defaultImpl)) {
            javaType2 = deserializationConfig.getTypeFactory().constructSpecializedType(javaType, this._defaultImpl);
        }
        else {
            javaType2 = null;
        }
        return javaType2;
    }
    
    @Override
    public StdTypeResolverBuilder inclusion(final JsonTypeInfo.As includeAs) {
        if (includeAs == null) {
            throw new IllegalArgumentException("includeAs cannot be null");
        }
        this._includeAs = includeAs;
        return this;
    }
    
    @Override
    public StdTypeResolverBuilder typeProperty(String defaultPropertyName) {
        if (defaultPropertyName == null || defaultPropertyName.length() == 0) {
            defaultPropertyName = this._idType.getDefaultPropertyName();
        }
        this._typeProperty = defaultPropertyName;
        return this;
    }
    
    @Override
    public StdTypeResolverBuilder defaultImpl(final Class<?> defaultImpl) {
        this._defaultImpl = defaultImpl;
        return this;
    }
    
    @Override
    public StdTypeResolverBuilder typeIdVisibility(final boolean typeIdVisible) {
        this._typeIdVisible = typeIdVisible;
        return this;
    }
    
    @Override
    public Class<?> getDefaultImpl() {
        return this._defaultImpl;
    }
    
    public String getTypeProperty() {
        return this._typeProperty;
    }
    
    public boolean isTypeIdVisible() {
        return this._typeIdVisible;
    }
    
    protected TypeIdResolver idResolver(final MapperConfig<?> mapperConfig, final JavaType javaType, final Collection<NamedType> collection, final boolean b, final boolean b2) {
        if (this._customIdResolver != null) {
            return this._customIdResolver;
        }
        if (this._idType == null) {
            throw new IllegalStateException("Cannot build, 'init()' not yet called");
        }
        switch (this._idType) {
            case CLASS: {
                return new ClassNameIdResolver(javaType, mapperConfig.getTypeFactory());
            }
            case MINIMAL_CLASS: {
                return new MinimalClassNameIdResolver(javaType, mapperConfig.getTypeFactory());
            }
            case NAME: {
                return TypeNameIdResolver.construct(mapperConfig, javaType, collection, b, b2);
            }
            case NONE: {
                return null;
            }
            default: {
                throw new IllegalStateException("Do not know how to construct standard type id resolver for idType: " + this._idType);
            }
        }
    }
    
    @Override
    public TypeResolverBuilder typeIdVisibility(final boolean b) {
        return this.typeIdVisibility(b);
    }
    
    @Override
    public TypeResolverBuilder defaultImpl(final Class clazz) {
        return this.defaultImpl((Class<?>)clazz);
    }
    
    @Override
    public TypeResolverBuilder typeProperty(final String s) {
        return this.typeProperty(s);
    }
    
    @Override
    public TypeResolverBuilder inclusion(final JsonTypeInfo.As as) {
        return this.inclusion(as);
    }
    
    @Override
    public TypeResolverBuilder init(final JsonTypeInfo.Id id, final TypeIdResolver typeIdResolver) {
        return this.init(id, typeIdResolver);
    }
}
