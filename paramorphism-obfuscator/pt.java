package paramorphism-obfuscator;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class pt {
   @NotNull
   private final String bbr;
   @NotNull
   private final String bbs;
   @NotNull
   private final String bbt;

   @NotNull
   public final String a() {
      return this.bbr;
   }

   @NotNull
   public final String b() {
      return this.bbs;
   }

   @NotNull
   public final String c() {
      return this.bbt;
   }

   public pt(@NotNull String var1, @NotNull String var2, @NotNull String var3) {
      super();
      this.bbr = var1;
      this.bbs = var2;
      this.bbt = var3;
   }

   @NotNull
   public final String d() {
      return this.bbr;
   }

   @NotNull
   public final String e() {
      return this.bbs;
   }

   @NotNull
   public final String f() {
      return this.bbt;
   }

   @NotNull
   public final pt a(@NotNull String var1, @NotNull String var2, @NotNull String var3) {
      return new pt(var1, var2, var3);
   }

   public static pt a(pt var0, String var1, String var2, String var3, int var4, Object var5) {
      if ((var4 & 1) != 0) {
         var1 = var0.bbr;
      }

      if ((var4 & 2) != 0) {
         var2 = var0.bbs;
      }

      if ((var4 & 4) != 0) {
         var3 = var0.bbt;
      }

      return var0.a(var1, var2, var3);
   }

   @NotNull
   public String toString() {
      return "MethodCall(owner=" + this.bbr + ", name=" + this.bbs + ", descriptor=" + this.bbt + ")";
   }

   public int hashCode() {
      String var10000 = this.bbr;
      int var1 = (var10000 != null ? var10000.hashCode() : 0) * 31;
      String var10001 = this.bbs;
      var1 = (var1 + (var10001 != null ? var10001.hashCode() : 0)) * 31;
      var10001 = this.bbt;
      return var1 + (var10001 != null ? var10001.hashCode() : 0);
   }

   public boolean equals(@Nullable Object var1) {
      if (this != var1) {
         if (var1 instanceof pt) {
            pt var2 = (pt)var1;
            if (Intrinsics.areEqual((Object)this.bbr, (Object)var2.bbr) && Intrinsics.areEqual((Object)this.bbs, (Object)var2.bbs) && Intrinsics.areEqual((Object)this.bbt, (Object)var2.bbt)) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }
}
