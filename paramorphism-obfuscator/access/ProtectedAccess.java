package paramorphism-obfuscator.access;

import paramorphism-obfuscator.wrappers.AccessWrapper;

public final class ProtectedAccess extends AccessWrapper {
   public static final ProtectedAccess blm;

   private ProtectedAccess() {
      super.setAccess(4);
   }

   static {
      ProtectedAccess var0 = new ProtectedAccess();
      blm = var0;
   }
}
