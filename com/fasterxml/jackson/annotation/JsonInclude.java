package com.fasterxml.jackson.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotation
public @interface JsonInclude {
   JsonInclude$Include value() default JsonInclude$Include.ALWAYS;

   JsonInclude$Include content() default JsonInclude$Include.ALWAYS;

   Class valueFilter() default Void.class;

   Class contentFilter() default Void.class;
}
