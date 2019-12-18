package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.core.type.*;
import java.io.*;
import java.lang.reflect.*;
import com.fasterxml.jackson.databind.type.*;
import java.util.*;

public abstract class JavaType extends ResolvedType implements Serializable, Type
{
    private static final long serialVersionUID = 1L;
    protected final Class<?> _class;
    protected final int _hash;
    protected final Object _valueHandler;
    protected final Object _typeHandler;
    protected final boolean _asStatic;
    
    protected JavaType(final Class<?> class1, final int n, final Object valueHandler, final Object typeHandler, final boolean asStatic) {
        super();
        this._class = class1;
        this._hash = class1.getName().hashCode() + n;
        this._valueHandler = valueHandler;
        this._typeHandler = typeHandler;
        this._asStatic = asStatic;
    }
    
    protected JavaType(final JavaType javaType) {
        super();
        this._class = javaType._class;
        this._hash = javaType._hash;
        this._valueHandler = javaType._valueHandler;
        this._typeHandler = javaType._typeHandler;
        this._asStatic = javaType._asStatic;
    }
    
    public abstract JavaType withTypeHandler(final Object p0);
    
    public abstract JavaType withContentTypeHandler(final Object p0);
    
    public abstract JavaType withValueHandler(final Object p0);
    
    public abstract JavaType withContentValueHandler(final Object p0);
    
    public JavaType withHandlersFrom(final JavaType javaType) {
        JavaType javaType2 = this;
        final Object typeHandler = javaType.getTypeHandler();
        if (typeHandler != this._typeHandler) {
            javaType2 = javaType2.withTypeHandler(typeHandler);
        }
        final Object valueHandler = javaType.getValueHandler();
        if (valueHandler != this._valueHandler) {
            javaType2 = javaType2.withValueHandler(valueHandler);
        }
        return javaType2;
    }
    
    public abstract JavaType withContentType(final JavaType p0);
    
    public abstract JavaType withStaticTyping();
    
    public abstract JavaType refine(final Class<?> p0, final TypeBindings p1, final JavaType p2, final JavaType[] p3);
    
    @Deprecated
    public JavaType forcedNarrowBy(final Class<?> clazz) {
        if (clazz == this._class) {
            return this;
        }
        return this._narrow(clazz);
    }
    
    @Deprecated
    protected abstract JavaType _narrow(final Class<?> p0);
    
    @Override
    public final Class<?> getRawClass() {
        return this._class;
    }
    
    @Override
    public final boolean hasRawClass(final Class<?> clazz) {
        return this._class == clazz;
    }
    
    public boolean hasContentType() {
        return true;
    }
    
    public final boolean isTypeOrSubTypeOf(final Class<?> clazz) {
        return this._class == clazz || clazz.isAssignableFrom(this._class);
    }
    
    public final boolean isTypeOrSuperTypeOf(final Class<?> clazz) {
        return this._class == clazz || this._class.isAssignableFrom(clazz);
    }
    
    @Override
    public boolean isAbstract() {
        return Modifier.isAbstract(this._class.getModifiers());
    }
    
    @Override
    public boolean isConcrete() {
        return (this._class.getModifiers() & 0x600) == 0x0 || this._class.isPrimitive();
    }
    
    @Override
    public boolean isThrowable() {
        return Throwable.class.isAssignableFrom(this._class);
    }
    
    @Override
    public boolean isArrayType() {
        return false;
    }
    
    @Override
    public final boolean isEnumType() {
        return this._class.isEnum();
    }
    
    @Override
    public final boolean isInterface() {
        return this._class.isInterface();
    }
    
    @Override
    public final boolean isPrimitive() {
        return this._class.isPrimitive();
    }
    
    @Override
    public final boolean isFinal() {
        return Modifier.isFinal(this._class.getModifiers());
    }
    
    @Override
    public abstract boolean isContainerType();
    
    @Override
    public boolean isCollectionLikeType() {
        return false;
    }
    
    @Override
    public boolean isMapLikeType() {
        return false;
    }
    
    public final boolean isJavaLangObject() {
        return this._class == Object.class;
    }
    
    public final boolean useStaticType() {
        return this._asStatic;
    }
    
    @Override
    public boolean hasGenericTypes() {
        return this.containedTypeCount() > 0;
    }
    
    @Override
    public JavaType getKeyType() {
        return null;
    }
    
    @Override
    public JavaType getContentType() {
        return null;
    }
    
    @Override
    public JavaType getReferencedType() {
        return null;
    }
    
    @Override
    public abstract int containedTypeCount();
    
    @Override
    public abstract JavaType containedType(final int p0);
    
    @Deprecated
    @Override
    public abstract String containedTypeName(final int p0);
    
    @Deprecated
    @Override
    public Class<?> getParameterSource() {
        return null;
    }
    
    public JavaType containedTypeOrUnknown(final int n) {
        final JavaType containedType = this.containedType(n);
        return (containedType == null) ? TypeFactory.unknownType() : containedType;
    }
    
    public abstract TypeBindings getBindings();
    
    public abstract JavaType findSuperType(final Class<?> p0);
    
    public abstract JavaType getSuperClass();
    
    public abstract List<JavaType> getInterfaces();
    
    public abstract JavaType[] findTypeParameters(final Class<?> p0);
    
    public <T> T getValueHandler() {
        return (T)this._valueHandler;
    }
    
    public <T> T getTypeHandler() {
        return (T)this._typeHandler;
    }
    
    public Object getContentValueHandler() {
        return null;
    }
    
    public Object getContentTypeHandler() {
        return null;
    }
    
    public boolean hasValueHandler() {
        return this._valueHandler != null;
    }
    
    public boolean hasHandlers() {
        return this._typeHandler != null || this._valueHandler != null;
    }
    
    public String getGenericSignature() {
        final StringBuilder sb = new StringBuilder(40);
        this.getGenericSignature(sb);
        return sb.toString();
    }
    
    public abstract StringBuilder getGenericSignature(final StringBuilder p0);
    
    public String getErasedSignature() {
        final StringBuilder sb = new StringBuilder(40);
        this.getErasedSignature(sb);
        return sb.toString();
    }
    
    public abstract StringBuilder getErasedSignature(final StringBuilder p0);
    
    @Override
    public abstract String toString();
    
    @Override
    public abstract boolean equals(final Object p0);
    
    @Override
    public final int hashCode() {
        return this._hash;
    }
    
    @Override
    public ResolvedType containedType(final int n) {
        return this.containedType(n);
    }
    
    @Override
    public ResolvedType getReferencedType() {
        return this.getReferencedType();
    }
    
    @Override
    public ResolvedType getContentType() {
        return this.getContentType();
    }
    
    @Override
    public ResolvedType getKeyType() {
        return this.getKeyType();
    }
}
