package paramorphism-obfuscator;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodType;
import java.lang.invoke.MethodHandles.Lookup;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import paramorphism-obfuscator.wrappers.AccessWrapper;
import paramorphism-obfuscator.wrappers.ClassWrapper;

public final class sb extends Lambda implements Function1 {
   public final ClassNode bgp;
   public final Type bgq;

   public Object invoke(Object var1) {
      this.a((ClassWrapper)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull ClassWrapper var1) {
      ClassWrapper.addField(var1, var1.e().a((AccessWrapper)var1.h()).addAccess((AccessWrapper)var1.y()), var1.bi(), "guard", (String)null, (Object)null, 24, (Object)null);
      ClassWrapper.addMethod(var1, var1.e().a((AccessWrapper)var1.h()), "main", var1.be(), new Object[]{Reflection.getOrCreateKotlinClass(String[].class)}, (String)null, (Type[])null, (Function1)(new sd(this, var1)), 48, (Object)null);
      ClassWrapper.addMethod(var1, var1.f().a((AccessWrapper)var1.h()), "link", Reflection.getOrCreateKotlinClass(CallSite.class), new Object[]{Reflection.getOrCreateKotlinClass(Lookup.class), Reflection.getOrCreateKotlinClass(String.class), Reflection.getOrCreateKotlinClass(MethodType.class)}, (String)null, (Type[])null, (Function1)(new ry(this)), 48, (Object)null);
   }

   public sb(ClassNode var1, Type var2) {
      super(1);
      this.bgp = var1;
      this.bgq = var2;
   }
}
