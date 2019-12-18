package paramorphism-obfuscator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Random;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.TypeCastException;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Ref$IntRef;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import paramorphism-obfuscator.access.PublicAccess;
import paramorphism-obfuscator.access.StaticAccess;
import paramorphism-obfuscator.wrappers.AccessWrapper;
import site.hackery.paramorphism.api.config.StrategyConfiguration;
import site.hackery.paramorphism.api.config.strategies.concealment.StringConcealingStrategyConfig;

public final class rn extends mm {
   public static final KProperty[] bfi = new KProperty[]{(KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(rn.class), "stringContainerPackage", "getStringContainerPackage()Ljava/lang/String;")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(rn.class), "dispatcherClassType", "getDispatcherClassType()Lorg/objectweb/asm/Type;"))};
   private String bfj;
   private final Lazy bfk;
   private final Lazy bfl;
   @NotNull
   private final StringConcealingStrategyConfig bfm;

   private final String a() {
      Lazy var1 = this.bfk;
      KProperty var3 = bfi[0];
      boolean var4 = false;
      return (String)var1.getValue();
   }

   private final Type b() {
      Lazy var1 = this.bfl;
      KProperty var3 = bfi[1];
      boolean var4 = false;
      return (Type)var1.getValue();
   }

   protected void f(@NotNull kg var1) {
      lu var2 = new lu(var1.i().getRemapper().getNameGeneration().getDictionaries(), var1.h().a());
      Random var3 = var1.h().a();
      this.a(var1, Reflection.getOrCreateKotlinClass(md.class), this.d(var1), (Function1)(new ri((rn)this)));
      this.a(var1, Reflection.getOrCreateKotlinClass(lx.class), (Function1)(new qt(this, var1, var3)));
      boolean var5 = false;
      Map var4 = (Map)(new LinkedHashMap());
      boolean var6 = false;
      List var7 = (List)(new ArrayList());
      Ref$IntRef var8 = new Ref$IntRef();
      var8.element = 0;
      this.a(var1, Reflection.getOrCreateKotlinClass(md.class), this.d(var1), (Function1)(new rj(this, var8, var7, var2, var4)));
      this.a(var1, Reflection.getOrCreateKotlinClass(ClassInfoList.class), (Function1)(new re(this, var7, var2)));
   }

   private final void a(md var1) {
      ClassNode var2 = var1.d();
      Iterator var4 = var2.fields.iterator();

      while(true) {
         while(true) {
            FieldNode var3;
            do {
               do {
                  if (!var4.hasNext()) {
                     return;
                  }

                  var3 = (FieldNode)var4.next();
               } while(!Intrinsics.areEqual((Object)var3.desc, (Object)"Ljava/lang/String;"));
            } while(var3.value == null);

            Object var5 = var3.value;
            boolean var6 = (var3.access & 8) != 0;
            MethodNode[] var7 = this.a(var2, var6);
            Type var8 = Type.getObjectType(var2.name);
            rp var9 = new rp(var6, var5, var8, var3);
            boolean var11 = false;
            if (var7.length == 0) {
               String var10 = var6 ? "<clinit>" : "<init>";
               PublicAccess var19 = PublicAccess.bln;
               boolean var20 = false;
               boolean var21 = false;
               boolean var16 = false;
               AccessWrapper var18 = var6 ? var19.a((AccessWrapper)StaticAccess.blo) : (AccessWrapper)var19;
               xi.a(var2, (Function1)(new rw(var18, var10, var9)));
            } else {
               var11 = false;
               MethodNode[] var12 = var7;
               int var13 = var7.length;

               for(int var14 = 0; var14 < var13; ++var14) {
                  MethodNode var15 = var12[var14];
                  boolean var17 = false;
                  this.a(var15.instructions, (Function0)(new rq(this, var9)));
               }
            }
         }
      }
   }

   private final MethodNode[] a(ClassNode var1, boolean var2) {
      String var3 = var2 ? "<clinit>" : "<init>";
      Iterable var4 = (Iterable)var1.methods;
      boolean var5 = false;
      Collection var7 = (Collection)(new ArrayList());
      boolean var8 = false;
      Iterator var9 = var4.iterator();

      Object var10;
      MethodNode var11;
      boolean var12;
      while(var9.hasNext()) {
         var10 = var9.next();
         var11 = (MethodNode)var10;
         var12 = false;
         if (Intrinsics.areEqual((Object)var11.name, (Object)var3)) {
            var7.add(var10);
         }
      }

      var4 = (Iterable)((List)var7);
      var5 = false;
      var7 = (Collection)(new ArrayList());
      var8 = false;
      var9 = var4.iterator();

      while(var9.hasNext()) {
         var10 = var9.next();
         var11 = (MethodNode)var10;
         var12 = false;
         AbstractInsnNode[] var13 = var11.instructions.toArray();
         boolean var14 = false;
         AbstractInsnNode[] var15 = var13;
         int var16 = var13.length;
         int var17 = 0;

         boolean var10000;
         while(true) {
            if (var17 >= var16) {
               var10000 = true;
               break;
            }

            AbstractInsnNode var18 = var15[var17];
            boolean var20 = false;
            if (var18 instanceof MethodInsnNode && Intrinsics.areEqual((Object)((MethodInsnNode)var18).owner, (Object)var1.name) && Intrinsics.areEqual((Object)((MethodInsnNode)var18).name, (Object)var3)) {
               var10000 = false;
               break;
            }

            ++var17;
         }

         if (var10000) {
            var7.add(var10);
         }
      }

      Collection var21 = (Collection)((List)var7);
      var5 = false;
      Object[] var22 = var21.toArray(new MethodNode[0]);
      if (var22 == null) {
         throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
      } else {
         return (MethodNode[])var22;
      }
   }

   public static MethodNode[] a(rn var0, ClassNode var1, boolean var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = false;
      }

      return var0.a(var1, var2);
   }

   private final void a(@NotNull InsnList var1, Function0 var2) {
      ListIterator var4 = var1.iterator();

      while(var4.hasNext()) {
         AbstractInsnNode var3 = (AbstractInsnNode)var4.next();
         int var5 = var3.getOpcode();
         if (172 <= var5) {
            if (177 >= var5) {
               var1.insertBefore(var3, (InsnList)var2.invoke());
            }
         }
      }

   }

   @NotNull
   protected StringConcealingStrategyConfig d() {
      return this.bfm;
   }

   public StrategyConfiguration c() {
      return (StrategyConfiguration)this.d();
   }

   public rn(@NotNull StringConcealingStrategyConfig var1) {
      super((mq)null, 1, (DefaultConstructorMarker)null);
      this.bfm = var1;
      this.bfk = LazyKt.lazy((Function0)(new rb(this)));
      this.bfl = LazyKt.lazy((Function0)(new qs(this)));
   }

   public static final void a(rn var0, md var1) {
      var0.a(var1);
   }

   public static final Function1 a(rn var0, kg var1) {
      return var0.e(var1);
   }

   public static final String a(rn var0) {
      String var10000 = var0.bfj;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("[Hidden]");
      }

      return var10000;
   }

   public static final void a(rn var0, String var1) {
      var0.bfj = var1;
   }

   public static final String b(rn var0) {
      return var0.a();
   }

   public static final Type c(rn var0) {
      return var0.b();
   }
}
