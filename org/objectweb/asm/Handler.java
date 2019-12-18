package org.objectweb.asm;

final class Handler {
   final Label startPc;
   final Label endPc;
   final Label handlerPc;
   final int catchType;
   final String catchTypeDescriptor;
   Handler nextHandler;

   Handler(Label var1, Label var2, Label var3, int var4, String var5) {
      super();
      this.startPc = var1;
      this.endPc = var2;
      this.handlerPc = var3;
      this.catchType = var4;
      this.catchTypeDescriptor = var5;
   }

   Handler(Handler var1, Label var2, Label var3) {
      this(var2, var3, var1.handlerPc, var1.catchType, var1.catchTypeDescriptor);
      this.nextHandler = var1.nextHandler;
   }

   static Handler removeRange(Handler var0, Label var1, Label var2) {
      if (var0 == null) {
         return null;
      } else {
         var0.nextHandler = removeRange(var0.nextHandler, var1, var2);
         int var3 = var0.startPc.bytecodeOffset;
         int var4 = var0.endPc.bytecodeOffset;
         int var5 = var1.bytecodeOffset;
         int var6 = var2 == null ? 0 : var2.bytecodeOffset;
         if (var5 < var4 && var6 > var3) {
            if (var5 <= var3) {
               return var6 >= var4 ? var0.nextHandler : new Handler(var0, var2, var0.endPc);
            } else if (var6 >= var4) {
               return new Handler(var0, var0.startPc, var1);
            } else {
               var0.nextHandler = new Handler(var0, var2, var0.endPc);
               return new Handler(var0, var0.startPc, var1);
            }
         } else {
            return var0;
         }
      }
   }

   static int getExceptionTableLength(Handler var0) {
      int var1 = 0;

      for(Handler var2 = var0; var2 != null; var2 = var2.nextHandler) {
         ++var1;
      }

      return var1;
   }

   static int getExceptionTableSize(Handler var0) {
      return 2 + 8 * getExceptionTableLength(var0);
   }

   static void putExceptionTable(Handler var0, ByteVector var1) {
      var1.putShort(getExceptionTableLength(var0));

      for(Handler var2 = var0; var2 != null; var2 = var2.nextHandler) {
         var1.putShort(var2.startPc.bytecodeOffset).putShort(var2.endPc.bytecodeOffset).putShort(var2.handlerPc.bytecodeOffset).putShort(var2.catchType);
      }

   }
}
