package com.fasterxml.jackson.databind;

import java.io.*;
import com.fasterxml.jackson.databind.util.*;
import java.lang.annotation.*;
import com.fasterxml.jackson.databind.introspect.*;
import com.fasterxml.jackson.databind.cfg.*;
import com.fasterxml.jackson.annotation.*;
import java.util.*;
import com.fasterxml.jackson.databind.jsonFormatVisitors.*;

public static class Std implements BeanProperty, Serializable
{
    private static final long serialVersionUID = 1L;
    protected final PropertyName _name;
    protected final JavaType _type;
    protected final PropertyName _wrapperName;
    protected final PropertyMetadata _metadata;
    protected final AnnotatedMember _member;
    
    public Std(final PropertyName name, final JavaType type, final PropertyName wrapperName, final AnnotatedMember member, final PropertyMetadata metadata) {
        super();
        this._name = name;
        this._type = type;
        this._wrapperName = wrapperName;
        this._metadata = metadata;
        this._member = member;
    }
    
    @Deprecated
    public Std(final PropertyName propertyName, final JavaType javaType, final PropertyName propertyName2, final Annotations annotations, final AnnotatedMember annotatedMember, final PropertyMetadata propertyMetadata) {
        this(propertyName, javaType, propertyName2, annotatedMember, propertyMetadata);
    }
    
    public Std(final Std std, final JavaType javaType) {
        this(std._name, javaType, std._wrapperName, std._member, std._metadata);
    }
    
    public Std withType(final JavaType javaType) {
        return new Std(this, javaType);
    }
    
    @Override
    public <A extends Annotation> A getAnnotation(final Class<A> clazz) {
        return (A)((this._member == null) ? null : this._member.getAnnotation(clazz));
    }
    
    @Override
    public <A extends Annotation> A getContextAnnotation(final Class<A> clazz) {
        return null;
    }
    
    @Deprecated
    @Override
    public JsonFormat.Value findFormatOverrides(final AnnotationIntrospector annotationIntrospector) {
        if (this._member != null && annotationIntrospector != null) {
            final JsonFormat.Value format = annotationIntrospector.findFormat(this._member);
            if (format != null) {
                return format;
            }
        }
        return Std.EMPTY_FORMAT;
    }
    
    @Override
    public JsonFormat.Value findPropertyFormat(final MapperConfig<?> mapperConfig, final Class<?> clazz) {
        final JsonFormat.Value defaultPropertyFormat = mapperConfig.getDefaultPropertyFormat(clazz);
        final AnnotationIntrospector annotationIntrospector = mapperConfig.getAnnotationIntrospector();
        if (annotationIntrospector == null || this._member == null) {
            return defaultPropertyFormat;
        }
        final JsonFormat.Value format = annotationIntrospector.findFormat(this._member);
        if (format == null) {
            return defaultPropertyFormat;
        }
        return defaultPropertyFormat.withOverrides(format);
    }
    
    @Override
    public JsonInclude.Value findPropertyInclusion(final MapperConfig<?> mapperConfig, final Class<?> clazz) {
        final JsonInclude.Value defaultInclusion = mapperConfig.getDefaultInclusion(clazz, this._type.getRawClass());
        final AnnotationIntrospector annotationIntrospector = mapperConfig.getAnnotationIntrospector();
        if (annotationIntrospector == null || this._member == null) {
            return defaultInclusion;
        }
        final JsonInclude.Value propertyInclusion = annotationIntrospector.findPropertyInclusion(this._member);
        if (propertyInclusion == null) {
            return defaultInclusion;
        }
        return defaultInclusion.withOverrides(propertyInclusion);
    }
    
    @Override
    public List<PropertyName> findAliases(final MapperConfig<?> mapperConfig) {
        return Collections.emptyList();
    }
    
    @Override
    public String getName() {
        return this._name.getSimpleName();
    }
    
    @Override
    public PropertyName getFullName() {
        return this._name;
    }
    
    @Override
    public JavaType getType() {
        return this._type;
    }
    
    @Override
    public PropertyName getWrapperName() {
        return this._wrapperName;
    }
    
    @Override
    public boolean isRequired() {
        return this._metadata.isRequired();
    }
    
    @Override
    public PropertyMetadata getMetadata() {
        return this._metadata;
    }
    
    @Override
    public AnnotatedMember getMember() {
        return this._member;
    }
    
    @Override
    public boolean isVirtual() {
        return false;
    }
    
    @Override
    public void depositSchemaProperty(final JsonObjectFormatVisitor jsonObjectFormatVisitor, final SerializerProvider serializerProvider) {
        throw new UnsupportedOperationException("Instances of " + this.getClass().getName() + " should not get visited");
    }
}
