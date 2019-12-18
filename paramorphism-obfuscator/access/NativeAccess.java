package paramorphism-obfuscator.access;

import paramorphism-obfuscator.wrappers.AccessWrapper;

public final class NativeAccess extends AccessWrapper {
   public static final NativeAccess bli;

   private NativeAccess() {
      super.setAccess(256);
   }

   static {
      NativeAccess var0 = new NativeAccess();
      bli = var0;
   }
}
