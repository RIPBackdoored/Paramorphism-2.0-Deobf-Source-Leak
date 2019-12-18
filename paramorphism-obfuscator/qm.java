package paramorphism-obfuscator;

import java.util.LinkedHashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Type;

public final class qm {
   private final Type bdi;
   @NotNull
   private final Map bdj;

   public final Type a() {
      return this.bdi;
   }

   @NotNull
   public final Map b() {
      return this.bdj;
   }

   public qm(@NotNull String var1) {
      super();
      this.bdi = Type.getObjectType(var1);
      boolean var2 = false;
      Map var4 = (Map)(new LinkedHashMap());
      this.bdj = var4;
   }
}
