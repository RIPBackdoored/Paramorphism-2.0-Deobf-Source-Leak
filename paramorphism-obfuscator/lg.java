package paramorphism-obfuscator;

import java.util.Random;
import org.jetbrains.annotations.NotNull;

public final class lg {
   private final Random tu;

   @NotNull
   public final Random a() {
      return new Random(this.tu.nextLong());
   }

   public lg(long var1) {
      super();
      this.tu = new Random(var1);
   }
}
