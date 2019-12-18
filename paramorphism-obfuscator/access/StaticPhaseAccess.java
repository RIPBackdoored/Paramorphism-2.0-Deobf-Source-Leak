package paramorphism-obfuscator.access;

import paramorphism-obfuscator.wrappers.AccessWrapper;

public final class StaticPhaseAccess extends AccessWrapper {
   public static final StaticPhaseAccess blb;

   private StaticPhaseAccess() {
      super.setAccess(64);
   }

   static {
      StaticPhaseAccess var0 = new StaticPhaseAccess();
      blb = var0;
   }
}
