package paramorphism-obfuscator;

import java.util.Map.Entry;
import kotlin.Pair;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import site.hackery.paramorphism.api.resources.Resource;

public final class ty extends Lambda implements Function1 {
   public static final ty biv = new ty();

   public Object invoke(Object var1) {
      return this.a((Entry)var1);
   }

   @Nullable
   public final Pair a(@NotNull Entry var1) {
      boolean var5 = false;
      String var2 = (String)var1.getKey();
      var5 = false;
      Resource var3 = (Resource)var1.getValue();
      Pair var10000;
      if (var3 != null) {
         var5 = false;
         boolean var6 = false;
         boolean var8 = false;
         var10000 = new Pair(var2, var3);
      } else {
         var10000 = null;
      }

      return var10000;
   }

   public ty() {
      super(1);
   }
}
