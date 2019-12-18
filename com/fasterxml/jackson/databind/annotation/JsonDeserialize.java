package com.fasterxml.jackson.databind.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotation;
import com.fasterxml.jackson.databind.JsonDeserializer$None;
import com.fasterxml.jackson.databind.KeyDeserializer$None;
import com.fasterxml.jackson.databind.util.Converter$None;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotation
public @interface JsonDeserialize {
   Class using() default JsonDeserializer$None.class;

   Class contentUsing() default JsonDeserializer$None.class;

   Class keyUsing() default KeyDeserializer$None.class;

   Class builder() default Void.class;

   Class converter() default Converter$None.class;

   Class contentConverter() default Converter$None.class;

   Class as() default Void.class;

   Class keyAs() default Void.class;

   Class contentAs() default Void.class;
}
