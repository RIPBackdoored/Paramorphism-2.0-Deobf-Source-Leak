package paramorphism-obfuscator;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import kotlin.Pair;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import site.hackery.paramorphism.api.config.naming.NameGenerationDictionaries;
import site.hackery.paramorphism.api.naming.MappingDictionary;

public final class lu {
   private final Map uh;
   private final NameGenerationDictionaries ui;
   private final Random uj;

   private final MappingDictionary a(oj var1) {
      // $FF: Couldn't be decompiled
   }

   private final int[] a(MappingDictionary var1, int var2) {
      Pair var3 = new Pair(var1, var2);
      Map var4 = this.uh;
      boolean var5 = false;
      boolean var7 = false;
      if (var4.containsKey(var3)) {
         return (int[])MapsKt.getValue(this.uh, var3);
      } else {
         int[] var9 = this.uj != null ? lt.a(var1.getElementSet().length, this.uj) : lt.a(var1.getElementSet().length);
         var5 = false;
         boolean var6 = false;
         boolean var8 = false;
         this.uh.put(var3, var9);
         return var9;
      }
   }

   @Nullable
   public final String a(int var1, int var2, @NotNull oj var3) {
      if (var2 < 0) {
         return null;
      } else {
         MappingDictionary var4 = this.a(var3);
         int[] var5 = this.a(var4, var1);
         boolean var6 = false;
         StringBuilder var7 = new StringBuilder();
         boolean var8 = false;
         boolean var9 = false;
         StringBuilder var10 = var7;
         boolean var11 = false;
         int var12 = var2;
         if (var2 == 0) {
            var7.insert(0, lt.a(var4, 0, var5));
         }

         while(var12 > 0) {
            int var13 = var12 % var4.getElementSet().length;
            var12 /= var4.getElementSet().length;
            var10.insert(0, lt.a(var4, var13, var5));
            if (var12 != 0) {
               var10.insert(0, var4.getJoiner());
            }
         }

         return var7.toString();
      }
   }

   public lu(@NotNull NameGenerationDictionaries var1, @Nullable Random var2) {
      super();
      this.ui = var1;
      this.uj = var2;
      boolean var3 = false;
      Map var5 = (Map)(new LinkedHashMap());
      this.uh = var5;
   }

   public lu(NameGenerationDictionaries var1, Random var2, int var3, DefaultConstructorMarker var4) {
      if ((var3 & 2) != 0) {
         var2 = (Random)null;
      }

      this(var1, var2);
   }
}
