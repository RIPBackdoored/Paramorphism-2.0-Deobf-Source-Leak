package paramorphism-obfuscator.access;

import paramorphism-obfuscator.wrappers.AccessWrapper;

public final class StaticAccess extends AccessWrapper {
   public static final StaticAccess blo;

   private StaticAccess() {
      super.setAccess(8);
   }

   static {
      StaticAccess var0 = new StaticAccess();
      blo = var0;
   }
}
