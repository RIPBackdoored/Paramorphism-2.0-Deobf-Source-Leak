package org.jline.builtins;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;

public class Source$URLSource implements Source {
   final URL url;
   final String name;

   public Source$URLSource(URL var1, String var2) {
      super();
      this.url = (URL)Objects.requireNonNull(var1);
      this.name = var2;
   }

   public String getName() {
      return this.name;
   }

   public InputStream read() throws IOException {
      return this.url.openStream();
   }
}
