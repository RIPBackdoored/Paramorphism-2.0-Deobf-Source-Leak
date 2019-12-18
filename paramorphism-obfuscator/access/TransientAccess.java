package paramorphism-obfuscator.access;

import paramorphism-obfuscator.wrappers.AccessWrapper;

public final class TransientAccess extends AccessWrapper {
   public static final TransientAccess blw;

   private TransientAccess() {
      super.setAccess(128);
   }

   static {
      TransientAccess var0 = new TransientAccess();
      blw = var0;
   }
}
