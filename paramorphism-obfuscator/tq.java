package paramorphism-obfuscator;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

public final class tq extends Lambda implements Function1 {
   public static final tq bii = new tq();

   public Object invoke(Object var1) {
      return this.a((String)var1);
   }

   @NotNull
   public final String a(@NotNull String var1) {
      return var1;
   }

   public tq() {
      super(1);
   }
}
