package com.fasterxml.jackson.databind.cfg;

import java.io.*;
import com.fasterxml.jackson.core.io.*;
import com.fasterxml.jackson.databind.type.*;
import java.lang.reflect.*;
import com.fasterxml.jackson.core.type.*;
import com.fasterxml.jackson.annotation.*;
import java.text.*;
import java.util.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.introspect.*;
import com.fasterxml.jackson.databind.util.*;
import com.fasterxml.jackson.databind.jsontype.*;

public abstract class MapperConfig<T extends MapperConfig<T>> implements ClassIntrospector.MixInResolver, Serializable
{
    private static final long serialVersionUID = 2L;
    protected static final JsonInclude.Value EMPTY_INCLUDE;
    protected static final JsonFormat.Value EMPTY_FORMAT;
    protected final int _mapperFeatures;
    protected final BaseSettings _base;
    
    protected MapperConfig(final BaseSettings base, final int mapperFeatures) {
        super();
        this._base = base;
        this._mapperFeatures = mapperFeatures;
    }
    
    protected MapperConfig(final MapperConfig<T> mapperConfig, final int mapperFeatures) {
        super();
        this._base = mapperConfig._base;
        this._mapperFeatures = mapperFeatures;
    }
    
    protected MapperConfig(final MapperConfig<T> mapperConfig, final BaseSettings base) {
        super();
        this._base = base;
        this._mapperFeatures = mapperConfig._mapperFeatures;
    }
    
    protected MapperConfig(final MapperConfig<T> mapperConfig) {
        super();
        this._base = mapperConfig._base;
        this._mapperFeatures = mapperConfig._mapperFeatures;
    }
    
    public static <F extends Enum> int collectFeatureDefaults(final Class<F> clazz) {
        int n = 0;
        for (final java.lang.Enum enum1 : (java.lang.Enum[])clazz.getEnumConstants()) {
            if (((ConfigFeature)enum1).enabledByDefault()) {
                n |= ((ConfigFeature)enum1).getMask();
            }
        }
        return n;
    }
    
    public abstract T with(final MapperFeature... p0);
    
    public abstract T without(final MapperFeature... p0);
    
    public abstract T with(final MapperFeature p0, final boolean p1);
    
    public final boolean isEnabled(final MapperFeature mapperFeature) {
        return (this._mapperFeatures & mapperFeature.getMask()) != 0x0;
    }
    
    public final boolean hasMapperFeatures(final int n) {
        return (this._mapperFeatures & n) == n;
    }
    
    public final boolean isAnnotationProcessingEnabled() {
        return this.isEnabled(MapperFeature.USE_ANNOTATIONS);
    }
    
    public final boolean canOverrideAccessModifiers() {
        return this.isEnabled(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS);
    }
    
    public final boolean shouldSortPropertiesAlphabetically() {
        return this.isEnabled(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY);
    }
    
    public abstract boolean useRootWrapping();
    
    public SerializableString compileString(final String s) {
        return new SerializedString(s);
    }
    
    public ClassIntrospector getClassIntrospector() {
        return this._base.getClassIntrospector();
    }
    
    public AnnotationIntrospector getAnnotationIntrospector() {
        if (this.isEnabled(MapperFeature.USE_ANNOTATIONS)) {
            return this._base.getAnnotationIntrospector();
        }
        return NopAnnotationIntrospector.instance;
    }
    
    public final PropertyNamingStrategy getPropertyNamingStrategy() {
        return this._base.getPropertyNamingStrategy();
    }
    
    public final HandlerInstantiator getHandlerInstantiator() {
        return this._base.getHandlerInstantiator();
    }
    
    public final TypeResolverBuilder<?> getDefaultTyper(final JavaType javaType) {
        return this._base.getTypeResolverBuilder();
    }
    
    public abstract SubtypeResolver getSubtypeResolver();
    
    public final TypeFactory getTypeFactory() {
        return this._base.getTypeFactory();
    }
    
    public final JavaType constructType(final Class<?> clazz) {
        return this.getTypeFactory().constructType(clazz);
    }
    
    public final JavaType constructType(final TypeReference<?> typeReference) {
        return this.getTypeFactory().constructType(typeReference.getType());
    }
    
    public JavaType constructSpecializedType(final JavaType javaType, final Class<?> clazz) {
        return this.getTypeFactory().constructSpecializedType(javaType, clazz);
    }
    
