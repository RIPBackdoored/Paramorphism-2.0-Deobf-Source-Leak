package paramorphism-obfuscator;

import java.io.ByteArrayInputStream;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.annotations.NotNull;
import paramorphism-obfuscator.wrappers.IInstructionWrapper;
import paramorphism-obfuscator.wrappers.MethodWrapper;

public final class mw extends Lambda implements Function1 {
   public final int vw;

   public mw(int var1) {
      super(1);
      this.vw = var1;
   }

   public Object invoke(Object var1) {
      this.a((MethodWrapper)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull MethodWrapper var1) {
      yf.a((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(ByteArrayInputStream.class), new Object[]{var1.be(), Reflection.getOrCreateKotlinClass(byte[].class)}, (String)null, (Function1)(new nj(this)), 4, (Object)null);
   }
}
