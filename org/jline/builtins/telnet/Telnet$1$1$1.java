package org.jline.builtins.telnet;

import java.io.IOException;
import java.io.InputStream;

class Telnet$1$1$1 extends InputStream {
   final Telnet$1$1 this$2;

   Telnet$1$1$1(Telnet$1$1 var1) {
      super();
      this.this$2 = var1;
   }

   public int read() throws IOException {
      return this.this$2.telnetIO.read();
   }

   public int read(byte[] var1, int var2, int var3) throws IOException {
      int var4 = this.read();
      if (var4 >= 0) {
         var1[var2] = (byte)var4;
         return 1;
      } else {
         return -1;
      }
   }
}
