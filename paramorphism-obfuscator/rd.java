package paramorphism-obfuscator;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Base64.Decoder;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.MethodNode;
import paramorphism-obfuscator.instruction.FieldInstruction;
import paramorphism-obfuscator.instruction.MethodInstruction;
import paramorphism-obfuscator.instruction.StackInstruction;
import paramorphism-obfuscator.instruction.TypeInstruction;
import paramorphism-obfuscator.wrappers.ClassWrapper;
import paramorphism-obfuscator.wrappers.IInstructionWrapper;
import paramorphism-obfuscator.wrappers.InstructionWrapper;

public final class rd extends Lambda implements Function1 {
   public final ClassWrapper bek;
   public final int bel;
   public final Set bem;
   public final Map ben;
   public final rs beo;
   public final String bep;
   public final MethodNode beq;

   public Object invoke(Object var1) {
      this.a((InstructionWrapper)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull InstructionWrapper var1) {
      byte[] var2 = qz.b(this.bel, this.bem, this.ben);
      MethodInstruction.addInvokeStatic((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(Base64.class), "getDecoder", Reflection.getOrCreateKotlinClass(Decoder.class));
      this.beo.a((IInstructionWrapper)var1, Base64.getEncoder().encodeToString(var2));
      MethodInstruction.addInvokeVirtual((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(Decoder.class), "decode", Reflection.getOrCreateKotlinClass(byte[].class), Reflection.getOrCreateKotlinClass(String.class));
      TypeInstruction.addNew((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(HashMap.class));
      StackInstruction.addDup((IInstructionWrapper)var1);
      MethodInstruction.addInvokeSpecial((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(HashMap.class), "<init>", var1.be());
      StackInstruction.addDup((IInstructionWrapper)var1);
      FieldInstruction.addPutStatic((IInstructionWrapper)var1, this.bek.getType(), this.bep, Reflection.getOrCreateKotlinClass(Map.class));
      MethodInstruction.addInvokeStatic((IInstructionWrapper)var1, this.bek.getType(), this.beq.name, var1.be(), Reflection.getOrCreateKotlinClass(byte[].class), Reflection.getOrCreateKotlinClass(Map.class));
   }

   public rd(ClassWrapper var1, int var2, Set var3, Map var4, rs var5, String var6, MethodNode var7) {
      super(1);
      this.bek = var1;
      this.bel = var2;
      this.bem = var3;
      this.ben = var4;
      this.beo = var5;
      this.bep = var6;
      this.beq = var7;
   }
}
