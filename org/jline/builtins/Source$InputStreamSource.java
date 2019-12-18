package org.jline.builtins;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class Source$InputStreamSource implements Source {
   final InputStream in;
   final String name;

   public Source$InputStreamSource(InputStream var1, boolean var2, String var3) {
      super();
      Objects.requireNonNull(var1);
      if (var2) {
         this.in = var1;
      } else {
         this.in = new Source$InputStreamSource$1(this, var1);
      }

      this.name = var3;
   }

   public String getName() {
      return this.name;
   }

   public InputStream read() throws IOException {
      return this.in;
   }
}
