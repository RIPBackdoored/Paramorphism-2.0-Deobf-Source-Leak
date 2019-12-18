package paramorphism-obfuscator.access;

import paramorphism-obfuscator.wrappers.AccessWrapper;

public final class DeprecatedAccess extends AccessWrapper {
   public static final DeprecatedAccess blc;

   private DeprecatedAccess() {
      super.setAccess(131072);
   }

   static {
      DeprecatedAccess var0 = new DeprecatedAccess();
      blc = var0;
   }
}
