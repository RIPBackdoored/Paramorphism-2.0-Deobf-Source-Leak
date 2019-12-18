package paramorphism-obfuscator;

import java.nio.file.Files;
import java.nio.file.Path;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

public final class tj extends Lambda implements Function0 {
   public final Path bib;

   public Object invoke() {
      return this.a();
   }

   public final byte[] a() {
      return Files.readAllBytes(this.bib);
   }

   public tj(Path var1) {
      super(0);
      this.bib = var1;
   }
}
