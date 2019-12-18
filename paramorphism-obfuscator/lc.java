package paramorphism-obfuscator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import kotlin.sequences.Sequence;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;
import site.hackery.paramorphism.api.resources.ClassInfo;
import site.hackery.paramorphism.api.resources.ClassSet;
import site.hackery.paramorphism.api.resources.ClassSetGroup;

public final class lc {
   public static final KProperty[] th = new KProperty[]{(KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(lc.class), "allEntries", "getAllEntries()Ljava/util/Map;"))};
   private final Map ti;
   private final Map tj;
   private final Lazy tk;
   private final ClassSet tl;
   private final ClassSetGroup tm;

   @Nullable
   public final le a(@NotNull String var1) {
      if (this.tj.containsKey(var1)) {
         Object var51 = this.tj.get(var1);
         if (var51 == null) {
            Intrinsics.throwNpe();
         }

         return (le)var51;
      } else {
         ClassNode var2 = this.b(var1);
         if (var2 == null) {
            return null;
         } else {
            Iterable var4 = (Iterable)var2.methods;
            boolean var5 = false;
            Collection var7 = (Collection)(new ArrayList());
            boolean var8 = false;
            Iterator var9 = var4.iterator();

            Object var10;
            MethodNode var11;
            boolean var12;
            while(var9.hasNext()) {
               boolean var45;
               label211: {
                  label210: {
                     var10 = var9.next();
                     var11 = (MethodNode)var10;
                     var12 = false;
                     Character var10000 = StringsKt.getOrNull((CharSequence)var11.name, 0);
                     if (var10000 != null) {
                        if (var10000 == '<') {
                           break label210;
                        }
                     }

                     if ((var11.access & 18) == 0) {
                        var45 = true;
                        break label211;
                     }
                  }

                  var45 = false;
               }

               if (var45) {
                  var7.add(var10);
               }
            }

            var4 = (Iterable)((List)var7);
            var5 = false;
            var7 = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault(var4, 10)));
            var8 = false;
            var9 = var4.iterator();

            String var26;
            while(var9.hasNext()) {
               var10 = var9.next();
               var11 = (MethodNode)var10;
               var12 = false;
               var26 = var11.name + ' ' + var11.desc;
               var7.add(var26);
            }

            Collection var27 = (Collection)((List)var7);
            var5 = false;
            Object[] var46 = var27.toArray(new String[0]);
            if (var46 == null) {
               throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
            } else {
               String[] var3 = (String[])var46;
               Iterable var29 = (Iterable)var2.fields;
               boolean var6 = false;
               Collection var36 = (Collection)(new ArrayList());
               boolean var38 = false;
               Iterator var39 = var29.iterator();

               boolean var13;
               Object var41;
               FieldNode var44;
               while(var39.hasNext()) {
                  var41 = var39.next();
                  var44 = (FieldNode)var41;
                  var13 = false;
                  if ((var44.access & 18) == 0) {
                     var36.add(var41);
                  }
               }

               var29 = (Iterable)((List)var36);
               var6 = false;
               var36 = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault(var29, 10)));
               var38 = false;
               var39 = var29.iterator();

               while(var39.hasNext()) {
                  var41 = var39.next();
                  var44 = (FieldNode)var41;
                  var13 = false;
                  var26 = var44.name + ' ' + var44.desc;
                  var36.add(var26);
               }

               Collection var30 = (Collection)((List)var36);
               var6 = false;
               var46 = var30.toArray(new String[0]);
               if (var46 == null) {
                  throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
               } else {
                  String[] var28 = (String[])var46;
                  String var47 = var2.superName;
                  le var48;
                  if (var47 != null) {
                     String var31 = var47;
                     boolean var34 = false;
                     var8 = false;
                     boolean var40 = false;
                     var48 = this.a(var31);
                  } else {
                     var48 = null;
                  }

                  le var32;
                  le[] var50;
                  label181: {
                     var32 = var48;
                     List var49 = var2.interfaces;
                     if (var49 != null) {
                        Iterable var35 = (Iterable)var49;
                        var8 = false;
                        Collection var42 = (Collection)(new ArrayList());
                        boolean var43 = false;
                        var13 = false;
                        Iterator var14 = var35.iterator();

                        while(var14.hasNext()) {
                           Object var15 = var14.next();
                           boolean var17 = false;
                           String var18 = (String)var15;
                           boolean var19 = false;
                           var48 = this.a(var18);
                           if (var48 != null) {
                              le var20 = var48;
                              boolean var21 = false;
                              boolean var22 = false;
                              boolean var24 = false;
                              var42.add(var20);
                           }
                        }

                        var7 = (Collection)((List)var42);
                        var8 = false;
                        var46 = var7.toArray(new le[0]);
                        if (var46 == null) {
                           throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
                        }

                        var50 = (le[])var46;
                        if (var50 != null) {
                           break label181;
                        }
                     }

                     var50 = new le[0];
                  }

                  le[] var33 = var50;
                  le var37 = new le(var2.name, var3, var28, var32, var33);
                  this.tj.put(var1, var37);
                  return var37;
               }
            }
         }
      }
   }

   private final Map a() {
      Lazy var1 = this.tk;
      KProperty var3 = th[0];
      boolean var4 = false;
      return (Map)var1.getValue();
   }

   @NotNull
   public final Iterator b() {
      Map var1 = this.a();
      boolean var2 = false;
      return var1.entrySet().iterator();
   }

   private final ClassNode b(String var1) {
      String var10000 = (String)this.ti.get(var1);
      if (var10000 == null) {
         var10000 = var1;
      }

      String var2 = var10000;
      ClassInfo var3;
      if (this.tl.contains(var2)) {
         var3 = this.tl.get(var2);
         return var3 != null ? var3.getNode() : null;
      } else {
         var3 = this.tm.lookup(var2);
         return var3 != null ? var3.getNode() : null;
      }
   }

   public lc(@NotNull ClassSet var1, @NotNull ClassSetGroup var2) {
      super();
      this.tl = var1;
      this.tm = var2;
      Sequence var3 = this.tl.all();
      boolean var4 = false;
      Map var6 = (Map)(new LinkedHashMap());
      boolean var7 = false;
      Iterator var8 = var3.iterator();

      while(var8.hasNext()) {
         Object var9 = var8.next();
         ClassInfo var11 = (ClassInfo)var9;
         boolean var12 = false;
         Pair var15 = TuplesKt.to(var11.getNode().name, var11.getOriginalName());
         var12 = false;
         var6.put(var15.getFirst(), var15.getSecond());
      }

      this.ti = var6;
      boolean var16 = false;
      Map var14 = (Map)(new LinkedHashMap());
      this.tj = var14;
      this.tk = LazyKt.lazy((Function0)(new ld(this)));
   }

   public static final ClassSet a(lc var0) {
      return var0.tl;
   }
}
