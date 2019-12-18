package paramorphism-obfuscator;

import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import site.hackery.paramorphism.api.config.ElementMask;
import site.hackery.paramorphism.api.config.ParamorphismConfig;
import site.hackery.paramorphism.api.config.StrategyConfiguration;

public final class li {
   public static final boolean a(@NotNull ElementMask var0, @NotNull String var1) {
      boolean var2 = true;
      String[] var3 = var0.getInclude();
      boolean var4 = false;
      boolean var6 = false;
      boolean var10000;
      String[] var5;
      int var7;
      String var8;
      boolean var10;
      int var11;
      if (var3.length != 0) {
         var3 = var0.getInclude();
         var4 = false;
         var5 = var3;
         var11 = var3.length;
         var7 = 0;

         while(true) {
            if (var7 >= var11) {
               var10000 = false;
               break;
            }

            var8 = var5[var7];
            var10 = false;
            if (a(var8, var1)) {
               var10000 = true;
               break;
            }

            ++var7;
         }

         var2 = var10000;
      }

      var3 = var0.getExclude();
      var4 = false;
      var5 = var3;
      var11 = var3.length;
      var7 = 0;

      while(true) {
         if (var7 >= var11) {
            var10000 = false;
            break;
         }

         var8 = var5[var7];
         var10 = false;
         if (a(var8, var1)) {
            var10000 = true;
            break;
         }

         ++var7;
      }

      if (var10000) {
         var2 = false;
      }

      return var2;
   }

   public static final boolean a(@NotNull String var0, @NotNull String var1) {
      if (StringsKt.last((CharSequence)var0) == '/') {
         return StringsKt.startsWith$default(var1, var0, false, 2, (Object)null);
      } else if (StringsKt.last((CharSequence)var0) == '*') {
         byte var3 = 0;
         int var4 = StringsKt.getLastIndex((CharSequence)var0) - 1;
         boolean var5 = false;
         String var7 = var0.substring(var3, var4);
         return StringsKt.startsWith$default(var1, var7, false, 2, (Object)null);
      } else {
         return Intrinsics.areEqual((Object)var0, (Object)var1);
      }
   }

   public static final boolean a(@NotNull String var0, @NotNull ParamorphismConfig var1, @NotNull StrategyConfiguration var2) {
      return var2.getOverrideGlobalMask() ? a(var2.getMask(), var0) : a(var1.getMask(), var0) && a(var2.getMask(), var0);
   }
}
