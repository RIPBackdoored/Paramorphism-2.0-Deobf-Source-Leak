package com.fasterxml.jackson.annotation;

import java.lang.annotation.*;

public @interface Type {
    Class<?> value();
    
    String name() default "";
}
