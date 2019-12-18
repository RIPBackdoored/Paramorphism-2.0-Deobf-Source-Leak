package org.jline.builtins;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class Source$PathSource implements Source {
   final Path path;
   final String name;

   public Source$PathSource(File var1, String var2) {
      this(((File)Objects.requireNonNull(var1)).toPath(), var2);
   }

   public Source$PathSource(Path var1, String var2) {
      super();
      this.path = (Path)Objects.requireNonNull(var1);
      this.name = var2;
   }

   public String getName() {
      return this.name;
   }

   public InputStream read() throws IOException {
      return Files.newInputStream(this.path);
   }
}
