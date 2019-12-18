package paramorphism-obfuscator;

import org.jetbrains.annotations.NotNull;

public final class mf {
   @NotNull
   private final String uv;
   @NotNull
   private String uw;

   @NotNull
   public final String a() {
      return this.uv;
   }

   @NotNull
   public final String b() {
      return this.uw;
   }

   public final void a(@NotNull String var1) {
      this.uw = var1;
   }

   public mf(@NotNull String var1, @NotNull String var2) {
      super();
      this.uv = var1;
      this.uw = var2;
   }
}
