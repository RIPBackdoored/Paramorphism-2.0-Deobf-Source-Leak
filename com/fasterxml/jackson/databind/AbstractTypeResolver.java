package com.fasterxml.jackson.databind;

public abstract class AbstractTypeResolver
{
    public AbstractTypeResolver() {
        super();
    }
    
    public JavaType findTypeMapping(final DeserializationConfig deserializationConfig, final JavaType javaType) {
        return null;
    }
    
    @Deprecated
    public JavaType resolveAbstractType(final DeserializationConfig deserializationConfig, final JavaType javaType) {
        return null;
    }
    
    public JavaType resolveAbstractType(final DeserializationConfig deserializationConfig, final BeanDescription beanDescription) {
        return null;
    }
}
