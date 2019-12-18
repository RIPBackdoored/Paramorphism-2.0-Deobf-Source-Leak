package org.intellij.lang.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.jetbrains.annotations.NonNls;

@Documented
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.PARAMETER, ElementType.METHOD})
public @interface Flow {
   @NonNls
   String DEFAULT_SOURCE = "The method argument (if parameter was annotated) or this container (if instance method was annotated)";
   @NonNls
   String THIS_SOURCE = "this";
   @NonNls
   String DEFAULT_TARGET = "This container (if the parameter was annotated) or the return value (if instance method was annotated)";
   @NonNls
   String RETURN_METHOD_TARGET = "The return value of this method";
   @NonNls
   String THIS_TARGET = "this";

   String source() default "The method argument (if parameter was annotated) or this container (if instance method was annotated)";

   boolean sourceIsContainer() default false;

   String target() default "This container (if the parameter was annotated) or the return value (if instance method was annotated)";

   boolean targetIsContainer() default false;
}
