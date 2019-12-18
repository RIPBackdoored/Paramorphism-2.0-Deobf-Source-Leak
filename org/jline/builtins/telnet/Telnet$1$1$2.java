package org.jline.builtins.telnet;

import java.io.IOException;
import java.io.OutputStream;

class Telnet$1$1$2 extends OutputStream {
   final Telnet$1$1 this$2;

   Telnet$1$1$2(Telnet$1$1 var1) {
      super();
      this.this$2 = var1;
   }

   public void write(int var1) throws IOException {
      this.this$2.telnetIO.write(var1);
   }

   public void flush() throws IOException {
      this.this$2.telnetIO.flush();
   }
}
