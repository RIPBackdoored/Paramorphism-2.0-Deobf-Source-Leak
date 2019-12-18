package paramorphism-obfuscator;

import org.jetbrains.annotations.NotNull;
import site.hackery.paramorphism.api.resources.ClassInfo;
import site.hackery.paramorphism.api.resources.Resource;

public class me {
   @NotNull
   private final ClassInfo us;
   @NotNull
   private String ut;
   @NotNull
   private Resource uu;

   @NotNull
   public final ClassInfo a() {
      return this.us;
   }

   @NotNull
   public final String b() {
      return this.ut;
   }

   public final void a(@NotNull String var1) {
      this.ut = var1;
   }

   @NotNull
   public final Resource c() {
      return this.uu;
   }

   public final void a(@NotNull Resource var1) {
      this.uu = var1;
   }

   public me(@NotNull ClassInfo var1, @NotNull String var2, @NotNull Resource var3) {
      super();
      this.us = var1;
      this.ut = var2;
      this.uu = var3;
   }
}
