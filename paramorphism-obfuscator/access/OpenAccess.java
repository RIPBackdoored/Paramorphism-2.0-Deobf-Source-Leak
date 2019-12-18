package paramorphism-obfuscator.access;

import paramorphism-obfuscator.wrappers.AccessWrapper;

public final class OpenAccess extends AccessWrapper {
   public static final OpenAccess bls;

   private OpenAccess() {
      super.setAccess(32);
   }

   static {
      OpenAccess var0 = new OpenAccess();
      bls = var0;
   }
}
