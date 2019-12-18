package com.fasterxml.jackson.core.type;

public abstract class ResolvedType
{
    public ResolvedType() {
        super();
    }
    
    public abstract Class<?> getRawClass();
    
    public abstract boolean hasRawClass(final Class<?> p0);
    
    public abstract boolean isAbstract();
    
    public abstract boolean isConcrete();
    
    public abstract boolean isThrowable();
    
    public abstract boolean isArrayType();
    
    public abstract boolean isEnumType();
    
    public abstract boolean isInterface();
    
    public abstract boolean isPrimitive();
    
    public abstract boolean isFinal();
    
    public abstract boolean isContainerType();
    
    public abstract boolean isCollectionLikeType();
    
    public boolean isReferenceType() {
        return this.getReferencedType() != null;
    }
    
    public abstract boolean isMapLikeType();
    
    public abstract boolean hasGenericTypes();
    
    @Deprecated
    public Class<?> getParameterSource() {
        return null;
    }
    
    public abstract ResolvedType getKeyType();
    
    public abstract ResolvedType getContentType();
    
    public abstract ResolvedType getReferencedType();
    
    public abstract int containedTypeCount();
    
    public abstract ResolvedType containedType(final int p0);
    
    public abstract String containedTypeName(final int p0);
    
    public abstract String toCanonical();
}
