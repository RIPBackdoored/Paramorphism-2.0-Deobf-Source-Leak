package paramorphism-obfuscator;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Type;
import paramorphism-obfuscator.wrappers.AccessWrapper;
import paramorphism-obfuscator.wrappers.ClassWrapper;

public final class si extends Lambda implements Function1 {
   public static final si bgy = new si();

   public Object invoke(Object var1) {
      this.a((ClassWrapper)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull ClassWrapper var1) {
      AccessWrapper var10001 = (AccessWrapper)var1.f();
      Type var10003 = var1.be();
      Function1 var10004 = (Function1)sc.bgr;
      Object[] var2 = new Object[0];
      Object var3 = null;
      Object var4 = null;
      Function1 var5 = var10004;
      ClassWrapper.addMethod(var1, var10001, "<init>", var10003, var2, (String)var4, (Type[])var3, var5, 48, (Object)null);
   }

   public si() {
      super(1);
   }
}
