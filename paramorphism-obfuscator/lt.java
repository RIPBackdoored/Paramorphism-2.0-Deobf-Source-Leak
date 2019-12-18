package paramorphism-obfuscator;

import java.util.Collection;
import java.util.Random;
import kotlin.collections.CollectionsKt;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;
import site.hackery.paramorphism.api.naming.MappingDictionary;

public final class lt {
   @NotNull
   public static final int[] a(int var0) {
      return CollectionsKt.toIntArray((Collection)CollectionsKt.toList((Iterable)RangesKt.until(0, var0)));
   }

   @NotNull
   public static final int[] a(int var0, @NotNull Random var1) {
      int[] var2 = a(var0);
      int var3 = 0;

      for(int var4 = var2.length; var3 < var4; ++var3) {
         int var5 = var1.nextInt(var0);
         int var6 = var2[var3];
         var2[var3] = var2[var5];
         var2[var5] = var6;
      }

      return var2;
   }

   @NotNull
   public static final String a(@NotNull MappingDictionary var0, int var1, @NotNull int[] var2) {
      return var0.getElementSet()[var2[var1]];
   }
}
