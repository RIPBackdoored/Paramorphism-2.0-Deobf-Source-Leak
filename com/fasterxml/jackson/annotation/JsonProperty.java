package com.fasterxml.jackson.annotation;

import java.lang.annotation.*;

@Target({ ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotation
public @interface JsonProperty {
    public static final String USE_DEFAULT_NAME = "";
    public static final int INDEX_UNKNOWN = -1;
    
    String value() default "";
    
    boolean required() default false;
    
    int index() default -1;
    
    String defaultValue() default "";
    
    Access access() default Access.AUTO;
    
    public enum Access
    {
        AUTO, 
        READ_ONLY, 
        WRITE_ONLY, 
        READ_WRITE;
        
        private static final Access[] $VALUES;
        
        public static Access[] values() {
            return Access.$VALUES.clone();
        }
        
        public static Access valueOf(final String s) {
            return Enum.valueOf(Access.class, s);
        }
        
        static {
            $VALUES = new Access[] { Access.AUTO, Access.READ_ONLY, Access.WRITE_ONLY, Access.READ_WRITE };
        }
    }
}
