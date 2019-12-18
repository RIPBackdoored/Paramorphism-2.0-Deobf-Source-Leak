package org.jline.builtins;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

class Source$InputStreamSource$1 extends FilterInputStream {
   final Source$InputStreamSource this$0;

   Source$InputStreamSource$1(Source$InputStreamSource var1, InputStream var2) {
      super(var2);
      this.this$0 = var1;
   }

   public void close() throws IOException {
   }
}
