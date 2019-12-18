package com.fasterxml.jackson.annotation;

import java.lang.annotation.*;

@Target({ ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface JacksonAnnotation {
}
