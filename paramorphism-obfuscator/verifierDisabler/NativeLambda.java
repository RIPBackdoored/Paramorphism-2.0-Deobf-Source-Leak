package paramorphism-obfuscator.verifierDisabler;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.FieldNode;
import paramorphism-obfuscator.kl;
import paramorphism-obfuscator.kr;
import paramorphism-obfuscator.kt;
import paramorphism-obfuscator.ky;
import paramorphism-obfuscator.lu;
import paramorphism-obfuscator.wrappers.AccessWrapper;
import paramorphism-obfuscator.wrappers.ClassWrapper;

public final class NativeLambda extends Lambda implements Function1 {
   public final lu rv;

   public Object invoke(Object var1) {
      this.a((ClassWrapper)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull ClassWrapper var1) {
      kr var2 = new kr(this);
      ky var3 = new ky(this);
      AccessWrapper var10001 = var1.f().a((AccessWrapper)var1.h());
      Type var10002 = var1.bm();
      String var10003 = var2.a(0);
      if (var10003 == null) {
         var10003 = "done";
      }

      FieldNode var4 = ClassWrapper.addField(var1, var10001, var10002, var10003, (String)null, (Object)null, 24, (Object)null);
      var10001 = var1.f().a((AccessWrapper)var1.h());
      KClass var26 = Reflection.getOrCreateKotlinClass(ClassLoader.class);
      var10003 = var2.a(1);
      if (var10003 == null) {
         var10003 = "classLoader";
      }

      FieldNode var5 = ClassWrapper.addField(var1, var10001, var26, var10003, (String)null, (Object)null, 24, (Object)null);
      var10001 = var1.f().a((AccessWrapper)var1.h());
      var26 = Reflection.getOrCreateKotlinClass(Method.class);
      var10003 = var2.a(2);
      if (var10003 == null) {
         var10003 = "findNative";
      }

      FieldNode var6 = ClassWrapper.addField(var1, var10001, var26, var10003, (String)null, (Object)null, 24, (Object)null);
      var10001 = var1.f().a((AccessWrapper)var1.h());
      var26 = Reflection.getOrCreateKotlinClass(Map.class);
      var10003 = var2.a(3);
      if (var10003 == null) {
         var10003 = "types";
      }

      FieldNode var7 = ClassWrapper.addField(var1, var10001, var26, var10003, (String)null, (Object)null, 24, (Object)null);
      var10001 = var1.f().a((AccessWrapper)var1.h());
      var26 = Reflection.getOrCreateKotlinClass(Map.class);
      var10003 = var2.a(4);
      if (var10003 == null) {
         var10003 = "structs";
      }

      FieldNode var8 = ClassWrapper.addField(var1, var10001, var26, var10003, (String)null, (Object)null, 24, (Object)null);
      var10001 = var1.f().a((AccessWrapper)var1.i()).addAccess((AccessWrapper)var1.h());
      var10003 = var2.a(5);
      if (var10003 == null) {
         var10003 = "unsafe";
      }

      FieldNode var9 = ClassWrapper.addField(var1, var10001, "sun/misc/Unsafe", var10003, (String)null, (Object)null, 24, (Object)null);
      String var10000 = var3.a(0);
      if (var10000 == null) {
         var10000 = "getUnsafe";
      }

      String var10 = var10000;
      var10000 = var3.a(1);
      if (var10000 == null) {
         var10000 = "resolveClassLoader";
      }

      String var11 = var10000;
      var10000 = var3.a(2);
      if (var10000 == null) {
         var10000 = "setupIntrospection";
      }

      String var12 = var10000;
      var10000 = var3.a(3);
      if (var10000 == null) {
         var10000 = "getSymbol";
      }

      String var13 = var10000;
      var10000 = var3.a(4);
      if (var10000 == null) {
         var10000 = "typeFields";
      }

      String var14 = var10000;
      var10000 = var3.a(5);
      if (var10000 == null) {
         var10000 = "fieldOffset";
      }

      String var15 = var10000;
      var10000 = var3.a(6);
      if (var10000 == null) {
         var10000 = "typeField";
      }

      String var16 = var10000;
      var10000 = var3.a(7);
      if (var10000 == null) {
         var10000 = "getString";
      }

      String var17 = var10000;
      var10000 = var3.a(8);
      if (var10000 == null) {
         var10000 = "readStructs";
      }

      String var18 = var10000;
      var10000 = var3.a(9);
      if (var10000 == null) {
         var10000 = "readTypes";
      }

      String var19 = var10000;
      var10000 = var3.a(10);
      if (var10000 == null) {
         var10000 = "getFlagType";
      }

      String var20 = var10000;
      String var21 = "run";
      var10001 = (AccessWrapper)var1.h();
      Type var27 = var1.be();
      Function1 var10004 = (Function1)(new UnsafeLambda(var1, var4, var10, var9));
      Object[] var22 = new Object[0];
      Object var23 = null;
      Object var24 = null;
      Function1 var25 = var10004;
      ClassWrapper.addMethod(var1, var10001, "<clinit>", var27, var22, (String)var24, (Type[])var23, var25, 48, (Object)null);
      var10001 = var1.f().a((AccessWrapper)var1.h());
      var10004 = (Function1)ReflectionLambda.ru;
      var22 = new Object[0];
      var23 = null;
      var24 = null;
      var25 = var10004;
      ClassWrapper.addMethod(var1, var10001, var10, "sun/misc/Unsafe", var22, (String)var24, (Type[])var23, var25, 48, (Object)null);
      var10001 = var1.f().a((AccessWrapper)var1.h());
      var27 = var1.be();
      var10004 = (Function1)(new JVMDllLambda(var1, var5, var6, var9));
      var22 = new Object[0];
      var23 = null;
      var24 = null;
      var25 = var10004;
      ClassWrapper.addMethod(var1, var10001, var11, var27, var22, (String)var24, (Type[])var23, var25, 48, (Object)null);
      var10001 = var1.f().a((AccessWrapper)var1.h());
      var27 = var1.be();
      var10004 = (Function1)(new kt(var1, var11, var7, var8, var18, var19));
      var22 = new Object[0];
      var23 = null;
      var24 = null;
      var25 = var10004;
      ClassWrapper.addMethod(var1, var10001, var12, var27, var22, (String)var24, (Type[])var23, var25, 48, (Object)null);
      ClassWrapper.addMethod(var1, var1.f().a((AccessWrapper)var1.h()), var13, var1.bk(), new Object[]{Reflection.getOrCreateKotlinClass(String.class)}, (String)null, (Type[])null, (Function1)(new InvokeLambda(var1, var6, var5, var9)), 48, (Object)null);
      ClassWrapper.addMethod(var1, var1.f().a((AccessWrapper)var1.h()), var14, Reflection.getOrCreateKotlinClass(Set.class), new Object[]{Reflection.getOrCreateKotlinClass(Object[].class)}, (String)null, (Type[])null, (Function1)kl.rr, 48, (Object)null);
      ClassWrapper.addMethod(var1, var1.f().a((AccessWrapper)var1.h()), var15, var1.bk(), new Object[]{Reflection.getOrCreateKotlinClass(Object[].class)}, (String)null, (Type[])null, (Function1)LongValueLambda.rw, 48, (Object)null);
      ClassWrapper.addMethod(var1, var1.f().a((AccessWrapper)var1.h()), var16, Reflection.getOrCreateKotlinClass(Object[].class), new Object[]{Reflection.getOrCreateKotlinClass(Object[].class), Reflection.getOrCreateKotlinClass(String.class)}, (String)null, (Type[])null, (Function1)(new IteratorLambda(var1, var14)), 48, (Object)null);
      ClassWrapper.addMethod(var1, var1.f().a((AccessWrapper)var1.h()), var17, Reflection.getOrCreateKotlinClass(String.class), new Object[]{var1.bk()}, (String)null, (Type[])null, (Function1)(new GetByteLambda(var1, var9)), 48, (Object)null);
      ClassWrapper.addMethod(var1, var1.f().a((AccessWrapper)var1.h()), var18, var1.be(), new Object[]{Reflection.getOrCreateKotlinClass(Map.class)}, (String)null, (Type[])null, (Function1)(new HotspotLambda(var1, var13, var9, var17)), 48, (Object)null);
      ClassWrapper.addMethod(var1, var1.f().a((AccessWrapper)var1.h()), var19, var1.be(), new Object[]{Reflection.getOrCreateKotlinClass(Map.class), Reflection.getOrCreateKotlinClass(Map.class)}, (String)null, (Type[])null, (Function1)(new PutUnsafeLambda(var1, var13, var9, var17)), 48, (Object)null);
      var10001 = var1.f().a((AccessWrapper)var1.h());
      KClass var28 = Reflection.getOrCreateKotlinClass(Object[].class);
      var10004 = (Function1)(new JVMFlagLambda(var1, var7));
      var22 = new Object[0];
      var23 = null;
      var24 = null;
      var25 = var10004;
      ClassWrapper.addMethod(var1, var10001, var20, var28, var22, (String)var24, (Type[])var23, var25, 48, (Object)null);
      var10001 = var1.e().a((AccessWrapper)var1.h());
      var27 = var1.be();
      var10004 = (Function1)(new PutByteLambda(var1, var4, var12, var20, var16, var9, var15, var17));
      var22 = new Object[0];
      var23 = null;
      var24 = null;
      var25 = var10004;
      ClassWrapper.addMethod(var1, var10001, var21, var27, var22, (String)var24, (Type[])var23, var25, 48, (Object)null);
   }

   public NativeLambda(lu var1) {
      super(1);
      this.rv = var1;
   }
}
