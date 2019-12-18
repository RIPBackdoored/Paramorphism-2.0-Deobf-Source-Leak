package paramorphism-obfuscator;

import java.util.LinkedHashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public final class oe {
   private final Map yj;

   public final int a(@NotNull String var1) {
      Map var2 = this.yj;
      boolean var3 = false;
      Object var4 = var2.get(var1);
      Object var10000;
      if (var4 == null) {
         boolean var5 = false;
         Integer var6 = 0;
         var2.put(var1, var6);
         var10000 = var6;
      } else {
         var10000 = var4;
      }

      return ((Number)var10000).intValue();
   }

   public final void a(@NotNull String var1, int var2) {
      this.yj.put(var1, var2);
   }

   public oe() {
      super();
      boolean var1 = false;
      Map var3 = (Map)(new LinkedHashMap());
      this.yj = var3;
   }
}
