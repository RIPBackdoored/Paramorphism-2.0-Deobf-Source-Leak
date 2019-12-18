package paramorphism-obfuscator;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Random;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Ref$IntRef;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.InsnList;
import paramorphism-obfuscator.instruction.ArrayVarInstruction;
import paramorphism-obfuscator.instruction.JumpInstruction;
import paramorphism-obfuscator.instruction.MathInstruction;
import paramorphism-obfuscator.instruction.NumberInstruction;
import paramorphism-obfuscator.instruction.VarInstruction;
import paramorphism-obfuscator.wrappers.IInstructionWrapper;
import paramorphism-obfuscator.wrappers.MethodWrapper;

public final class nk implements nd {
   private final Random wr;

   @NotNull
   public byte[] a(@NotNull byte[] var1) {
      ByteArrayOutputStream var2 = new ByteArrayOutputStream();
      Closeable var3 = (Closeable)(new GZIPOutputStream((OutputStream)var2));
      boolean var4 = false;
      Throwable var5 = (Throwable)null;
      boolean var21 = false;

      boolean var7;
      try {
         var21 = true;
         GZIPOutputStream var6 = (GZIPOutputStream)var3;
         var7 = false;
         var6.write(var1);
         Unit var26 = Unit.INSTANCE;
         var21 = false;
      } catch (Throwable var22) {
         var5 = var22;
         throw var22;
      } finally {
         if (var21) {
            CloseableKt.closeFinally(var3, var5);
         }
      }

      CloseableKt.closeFinally(var3, var5);
      byte[] var24 = var2.toByteArray();
      var4 = false;
      boolean var25 = false;
      byte[] var27 = var24;
      var7 = false;
      int[] var8 = new int[]{202, 254, 186, 190, 0, 0, 0, 52, this.wr.nextInt(), this.wr.nextInt()};
      boolean var10 = false;
      int var11 = 0;
      int[] var12 = var8;
      int var13 = var8.length;

      for(int var14 = 0; var14 < var13; ++var14) {
         int var15 = var12[var14];
         int var17 = var11++;
         boolean var18 = false;
         var27[var17] = (byte)var15;
      }

      return var24;
   }

   public int a(@NotNull MethodWrapper var1, int var2) {
      boolean var3 = false;
      boolean var4 = false;
      boolean var6 = false;
      int var7 = var2 + 1;
      NumberInstruction.addSmallInt((IInstructionWrapper)var1, 7);
      VarInstruction.addIntStore((IInstructionWrapper)var1, var7);
      var1.getLabels().a(1).a();
      VarInstruction.addIntLoad((IInstructionWrapper)var1, var7);
      JumpInstruction.addIfLess((IInstructionWrapper)var1, var1.getLabels().a(2));
      VarInstruction.addObjectLoad((IInstructionWrapper)var1, var2);
      NumberInstruction.addSmallInt((IInstructionWrapper)var1, 7);
      VarInstruction.addIntLoad((IInstructionWrapper)var1, var7);
      MathInstruction.addISubtract((IInstructionWrapper)var1);
      NumberInstruction.addLdc((IInstructionWrapper)var1, 2272919233031569408L);
      NumberInstruction.addSmallInt((IInstructionWrapper)var1, 8);
      VarInstruction.addIntLoad((IInstructionWrapper)var1, var7);
      MathInstruction.addIMultiply((IInstructionWrapper)var1);
      MathInstruction.addLShiftRight((IInstructionWrapper)var1);
      NumberInstruction.addLdc((IInstructionWrapper)var1, 255L);
      MathInstruction.addLAnd((IInstructionWrapper)var1);
      MathInstruction.addLongToInt((IInstructionWrapper)var1);
      MathInstruction.addIntToByte((IInstructionWrapper)var1);
      ArrayVarInstruction.addByteArrayStore((IInstructionWrapper)var1);
      MathInstruction.addIIncrement((IInstructionWrapper)var1, var7, -1);
      JumpInstruction.addGoto((IInstructionWrapper)var1, var1.getLabels().a(1));
      var1.getLabels().a(2).a();
      int var8 = var7 + 1;
      yf.a((IInstructionWrapper)var1, Reflection.getOrCreateKotlinClass(GZIPInputStream.class), new Object[]{var1.be(), Reflection.getOrCreateKotlinClass(InputStream.class)}, (String)null, (Function1)(new mw(var2)), 4, (Object)null);
      VarInstruction.addObjectStore((IInstructionWrapper)var1, var8);
      Ref$IntRef var9 = new Ref$IntRef();
      var9.element = -1;
      Pair var10 = xg.a((Function1)(new nm(var9, var8)));
      boolean var11 = false;
      boolean var12 = false;
      boolean var14 = false;
      var1.getInstructions().add((InsnList)var10.getFirst());
      var1.getExceptions().addAll((Collection)var10.getSecond());
      return var9.element;
   }

   public nk(@NotNull Random var1) {
      super();
      this.wr = var1;
   }
}
