package com.fasterxml.jackson.databind.util;

import java.lang.annotation.Annotation;

public interface Annotations {
   Annotation get(Class var1);

   boolean has(Class var1);

   boolean hasOneOf(Class[] var1);

   int size();
}
