package com.fasterxml.jackson.databind.annotation;

import com.fasterxml.jackson.annotation.JsonInclude$Include;

public @interface JsonAppend$Attr {
   String value();

   String propName() default "";

   String propNamespace() default "";

   JsonInclude$Include include() default JsonInclude$Include.NON_NULL;

   boolean required() default false;
}
