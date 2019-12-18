package paramorphism-obfuscator.access;

import paramorphism-obfuscator.wrappers.AccessWrapper;

public final class TransitiveAccess extends AccessWrapper {
   public static final TransitiveAccess blv;

   private TransitiveAccess() {
      super.setAccess(32);
   }

   static {
      TransitiveAccess var0 = new TransitiveAccess();
      blv = var0;
   }
}
