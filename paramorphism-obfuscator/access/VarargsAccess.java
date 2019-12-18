package paramorphism-obfuscator.access;

import paramorphism-obfuscator.wrappers.AccessWrapper;

public final class VarargsAccess extends AccessWrapper {
   public static final VarargsAccess blu;

   private VarargsAccess() {
      super.setAccess(128);
   }

   static {
      VarargsAccess var0 = new VarargsAccess();
      blu = var0;
   }
}
