package paramorphism-obfuscator;

import java.util.Iterator;
import java.util.Map;
import kotlin.Triple;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.commons.Remapper;

public final class ot extends Remapper {
   private final Map zx;
   @NotNull
   private final kg zy;
   @NotNull
   private final oh zz;
   @NotNull
   private final lu baa;
   @NotNull
   private final Function1 bab;
   @NotNull
   private final lc bac;
   @NotNull
   private final Map bad;
   private final boolean bae;

   @NotNull
   public String map(@NotNull String var1) {
      if (!(Boolean)this.bab.invoke(var1)) {
         return super.map(var1);
      } else {
         String var10000 = (String)this.zz.c().get(var1);
         boolean var4;
         boolean var6;
         if (var10000 != null) {
            String var10 = var10000;
            boolean var13 = false;
            var4 = false;
            var6 = false;
            return var10;
         } else {
            StringBuilder var2 = new StringBuilder();
            Map var3 = this.zz.b();
            var4 = false;
            var6 = false;
            if (!var3.containsKey(var1)) {
               return var1;
            } else {
               if (this.bae) {
                  Integer var11 = (Integer)this.zz.b().get(var1);
                  if (var11 != null) {
                     String var10001 = this.baa.a(0, var11, oj.zf);
                     if (var10001 == null) {
                        var10001 = var1;
                     }

                     var2.append(var10001);
                  } else {
                     var2.append(var1);
                  }
               } else {
                  StringBuilder var12 = new StringBuilder();
                  int var14 = 0;

                  for(Iterator var15 = om.a(var1, '/', '$').iterator(); var15.hasNext(); ++var14) {
                     og var5 = (og)var15.next();
                     var12.append(var5.a());
                     var12.append(var5.b());
                     String var7 = var12.toString();
                     String var8 = var5.c();
                     Integer var9;
                     switch(var8.hashCode()) {
                     case 0:
                        if (!var8.equals("")) {
                           continue;
                        }
                        break;
                     case 36:
                        if (!var8.equals("$")) {
                           continue;
                        }
                        break;
                     case 47:
                        if (var8.equals("/")) {
                           var9 = (Integer)this.zz.a().get(var7);
                           if (var9 != null && var9 >= 0) {
                              var2.append(var5.a());
                              var2.append(this.baa.a(var14, var9, oj.ze));
                           } else {
                              var2.append(var5.a());
                              var2.append(var5.b());
                           }
                        }
                     default:
                        continue;
                     }

                     var9 = (Integer)this.zz.b().get(var7);
                     if (var9 != null && var9 >= 0) {
                        var2.append(var5.a());
                        var2.append(this.baa.a(var14, var9, oj.zf));
                     } else {
                        var2.append(var5.a());
                        var2.append(var5.b());
                     }
                  }
               }

               return ((mf)this.zy.a().a(new mf(var1, var2.toString()))).b();
            }
         }
      }
   }

   @NotNull
   public String mapMethodName(@NotNull String var1, @NotNull String var2, @NotNull String var3) {
      oy var4 = new oy(this);
      String var10000 = var4.a(var1, var2, var3);
      if (var10000 == null) {
         var10000 = super.mapMethodName(var1, var2, var3);
      }

      return var10000;
   }

   @NotNull
   public String mapFieldName(@NotNull String var1, @NotNull String var2, @NotNull String var3) {
      pc var4 = new pc(this);
      String var10000 = var4.a(var1, var2, var3);
      if (var10000 == null) {
         var10000 = super.mapFieldName(var1, var2, var3);
      }

      return var10000;
   }

   @NotNull
   public String mapInvokeDynamicMethodName(@NotNull String var1, @NotNull String var2) {
      Triple var3 = (Triple)this.bad.get(var1 + ' ' + var2);
      if (var3 != null) {
         String var4 = (String)var3.component1();
         String var5 = (String)var3.component2();
         String var6 = (String)var3.component3();
         return this.mapMethodName(var4, var5, var6);
      } else {
         return var1;
      }
   }

   @NotNull
   public final kg a() {
      return this.zy;
   }

   @NotNull
   public final oh b() {
      return this.zz;
   }

   @NotNull
   public final lu c() {
      return this.baa;
   }

   @NotNull
   public final Function1 d() {
      return this.bab;
   }

   @NotNull
   public final lc e() {
      return this.bac;
   }

   @NotNull
   public final Map f() {
      return this.bad;
   }

   public final boolean g() {
      return this.bae;
   }

   public ot(@NotNull kg var1, @NotNull oh var2, @NotNull lu var3, @NotNull Function1 var4, @NotNull lc var5, @NotNull Map var6, boolean var7) {
      super();
      this.zy = var1;
      this.zz = var2;
      this.baa = var3;
      this.bab = var4;
      this.bac = var5;
      this.bad = var6;
      this.bae = var7;
      this.zx = MapsKt.toMap(SequencesKt.map(this.zy.b().all(), (Function1)oz.ban));
   }

   public static final Map a(ot var0) {
      return var0.zx;
   }
}
