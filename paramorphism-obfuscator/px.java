package paramorphism-obfuscator;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodType;
import java.lang.invoke.MethodHandles.Lookup;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import paramorphism-obfuscator.wrappers.AccessWrapper;
import paramorphism-obfuscator.wrappers.ClassWrapper;

public final class px extends Lambda implements Function1 {
   public final ClassNode bca;
   public final String bcb;
   public final List bcc;
   public final List bcd;

   public Object invoke(Object var1) {
      this.a((ClassWrapper)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull ClassWrapper var1) {
      FieldNode var2 = ClassWrapper.addField(var1, var1.f().a((AccessWrapper)var1.h()), Reflection.getOrCreateKotlinClass(Object[].class), "a", (String)null, (Object)null, 24, (Object)null);
      ClassWrapper.addMethod(var1, var1.e().a((AccessWrapper)var1.h()), "bootstrap", Reflection.getOrCreateKotlinClass(CallSite.class), new Object[]{Reflection.getOrCreateKotlinClass(Lookup.class), Reflection.getOrCreateKotlinClass(String.class), Reflection.getOrCreateKotlinClass(MethodType.class), var1.bk(), var1.bi()}, (String)null, (Type[])null, (Function1)(new pu(this, var1, var2)), 48, (Object)null);
      AccessWrapper var10001 = (AccessWrapper)var1.h();
      Type var10003 = var1.be();
      Function1 var10004 = (Function1)(new qi(this, var1, var2));
      Object[] var3 = new Object[0];
      Object var4 = null;
      Object var5 = null;
      Function1 var6 = var10004;
      ClassWrapper.addMethod(var1, var10001, "<clinit>", var10003, var3, (String)var5, (Type[])var4, var6, 48, (Object)null);
   }

   public px(ClassNode var1, String var2, List var3, List var4) {
      super(1);
      this.bca = var1;
      this.bcb = var2;
      this.bcc = var3;
      this.bcd = var4;
   }
}
