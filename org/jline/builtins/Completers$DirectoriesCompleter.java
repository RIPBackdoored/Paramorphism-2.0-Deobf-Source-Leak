package org.jline.builtins;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;

public class Completers$DirectoriesCompleter extends Completers$FileNameCompleter {
   private final Path currentDir;

   public Completers$DirectoriesCompleter(File var1) {
      this(var1.toPath());
   }

   public Completers$DirectoriesCompleter(Path var1) {
      super();
      this.currentDir = var1;
   }

   protected Path getUserDir() {
      return this.currentDir;
   }

   protected boolean accept(Path var1) {
      return Files.isDirectory(var1, new LinkOption[0]) && super.accept(var1);
   }
}
