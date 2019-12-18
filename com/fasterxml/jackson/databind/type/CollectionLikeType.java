package com.fasterxml.jackson.databind.type;

import com.fasterxml.jackson.databind.*;
import java.lang.reflect.*;
import java.util.*;
import com.fasterxml.jackson.core.type.*;

public class CollectionLikeType extends TypeBase
{
    private static final long serialVersionUID = 1L;
    protected final JavaType _elementType;
    
    protected CollectionLikeType(final Class<?> clazz, final TypeBindings typeBindings, final JavaType javaType, final JavaType[] array, final JavaType elementType, final Object o, final Object o2, final boolean b) {
        super(clazz, typeBindings, javaType, array, elementType.hashCode(), o, o2, b);
        this._elementType = elementType;
    }
    
    protected CollectionLikeType(final TypeBase typeBase, final JavaType elementType) {
        super(typeBase);
        this._elementType = elementType;
    }
    
    public static CollectionLikeType construct(final Class<?> clazz, final TypeBindings typeBindings, final JavaType javaType, final JavaType[] array, final JavaType javaType2) {
        return new CollectionLikeType(clazz, typeBindings, javaType, array, javaType2, null, null, false);
    }
    
    @Deprecated
    public static CollectionLikeType construct(final Class<?> clazz, final JavaType javaType) {
        final TypeVariable<Class<?>>[] typeParameters = clazz.getTypeParameters();
        TypeBindings typeBindings;
        if (typeParameters == null || typeParameters.length != 1) {
            typeBindings = TypeBindings.emptyBindings();
        }
        else {
            typeBindings = TypeBindings.create(clazz, javaType);
        }
        return new CollectionLikeType(clazz, typeBindings, TypeBase._bogusSuperClass(clazz), null, javaType, null, null, false);
    }
    
    public static CollectionLikeType upgradeFrom(final JavaType javaType, final JavaType javaType2) {
        if (javaType instanceof TypeBase) {
            return new CollectionLikeType((TypeBase)javaType, javaType2);
        }
        throw new IllegalArgumentException("Cannot upgrade from an instance of " + javaType.getClass());
    }
    
    @Deprecated
    @Override
    protected JavaType _narrow(final Class<?> clazz) {
        return new CollectionLikeType(clazz, this._bindings, this._superClass, this._superInterfaces, this._elementType, this._valueHandler, this._typeHandler, this._asStatic);
    }
    
    @Override
    public JavaType withContentType(final JavaType javaType) {
        if (this._elementType == javaType) {
            return this;
        }
        return new CollectionLikeType(this._class, this._bindings, this._superClass, this._superInterfaces, javaType, this._valueHandler, this._typeHandler, this._asStatic);
    }
    
    @Override
    public CollectionLikeType withTypeHandler(final Object o) {
        return new CollectionLikeType(this._class, this._bindings, this._superClass, this._superInterfaces, this._elementType, this._valueHandler, o, this._asStatic);
    }
    
    @Override
    public CollectionLikeType withContentTypeHandler(final Object o) {
        return new CollectionLikeType(this._class, this._bindings, this._superClass, this._superInterfaces, this._elementType.withTypeHandler(o), this._valueHandler, this._typeHandler, this._asStatic);
    }
    
    @Override
    public CollectionLikeType withValueHandler(final Object o) {
        return new CollectionLikeType(this._class, this._bindings, this._superClass, this._superInterfaces, this._elementType, o, this._typeHandler, this._asStatic);
    }
    
    @Override
    public CollectionLikeType withContentValueHandler(final Object o) {
        return new CollectionLikeType(this._class, this._bindings, this._superClass, this._superInterfaces, this._elementType.withValueHandler(o), this._valueHandler, this._typeHandler, this._asStatic);
    }
    
    @Override
    public JavaType withHandlersFrom(final JavaType javaType) {
        JavaType javaType2 = super.withHandlersFrom(javaType);
        final JavaType contentType = javaType.getContentType();
        if (contentType != null) {
            final JavaType withHandlers = this._elementType.withHandlersFrom(contentType);
            if (withHandlers != this._elementType) {
                javaType2 = javaType2.withContentType(withHandlers);
            }
        }
        return javaType2;
    }
    
    @Override
    public CollectionLikeType withStaticTyping() {
        if (this._asStatic) {
            return this;
        }
        return new CollectionLikeType(this._class, this._bindings, this._superClass, this._superInterfaces, this._elementType.withStaticTyping(), this._valueHandler, this._typeHandler, true);
    }
    
    @Override
    public JavaType refine(final Class<?> clazz, final TypeBindings typeBindings, final JavaType javaType, final JavaType[] array) {
        return new CollectionLikeType(clazz, typeBindings, javaType, array, this._elementType, this._valueHandler, this._typeHandler, this._asStatic);
    }
    
    @Override
    public boolean isContainerType() {
        return true;
    }
    
    @Override
    public boolean isCollectionLikeType() {
        return true;
    }
    
    @Override
    public JavaType getContentType() {
        return this._elementType;
    }
    
    @Override
    public Object getContentValueHandler() {
        return this._elementType.getValueHandler();
    }
    
    @Override
    public Object getContentTypeHandler() {
        return this._elementType.getTypeHandler();
    }
    
    @Override
    public boolean hasHandlers() {
        return super.hasHandlers() || this._elementType.hasHandlers();
    }
    
    @Override
    public StringBuilder getErasedSignature(final StringBuilder sb) {
        return TypeBase._classSignature(this._class, sb, true);
    }
    
    @Override
    public StringBuilder getGenericSignature(final StringBuilder sb) {
        TypeBase._classSignature(this._class, sb, false);
        sb.append('<');
        this._elementType.getGenericSignature(sb);
        sb.append(">;");
        return sb;
    }
    
    @Override
    protected String buildCanonicalName() {
        final StringBuilder sb = new StringBuilder();
        sb.append(this._class.getName());
        if (this._elementType != null) {
            sb.append('<');
            sb.append(this._elementType.toCanonical());
            sb.append('>');
        }
        return sb.toString();
    }
    
    public boolean isTrueCollectionType() {
        return Collection.class.isAssignableFrom(this._class);
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (o.getClass() != this.getClass()) {
            return false;
        }
        final CollectionLikeType collectionLikeType = (CollectionLikeType)o;
        return this._class == collectionLikeType._class && this._elementType.equals(collectionLikeType._elementType);
    }
    
    @Override
    public String toString() {
        return "[collection-like type; class " + this._class.getName() + ", contains " + this._elementType + "]";
    }
    
    @Override
    public JavaType withStaticTyping() {
        return this.withStaticTyping();
    }
    
    @Override
    public JavaType withContentValueHandler(final Object o) {
        return this.withContentValueHandler(o);
    }
    
    @Override
    public JavaType withValueHandler(final Object o) {
        return this.withValueHandler(o);
    }
    
    @Override
    public JavaType withContentTypeHandler(final Object o) {
        return this.withContentTypeHandler(o);
    }
    
    @Override
    public JavaType withTypeHandler(final Object o) {
        return this.withTypeHandler(o);
    }
    
    @Override
    public ResolvedType getContentType() {
        return this.getContentType();
    }
}
