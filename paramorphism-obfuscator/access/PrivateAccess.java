package paramorphism-obfuscator.access;

import paramorphism-obfuscator.wrappers.AccessWrapper;

public final class PrivateAccess extends AccessWrapper {
   public static final PrivateAccess bll;

   private PrivateAccess() {
      super.setAccess(2);
   }

   static {
      PrivateAccess var0 = new PrivateAccess();
      bll = var0;
   }
}
