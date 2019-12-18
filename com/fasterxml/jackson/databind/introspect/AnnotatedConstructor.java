package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.util.*;
import java.lang.reflect.*;
import java.io.*;

public final class AnnotatedConstructor extends AnnotatedWithParams
{
    private static final long serialVersionUID = 1L;
    protected final Constructor<?> _constructor;
    protected Serialization _serialization;
    
    public AnnotatedConstructor(final TypeResolutionContext typeResolutionContext, final Constructor<?> constructor, final AnnotationMap annotationMap, final AnnotationMap[] array) {
        super(typeResolutionContext, annotationMap, array);
        if (constructor == null) {
            throw new IllegalArgumentException("Null constructor not allowed");
        }
        this._constructor = constructor;
    }
    
    protected AnnotatedConstructor(final Serialization serialization) {
        super(null, null, null);
        this._constructor = null;
        this._serialization = serialization;
    }
    
    @Override
    public AnnotatedConstructor withAnnotations(final AnnotationMap annotationMap) {
        return new AnnotatedConstructor(this._typeContext, this._constructor, annotationMap, this._paramAnnotations);
    }
    
    @Override
    public Constructor<?> getAnnotated() {
        return this._constructor;
    }
    
    public int getModifiers() {
        return this._constructor.getModifiers();
    }
    
    @Override
    public String getName() {
        return this._constructor.getName();
    }
    
    @Override
    public JavaType getType() {
        return this._typeContext.resolveType(this.getRawType());
    }
    
    @Override
    public Class<?> getRawType() {
        return this._constructor.getDeclaringClass();
    }
    
    @Override
    public int getParameterCount() {
        return this._constructor.getParameterTypes().length;
    }
    
    @Override
    public Class<?> getRawParameterType(final int n) {
        final Class<?>[] parameterTypes = this._constructor.getParameterTypes();
        return (n >= parameterTypes.length) ? null : parameterTypes[n];
    }
    
    @Override
    public JavaType getParameterType(final int n) {
        final Type[] genericParameterTypes = this._constructor.getGenericParameterTypes();
        if (n >= genericParameterTypes.length) {
            return null;
        }
        return this._typeContext.resolveType(genericParameterTypes[n]);
    }
    
    @Deprecated
    @Override
    public Type getGenericParameterType(final int n) {
        final Type[] genericParameterTypes = this._constructor.getGenericParameterTypes();
        if (n >= genericParameterTypes.length) {
            return null;
        }
        return genericParameterTypes[n];
    }
    
    @Override
    public final Object call() throws Exception {
        return this._constructor.newInstance(new Object[0]);
    }
    
    @Override
    public final Object call(final Object[] array) throws Exception {
        return this._constructor.newInstance(array);
    }
    
    @Override
    public final Object call1(final Object o) throws Exception {
        return this._constructor.newInstance(o);
    }
    
    @Override
    public Class<?> getDeclaringClass() {
        return this._constructor.getDeclaringClass();
    }
    
    @Override
    public Member getMember() {
        return this._constructor;
    }
    
    @Override
    public void setValue(final Object o, final Object o2) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Cannot call setValue() on constructor of " + this.getDeclaringClass().getName());
    }
    
    @Override
    public Object getValue(final Object o) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Cannot call getValue() on constructor of " + this.getDeclaringClass().getName());
    }
    
    @Override
    public String toString() {
        return "[constructor for " + this.getName() + ", annotations: " + this._annotations + "]";
    }
    
    @Override
    public int hashCode() {
        return this._constructor.getName().hashCode();
    }
    
    @Override
    public boolean equals(final Object o) {
        return o == this || (ClassUtil.hasClass(o, this.getClass()) && ((AnnotatedConstructor)o)._constructor == this._constructor);
    }
    
    Object writeReplace() {
        return new AnnotatedConstructor(new Serialization(this._constructor));
    }
    
    Object readResolve() {
        final Class<?> clazz = this._serialization.clazz;
        try {
            final Constructor<?> declaredConstructor = clazz.getDeclaredConstructor(this._serialization.args);
            if (!declaredConstructor.isAccessible()) {
                ClassUtil.checkAndFixAccess(declaredConstructor, false);
            }
            return new AnnotatedConstructor(null, declaredConstructor, null, null);
        }
        catch (Exception ex) {
            throw new IllegalArgumentException("Could not find constructor with " + this._serialization.args.length + " args from Class '" + clazz.getName());
        }
    }
    
    @Override
    public Annotated withAnnotations(final AnnotationMap annotationMap) {
        return this.withAnnotations(annotationMap);
    }
    
    @Override
    public AnnotatedElement getAnnotated() {
        return this.getAnnotated();
    }
    
    private static final class Serialization implements Serializable
    {
        private static final long serialVersionUID = 1L;
        protected Class<?> clazz;
        protected Class<?>[] args;
        
        public Serialization(final Constructor<?> constructor) {
            super();
            this.clazz = constructor.getDeclaringClass();
            this.args = (Class<?>[])constructor.getParameterTypes();
        }
    }
}
