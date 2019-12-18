package paramorphism-obfuscator;

import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class pq {
   @NotNull
   private final String bbi;
   @NotNull
   private final String bbj;

   public final int a() {
      return StringsKt.replace$default(this.bbi, '/', '.', false, 4, (Object)null).hashCode() * 31 + this.bbj.hashCode();
   }

   @NotNull
   public final String b() {
      return this.bbi;
   }

   @NotNull
   public final String c() {
      return this.bbj;
   }

   public pq(@NotNull String var1, @NotNull String var2) {
      super();
      this.bbi = var1;
      this.bbj = var2;
   }

   @NotNull
   public final String d() {
      return this.bbi;
   }

   @NotNull
   public final String e() {
      return this.bbj;
   }

   @NotNull
   public final pq a(@NotNull String var1, @NotNull String var2) {
      return new pq(var1, var2);
   }

   public static pq a(pq var0, String var1, String var2, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         var1 = var0.bbi;
      }

      if ((var3 & 2) != 0) {
         var2 = var0.bbj;
      }

      return var0.a(var1, var2);
   }

   @NotNull
   public String toString() {
      return "Caller(owner=" + this.bbi + ", name=" + this.bbj + ")";
   }

   public int hashCode() {
      String var10000 = this.bbi;
      int var1 = (var10000 != null ? var10000.hashCode() : 0) * 31;
      String var10001 = this.bbj;
      return var1 + (var10001 != null ? var10001.hashCode() : 0);
   }

   public boolean equals(@Nullable Object var1) {
      if (this != var1) {
         if (var1 instanceof pq) {
            pq var2 = (pq)var1;
            if (Intrinsics.areEqual((Object)this.bbi, (Object)var2.bbi) && Intrinsics.areEqual((Object)this.bbj, (Object)var2.bbj)) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }
}
