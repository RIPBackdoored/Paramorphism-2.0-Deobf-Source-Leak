package paramorphism-obfuscator.wrappers;

import org.jetbrains.annotations.NotNull;

public class AccessWrapper {
   private final int access;

   @NotNull
   public final AccessWrapper addAccess(@NotNull AccessWrapper var1) {
      AccessWrapper var10000 = new AccessWrapper;
      var10000.setAccess(this.access | var1.access);
      return var10000;
   }

   public final int getAccess() {
      return this.access;
   }

   public void setAccess(int var1) {
      super();
      this.access = var1;
   }
}
