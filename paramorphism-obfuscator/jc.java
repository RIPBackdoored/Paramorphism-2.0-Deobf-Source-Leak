package paramorphism-obfuscator;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

public final class jc extends Lambda implements Function0 {
   public final iv oh;

   public Object invoke() {
      return this.a();
   }

   @NotNull
   public final jy a() {
      return new jy(iv.a(this.oh) + ".dictionaries", iv.b(this.oh));
   }

   public jc(iv var1) {
      super(0);
      this.oh = var1;
   }
}
