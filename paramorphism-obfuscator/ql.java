package paramorphism-obfuscator;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

public final class ql extends Lambda implements Function1 {
   public static final ql bdh = new ql();

   public Object invoke(Object var1) {
      return this.a(((Number)var1).intValue());
   }

   @NotNull
   public final Function1 a(int var1) {
      return (Function1)(new qn(var1));
   }

   public ql() {
      super(1);
   }
}
