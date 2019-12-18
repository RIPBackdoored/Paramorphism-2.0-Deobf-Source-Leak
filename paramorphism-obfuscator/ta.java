package paramorphism-obfuscator;

import kotlin.Pair;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

public final class ta extends Lambda implements Function1 {
   public static final ta bhs = new ta();

   public Object invoke(Object var1) {
      return this.a((Pair)var1);
   }

   public final boolean a(@NotNull Pair var1) {
      String var2 = (String)var1.component1();
      return StringsKt.endsWith$default(var2, ".class", false, 2, (Object)null);
   }

   public ta() {
      super(1);
   }
}
