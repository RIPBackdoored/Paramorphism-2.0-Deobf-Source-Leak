package paramorphism-obfuscator;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class og {
   @NotNull
   private final String yw;
   @NotNull
   private final String yx;
   @NotNull
   private final String yy;

   @NotNull
   public final String a() {
      return this.yw;
   }

   @NotNull
   public final String b() {
      return this.yx;
   }

   @NotNull
   public final String c() {
      return this.yy;
   }

   public og(@NotNull String var1, @NotNull String var2, @NotNull String var3) {
      super();
      this.yw = var1;
      this.yx = var2;
      this.yy = var3;
   }

   @NotNull
   public final String d() {
      return this.yw;
   }

   @NotNull
   public final String e() {
      return this.yx;
   }

   @NotNull
   public final String f() {
      return this.yy;
   }

   @NotNull
   public final og a(@NotNull String var1, @NotNull String var2, @NotNull String var3) {
      return new og(var1, var2, var3);
   }

   public static og a(og var0, String var1, String var2, String var3, int var4, Object var5) {
      if ((var4 & 1) != 0) {
         var1 = var0.yw;
      }

      if ((var4 & 2) != 0) {
         var2 = var0.yx;
      }

      if ((var4 & 4) != 0) {
         var3 = var0.yy;
      }

      return var0.a(var1, var2, var3);
   }

   @NotNull
   public String toString() {
      return "Segment(delimiterBefore=" + this.yw + ", text=" + this.yx + ", delimiterAfter=" + this.yy + ")";
   }

   public int hashCode() {
      String var10000 = this.yw;
      int var1 = (var10000 != null ? var10000.hashCode() : 0) * 31;
      String var10001 = this.yx;
      var1 = (var1 + (var10001 != null ? var10001.hashCode() : 0)) * 31;
      var10001 = this.yy;
      return var1 + (var10001 != null ? var10001.hashCode() : 0);
   }

   public boolean equals(@Nullable Object var1) {
      if (this != var1) {
         if (var1 instanceof og) {
            og var2 = (og)var1;
            if (Intrinsics.areEqual((Object)this.yw, (Object)var2.yw) && Intrinsics.areEqual((Object)this.yx, (Object)var2.yx) && Intrinsics.areEqual((Object)this.yy, (Object)var2.yy)) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }
}
