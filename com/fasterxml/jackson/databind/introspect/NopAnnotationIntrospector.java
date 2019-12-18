package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import java.io.Serializable;

public abstract class NopAnnotationIntrospector extends AnnotationIntrospector implements Serializable {
   private static final long serialVersionUID = 1L;
   public static final NopAnnotationIntrospector instance = new NopAnnotationIntrospector$1();

   public NopAnnotationIntrospector() {
      super();
   }

   public Version version() {
      return Version.unknownVersion();
   }
}
