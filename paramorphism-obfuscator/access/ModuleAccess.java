package paramorphism-obfuscator.access;

import paramorphism-obfuscator.wrappers.AccessWrapper;

public final class ModuleAccess extends AccessWrapper {
   public static final ModuleAccess blh;

   private ModuleAccess() {
      super.setAccess(32768);
   }

   static {
      ModuleAccess var0 = new ModuleAccess();
      blh = var0;
   }
}
