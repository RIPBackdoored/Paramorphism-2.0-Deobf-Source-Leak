package paramorphism-obfuscator;

import org.jetbrains.annotations.NotNull;

public final class lh {
   public static final lh tv;

   public final void a(@NotNull String var1) {
      boolean var2 = false;
      System.out.println(var1);
   }

   public final void b(@NotNull String var1) {
      System.err.println(var1);
   }

   private lh() {
      super();
   }

   static {
      lh var0 = new lh();
      tv = var0;
   }
}
