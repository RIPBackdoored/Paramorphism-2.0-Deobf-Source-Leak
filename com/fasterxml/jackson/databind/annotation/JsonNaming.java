package com.fasterxml.jackson.databind.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotation;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.ANNOTATION_TYPE, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotation
public @interface JsonNaming {
   Class value() default PropertyNamingStrategy.class;
}
