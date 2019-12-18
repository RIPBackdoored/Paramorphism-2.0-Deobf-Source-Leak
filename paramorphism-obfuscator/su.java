package paramorphism-obfuscator;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

public final class su extends Lambda implements Function1 {
   public static final su bhm = new su();

   public Object invoke(Object var1) {
      return this.a((String)var1);
   }

   public final boolean a(@NotNull String var1) {
      return !StringsKt.endsWith$default(var1, ".class", false, 2, (Object)null);
   }

   public su() {
      super(1);
   }
}
