package paramorphism-obfuscator.access;

import paramorphism-obfuscator.wrappers.AccessWrapper;

public final class SuperAccess extends AccessWrapper {
   public static final SuperAccess blj;

   private SuperAccess() {
      super.setAccess(32);
   }

   static {
      SuperAccess var0 = new SuperAccess();
      blj = var0;
   }
}
