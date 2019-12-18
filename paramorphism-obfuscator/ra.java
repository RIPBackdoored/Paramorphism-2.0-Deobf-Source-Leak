package paramorphism-obfuscator;

import java.util.LinkedHashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Type;

public final class ra {
   @NotNull
   private final Type beg;
   @NotNull
   private final Map beh;

   @NotNull
   public final Type a() {
      return this.beg;
   }

   @NotNull
   public final Map b() {
      return this.beh;
   }

   public ra(@NotNull String var1) {
      super();
      this.beg = Type.getObjectType(var1);
      boolean var2 = false;
      Map var4 = (Map)(new LinkedHashMap());
      this.beh = var4;
   }
}
