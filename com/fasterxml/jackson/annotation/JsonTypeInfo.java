package com.fasterxml.jackson.annotation;

import java.lang.annotation.*;

@Target({ ElementType.ANNOTATION_TYPE, ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotation
public @interface JsonTypeInfo {
    Id use();
    
    As include() default As.PROPERTY;
    
    String property() default "";
    
    Class<?> defaultImpl() default JsonTypeInfo.class;
    
    boolean visible() default false;
    
    public enum Id
    {
        NONE((String)null), 
        CLASS("@class"), 
        MINIMAL_CLASS("@c"), 
        NAME("@type"), 
        CUSTOM((String)null);
        
        private final String _defaultPropertyName;
        private static final Id[] $VALUES;
        
        public static Id[] values() {
            return Id.$VALUES.clone();
        }
        
        public static Id valueOf(final String s) {
            return Enum.valueOf(Id.class, s);
        }
        
        private Id(final String defaultPropertyName) {
            this._defaultPropertyName = defaultPropertyName;
        }
        
        public String getDefaultPropertyName() {
            return this._defaultPropertyName;
        }
        
        static {
            $VALUES = new Id[] { Id.NONE, Id.CLASS, Id.MINIMAL_CLASS, Id.NAME, Id.CUSTOM };
        }
    }
    
    public enum As
    {
        PROPERTY, 
        WRAPPER_OBJECT, 
        WRAPPER_ARRAY, 
        EXTERNAL_PROPERTY, 
        EXISTING_PROPERTY;
        
        private static final As[] $VALUES;
        
        public static As[] values() {
            return As.$VALUES.clone();
        }
        
        public static As valueOf(final String s) {
            return Enum.valueOf(As.class, s);
        }
        
        static {
            $VALUES = new As[] { As.PROPERTY, As.WRAPPER_OBJECT, As.WRAPPER_ARRAY, As.EXTERNAL_PROPERTY, As.EXISTING_PROPERTY };
        }
    }
    
    @Deprecated
    public abstract static class None
    {
        public None() {
            super();
        }
    }
}
