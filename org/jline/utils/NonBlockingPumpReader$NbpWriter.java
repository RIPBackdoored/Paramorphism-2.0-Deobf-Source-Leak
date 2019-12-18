package org.jline.utils;

import java.io.IOException;
import java.io.Writer;

class NonBlockingPumpReader$NbpWriter extends Writer {
   final NonBlockingPumpReader this$0;

   private NonBlockingPumpReader$NbpWriter(NonBlockingPumpReader var1) {
      super();
      this.this$0 = var1;
   }

   public void write(char[] var1, int var2, int var3) throws IOException {
      this.this$0.write(var1, var2, var3);
   }

   public void flush() throws IOException {
      this.this$0.flush();
   }

   public void close() throws IOException {
      this.this$0.close();
   }

   NonBlockingPumpReader$NbpWriter(NonBlockingPumpReader var1, NonBlockingPumpReader$1 var2) {
      this(var1);
   }
}
