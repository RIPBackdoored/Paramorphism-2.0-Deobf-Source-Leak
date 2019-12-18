package com.fasterxml.jackson.databind.jsonschema;

import com.fasterxml.jackson.annotation.JacksonAnnotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotation
public @interface JsonSerializableSchema {
   String NO_VALUE = "##irrelevant";

   String id() default "";

   String schemaType() default "any";

   /** @deprecated */
   @Deprecated
   String schemaObjectPropertiesDefinition() default "##irrelevant";

   /** @deprecated */
   @Deprecated
   String schemaItemDefinition() default "##irrelevant";
}
