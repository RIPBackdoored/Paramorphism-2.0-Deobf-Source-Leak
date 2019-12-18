package paramorphism-obfuscator;

import java.util.Iterator;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Reflection;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import paramorphism-obfuscator.instruction.MethodInstruction;
import paramorphism-obfuscator.instruction.NumberInstruction;
import paramorphism-obfuscator.instruction.StackInstruction;
import paramorphism-obfuscator.instruction.TypeInstruction;
import paramorphism-obfuscator.wrappers.ClassWrapper;
import paramorphism-obfuscator.wrappers.IInstructionWrapper;

public final class rs extends Lambda implements Function2 {
   public final ClassWrapper bfw;

   public Object invoke(Object var1, Object var2) {
      this.a((IInstructionWrapper)var1, (String)var2);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull IInstructionWrapper var1, @NotNull String var2) {
      if (var2.length() > 32627) {
         TypeInstruction.addNew(var1, Reflection.getOrCreateKotlinClass(StringBuilder.class));
         StackInstruction.addDup(var1);
         MethodInstruction.addInvokeSpecial(var1, Reflection.getOrCreateKotlinClass(StringBuilder.class), "<init>", this.bfw.be());
         Iterator var4 = StringsKt.chunked((CharSequence)var2, 32627).iterator();

         while(var4.hasNext()) {
            String var3 = (String)var4.next();
            NumberInstruction.addLdc(var1, var3);
            MethodInstruction.addInvokeVirtual(var1, Reflection.getOrCreateKotlinClass(StringBuilder.class), "append", Reflection.getOrCreateKotlinClass(StringBuilder.class), Reflection.getOrCreateKotlinClass(String.class));
         }

         MethodInstruction.addInvokeVirtual(var1, Reflection.getOrCreateKotlinClass(StringBuilder.class), "toString", Reflection.getOrCreateKotlinClass(String.class));
      } else {
         NumberInstruction.addLdc(var1, var2);
      }

   }

   public rs(ClassWrapper var1) {
      super(2);
      this.bfw = var1;
   }
}
