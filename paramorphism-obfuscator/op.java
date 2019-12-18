package paramorphism-obfuscator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;
import kotlin.collections.CollectionsKt;
import org.jetbrains.annotations.NotNull;
import site.hackery.paramorphism.api.config.strategies.obfuscation.remapper.RemappingStrategyConfig;

public final class op {
   public static final void a(@NotNull oh param0, @NotNull kg param1, @NotNull RemappingStrategyConfig param2) {
      // $FF: Couldn't be decompiled
   }

   public static final void b(@NotNull oh param0, @NotNull kg param1, @NotNull RemappingStrategyConfig param2) {
      // $FF: Couldn't be decompiled
   }

   public static final void a(@NotNull oh param0, @NotNull lu param1, @NotNull lc param2, @NotNull kg param3, @NotNull RemappingStrategyConfig param4) {
      // $FF: Couldn't be decompiled
   }

   public static final void c(@NotNull oh param0, @NotNull kg param1, @NotNull RemappingStrategyConfig param2) {
      // $FF: Couldn't be decompiled
   }

   private static final List a(String var0, String var1, String var2, lc var3) {
      le var10000 = var3.a(var0);
      boolean var6;
      if (var10000 != null) {
         le var4 = var10000;
         var6 = false;
         Set var7 = (Set)(new LinkedHashSet());
         ol var8 = new ol(var1, var2, var7);
         var8.a(var4);
         return CollectionsKt.toList((Iterable)var7);
      } else {
         String var5 = "Class '" + var0 + "' was not present in class hierarchy map";
         var6 = false;
         throw (Throwable)(new IllegalStateException(var5.toString()));
      }
   }

   private static final List b(String var0, String var1, String var2, lc var3) {
      boolean var5 = false;
      List var4 = (List)(new ArrayList());
      le var10000 = var3.a(var0);
      if (var10000 != null) {
         le var11 = var10000;
         Iterator var13 = var3.b();

         while(var13.hasNext()) {
            Entry var12 = (Entry)var13.next();
            boolean var10 = false;
            le var8 = (le)var12.getValue();
            if (a(var8.a(), var1, var2, var3).contains(var11)) {
               var4.add(var8);
            }
         }

         return var4;
      } else {
         String var6 = var0 + " was not found in the class path.";
         boolean var7 = false;
         throw (Throwable)(new IllegalStateException(var6.toString()));
      }
   }
}
