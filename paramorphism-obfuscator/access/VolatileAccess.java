package paramorphism-obfuscator.access;

import paramorphism-obfuscator.wrappers.AccessWrapper;

public final class VolatileAccess extends AccessWrapper {
   public static final VolatileAccess blp;

   private VolatileAccess() {
      super.setAccess(64);
   }

   static {
      VolatileAccess var0 = new VolatileAccess();
      blp = var0;
   }
}
