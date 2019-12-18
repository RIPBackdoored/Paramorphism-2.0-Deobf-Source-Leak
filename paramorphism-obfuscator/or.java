package paramorphism-obfuscator;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import kotlin.jvm.internal.Ref$IntRef;
import org.jetbrains.annotations.NotNull;

public final class or {
   private final Map zs;
   private final Map zt;
   private final Map zu;

   public final int a(@NotNull String var1) {
      Ref$IntRef var2 = new Ref$IntRef();
      Map var3 = this.zs;
      boolean var4 = false;
      Object var5 = var3.get(var1);
      boolean var6;
      Object var10000;
      if (var5 == null) {
         var6 = false;
         Integer var18 = 0;
         var3.put(var1, var18);
         var10000 = var18;
      } else {
         var10000 = var5;
      }

      Object var11 = var10000;
      var2.element = ((Number)var11).intValue();
      Map var13 = this.zt;
      boolean var15 = false;
      Object var19 = var13.get(var1);
      if (var19 == null) {
         boolean var7 = false;
         Stack var20 = new Stack();
         var13.put(var1, var20);
         var10000 = var20;
      } else {
         var10000 = var19;
      }

      Stack var12 = (Stack)var10000;
      Map var16 = this.zu;
      var6 = false;
      Object var21 = var16.get(var1);
      if (var21 == null) {
         boolean var8 = false;
         boolean var9 = false;
         Set var23 = (Set)(new LinkedHashSet());
         var16.put(var1, var23);
         var10000 = var23;
      } else {
         var10000 = var21;
      }

      Set var14 = (Set)var10000;
      os var17 = new os(var12, var2);
      var6 = false;

      int var22;
      do {
         var22 = var17.a();
         this.zs.put(var1, var2.element);
      } while(var14.contains(var22));

      return var22;
   }

   public final void a(@NotNull String var1, int var2) {
      Map var3 = this.zt;
      boolean var4 = false;
      Object var5 = var3.get(var1);
      Object var10000;
      if (var5 == null) {
         boolean var6 = false;
         Stack var7 = new Stack();
         var3.put(var1, var7);
         var10000 = var7;
      } else {
         var10000 = var5;
      }

      ((Stack)var10000).push(var2);
   }

   public final void b(@NotNull String var1, int var2) {
      Map var3 = this.zu;
      boolean var4 = false;
      Object var5 = var3.get(var1);
      Object var10000;
      if (var5 == null) {
         boolean var6 = false;
         boolean var7 = false;
         Set var8 = (Set)(new LinkedHashSet());
         var3.put(var1, var8);
         var10000 = var8;
      } else {
         var10000 = var5;
      }

      ((Set)var10000).add(var2);
   }

   public or() {
      super();
      boolean var1 = false;
      Map var3 = (Map)(new LinkedHashMap());
      this.zs = var3;
      var1 = false;
      var3 = (Map)(new LinkedHashMap());
      this.zt = var3;
      var1 = false;
      var3 = (Map)(new LinkedHashMap());
      this.zu = var3;
   }
}
