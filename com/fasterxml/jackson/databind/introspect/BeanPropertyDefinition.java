package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.annotation.*;
import java.util.*;
import com.fasterxml.jackson.databind.util.*;
import java.io.*;
import com.fasterxml.jackson.databind.*;

public abstract class BeanPropertyDefinition implements Named
{
    protected static final JsonInclude.Value EMPTY_INCLUDE;
    
    public BeanPropertyDefinition() {
        super();
    }
    
    public abstract BeanPropertyDefinition withName(final PropertyName p0);
    
    public abstract BeanPropertyDefinition withSimpleName(final String p0);
    
    @Override
    public abstract String getName();
    
    public abstract PropertyName getFullName();
    
    public boolean hasName(final PropertyName propertyName) {
        return this.getFullName().equals(propertyName);
    }
    
    public abstract String getInternalName();
    
    public abstract PropertyName getWrapperName();
    
    public abstract boolean isExplicitlyIncluded();
    
    public boolean isExplicitlyNamed() {
        return this.isExplicitlyIncluded();
    }
    
    public abstract JavaType getPrimaryType();
    
    public abstract Class<?> getRawPrimaryType();
    
    public abstract PropertyMetadata getMetadata();
    
    public boolean isRequired() {
        return this.getMetadata().isRequired();
    }
    
    public boolean couldDeserialize() {
        return this.getMutator() != null;
    }
    
    public boolean couldSerialize() {
        return this.getAccessor() != null;
    }
    
    public abstract boolean hasGetter();
    
    public abstract boolean hasSetter();
    
    public abstract boolean hasField();
    
    public abstract boolean hasConstructorParameter();
    
    public abstract AnnotatedMethod getGetter();
    
    public abstract AnnotatedMethod getSetter();
    
    public abstract AnnotatedField getField();
    
    public abstract AnnotatedParameter getConstructorParameter();
    
    public Iterator<AnnotatedParameter> getConstructorParameters() {
        return ClassUtil.emptyIterator();
    }
    
    public AnnotatedMember getAccessor() {
        Serializable s = this.getGetter();
        if (s == null) {
            s = this.getField();
        }
        return (AnnotatedMember)s;
    }
    
    public AnnotatedMember getMutator() {
        AnnotatedMember annotatedMember = this.getConstructorParameter();
        if (annotatedMember == null) {
            annotatedMember = this.getSetter();
            if (annotatedMember == null) {
                annotatedMember = this.getField();
            }
        }
        return annotatedMember;
    }
    
    public AnnotatedMember getNonConstructorMutator() {
        Serializable s = this.getSetter();
        if (s == null) {
            s = this.getField();
        }
        return (AnnotatedMember)s;
    }
    
    public abstract AnnotatedMember getPrimaryMember();
    
    public Class<?>[] findViews() {
        return null;
    }
    
    public AnnotationIntrospector.ReferenceProperty findReferenceType() {
        return null;
    }
    
    public String findReferenceName() {
        final AnnotationIntrospector.ReferenceProperty referenceType = this.findReferenceType();
        return (referenceType == null) ? null : referenceType.getName();
    }
    
    public boolean isTypeId() {
        return false;
    }
    
    public ObjectIdInfo findObjectIdInfo() {
        return null;
    }
    
    public abstract JsonInclude.Value findInclusion();
    
    static {
        EMPTY_INCLUDE = JsonInclude.Value.empty();
    }
}
