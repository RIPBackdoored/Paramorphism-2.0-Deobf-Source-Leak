package paramorphism-obfuscator.access;

import paramorphism-obfuscator.wrappers.AccessWrapper;

public final class InterfaceAccess extends AccessWrapper {
   public static final InterfaceAccess blf;

   private InterfaceAccess() {
      super.setAccess(512);
   }

   static {
      InterfaceAccess var0 = new InterfaceAccess();
      blf = var0;
   }
}
