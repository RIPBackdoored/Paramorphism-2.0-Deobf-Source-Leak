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
import paramorphism-obfuscator.wrappers.AccessWrapper;
import paramorphism-obfuscator.wrappers.ClassWrapper;

public final class qf extends Lambda implements Function1 {
   public final String bct;

   public Object invoke(Object var1) {
      this.a((ClassWrapper)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull ClassWrapper var1) {
      ClassWrapper.addMethod(var1, var1.e().a((AccessWrapper)var1.w()), this.bct, Reflection.getOrCreateKotlinClass(CallSite.class), new Object[]{Reflection.getOrCreateKotlinClass(Lookup.class), Reflection.getOrCreateKotlinClass(MethodType.class), var1.bi(), var1.bi()}, (String)null, (Type[])null, (Function1)py.bce, 48, (Object)null);
   }

   public qf(String var1) {
      super(1);
      this.bct = var1;
   }
}
