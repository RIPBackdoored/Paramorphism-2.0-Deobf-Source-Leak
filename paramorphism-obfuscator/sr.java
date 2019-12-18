package paramorphism-obfuscator;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

public final class sr extends Lambda implements Function1 {
   public static final sr bhj = new sr();

   public Object invoke(Object var1) {
      return this.a((String)var1);
   }

   @NotNull
   public final String a(@NotNull String var1) {
      return StringsKt.endsWith$default(var1, ".class", false, 2, (Object)null) ? StringsKt.dropLast(var1, ".class".length()) : var1;
   }

   public sr() {
      super(1);
   }
}
