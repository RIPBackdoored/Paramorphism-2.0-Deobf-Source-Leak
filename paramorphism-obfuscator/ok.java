package paramorphism-obfuscator;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ok extends oi {
   @NotNull
   private final String zi;

   @NotNull
   public final String a() {
      return this.zi;
   }

   public ok(@NotNull String var1) {
      super((DefaultConstructorMarker)null);
      this.zi = var1;
   }

   @NotNull
   public final String b() {
      return this.zi;
   }

   @NotNull
   public final ok a(@NotNull String var1) {
      return new ok(var1);
   }

   public static ok a(ok var0, String var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = var0.zi;
      }

      return var0.a(var1);
   }

   @NotNull
   public String toString() {
      return "NameMapping(name=" + this.zi + ")";
   }

   public int hashCode() {
      String var10000 = this.zi;
      return var10000 != null ? var10000.hashCode() : 0;
   }

   public boolean equals(@Nullable Object var1) {
      if (this != var1) {
         if (var1 instanceof ok) {
            ok var2 = (ok)var1;
            if (Intrinsics.areEqual((Object)this.zi, (Object)var2.zi)) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }
}
