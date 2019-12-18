package org.jline.builtins;

import java.io.InputStream;

public class Source$StdInSource extends Source$InputStreamSource {
   public Source$StdInSource() {
      this(System.in);
   }

   public Source$StdInSource(InputStream var1) {
      super(var1, false, (String)null);
   }
}
