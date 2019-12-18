package com.fasterxml.jackson.annotation;

import java.lang.annotation.*;

@Target({ ElementType.ANNOTATION_TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotation
public @interface JsonAnyGetter {
    boolean enabled() default true;
}
