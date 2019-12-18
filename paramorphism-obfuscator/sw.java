package paramorphism-obfuscator;

import kotlin.Pair;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

public final class sw extends Lambda implements Function1 {
   public static final sw bhn = new sw();

   public Object invoke(Object var1) {
      return this.a((Pair)var1);
   }

   @NotNull
   public final String a(@NotNull Pair var1) {
      String var2 = (String)var1.component1();
      return StringsKt.dropLast(var2, ".class".length());
   }

   public sw() {
      super(1);
   }
}
