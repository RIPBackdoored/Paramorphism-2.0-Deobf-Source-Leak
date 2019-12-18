package paramorphism-obfuscator;

import com.fasterxml.jackson.databind.JsonNode;
import java.io.File;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ix {
   @NotNull
   public static File a(jn var0, @NotNull String var1) {
      return !(new File(var1)).isAbsolute() ? new File(var0.d(), var1) : new File(var1);
   }

   @Nullable
   public static String b(jn var0, @NotNull String var1) {
      JsonNode var10000 = var0.e(var1);
      return var10000 != null ? var10000.textValue() : null;
   }

   @Nullable
   public static File c(jn var0, @NotNull String var1) {
      String var10000 = var0.b(var1);
      File var8;
      if (var10000 != null) {
         String var2 = var10000;
         boolean var4 = false;
         boolean var5 = false;
         boolean var7 = false;
         var8 = var0.d(var2);
      } else {
         var8 = null;
      }

      return var8;
   }
}
