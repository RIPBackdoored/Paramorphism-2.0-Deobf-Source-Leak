package paramorphism-obfuscator.instruction;

import org.jetbrains.annotations.NotNull;
import paramorphism-obfuscator.wrappers.IInstructionWrapper;

public final class IntegerInstruction {
   public static final void addInteger(@NotNull IInstructionWrapper var0, int var1) {
      if (var1 == -1) {
         NumberInstruction.addNot(var0);
      } else if (var1 == 0) {
         NumberInstruction.addIZero(var0);
      } else if (var1 == 1) {
         NumberInstruction.addIOne(var0);
      } else if (var1 == 2) {
         NumberInstruction.addITwo(var0);
      } else if (var1 == 3) {
         NumberInstruction.addIThree(var0);
      } else if (var1 == 4) {
         NumberInstruction.addIFour(var0);
      } else if (var1 == 5) {
         NumberInstruction.addIFive(var0);
      } else {
         if (-128 <= var1) {
            if (127 >= var1) {
               NumberInstruction.addSmallInt(var0, var1);
               return;
            }
         }

         if (-32768 <= var1) {
            if (32767 >= var1) {
               NumberInstruction.addBigInt(var0, var1);
               return;
            }
         }

         NumberInstruction.addLdc(var0, var1);
      }

   }
}
