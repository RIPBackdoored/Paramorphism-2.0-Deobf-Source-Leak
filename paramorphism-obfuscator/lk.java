package paramorphism-obfuscator;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.annotations.NotNull;
import paramorphism-obfuscator.instruction.ArrayVarInstruction;
import paramorphism-obfuscator.instruction.JumpInstruction;
import paramorphism-obfuscator.instruction.MethodInstruction;
import paramorphism-obfuscator.instruction.NumberInstruction;
import paramorphism-obfuscator.instruction.VarInstruction;
import paramorphism-obfuscator.wrappers.IInstructionWrapper;

public final class lk {
   public static final int a(@NotNull IInstructionWrapper var0, int var1) {
      yf.a(var0, Reflection.getOrCreateKotlinClass(ByteArrayOutputStream.class), new Object[]{Primitives.getVoid(), Primitives.getInt()}, (String)null, (Function1)(new lj(var1)), 4, (Object)null);
      int var2 = var1 + 1;
      VarInstruction.addObjectStore(var0, var2);
      NumberInstruction.addBigInt(var0, 8192);
      ArrayVarInstruction.addIndexInt(var0, Primitives.getByte());
      int var3 = var2 + 1;
      VarInstruction.addObjectStore(var0, var3);
      VarInstruction.addObjectLoad(var0, var1);
      VarInstruction.addObjectLoad(var0, var3);
      MethodInstruction.addInvokeVirtual(var0, Reflection.getOrCreateKotlinClass(InputStream.class), "read", Primitives.getInt(), Reflection.getOrCreateKotlinClass(byte[].class));
      int var4 = var3 + 1;
      VarInstruction.addIntStore(var0, var4);
      ((yi)var0).a().a(0).a();
      VarInstruction.addIntLoad(var0, var4);
      JumpInstruction.addIfLess(var0, ((yi)var0).a().a(1));
      VarInstruction.addObjectLoad(var0, var2);
      VarInstruction.addObjectLoad(var0, var3);
      NumberInstruction.addIZero(var0);
      VarInstruction.addIntLoad(var0, var4);
      MethodInstruction.addInvokeVirtual(var0, Reflection.getOrCreateKotlinClass(ByteArrayOutputStream.class), "write", Primitives.getVoid(), Reflection.getOrCreateKotlinClass(byte[].class), Primitives.getInt(), Primitives.getInt());
      VarInstruction.addObjectLoad(var0, var1);
      VarInstruction.addObjectLoad(var0, var3);
      MethodInstruction.addInvokeVirtual(var0, Reflection.getOrCreateKotlinClass(InputStream.class), "read", Primitives.getInt(), Reflection.getOrCreateKotlinClass(byte[].class));
      VarInstruction.addIntStore(var0, var4);
      JumpInstruction.addGoto(var0, ((yi)var0).a().a(0));
      ((yi)var0).a().a(1).a();
      VarInstruction.addObjectLoad(var0, var2);
      MethodInstruction.addInvokeVirtual(var0, Reflection.getOrCreateKotlinClass(ByteArrayOutputStream.class), "toByteArray", Reflection.getOrCreateKotlinClass(byte[].class));
      VarInstruction.addObjectStore(var0, var3);
      return var3;
   }
}
