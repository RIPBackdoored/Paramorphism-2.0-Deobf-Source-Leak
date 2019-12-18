package paramorphism-obfuscator;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Type;
import paramorphism-obfuscator.wrappers.AccessWrapper;
import paramorphism-obfuscator.wrappers.ClassWrapper;

public final class rw extends Lambda implements Function1 {
   public final AccessWrapper bgd;
   public final String bge;
   public final rp bgf;

   public Object invoke(Object var1) {
      this.a((ClassWrapper)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull ClassWrapper var1) {
      AccessWrapper var10001 = this.bgd;
      String var10002 = this.bge;
      Type var10003 = var1.be();
      Function1 var10004 = (Function1)(new qx(this));
      Object[] var2 = new Object[0];
      Object var3 = null;
      Object var4 = null;
      Function1 var5 = var10004;
      ClassWrapper.addMethod(var1, var10001, var10002, var10003, var2, (String)var4, (Type[])var3, var5, 48, (Object)null);
   }

   public rw(AccessWrapper var1, String var2, rp var3) {
      super(1);
      this.bgd = var1;
      this.bge = var2;
      this.bgf = var3;
   }
}
