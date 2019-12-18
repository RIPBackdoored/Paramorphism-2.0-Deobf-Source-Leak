package org.jline.utils;

import java.io.IOException;
import java.io.OutputStream;

class NonBlockingPumpInputStream$NbpOutputStream extends OutputStream {
   final NonBlockingPumpInputStream this$0;

   private NonBlockingPumpInputStream$NbpOutputStream(NonBlockingPumpInputStream var1) {
      super();
      this.this$0 = var1;
   }

   public void write(int var1) throws IOException {
      this.this$0.write(new byte[]{(byte)var1}, 0, 1);
   }

   public void write(byte[] var1, int var2, int var3) throws IOException {
      this.this$0.write(var1, var2, var3);
   }

   public void flush() throws IOException {
      this.this$0.flush();
   }

   public void close() throws IOException {
      this.this$0.close();
   }

   NonBlockingPumpInputStream$NbpOutputStream(NonBlockingPumpInputStream var1, NonBlockingPumpInputStream$1 var2) {
      this(var1);
   }
}
