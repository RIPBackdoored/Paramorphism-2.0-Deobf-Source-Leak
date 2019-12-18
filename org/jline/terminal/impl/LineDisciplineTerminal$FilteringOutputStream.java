package org.jline.terminal.impl;

import java.io.IOException;
import java.io.OutputStream;

class LineDisciplineTerminal$FilteringOutputStream extends OutputStream {
   final LineDisciplineTerminal this$0;

   private LineDisciplineTerminal$FilteringOutputStream(LineDisciplineTerminal var1) {
      super();
      this.this$0 = var1;
   }

   public void write(int var1) throws IOException {
      this.this$0.processOutputByte(var1);
      this.flush();
   }

   public void write(byte[] var1, int var2, int var3) throws IOException {
      if (var1 == null) {
         throw new NullPointerException();
      } else if (var2 >= 0 && var2 <= var1.length && var3 >= 0 && var2 + var3 <= var1.length && var2 + var3 >= 0) {
         if (var3 != 0) {
            for(int var4 = 0; var4 < var3; ++var4) {
               this.this$0.processOutputByte(var1[var2 + var4]);
            }

            this.flush();
         }
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public void flush() throws IOException {
      this.this$0.masterOutput.flush();
   }

   public void close() throws IOException {
      this.this$0.masterOutput.close();
   }

   LineDisciplineTerminal$FilteringOutputStream(LineDisciplineTerminal var1, LineDisciplineTerminal$1 var2) {
      this(var1);
   }
}
