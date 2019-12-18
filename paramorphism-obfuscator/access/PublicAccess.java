package paramorphism-obfuscator.access;

import paramorphism-obfuscator.wrappers.AccessWrapper;

public final class PublicAccess extends AccessWrapper {
   public static final PublicAccess bln;

   private PublicAccess() {
      super.setAccess(1);
   }

   static {
      PublicAccess var0 = new PublicAccess();
      bln = var0;
   }
}
