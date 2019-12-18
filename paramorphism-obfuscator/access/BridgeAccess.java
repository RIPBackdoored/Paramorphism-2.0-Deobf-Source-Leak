package paramorphism-obfuscator.access;

import paramorphism-obfuscator.wrappers.AccessWrapper;

public final class BridgeAccess extends AccessWrapper {
   public static final BridgeAccess blx;

   private BridgeAccess() {
      super.setAccess(64);
   }

   static {
      BridgeAccess var0 = new BridgeAccess();
      blx = var0;
   }
}
