package paramorphism-obfuscator;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodType;
import java.lang.invoke.MethodHandles.Lookup;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Handle;
import paramorphism-obfuscator.instruction.InvokeDynamicInstruction;
import paramorphism-obfuscator.wrappers.IInstructionWrapper;
import paramorphism-obfuscator.wrappers.InstructionWrapper;

public final class rk extends Lambda implements Function1 {
   public final rj bfc;
   public final int bfd;
   public final ra bfe;
   public final String bff;

   public Object invoke(Object var1) {
      this.a((InstructionWrapper)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull InstructionWrapper var1) {
      long var2 = (long)this.bfd << 32 | (long)CollectionsKt.indexOf((Iterable)this.bfe.b().keySet(), this.bff);
      IInstructionWrapper var10000 = (IInstructionWrapper)var1;
      KClass var10002 = Reflection.getOrCreateKotlinClass(String.class);
      Handle var10003 = InvokeDynamicInstruction.getStaticHandle((IInstructionWrapper)var1, rn.c(this.bfc.bex), "bootstrap", Reflection.getOrCreateKotlinClass(CallSite.class), Reflection.getOrCreateKotlinClass(Lookup.class), Reflection.getOrCreateKotlinClass(String.class), Reflection.getOrCreateKotlinClass(MethodType.class), var1.bk());
      Long[] var10004 = new Long[]{var2};
      Object[] var4 = new Object[0];
      Long[] var5 = var10004;
      Handle var6 = var10003;
      InvokeDynamicInstruction.addInvokeDynamic(var10000, "get", var10002, var4, var6, var5);
   }

   public rk(rj var1, int var2, ra var3, String var4) {
      super(1);
      this.bfc = var1;
      this.bfd = var2;
      this.bfe = var3;
      this.bff = var4;
   }
}