    public BeanDescription introspectClassAnnotations(final Class<?> clazz) {
        return this.introspectClassAnnotations(this.constructType(clazz));
    }
    
    public BeanDescription introspectClassAnnotations(final JavaType javaType) {
        return this.getClassIntrospector().forClassAnnotations(this, javaType, this);
    }
    
    public BeanDescription introspectDirectClassAnnotations(final Class<?> clazz) {
        return this.introspectDirectClassAnnotations(this.constructType(clazz));
    }
    
    public final BeanDescription introspectDirectClassAnnotations(final JavaType javaType) {
        return this.getClassIntrospector().forDirectClassAnnotations(this, javaType, this);
    }
    
    public abstract ConfigOverride findConfigOverride(final Class<?> p0);
    
    public abstract ConfigOverride getConfigOverride(final Class<?> p0);
    
    public abstract JsonInclude.Value getDefaultPropertyInclusion();
    
    public abstract JsonInclude.Value getDefaultPropertyInclusion(final Class<?> p0);
    
    public JsonInclude.Value getDefaultPropertyInclusion(final Class<?> clazz, final JsonInclude.Value value) {
        final JsonInclude.Value include = this.getConfigOverride(clazz).getInclude();
        if (include != null) {
            return include;
        }
        return value;
    }
    
    public abstract JsonInclude.Value getDefaultInclusion(final Class<?> p0, final Class<?> p1);
    
    public JsonInclude.Value getDefaultInclusion(final Class<?> clazz, final Class<?> clazz2, final JsonInclude.Value value) {
        return JsonInclude.Value.mergeAll(value, this.getConfigOverride(clazz).getInclude(), this.getConfigOverride(clazz2).getIncludeAsProperty());
    }
    
    public abstract JsonFormat.Value getDefaultPropertyFormat(final Class<?> p0);
    
    public abstract JsonIgnoreProperties.Value getDefaultPropertyIgnorals(final Class<?> p0);
    
    public abstract JsonIgnoreProperties.Value getDefaultPropertyIgnorals(final Class<?> p0, final AnnotatedClass p1);
    
    public abstract VisibilityChecker<?> getDefaultVisibilityChecker();
    
    public abstract VisibilityChecker<?> getDefaultVisibilityChecker(final Class<?> p0, final AnnotatedClass p1);
    
    public abstract JsonSetter.Value getDefaultSetterInfo();
    
    public abstract Boolean getDefaultMergeable();
    
    public abstract Boolean getDefaultMergeable(final Class<?> p0);
    
    public final DateFormat getDateFormat() {
        return this._base.getDateFormat();
    }
    
    public final Locale getLocale() {
        return this._base.getLocale();
    }
    
    public final TimeZone getTimeZone() {
        return this._base.getTimeZone();
    }
    
    public abstract Class<?> getActiveView();
    
    public Base64Variant getBase64Variant() {
        return this._base.getBase64Variant();
    }
    
    public abstract ContextAttributes getAttributes();
    
    public abstract PropertyName findRootName(final JavaType p0);
    
    public abstract PropertyName findRootName(final Class<?> p0);
    
    public TypeResolverBuilder<?> typeResolverBuilderInstance(final Annotated annotated, final Class<? extends TypeResolverBuilder<?>> clazz) {
        final HandlerInstantiator handlerInstantiator = this.getHandlerInstantiator();
        if (handlerInstantiator != null) {
            final TypeResolverBuilder<?> typeResolverBuilderInstance = handlerInstantiator.typeResolverBuilderInstance(this, annotated, clazz);
            if (typeResolverBuilderInstance != null) {
                return typeResolverBuilderInstance;
            }
        }
        return ClassUtil.createInstance(clazz, this.canOverrideAccessModifiers());
    }
    
    public TypeIdResolver typeIdResolverInstance(final Annotated annotated, final Class<? extends TypeIdResolver> clazz) {
        final HandlerInstantiator handlerInstantiator = this.getHandlerInstantiator();
        if (handlerInstantiator != null) {
            final TypeIdResolver typeIdResolverInstance = handlerInstantiator.typeIdResolverInstance(this, annotated, clazz);
            if (typeIdResolverInstance != null) {
                return typeIdResolverInstance;
            }
        }
        return ClassUtil.createInstance(clazz, this.canOverrideAccessModifiers());
    }
    
    static {
        EMPTY_INCLUDE = JsonInclude.Value.empty();
        EMPTY_FORMAT = JsonFormat.Value.empty();
    }
}
