package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.cfg.PackageVersion;

final class NopAnnotationIntrospector$1 extends NopAnnotationIntrospector {
   private static final long serialVersionUID = 1L;

   NopAnnotationIntrospector$1() {
      super();
   }

   public Version version() {
      return PackageVersion.VERSION;
   }
}
