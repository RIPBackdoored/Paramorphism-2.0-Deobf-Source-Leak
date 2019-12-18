package paramorphism-obfuscator.access;

import paramorphism-obfuscator.wrappers.AccessWrapper;

public final class DefaultAccess extends AccessWrapper {
   public static final DefaultAccess blk;

   private DefaultAccess() {
      super.setAccess(0);
   }

   static {
      DefaultAccess var0 = new DefaultAccess();
      blk = var0;
   }
}
