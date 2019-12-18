package paramorphism-obfuscator;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodType;
import java.lang.invoke.MethodHandles.Lookup;
import java.util.Map;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;
import paramorphism-obfuscator.wrappers.AccessWrapper;
import paramorphism-obfuscator.wrappers.ClassWrapper;

public final class qb extends Lambda implements Function1 {
   public final String bch;
   public final qm bci;
   public final String bcj;

   public Object invoke(Object var1) {
      this.a((ClassWrapper)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull ClassWrapper var1) {
      var1.getClassNode().interfaces = CollectionsKt.listOf(this.bch);
      FieldNode var2 = ClassWrapper.addField(var1, (AccessWrapper)var1.f(), Reflection.getOrCreateKotlinClass(Map.class), "lookups", (String)null, (Object)null, 24, (Object)null);
      MethodNode var3 = ClassWrapper.addMethod(var1, (AccessWrapper)var1.f(), "!!", var1.be(), new Object[]{Reflection.getOrCreateKotlinClass(byte[].class)}, (String)null, (Type[])null, (Function1)(new qh(var1, var2)), 48, (Object)null);
      AccessWrapper var10001 = (AccessWrapper)var1.e();
      Type var10003 = var1.be();
      Function1 var10004 = (Function1)(new qr(this, var1, var2, var3));
      Object[] var4 = new Object[0];
      Object var5 = null;
      Object var6 = null;
      Function1 var7 = var10004;
      ClassWrapper.addMethod(var1, var10001, "<init>", var10003, var4, (String)var6, (Type[])var5, var7, 48, (Object)null);
      ClassWrapper.addMethod(var1, (AccessWrapper)var1.e(), this.bcj, Reflection.getOrCreateKotlinClass(CallSite.class), new Object[]{Reflection.getOrCreateKotlinClass(Lookup.class), Reflection.getOrCreateKotlinClass(MethodType.class), var1.bi(), var1.bi()}, (String)null, (Type[])null, (Function1)(new pv(var1, var2)), 48, (Object)null);
   }

   public qb(String var1, qm var2, String var3) {
      super(1);
      this.bch = var1;
      this.bci = var2;
      this.bcj = var3;
   }
}
