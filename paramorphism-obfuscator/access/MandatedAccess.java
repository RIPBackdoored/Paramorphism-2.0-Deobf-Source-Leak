package paramorphism-obfuscator.access;

import paramorphism-obfuscator.wrappers.AccessWrapper;

public final class MandatedAccess extends AccessWrapper {
   public static final MandatedAccess blg;

   private MandatedAccess() {
      super.setAccess(32768);
   }

   static {
      MandatedAccess var0 = new MandatedAccess();
      blg = var0;
   }
}
