package paramorphism-obfuscator;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import site.hackery.paramorphism.api.config.strategies.obfuscation.remapper.RemappingStrategyConfig;

public final class oc extends Lambda implements Function1 {
   public final on xv;
   public final Map xw;
   public final kg xx;
   public final oh xy;
   public final lc xz;
   public final RemappingStrategyConfig ya;
   public final lu yb;

   public oc(on var1, Map var2, kg var3, oh var4, lc var5, RemappingStrategyConfig var6, lu var7) {
      super(1);
      this.xv = var1;
      this.xw = var2;
      this.xx = var3;
      this.xy = var4;
      this.xz = var5;
      this.ya = var6;
      this.yb = var7;
   }

   public Object invoke(Object var1) {
      this.a((le)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull le var1) {
      Map var2 = (Map)this.xy.d().get(var1.a());
      boolean var7;
      boolean var10;
      if (var2 != null) {
         boolean var4 = false;
         Iterator var5 = var2.entrySet().iterator();

         while(var5.hasNext()) {
            Entry var6 = (Entry)var5.next();
            var7 = false;
            String var3 = (String)var6.getKey();
            List var8 = StringsKt.split$default((CharSequence)var3, new String[]{" "}, false, 0, 6, (Object)null);
            var10 = false;
            String var22 = (String)var8.get(0);
            var10 = false;
            String var27 = (String)var8.get(1);
            String var9 = var27;
            var10 = false;
            int var11 = StringsKt.getLastIndex((CharSequence)var27);

            boolean var16;
            String var10000;
            label110: {
               for(boolean var12 = false; var11 >= 0; --var11) {
                  char var13 = var9.charAt(var11);
                  boolean var14 = false;
                  if (var13 == ')') {
                     byte var33 = 0;
                     int var15 = var11 + 1;
                     var16 = false;
                     var10000 = var9.substring(var33, var15);
                     break label110;
                  }
               }

               var10000 = "";
            }

            String var29 = var10000;
            var10000 = this.xv.a(var1.a(), var22, var27);
            if (var10000 != null) {
               var9 = var10000;
               var10 = false;
               boolean var31 = false;
               boolean var32 = false;
               Map var34 = this.xw;
               var16 = false;
               Object var17 = var34.get(var29);
               Object var35;
               if (var17 == null) {
                  boolean var18 = false;
                  boolean var19 = false;
                  Set var37 = (Set)(new LinkedHashSet());
                  var34.put(var29, var37);
                  var35 = var37;
               } else {
                  var35 = var17;
               }

               ((Set)var35).add(var9);
            }
         }
      }

      le var36 = var1.d();
      boolean var24;
      if (var36 != null) {
         le var25 = var36;
         var24 = false;
         boolean var20 = false;
         var7 = false;
         this.a(var25);
      }

      le[] var26 = var1.e();
      var24 = false;
      le[] var21 = var26;
      int var23 = var26.length;

      for(int var28 = 0; var28 < var23; ++var28) {
         le var30 = var21[var28];
         var10 = false;
         this.a(var30);
      }

   }
}
