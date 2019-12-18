package com.fasterxml.jackson.databind.annotation;

import com.fasterxml.jackson.annotation.JsonInclude$Include;

public @interface JsonAppend$Prop {
   Class value();

   String name() default "";

   String namespace() default "";

   JsonInclude$Include include() default JsonInclude$Include.NON_NULL;

   boolean required() default false;

   Class type() default Object.class;
}
