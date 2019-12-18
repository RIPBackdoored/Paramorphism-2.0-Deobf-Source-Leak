package paramorphism-obfuscator.access;

import paramorphism-obfuscator.wrappers.AccessWrapper;

public final class AnnotationAccess extends AccessWrapper {
   public static final AnnotationAccess bla;

   private AnnotationAccess() {
      super.setAccess(8192);
   }

   static {
      AnnotationAccess var0 = new AnnotationAccess();
      bla = var0;
   }
}
