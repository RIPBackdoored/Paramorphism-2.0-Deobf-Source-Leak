package paramorphism-obfuscator.access;

import paramorphism-obfuscator.wrappers.AccessWrapper;

public final class SynchronizedAccess extends AccessWrapper {
   public static final SynchronizedAccess blr;

   private SynchronizedAccess() {
      super.setAccess(32);
   }

   static {
      SynchronizedAccess var0 = new SynchronizedAccess();
      blr = var0;
   }
}
